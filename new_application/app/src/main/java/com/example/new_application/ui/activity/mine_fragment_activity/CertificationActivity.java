package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.CertificationAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.user_info_activity.InformationActivity;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CertificationActivity extends BaseActivity {
    @BindView(R.id.server_group)
    Group server_group;
    @BindView(R.id.telegram_etv)
    EditText telegram_etv;
    @BindView(R.id.skype_etv)
    EditText skype_etv;
    @BindView(R.id.certification_type_tv)
    TextView certification_type_tv;
    @BindView(R.id.certification_type_iv)
    ImageView certification_type_iv;
    @BindView(R.id.problem_etv)
    EditText problem_etv;
    @BindView(R.id.limit_tv)
    TextView limit_tv;
    @BindView(R.id.reason_one_tv)
    TextView reason_one_tv;
    @BindView(R.id.reason_two_tv)
    TextView reason_two_tv;
    @BindView(R.id.chain_one_title_tv)
    TextView chain_one_title_tv;
    @BindView(R.id.chain_one_remark_tv)
    TextView chain_one_remark_tv;
    @BindView(R.id.chain_two_title_tv)
    TextView chain_two_title_tv;
    @BindView(R.id.chain_two_remark_tv)
    TextView chain_two_remark_tv;
    @BindView(R.id.chain_three_title_tv)
    TextView chain_three_title_tv;
    @BindView(R.id.chain_three_remark_tv)
    TextView chain_three_remark_tv;
    @BindView(R.id.personal_constrainLayout)
    ConstraintLayout personal_constrainLayout;
    @BindView(R.id.company_constrainLayout)
    ConstraintLayout company_constrainLayout;
    @BindView(R.id.personal_iv)
    ImageView personal_iv;
    @BindView(R.id.company_iv)
    ImageView company_iv;
    @BindView(R.id.certification_btn)
    Button certification_btn;
    @BindView(R.id.certification_recycler)
    RecyclerView certification_recycler;
    @BindView(R.id.type_tv)
    TextView type_tv;
    CertificationAdapter certificationAdapter;
    ArrayList<HelpChildEntity>certificationEntityArrayList = new ArrayList<>();
    ArrayList<String> selectorServerList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    public void getIntentData() {
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"??????????????????");
        server_group.setVisibility(View.VISIBLE);

        //1?????????,2?????????,3?????????,4???????????????
        String verifyStatus = Utils.getUserInfo().getVerifyStatus();
        if(verifyStatus.equals("1")||verifyStatus.equals("4")){
            certification_btn.setText("????????????????????????,????????????");
            certification_btn.setClickable(true);
        }else if(verifyStatus.equals("2")){
            certification_btn.setText("?????????");
            certification_btn.setClickable(false);
        }else if(verifyStatus.equals("3")){
            certification_btn.setText("?????????");
            certification_btn.setClickable(false);
        }
        String telegram = Utils.getUserInfo().getTelegram();
        if(StringMyUtil.isNotEmpty(telegram)){
            telegram_etv.setText(telegram);
            telegram_etv.setEnabled(false);
        }
        chain_one_title_tv.setText("?????????");
        chain_two_title_tv.setText("?????????");
        chain_three_title_tv.setText("?????????");
        chain_one_remark_tv.setText("????????????\n" +
                "????????????");
        chain_two_remark_tv.setText("????????????\n" +
                "????????????");
        chain_three_remark_tv.setText("????????????\n" +
                "????????????");
        reason_one_tv.setText("??????????????????????????????");
        reason_two_tv.setText("?????????????????????????????????????????????");
        type_tv.setText("????????????");
        server_group.setVisibility(View.GONE);
        initProblemRecycler();

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestServerType();
    }

    private void requestServerType() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.MINE_SERVER_TYPE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                selectorServerList.clear();
                if(Utils.isNotEmptyArray(result)){
                    List<MineServerEntity> mineServerEntityList = JSONArray.parseArray(result, MineServerEntity.class);
                    String content="";
                    for (int i = 0; i <mineServerEntityList.size() ; i++) {
                        content+=mineServerEntityList.get(i).getTwoLevelClassificationName()+",";
                        selectorServerList.add(mineServerEntityList.get(i).getTwoLevelClassificationId());
                    }
                    if(StringMyUtil.isEmptyString(content)){
                        certification_type_tv.setText("?????????");

                    }else {
                        certification_type_tv.setText(content.substring(0,content.length()-1));
                    }
                }

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    private void initProblemRecycler() {
        certificationAdapter = new CertificationAdapter(R.layout.certification_item,certificationEntityArrayList);
        certification_recycler.setLayoutManager(new LinearLayoutManager(this));
        certification_recycler.setAdapter(certificationAdapter);
        certificationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HelpChildEntity helpChildEntity = certificationEntityArrayList.get(position);
                helpChildEntity.setOpen(!helpChildEntity.isOpen());
                certificationAdapter.notifyItemChanged(position);
            }
        });
        requestProblemList();
    }

    private void requestProblemList() {
        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, 2+"", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HelpChildEntity> helpChildEntities = JSONArray.parseArray(result, HelpChildEntity.class);
                certificationEntityArrayList.addAll(helpChildEntities);
                certificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @OnClick({R.id.personal_constrainLayout,R.id.company_constrainLayout,R.id.certification_type_tv,R.id.certification_type_iv,R.id.certification_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.personal_constrainLayout:
                company_constrainLayout.setBackgroundResource(R.drawable.certification_un_check_shape);
                personal_constrainLayout.setBackgroundResource(R.drawable.certification_check_shape);
                company_iv.setVisibility(View.INVISIBLE);
                personal_iv.setVisibility(View.VISIBLE);
                break;
            case R.id.company_constrainLayout:
                company_constrainLayout.setBackgroundResource(R.drawable.certification_check_shape);
                personal_constrainLayout.setBackgroundResource(R.drawable.certification_un_check_shape);
                company_iv.setVisibility(View.VISIBLE);
                personal_iv.setVisibility(View.INVISIBLE);
                break;
            case R.id.certification_type_iv:
            case R.id.certification_type_tv:

                ChooseServerActivity.startAcy(CertificationActivity.this,selectorServerList);
                break;
            case R.id.certification_btn:
                if(selectorServerList.size()==0){
                    showtoast("?????????????????????");
                    return;
                }
                String  telegram = telegram_etv.getText().toString();
                if(StringMyUtil.isEmptyString(telegram)){
                    showtoast("?????????telegram");
                    return;
                }
                requestVerify();
                break;
            default:
                break;
        }
    }

    private void requestVerify() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("telegram",telegram_etv.getText().toString());
        data.put("twoLevelClassification", Utils.ArrayToStrWithComma(selectorServerList));
        data.put("verifyIntroduction",problem_etv.getText().toString());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.VERIFY, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("????????????");
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

}