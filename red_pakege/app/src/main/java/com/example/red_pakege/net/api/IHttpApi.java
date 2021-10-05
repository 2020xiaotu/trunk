package com.example.red_pakege.net.api;



import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.net.entity.DrawRecordEntity;
import com.example.red_pakege.net.entity.GameListEntity;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.net.entity.RechargeRecordEntity;
import com.example.red_pakege.net.entity.RemittanceEntity;
import com.example.red_pakege.net.entity.YuebaoDetailEntity;
import com.example.red_pakege.net.entity.YuebaoInfoEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * created  by ganzhe on 2019/11/14.
 */
public interface IHttpApi {

    @GET("gameClass/getAllGameClass_xxx")
    Observable<GameListEntity> getEntityTest();
    @GET("gameClass/getAllGameClass_")
    Observable<Response<ResponseBody>> getStringTest();

    //=====================================APP 用户=================================
    //用户登录
    @POST("pay/apiMember/login")
    Observable<UserEntity> PostLogin(@Body RequestBody requestBody);
    //用户注册
    @POST("pay/apiMember/register")
    Observable<UserEntity> PostRegister(@Body RequestBody requestBody);
    //用户余额
    @POST("pay/apiMember/memberMoney")
    Observable<MemberMoneyEntity> PostMemberMoney(@Body RequestBody requestBody);
    //用户余额
    @POST("pay/apiMember/memberMoney")
    Observable<Response<ResponseBody>> userMoney(@Body RequestBody requestBody);
    //用户信息
    @POST("pay/apiMember/aboutPerson")
    Observable<UserEntity> PostUserInfo(@Body RequestBody requestBody);
    //更新修改用户信息
    @POST("pay/apiMember/updatePerson")
    Observable<BaseEntity> PostModifyUserInfo(@Body RequestBody requestBody);

    //======================================APP 余额宝==========================

    //用户余额宝明细
    @POST("pay/apiMemberYueBao/getMemberYueBaoRecordList")
    Observable<YuebaoDetailEntity> PostYuebaoDetail(@Body RequestBody requestBody);

    //获取余额宝信息
    @POST("pay/apiMemberYueBao/getYueBaoInfo")
    Observable<YuebaoInfoEntity> PostYuebaoInfo(@Body RequestBody requestBody);

    //提现余额宝
    @POST("pay/apiMemberYueBao/transferOutYueBao")
    Observable<BaseEntity> PostOutYueBao(@Body RequestBody requestBody);

    //转入余额宝
    @POST("pay/apiMemberYueBao/transferToYueBao")
    Observable<BaseEntity> PostInYueBao(@Body RequestBody requestBody);



    //=====================================APP 充值=====================================
    //通道列表
    @POST("pay/apiRecharge/bankList")
    Observable<RechargeEntreyEntity> PostRechargeEntry(@Body RequestBody requestBody);
    //提交汇款订单
    @POST("pay/apiRecharge/createRemittance")
    Observable<RemittanceEntity> PostRemittance(@Body RequestBody requestBody);
    //充值记录
    @POST("pay/apiRecharge/rechargeList")
    Observable<RechargeRecordEntity> PostRechargeRecord(@Body RequestBody requestBody);
    //提现记录
    @POST("pay/apiRecharge/drawRecordList")
    Observable<DrawRecordEntity> PostDrawRecord(@Body RequestBody requestBody);
    //用户余额提现
    @POST("pay/apiRecharge/balanceDraw")
    Observable<BaseEntity> PostBalanceDraw(@Body RequestBody requestBody);


//    ---------------------------------------  聊天----------------------------------
    //发牛牛红包
    @POST("redEnvelope/sendNiuNiuEnvelope")
    Observable<Response<ResponseBody>> sendNiuNiu(@Body RequestBody requestBody);
    //拆包
    @GET("redEnvelope/canGrab")
    Observable<Response<ResponseBody>> caiBao(@QueryMap Map<String, Object> data);
    //红包详情
    @GET("redEnvelope/detail")
    Observable<Response<ResponseBody>> pakegeDetail(@QueryMap Map<String, Object> data);
    //抢包
    @GET("redEnvelope/grabEnvelope")
    Observable<Response<ResponseBody>> getPageke(@QueryMap Map<String, Object> data);
    //获取用户名和头像
    @GET("RongCloud/getRUser")
    Observable<Response<ResponseBody>> getRUser(@QueryMap Map<String, Object> data);
    //获取红包房间列表
    @GET("gameClassRoom/getRoom")
    Observable<Response<ResponseBody>> getRedRoom(@QueryMap Map<String, Object> data);
    //获取融云token
    @GET("RongCloud/getToken")
    Observable<Response<ResponseBody>> getRongTok(@QueryMap Map<String, Object> data);
    //加入房间
    @GET("RongCloud/joinRoom")
    Observable<Response<ResponseBody>> joinChatRoom(@QueryMap Map<String, Object> data);
    //获取游戏列表
    @GET("category/list")
    Observable<Response<ResponseBody>> getGameList(@QueryMap Map<String, Object> data);
    //获取房间信息
    @GET("gameClassRoom/getRoomById")
    Observable<Response<ResponseBody>> getRoomInfo(@QueryMap Map<String, Object> data);
    //发扫雷红包
    @POST("redEnvelope/sendBombEnvelope")
    Observable<Response<ResponseBody>> sendSaolei(@Body RequestBody requestBody);
    //发禁枪红包
    @POST("redEnvelope/sendProhibitGarbEnvelope")
    Observable<Response<ResponseBody>> sendJinQiang(@Body RequestBody requestBody);

}
