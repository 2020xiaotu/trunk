package com.example.red_pakege.activity.red_game_activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelper;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.game_group_adapter.RedPakegeListRecycleAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RedPakegeListModel;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.RefreshUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import okhttp3.Headers;
public class RedPakegeListFragment extends BaseFragment {
        private RecyclerView recyclerView;
        private RefreshLayout refreshLayout;
        private RedPakegeListRecycleAdapter redPakegeListRecycleAdapter;
        private ArrayList<RedPakegeListModel>redPakegeListModelArrayList = new ArrayList<>();
        private int typeId;
        private int game;
        private SharedPreferenceHelper sp = new SharedPreferenceHelperImpl();
        private BigDecimal amount;
        private BigDecimal commission;

        private ConstraintLayout loadingLinear;
        private LinearLayout nodataLinear;
        private LinearLayout errorLinear;
        private TextView reloadTv;
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_pakege_list, container, false);
        typeId = getArguments().getInt("typeId");
        game =getArguments().getInt("parentId");
        bindView(view);
        initRefresh();
        initRecycle();

        return view;
    }

    private void initRecycle() {
        redPakegeListRecycleAdapter =new RedPakegeListRecycleAdapter(redPakegeListModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(redPakegeListRecycleAdapter);
        //获取房间列表
        getRoomList(false);
        redPakegeListRecycleAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RedPakegeListModel redPakegeListModel = redPakegeListModelArrayList.get(position);
                //请求加入聊天室接口 , 并跳转聊天室界面
                    HttpApiUtils.normalRequest(getContext(), RequestUtil.USER_MONEY, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
 /*                           System.out.println(result);
                            JSONObject data = JSONObject.parseObject(result).getJSONObject("data");
                             amount = data.getBigDecimal("amount");//用户余额
                            System.out.println("amount "+ amount);
                             commission = data.getBigDecimal("commission");//用户佣金
                            BigDecimal userWalletMinPrice = redPakegeListModel.getUserWalletMinPrice();
                            System.out.println("userWalletMinPrice  = "+userWalletMinPrice);
                            if(amount!=null&&amount.compareTo(userWalletMinPrice)==1){//用户余额大于房间最低金额*/
                                joinRoom(redPakegeListModel);
                     /*       }else {
                                showtoast("穷逼,没钱就滚");
                            }*/
                        }

                        @Override
                        public void onFaild(String msg) {
                        }
                    });


            }
        });
    }
    private void joinRoom(RedPakegeListModel redPakegeListModel) {

//                RongIM.getInstance().startConversation(getContext(), Conversation.ConversationType.GROUP , "10",redPakegeListModel.getName() );
        HashMap<String, Object> data = new HashMap<>();
        final int roomId = redPakegeListModel.getId();
        data.put("roomId", roomId);
        HttpApiUtils.normalRequest(getContext(), RequestUtil.JOIN_ROOM, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getString("message").equals("操作成功")){
                    //请求加入房间成功, 进入聊天界面
                    Bundle bundle = new Bundle();
                    //将game传入,用户会话界面中判断显示哪种游戏的ui和逻辑
                    bundle.putInt("typeId",typeId);
                    bundle.putInt("game",game);
                    RongIM.getInstance().startConversation(getContext(), Conversation.ConversationType.GROUP , roomId +"",redPakegeListModel.getName(),bundle);
//                }
            }

            @Override
            public void onFaild(String msg) {
            }
        });
    }

    private void getRoomList(boolean isRefresh) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("typeId",typeId);
        data.put("game",game);
        HttpApiUtils.showLoadRequest(getContext(),this, RequestUtil.GET_RED_ROOM, data,loadingLinear,errorLinear,reloadTv, (View) recyclerView,false,isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int size = jsonArray.size();
                RefreshUtil.succse(1,refreshLayout,loadingLinear,nodataLinear,size,false,isRefresh,redPakegeListModelArrayList);
                for (int i = 0; i < size; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Integer id = object.getInteger("id");
                    String name = object.getString("name");
                    String image = object.getString("image");
                    String notice = object.getString("notice");
                    BigDecimal userWalletMinPrice = object.getBigDecimal("userWalletMinPrice");
                    redPakegeListModelArrayList.add(new RedPakegeListModel(id,image,name,notice,userWalletMinPrice));
                    //xuhuiping
                }
                redPakegeListRecycleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFaild(String msg) {
                RefreshUtil.fail(isRefresh,false,1,refreshLayout);
            }
        });
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        getRoomList(false);
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtil.initRefreshLoadMore(new SoftReference<>(getContext()), refreshLayout, new RefreshUtil.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getRoomList(true);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });
    }

    private void bindView(View view) {
        recyclerView=view.findViewById(R.id.red_pakege_list_recycle);
        refreshLayout=view.findViewById(R.id.red_pakege_list_refresh);
        loadingLinear=view.findViewById(R.id.loading_linear);
        errorLinear=view.findViewById(R.id.error_linear);
        nodataLinear=view.findViewById(R.id.nodata_linear);
        reloadTv=view.findViewById(R.id.reload_tv);
    }

    public static RedPakegeListFragment newInstance(int position,int typeId,int parentId){
        RedPakegeListFragment redPakegeListFragment = new RedPakegeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putInt("typeId",typeId);
        bundle.putInt("parentId",parentId);
        redPakegeListFragment.setArguments(bundle);
        return redPakegeListFragment;
    }
}
