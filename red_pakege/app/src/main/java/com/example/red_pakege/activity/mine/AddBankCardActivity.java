package com.example.red_pakege.activity.mine;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.BuildConfig;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.config.ModifyType;
import com.example.red_pakege.model.BankInfoModel;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.util.JsonUtil;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.widget.MyChoiceBankPop;
import com.example.red_pakege.widget.TextViewDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import butterknife.BindView;

import static com.example.red_pakege.config.Constants.ModyTYPE;
import static com.example.red_pakege.util.ActionBarUtil.initMainActionbar;

public class AddBankCardActivity extends BaseActivity {

    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.iv_commonbar_back)
    ImageView mBack;
    @BindView(R.id.tv_commonbar_title)
    TextView mTitle;

    @BindView(R.id.tv_addbankcard_cardtype)
    TextViewDrawable tv_addbankcard_cardtype;
    @BindView(R.id.tv_addbankcard_commit)
    TextView tv_addbankcard_commit;
    @BindView(R.id.et_addbankcard_cardname)
    EditText et_addbankcard_cardname;
    @BindView(R.id.et_addbankcard_cardnum)
    EditText et_addbankcard_cardnum;
    @BindView(R.id.ed_addbankcard_cardaddr)
    EditText ed_addbankcard_zhihang;
    @BindView(R.id.ed_addbankcard_paypasswd)
    EditText ed_addbankcard_paypasswd;


    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;
    UserEntity userEntity;



    private List<BankInfoModel> bankInfoModelList = new ArrayList<>();
    private int cardPosition = 0;
    private Drawable drawabright;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addbankcard;
    }

    @Override
    protected void initToolbar() {
        initMainActionbar(this, mToolbar);
        mTitle.setText("添加修改银行卡");
    }

    @Override
    protected void initView() {

        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        userEntity = JsonUtil.Str2UserEntity(mSharedPreferenceHelperImpl.getUserInfo());

        //获取银行列表信息
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        String str = JsonUtil.getJson(this,"banks.json");
        LogUtils.e(str);
        TreeMap<String, String> treeMap = gson.fromJson(str, TreeMap.class);
        List<String> list_key = new ArrayList(treeMap.keySet());
        List<String> list_value = new ArrayList(treeMap.values());
        for (int i=0;i<list_key.size();i++){
            bankInfoModelList.add(new BankInfoModel(list_key.get(i),list_value.get(i)));
        }
        /* List<BankInfoModel> list = map.entrySet().parallelStream().sorted(Comparator.comparing(e -> e.getKey()))
                .map(e -> new BankInfoModel(e.getKey(), e.getValue())).collect(Collectors.toList());*/
    }


    @Override
    protected void initEventAndData() {
        ChoiceBankType();
        mBack.setOnClickListener(v -> onBackPressedSupport());
        tv_addbankcard_commit.setOnClickListener(v -> ReqModifyBankCard());
    }




    private void ChoiceBankType(){

        drawabright = getResources().getDrawable(R.drawable.gray_right_arrow);
        tv_addbankcard_cardtype.setCompoundDrawablesWithIntrinsicBounds(null,null,drawabright,null);

        tv_addbankcard_cardtype.setOnClickListener(v -> {
            MyChoiceBankPop  myChoiceBankPop = new MyChoiceBankPop(this,bankInfoModelList,tv_addbankcard_cardtype.getText().toString());
            myChoiceBankPop.showPopupWindow(tv_addbankcard_cardtype);
            myChoiceBankPop.setOnBankItemClickListener(new MyChoiceBankPop.OnBankItemClickListener() {
                @Override
                public void onBankItemClick(int position) {
                    LogUtils.e("item:"+position);
                    cardPosition = position;
                    tv_addbankcard_cardtype.setText(bankInfoModelList.get(position).getName());
                    int ResId = getResources().getIdentifier(bankInfoModelList.get(position).getResId(),"drawable", BuildConfig.APPLICATION_ID);
                    Drawable drawableLeft = getResources().getDrawable(ResId);
                    tv_addbankcard_cardtype.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null,drawabright,null);
                }
            });
        });
    }


    private boolean CheckEdit(){
        String name = et_addbankcard_cardname.getText().toString();
        String num = et_addbankcard_cardnum.getText().toString();
        String type = tv_addbankcard_cardtype.getText().toString();
        String addr = ed_addbankcard_zhihang.getText().toString();
        String paypasswd = ed_addbankcard_paypasswd.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtils.showToast("请输入持卡人姓名");
            return false;
        }
        if (TextUtils.isEmpty(num)||num.length()<15||num.length()>20){
            ToastUtils.showToast("卡号不符合规则");
            return false;
        }
        if (type.equals("请选择银行")){
            ToastUtils.showToast("请选择银行卡");
            return false;
        }
        if (TextUtils.isEmpty(addr)){
            ToastUtils.showToast("请输入开户支行");
            return false;
        }
        if (TextUtils.isEmpty(paypasswd)){
            ToastUtils.showToast("请输入支付密码");
            return false;
        }
        return true;
    }


    private void ReqModifyBankCard(){
       if (!CheckEdit()){
            return;
       }
        Map map = new HashMap();
        map.put("bankAccountName",et_addbankcard_cardname.getText().toString());
        map.put("bankCard",et_addbankcard_cardnum.getText().toString());
        map.put("bankName",tv_addbankcard_cardtype.getText().toString());
        map.put("bankDot",ed_addbankcard_zhihang.getText().toString());
        map.put("paypassword",ed_addbankcard_paypasswd.getText().toString());

        map.put(ModyTYPE, ModifyType.BANKCARD.getIndex());

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseEntityObserver<BaseEntity>(){
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.toString());
                        closeLoading();
                        ToastUtils.showToast("修改银行卡信息成功");
                        UserEntity.DataBean.MemberInfoBean userInfo = userEntity.getData().getMemberInfo();

                        userInfo.setBankAccountName(et_addbankcard_cardname.getText().toString());
                        userInfo.setBankCard(et_addbankcard_cardnum.getText().toString());
                        userInfo.setBankName(tv_addbankcard_cardtype.getText().toString());
                        userInfo.setBankDot(ed_addbankcard_zhihang.getText().toString());

                        userEntity.getData().setMemberInfo(userInfo);
                        mSharedPreferenceHelperImpl.setUserInfo(JsonUtil.UserEntity2Str(userEntity));
                        onBackPressedSupport();
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        showErrorMsg(msg);
                    }

                    @Override
                    protected void onRequestStart() {
                        showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        closeLoading();
                    }
                });

    }




}
