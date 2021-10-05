package com.example.new_application.ui.fragment.message_fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.MainActivity;
import com.example.new_application.R;
import com.example.new_application.adapter.MessageAdapter;
import com.example.new_application.adapter.SystemMessageAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.HomeNoticeEntity;
import com.example.new_application.bean.MessageEntity;
import com.example.new_application.bean.MessageTabEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.GuaranteeOrderDetailsActivity;
import com.example.new_application.ui.activity.user_info_activity.BannerWebViewActivity;
import com.example.new_application.ui.pop.HomeAtyPop;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class MessageFragment extends BaseFragment {
    @BindView(R.id.message_recycler)
    RecyclerView message_recycler;
    @BindView(R.id.message_refresh)
    SmartRefreshLayout message_refresh;
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    MessageAdapter messageAdapter;
    ArrayList<MessageEntity> messageEntityArrayList = new ArrayList<>();
    int pageNo=1;
    MessageTabEntity messageTabEntity;
    static int MESSAGE_DETAILS_CODE = 100;

    SystemMessageAdapter systemMessageAdapter;
    ArrayList<HomeNoticeEntity> systemMessageList = new ArrayList<>();
    @Override
    protected void init(Bundle savedInstanceState) {
        getArgumentsData();
        initRecycler();
    }

    private void getArgumentsData() {
        messageTabEntity= (MessageTabEntity) getArguments().getSerializable("messageTabEntity");
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestMessageList(false,false);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    private void initRecycler() {
        if(messageTabEntity.getType()==1000){
            systemMessageAdapter = new SystemMessageAdapter(R.layout.message_reccler_item,systemMessageList);
            message_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            message_recycler.setAdapter(systemMessageAdapter);
            systemMessageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    HomeNoticeEntity homeNoticeEntity = systemMessageList.get(position);
                    BannerWebViewActivity.startActivity(getContext(),homeNoticeEntity.getContent(),homeNoticeEntity.getTheme());
                }
            });
        }else {
            messageAdapter = new MessageAdapter(R.layout.message_reccler_item, messageEntityArrayList);
            message_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            message_recycler.setAdapter(messageAdapter);
            messageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    MessageEntity messageEntity = messageEntityArrayList.get(position);
                    String type = messageEntity.getType();
                    if(type.equals("21")){
                        /**
                         * 帖子,跳转帖子详情
                         */
                        ServerDetailsActivity.startAty(MessageFragment.this,messageEntity.getId(),messageEntity.getOtherId());
                    }else if(type.equals("22")){
                        /**
                         * 担保跳转担保详情
                         */
                        GuaranteeOrderDetailsActivity.startAty(MessageFragment.this,messageEntity.getOtherId(),messageEntity.getId(),MESSAGE_DETAILS_CODE);
                    }else {
                        //直接请求更改未读状态
                        requestReadMessage(messageEntity.getId());
                    }

                    messageEntityArrayList.get(position).setIsRead("1");
                    messageAdapter.notifyItemChanged(position);
                }
            });
        }

        requestMessageList(false,false);

    }
    /**
     * 更换消息未读状态
     * @param messageId
     */
    private void requestReadMessage(String messageId) {
        HttpApiUtils.pathNormalRequest(getActivity(), MessageFragment.this, RequestUtils.MESSAGE_DETAILS, messageId, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess: 消息已读" );
                if(getActivity() instanceof MainActivity){
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.requestUnreadMessage();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    @Override
    protected void initRefresh() {
        super.initRefresh();

        RefreshUtils.initRefreshLoadMore(getContext(), message_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestMessageList(true,false);

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestMessageList(false,true);

            }
        });

        if(messageTabEntity.getType()==1000){
            message_refresh.setEnableLoadMore(false);
        }
    }

    private void requestMessageList(boolean isRefresh, boolean isLoadMore) {
        if(messageTabEntity.getType()==1000){
            requestSystemMessageList(isRefresh,isLoadMore);
        }else {
            requestOtherMessageList(isRefresh, isLoadMore);
        }

    }
    private void requestSystemMessageList(boolean isRefresh, boolean isLoadMore) {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.SYSTEM_NOTICE, "3", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HomeNoticeEntity> homeNoticeEntities = JSONArray.parseArray(result, HomeNoticeEntity.class);
                RefreshUtils.succse(pageNo,message_refresh,loading_linear,nodata_linear,homeNoticeEntities.size(),isLoadMore,isRefresh,systemMessageList);
                systemMessageList.addAll(homeNoticeEntities);
                systemMessageAdapter.notifyDataSetChanged();
            }



            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,message_refresh);
            }
        });
    }
    private void requestOtherMessageList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("type",messageTabEntity.getType());
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.MESSAGE_LIST, data, loading_linear, error_linear, reload_tv, message_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MessageEntity> messageEntities = JSONArray.parseArray(result, MessageEntity.class);
                RefreshUtils.succse(pageNo,message_refresh,loading_linear,nodata_linear,messageEntities.size(),isLoadMore,isRefresh,messageEntityArrayList);
                messageEntityArrayList.addAll(messageEntities);
                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,message_refresh);
            }
        });
    }


    public static MessageFragment newInstance (int position, MessageTabEntity messageTabEntity){
        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        bundle.putSerializable("messageTabEntity",messageTabEntity);
        messageFragment.setArguments(bundle);
        return messageFragment;
    }
}