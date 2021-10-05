package com.example.red_pakege.activity;

import android.widget.Button;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxUtil;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.net.entity.GameListEntity;
import com.example.red_pakege.net.entity.ParamYuebaoEntity;
import com.example.red_pakege.net.entity.YuebaoDetailEntity;


import butterknife.BindView;
import okhttp3.Headers;
import okhttp3.ResponseBody;

/**
 * created  by ganzhe on 2019/11/15.
 */
public class TestActivity extends BaseActivity {



    @BindView(R.id.btn_test)
    Button btn_test;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initEventAndData() {
        btn_test.setOnClickListener(v -> {
           /*HttpApiImpl.getInstance()
                   .getEntityTest()
                   .compose(RxUtil.rxSchedulerHelper(this, true))
                   .subscribe(new BaseEntityObserver<GameListEntity>(){
                       @Override
                       public void onSuccess(GameListEntity response) {
                           LogUtils.e(""+response);
                       }
                       @Override
                       public void onFail(String msg) {
                           LogUtils.e(msg);
                       }
                   });*/
            ParamYuebaoEntity paramYuebaoEntity = new ParamYuebaoEntity();
            paramYuebaoEntity.setAmountType(0);
            paramYuebaoEntity.setSign("123456789");
            paramYuebaoEntity.setSource("0");
            paramYuebaoEntity.setToken("123456");
            paramYuebaoEntity.setUser_id("654321");
            HttpApiImpl.getInstance()
                    .PostYuebaoDetail(paramYuebaoEntity)
                    .compose(RxUtil.rxSchedulerHelper(this, true))
                    .subscribe(new BaseEntityObserver<YuebaoDetailEntity>() {
                        @Override
                        public void onSuccess(YuebaoDetailEntity entity) {
                            LogUtils.e("" + entity);
                        }
                        @Override
                        public void onFail(String msg) {
                            LogUtils.e("" + msg);
                        }
                    });
          /*  HttpApiUtils.normalRequest(this,"gameClass/getAllGameClass",null,new HttpApiUtils.OnRequestLintener(){
                @Override
                public void onSuccess(String result, Headers headers) {
                    LogUtils.e(result);
                }
                @Override
                public void onFaild(String msg) {
                    LogUtils.e(msg);
                }
            });*/
        });
    }
}
