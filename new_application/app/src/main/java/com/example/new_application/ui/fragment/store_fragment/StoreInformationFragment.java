package com.example.new_application.ui.fragment.store_fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.adapter.ServerLabelAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ServerLabelEntity;
import com.example.new_application.bean.StoreDetailsEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.SearchActivity;
import com.example.new_application.ui.activity.home_activity.StoreInformationActivity;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.flowLayout.FlowLayout;
import com.example.new_application.utils.flowLayout.TagAdapter;
import com.example.new_application.utils.flowLayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺资料
 */
public class StoreInformationFragment extends BaseFragment {
    @BindView(R.id.top_tip_constraintLayout)
    ConstraintLayout top_tip_constraintLayout;
    @BindView(R.id.top_tip_iv)
    ImageView top_tip_iv;
    @BindView(R.id.top_tip_tv)
    TextView top_tip_tv;
    @BindView(R.id.top_tip_content_tv)
    TextView top_tip_content_tv;
    @BindView(R.id.about_us_content_tv)
    TextView about_us_content_tv;
    @BindView(R.id.show_more_linear)
    LinearLayout show_more_linear;
    @BindView(R.id.show_more_tv)
    TextView show_more_tv;
    @BindView(R.id.show_more_iv)
    ImageView show_more_iv;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout id_flowlayout;
    @BindView(R.id.no_label_tv)
    TextView no_label_tv;
    @BindView(R.id.official_evaluate_tv)
    TextView official_evaluate_tv;
    @BindView(R.id.process_content_tv)
    TextView process_content_tv;
    @BindView(R.id.top_tip_details_tv)
    TextView top_tip_details_tv;
    @BindView(R.id.commission_tv)
    TextView commission_tv;
    ArrayList<ServerLabelEntity>serverLabelEntityArrayList = new ArrayList<>();

    boolean isShowMore = true;
    StoreInformationActivity storeInformationActivity;
    @Override
    protected void init(Bundle savedInstanceState) {
        if(getActivity() instanceof StoreInformationActivity){
            storeInformationActivity = (StoreInformationActivity) getActivity();
        }
//        initLabelRecycler();
        requestServerInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_store_inoformation;
    }

    public static StoreInformationFragment newInstance(int position){
        StoreInformationFragment storeInformationFragment = new StoreInformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        storeInformationFragment.setArguments(bundle);
        return storeInformationFragment;
    }

    private void requestServerInfo() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("memberId",storeInformationActivity.getMemberInfoVoBean().getId());
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.STORE_DETAILS, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                StoreDetailsEntity storeDetailsEntity = JSONObject.parseObject(result, StoreDetailsEntity.class);
                StoreDetailsEntity.MemberInfoVoBean memberInfoVo = storeDetailsEntity.getMemberInfoVo();
                String earnestMoney = memberInfoVo.getEarnestMoney();
                if(StringMyUtil.isNotEmpty(earnestMoney)&&!earnestMoney.equals("0")&&!earnestMoney.equals("0.00")){
                    commission_tv.setVisibility(View.VISIBLE);
                    commission_tv.setText(",保障金¥"+earnestMoney+"元");
                }else {
                    commission_tv.setVisibility(View.GONE);
                }
                if(memberInfoVo.getVerifyStatus().equals("3")){
                    top_tip_tv.setText("商家已加入诚信保障计划");
                    top_tip_tv.setTextColor(Color.parseColor("#38A447"));
                    top_tip_content_tv.setTextColor(Color.parseColor("#38A447"));
                    top_tip_details_tv.setTextColor(Color.parseColor("#38A447"));
                    top_tip_iv.setImageResource(R.drawable.fwbz_yibz);
                    top_tip_constraintLayout.setBackgroundResource(R.drawable.store_tip_chek_linear_shape);
                }else {
                    top_tip_tv.setText("该商家未加入诚信保计划,无任何保证金。");
                    top_tip_tv.setTextColor(Color.parseColor("#707070"));
                    top_tip_iv.setImageResource(R.drawable.fwbz_wbz);
                    top_tip_content_tv.setTextColor(Color.parseColor("#707070"));
                    top_tip_details_tv.setTextColor(Color.parseColor("#707070"));
                    top_tip_constraintLayout.setBackgroundResource(R.drawable.store_tip_unchek_linear_shape);
                    top_tip_content_tv.setText("1、该商家与平台无任何业务关系，任何线下交易与平台无任何关系！\n2、您也可以付费委托平台担保合作或者委托平台为您匹配相关资源。\n3、飞鸽网为半开放式交易平台，所有用户均可免费发布需求与服务！");
                }
                String details = memberInfoVo.getDetails();
                if(StringMyUtil.isNotEmpty(details)){
                    JSONObject jsonObject = JSONObject.parseObject(details);
                    String cxbz = jsonObject.getString("cxbz");//诚信保障
                    String xylc = jsonObject.getString("xylc");//协议流程
                    String aboutUs = jsonObject.getString("aboutUs");//关于我们
                    String officialEvaluate = jsonObject.getString("officialEvaluate");//官方评测
                    if(StringMyUtil.isNotEmpty(cxbz)){
                        if(memberInfoVo.getVerifyStatus().equals("3")){
                            top_tip_content_tv.setText(Html.fromHtml(cxbz));
                        }
                        top_tip_details_tv.setVisibility(View.VISIBLE);
                    }else {
                        top_tip_details_tv.setVisibility(View.GONE);
                    }
                    if(StringMyUtil.isEmptyString(xylc)){
                        /**
                         * 没有协议流畅
                         */
                        process_content_tv.setText("与平台无任何合作协议流程，请谨慎交易，有任何问题均与平台无关！");
                    }else {
                        process_content_tv.setText(Html.fromHtml(xylc));
                    }
                    if(StringMyUtil.isNotEmpty(aboutUs)){
                        about_us_content_tv.setText(Html.fromHtml(aboutUs));
                    }
                    if(StringMyUtil.isEmptyString(officialEvaluate)){
                        official_evaluate_tv.setText("无任何官方评测,请谨慎交易,有任何问题均与平台无关!");
                    }else {
                        official_evaluate_tv.setText(Html.fromHtml(officialEvaluate));

                    }
                }
                /**
                 * 服务标签
                 */
                String label = memberInfoVo.getLabel();
                if(StringMyUtil.isNotEmpty(label)){
                    String[] split = label.split(",");
                    for (int i = 0; i < split.length; i++) {
                        serverLabelEntityArrayList.add(new ServerLabelEntity(split[i]));
                    }
//                    serverLabelAdapter.notifyDataSetChanged();
                    initLabelRecycler();
                }


            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initLabelRecycler() {
/*        serverLabelAdapter = new ServerLabelAdapter(R.layout.server_label_item,serverLabelEntityArrayList);
        label_recycler.setLayoutManager(new GridLayoutManager(getContext(),4));
        label_recycler.setAdapter(serverLabelAdapter);*/

        id_flowlayout.setAdapter(new TagAdapter<ServerLabelEntity>(serverLabelEntityArrayList) {
            @Override
            public View getView(FlowLayout parent, int position, ServerLabelEntity serverLabelEntity) {
               TextView server_label_name_tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.server_details_label_item,
                        id_flowlayout, false);
                server_label_name_tv.setText(serverLabelEntity.getName());
                return server_label_name_tv;
            }
        });

    }

    @OnClick({R.id.show_more_linear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.show_more_linear:
                if(isShowMore){
                    show_more_iv.setImageResource(R.drawable.gywm_djsq);
                    show_more_tv.setText("点击收起");
                    about_us_content_tv.setMaxLines(10000);
                }else {
                    show_more_iv.setImageResource(R.drawable.gywm_djzk);
                    show_more_tv.setText("点击展开");
                    about_us_content_tv.setMaxLines(4);
                }
                isShowMore=!isShowMore;
                break;
            default:
                break;
        }
    }
}