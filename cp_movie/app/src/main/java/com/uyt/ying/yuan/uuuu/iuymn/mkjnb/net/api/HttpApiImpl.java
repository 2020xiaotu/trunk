/*
 * Copyright (c) 2019.  xxxx
 */

package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.net.api;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import com.uyt.ying.rxhttp.net.common.RetrofitFactory;
import com.uyt.ying.rxhttp.sp.SharedPreferenceHelperImpl;
import com.uyt.ying.yuan.BuildConfig;
import com.uyt.ying.yuan.R;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.net.entity.ChanelEntity;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request.AESUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request.StringMyUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.CommonStr;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.DeviceUtils;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.HttpUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.JsonUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.MyApplication;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.SharePreferencesUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.SystemUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.TimerUtils;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.VersionUtils;
import com.cf.msc.sdk.AppVest;
import com.cf.msc.sdk.SecurityConnection;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class HttpApiImpl {

    public IHttpApi iHttpApiT;
//    public CacheProvider cacheProvider;


    private HttpApiImpl() {
        iHttpApiT = RetrofitFactory.getInstance().create(IHttpApi.class);
   //     cacheProvider = RetrofitFactory.getInstance().cacheProvider;
    }
    public HttpApiImpl(String base_url) {
        iHttpApiT = RetrofitFactory.getInstance().create(base_url,IHttpApi.class);
     //   cacheProvider = RetrofitFactory.getInstance().cacheProvider;
    }
    public HttpApiImpl(String base_url,long timeout) {
        iHttpApiT = RetrofitFactory.getInstance().create(base_url,IHttpApi.class);
     //   cacheProvider = RetrofitFactory.getInstance().cacheProvider;
    }

    public static HttpApiImpl  getInstance() {
        return HttpApiImplHolder.S_INSTANCE;
    }


    private static class HttpApiImplHolder {
        private static final HttpApiImpl S_INSTANCE = new HttpApiImpl();
    }

    public static HttpApiImpl  getInstance1() {
        return HttpApiImplHolder1.S_INSTANCE1;
    }

    private static class HttpApiImplHolder1 {
        private static final HttpApiImpl S_INSTANCE1 = new HttpApiImpl(BuildConfig.API_HOST1);

    }

    public static HttpApiImpl  getInstance2() {
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        if(sp.getAppVestFlag()==1){
            return HttpApiImplHolder2AppVest.S_INSTANCE2;
        }else {
            return HttpApiImplHolder2.S_INSTANCE2;
        }
    }

    private static class HttpApiImplHolder2AppVest {
        //      private static final HttpApiImpl S_INSTANCE2 = new HttpApiImpl(BuildConfig.API_HOST2);
        static SecurityConnection conn = AppVest.getServerIPAndPort("www.tiantian.com", 1002);
        private static final HttpApiImpl S_INSTANCE2 = new HttpApiImpl(String.format("https://%s:%s/web/ws/", conn.getServerIp(), conn.getServerPort()));
    }
    private static class HttpApiImplHolder2 {
              private static final HttpApiImpl S_INSTANCE2 = new HttpApiImpl(BuildConfig.API_HOST2);
    }

    //=============================================API????????????========================



    /**
     * ??????????????????
     * @return
     */
    public Observable<Response<ResponseBody>> getActivityRank(){
        HashMap map = new HashMap();
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
//        return iHttpApiT.getActivityRank(aaa);
        return null;
    }
    /**
     * ?????????????????????
     */
    public Observable<Response<ResponseBody>> getChatUserInfo(){
        HashMap map = new HashMap();
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        return iHttpApiT.getChatUserInfo(aaa);
    }


    /**
     * ????????????
     */
    public Observable<Response<ResponseBody>> getHbStatus(int type){
        HashMap map = new HashMap();
        map.put("type",type);
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        return iHttpApiT.getHbStatus(aaa);
    }

    /**
     * ????????????????????????
     * @return
     */
    public Observable<Response<ResponseBody>> getShipinList(long id,int selectType,int pageNo,int pageSize){
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("selectType",selectType);
        map.put("page",pageNo);
        map.put("pageSize",pageSize);
        putCommonParams(map);
        return iHttpApiT.homeMovie(map);
    }

    /**
     * ??????????????????
     * @return
     */
    public Observable<Response<ResponseBody>> getLiveList(String url,int pageNo, int pageSize,String  categoryId,String area){

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("source", 2);
        if(StringMyUtil.isNotEmpty(categoryId)&&!categoryId.equals(CommonStr.CATEGORY_DEFAULT_VALUE)){
            map.put("categoryId", categoryId);
        }
        if(!area.equals(CommonStr.AREA_DEFAULT_VALUE)){
            map.put("area", area);
        }
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        return iHttpApiT.getRoomList(url,aaa);
    }

    /**
     * getNavigateList
     * @return
     */

    public Observable<Response<ResponseBody>> getNavigateList(){
        HashMap<String, Object> datalottery = new HashMap<>();
        datalottery.put("isHot", "");
        datalottery.put("game", 0);//???????????????
        datalottery.put("type_id", 0);//???????????????
        datalottery.put("source", 2);
        putCommonParams(datalottery);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(datalottery);
        return iHttpApiT.getNavigateList(aaa);
    }


    /**  0  ???????????????????????? ??????
     * ??????????????????
     * @param id ??????ID
     * @return
     */
    public Observable<ChanelEntity> getChannelList(long id, int page, int pageSize){
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("page",page);
        map.put("pageSize",pageSize);
        putCommonParams(map);
        return iHttpApiT.getChannelList(map);
    }

    /**
     * ???????????????
     * @param url
     * @param type_id
     * @return
     * @throws Exception
     */
    public Observable<Response<ResponseBody>> getCountDown(String url, int type_id) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("type_id", type_id);//????????????????????????
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        putCommonParams(dataMap);
        return iHttpApiT.getCountDown(url, aaa);
    }

    /**
     * ??????????????????
     * @param url
     * @param type_id
     * @return
     */
    public Observable<Response<ResponseBody>> getLastLottery(String url, int type_id){

        HashMap<String, Object> dataMap = new HashMap<>();//??????????????????????????????

        dataMap.put("type_id", type_id);//????????????????????????
        dataMap.put("flag", 1);
        dataMap.put("pageNo", 1);
        dataMap.put("pageSize", 10);
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.getLastLottery(url, aaa);
    }

    /**
     * ??????????????????
     * @param game
     * @param type_id
     * @return
     */
    public Observable<Response<ResponseBody>> getGameRuleData(int game,int type_id){
//    public Observable<Response<ResponseBody>> getGameRuleData(int game, int type_id, CallBack callback){
        Context mContext = MyApplication.getInstance();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("game", game);
        dataMap.put("type_id", type_id);
        String req_url = "";
        Resources res = mContext.getResources();
        String[] gameRules = res.getStringArray(R.array.getgroup);
        if (game <= gameRules.length) {
            req_url = gameRules[game - 1];
        }
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.getGameRuleData(req_url, aaa);

    }

    /**
     * ?????????
     * @param url
     * @return
     */
    public Observable<Response<ResponseBody>> getPaihang(String url){
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("pageNo",1);
        dataMap.put("pageSize",20);
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.getLastLottery(url, aaa);
    }
    public Observable<Response<ResponseBody>> pingTest(){
        HashMap<String, Object> dataMap = new HashMap<>();
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.pingTest(aaa);
    }
    /**
     * ???????????????
     */
    public Observable<Response<ResponseBody>> getYestodayRankList(int typeId,int pageNo,int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("typeId",typeId);
        putCommonParams(map);
//        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        return iHttpApiT.getYestodayList( HttpUtil.postRequest(map,true));

    }

    /**
     * liveroomfragment??????
     * @param url
     * @return
     */
    public Observable<Response<ResponseBody>> getNotice(String url){
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("pageNo",1);
        dataMap.put("pageSize",15);
        dataMap.put("type",7);
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.getLastLottery(url, aaa);
    }

    public Observable<Response<ResponseBody>> getZjInfo(String url){
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("pageNo",1);
        dataMap.put("pageSize", 50);
        putCommonParams(dataMap);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(dataMap);
        return iHttpApiT.getLastLottery(url, aaa);
    }
    /**
     * ????????????
     * @return
     */
/*
    public Observable<Response<ResponseBody>> getGiftList(){
        Map<String,Object> aaa = JsonUtil.getDefReqMap(new HashMap<String, Object>());
        return iHttpApiT.getGiftlist(aaa);
    }
*/

    /**
     * ????????????rxcache
     */
//    public Observable<Reply<Response<ResponseBody>>> getGiftWithCache(){
//        return cacheProvider.getGiftlist(getGiftList());
//    }

//    public Observable<Reply<Response<ResponseBody>>> getGiftWithCache(){
//
//    }

    /**
     * ????????????
     */
    public Observable<Response<ResponseBody>> getAmount(){
        HashMap<String, Object> map = new HashMap<>();
        putCommonParams(map);
        RequestBody requestBody = HttpUtil.postRequest(map, true);
        return iHttpApiT.getAmount(requestBody);
    }
    /**
     * ????????????
     */
    public Observable<Response<ResponseBody>> getIsZj(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", SharePreferencesUtil.getString(MyApplication.getInstance(), "nickname", ""));
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        return iHttpApiT.requestIsZj(aaa);
    }
    /**
     * ??????
     * @param reqMap
     * @return
     */
    public Observable<Response<ResponseBody>> getBet(int game, Map<String,Object> reqMap, Activity activity, Fragment fragment){
        HashMap<String,Object> map = new HashMap<>();
        String curtime = TimerUtils.getTimeStyleTwo();
        map.put("curtime", curtime);
        map.putAll(reqMap);
        putCommonParams(map);
        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
        String bet_url = "";
        Resources res = MyApplication.getInstance().getResources();
        String[] gameRules = res.getStringArray(R.array.cpmovie_touzhu);
        if (game <= gameRules.length) {
            bet_url = gameRules[game - 1];
        }
        return iHttpApiT.getBet(bet_url,aaa);

    }

    /**
     * ?????????????????????
     */
    public Observable<Response<ResponseBody>> RefreshRoomUrl(String anchorAccount,String remoteLiveManagementId,String liveRoomId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("liveRoomId", liveRoomId);
        map.put("anchorAccount",anchorAccount);
        map.put("remoteLiveManagementId",remoteLiveManagementId);
        putCommonParams(map);
        RequestBody requestBody = HttpUtil.postRequest(map, true);
        return iHttpApiT.RefreshRoomUrl(requestBody);
    }

    /**
     * ??????/??????/??????????????????
     * @return
     */
//    public Observable<RongcloudHBParameter> rongcloudHBParameter(){
//        Map<String, Object> map = getBaseMap();
//        Map<String,Object> aaa = JsonUtil.getDefReqMap(map);
//        return iHttpApiT.rongcloudHBParameter(aaa);
//    }
//
//    public Map<String, Object> getBaseMap(){
//        Context mContext = MyApplication.getInstance();
//        Map<String, Object> dataMap = new HashMap<>();
//        String domain = SharePreferencesUtil.getString(mContext,"domain","");
//        Long user_id = SharePreferencesUtil.getLong(mContext, "user_id", 0L);
//        String token = SharePreferencesUtil.getString(mContext, "token", "");
//       /* dataMap.put("user_id", user_id);
//        dataMap.put("token", token);*/
//        return dataMap;
//    }


    public void putCommonParams( HashMap<String, Object> data){
//        String domain = SharePreferencesUtil.getString(MyApplication.getInstance(), "domain", "");
        String token = SharePreferencesUtil.getString(MyApplication.getInstance(), "token", "");
        Long user_id1 = SharePreferencesUtil.getLong(MyApplication.getInstance(), "user_id", 0l);
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        String domain = sp.getNewBaseUrl();
        domain = StringMyUtil.isEmptyString(domain)? BuildConfig.API_HOST1:domain;
        if(!data.containsKey("loginType")){
            data.put("loginType",1);
        }

        if (!StringMyUtil.isEmptyString(domain) && !data.containsKey("domain")) {
            data.put("domain", domain);
        }
        data.put("source", 2);
        if (!StringMyUtil.isEmptyString(token) && !data.containsKey("token")) {
            data.put("token", token);
            data.put("tokenInfo", token);
        }
        if (user_id1 != 0l) {
            data.put("user_id", user_id1);
        }
        if(!data.containsKey("deviceNumber")){
            data.put("deviceNumber", AESUtil.encrypt(DeviceUtils.getUniqueId(MyApplication.getInstance())));//?????????
        }
        if(!data.containsKey("appVersionName")){
            data.put("appVersionName", VersionUtils.getAppVersionName(MyApplication.getInstance()));
        }
        if(!data.containsKey("appVersionName")){
            data.put("appVersionName", SystemUtil.getSystemVersion());
        }
        if(!data.containsKey("mobileBrandModels")) {
            data.put("mobileBrandModels", SystemUtil.getSystemModel());
        }
        if(!data.containsKey("mobileBrand")){
            data.put("mobileBrand",SystemUtil.getDeviceBrand());
        }
    }


}
