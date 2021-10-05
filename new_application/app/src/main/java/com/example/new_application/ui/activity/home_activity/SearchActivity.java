package com.example.new_application.ui.activity.home_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ClassificationDetailsAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.SaveLiveSearchUtil;
import com.example.new_application.utils.Utils;
import com.example.new_application.utils.flowLayout.FlowLayout;
import com.example.new_application.utils.flowLayout.TagAdapter;
import com.example.new_application.utils.flowLayout.TagFlowLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_history_relativeLayout)
    RelativeLayout search_history_relativeLayout;
    @BindView(R.id.search_delete_linear)
    LinearLayout search_delete_linear;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout id_flowlayout;
    @BindView(R.id.search_recycle)
    RecyclerView search_recycle;
    @BindView(R.id.search_refresh)
    SmartRefreshLayout search_refresh;
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    //搜索记录list
    private List<String> searchHistoryList;
    //历史记录保存util
    private SaveLiveSearchUtil saveSearchUtil =new SaveLiveSearchUtil();
    //流式布局的字item
    private TextView historyItemTv;
    private ClassificationDetailsAdapter serverHallAdapter;
    ArrayList<ClassificationDetailsEntity> serverHallList = new ArrayList<>();
    private int pageNo = 1;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //搜索记录
        initSearchHistory();
        //热门搜索
//        initHotSearchRecy();
        //搜索结果
        initRecycle();
        initRefresh();

        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    search_tv.performClick();
                }
                return false;
            }
        });
    }

    private void initRecycle() {
        serverHallAdapter = new ClassificationDetailsAdapter(R.layout.classification_details_item, serverHallList);
        search_recycle.setLayoutManager(new LinearLayoutManager(this));
        search_recycle.setAdapter(serverHallAdapter);
        serverHallAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationDetailsEntity classificationDetailsEntity = serverHallList.get(position);
                ServerDetailsActivity.startAty(SearchActivity.this,classificationDetailsEntity.getId());
            }
        });
    }


    private void initRefresh() {
        RefreshUtils.initRefreshLoadMore(this, search_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestServerHallList(true,false);

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestServerHallList(false,true);
            }
        });
    }

    /*
搜索历史flowLayout
 */
        private void initSearchHistory() {
        id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String s = searchHistoryList.get(position);
                search_edit.setText(s);
                search_edit.setSelection(search_edit.getText().toString().length());
                clickSearchBtn();
                return true;
            }
        });
        getCacheData();
    }
    /*
点击搜索按钮后的系列操作
 */
    private void clickSearchBtn() {
        //隐藏显或示电影recycleView 搜索历史  热门搜索
        if (search_refresh.getVisibility() != View.VISIBLE) {
            //电影列表外层的刷新控件(隐藏刷新控件,列表也会隐藏)
            search_refresh.setVisibility(View.VISIBLE);
            search_recycle.setVisibility(View.VISIBLE);
            //历史记录列表
            id_flowlayout.setVisibility(View.GONE);
            //热门搜索
            //历史记录提示框(包括删除图标)
            search_history_relativeLayout.setVisibility(View.GONE);
            //热门搜索提示框
        }
        //收回软键盘
        Utils.hideSoftKeyBoard(this);
        //储存搜索记录
        saveSearchUtil.saveSearchHistory(search_edit.getText().toString());
        //取出新的搜索记录
        getCacheData();
        pageNo = 1;
        //请求搜索结果
        requestServerHallList( false, false);
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        pageNo=1;
        requestServerHallList(false,false);
    }

    public void requestServerHallList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("latest","-1");
        data.put("myCollect","0");
        String title = search_edit.getText().toString();
        if(StringMyUtil.isNotEmpty(title)){
            data.put("title",title);
        }
        HttpApiUtils.wwwShowLoadRequest(this, null, RequestUtils.SERVER_HALL_LIST, data, loading_linear, error_linear, reload_tv, search_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                RefreshUtils.succse(pageNo,search_refresh,loading_linear,nodata_linear,classificationDetailsEntityList.size(),isLoadMore,isRefresh,serverHallList);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 官方显示官方认证
                    String priceType = classificationDetailsEntity.getPriceType();//价格类型(1：一口价，2：范围价格，3：议价)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//标签id. 逗号拼接,用于筛选下面的标签
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    serverHallList.add(classificationDetailsEntity);
                    serverHallAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,search_refresh);
            }
        });
    }



    /*
取出最新的聊天记录
 */
    private void getCacheData() {
        searchHistoryList = saveSearchUtil.getSearchHistory();
        //通知更新ui
        handler.sendEmptyMessage(1);
    }
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //点击搜索后,更新历史记录的ui
                    id_flowlayout.setAdapter(new TagAdapter<String>(searchHistoryList) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            historyItemTv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.history_fralayout_item,
                                    id_flowlayout, false);
                            historyItemTv.setText(s);
                            return historyItemTv;
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };
    @OnClick({R.id.search_tv,R.id.search_delete_linear,R.id.search_back_linear})
    public void  onClick(View view){
        switch (view.getId()){
            //点击搜索
            case R.id.search_tv:
                clickSearchBtn();
                break;
            case R.id.search_delete_linear:
                //点击删除聊天记录.,将sp中的聊天记录设为空,取出新缓存后更新ui
                sp.setSearchCache("");
                getCacheData();
                showtoast("搜索记录删除成功");
                break;
            case R.id.search_back_linear:
                finish();
                break;
            default:
                break;
        }
    }
}