package com.example.red_pakege.net.api;

import com.cambodia.zhanbang.rxhttp.net.common.RetrofitFactory;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.net.entity.DrawRecordEntity;
import com.example.red_pakege.net.entity.GameListEntity;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.example.red_pakege.net.entity.ParamYuebaoEntity;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.net.entity.RechargeRecordEntity;
import com.example.red_pakege.net.entity.RemittanceEntity;
import com.example.red_pakege.net.entity.YuebaoDetailEntity;
import com.example.red_pakege.net.entity.YuebaoInfoEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.cambodia.zhanbang.rxhttp.net.common.RxLibConstants.SP_TOKEN;
import static com.cambodia.zhanbang.rxhttp.net.common.RxLibConstants.USER_ID;
import static com.example.red_pakege.util.JsonUtil.Entity2JsonRequestBody;
import static com.example.red_pakege.util.JsonUtil.Map2JsonRequestBody;


/**
 * created  by ganzhe on 2019/11/14.
 */
public class HttpApiImpl {

    public IHttpApi iHttpApiT;
    private SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;
    private int user_id;
    private String token;

    private HttpApiImpl() {
        iHttpApiT = RetrofitFactory.getInstance().create(IHttpApi.class);

    }
    public HttpApiImpl(String base_url) {
        iHttpApiT = RetrofitFactory.getInstance().create(base_url,IHttpApi.class);

    }

    public static HttpApiImpl  getInstance() {
        return HttpApiImplHolder.S_INSTANCE;
    }

    private static class HttpApiImplHolder {
        private static final HttpApiImpl S_INSTANCE = new HttpApiImpl();
    }



    //=============================================API????????????========================

    public Observable<GameListEntity> getEntityTest() {
        return iHttpApiT.getEntityTest();
    }
    public Observable<Response<ResponseBody>> getStringTest() {
        return iHttpApiT.getStringTest();
    }

    public Observable<YuebaoDetailEntity> PostYuebaoDetail(ParamYuebaoEntity entity){
        return iHttpApiT.PostYuebaoDetail(Entity2JsonRequestBody(entity));
    }
    public Observable<YuebaoInfoEntity> PostYuebaoInfo(){
        Map map = getDefMap();
        return iHttpApiT.PostYuebaoInfo(Map2JsonRequestBody(map));
    }
    public Observable<BaseEntity> PostOutYueBao(ParamYuebaoEntity entity){
        return iHttpApiT.PostOutYueBao(Entity2JsonRequestBody(entity));
    }

    //=========================================================================

    /**
     * ?????????????????? map
     * token userId
     * @return
     */
    public Map getDefMap(){
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        Gson gson = new GsonBuilder().serializeNulls().create();
        UserEntity userEntity = gson.fromJson(mSharedPreferenceHelperImpl.getUserInfo(),UserEntity.class);
        if (null!=userEntity){
            user_id = userEntity.getData().getMemberInfo().getId();
        }
        token = mSharedPreferenceHelperImpl.getToken();

        Map map = new HashMap();
        map.put("sign","123456789");
        map.put("source","0");
        map.put(SP_TOKEN,token);
        map.put(USER_ID,user_id);
        return map;
    }

    //=======================================??????=====================================

    /**
     * ??????
     * @param phone
     * @param passwd
     * @return
     */
    public Observable<UserEntity> PostLogin(String phone,String passwd){

        Map map = new HashMap();
        map.put("sign","123456789");
        map.put("source","0");
        map.put("phone",phone);
        map.put("password",passwd);
        return iHttpApiT.PostLogin(Map2JsonRequestBody(map));
    }
    /**
     * ??????
     * @param map
     * @return
     */
    public Observable<UserEntity> PostRegister(Map map){
        return iHttpApiT.PostRegister(Map2JsonRequestBody(map));
    }

    /**
     * ??????????????????
     * @return
     */
    public Observable<MemberMoneyEntity> PostMemberMoney(){
        Map map = getDefMap();
        return iHttpApiT.PostMemberMoney(Map2JsonRequestBody(map));
    }

    /**
     * ????????????
     * @return
     */
    public Observable<UserEntity> PostUserInfo(){

        Map map = getDefMap();
        return iHttpApiT.PostUserInfo(Map2JsonRequestBody(map));
    }

    /**
     * ????????????????????????
     * type ??? 1 ????????????;2 ????????????;3 ??????????????????;4 ????????????;5 ??????????????????;6 ??????????????????;7 ??????????????????
     * @param paraMap
     * @return
     */
    public Observable<BaseEntity> PostModifyUserInfo(Map paraMap){
        Map map = getDefMap();
        map.remove(SP_TOKEN);
        map.putAll(paraMap);

        return iHttpApiT.PostModifyUserInfo(Map2JsonRequestBody(map));
    }
    //===============================APP??????=============================


    /**
     * ??????????????????
     * @return
     */
    public Observable<RechargeEntreyEntity> PostRechargeEntry(){
        Map map = getDefMap();
        return iHttpApiT.PostRechargeEntry(Map2JsonRequestBody(map));
    }

    /**
     * ??????????????????
     * @param bank_id
     * @param name
     * @param price
     * @return
     */
    public Observable<RemittanceEntity> PostRemittance(String bank_id, String name, String price){
        Map map = getDefMap();
        map.put("bank_id",bank_id);
        map.put("name",name);
        map.put("price",price);
        return iHttpApiT.PostRemittance(Map2JsonRequestBody(map));
    }

    /**
     * ??????????????????

     * @return
     */
    public Observable<RechargeRecordEntity> PostRechargeRecord(Map reqmap){
        Map map = getDefMap();
        map.putAll(reqmap);
        return iHttpApiT.PostRechargeRecord(Map2JsonRequestBody(map));
    }

    /**
     * ????????????
     * @param price
     * @param paypasswd
     * @return
     */
    public Observable<BaseEntity> PostBalanceDraw(String price,String paypasswd){
        Map map = getDefMap();
        map.put("price",price);
        map.put("paypassword",paypasswd);
        return iHttpApiT.PostBalanceDraw(Map2JsonRequestBody(map));
    }

    /**
     * ????????????
     * @param reqmap
     * @return
     */
    public Observable<DrawRecordEntity> PostDrawRecord(Map reqmap){
        Map map = getDefMap();
        map.putAll(reqmap);
        return iHttpApiT.PostDrawRecord(Map2JsonRequestBody(map));
    }


}
