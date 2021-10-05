package com.example.red_pakege.util;

public class RequestUtil {
    /*
    roomId 房间id
    amount 发包金额
     */
    public static final String SEND_NIUNIU="redEnvelope/sendNiuNiuEnvelope";

    /*
    拆包
    orderCode  订单编号
    typeId  游戏id
     */
    public static final String CAIBAO="redEnvelope/canGrab";

    /*
    红包详情
    orderCode  订单编号
    typeId  游戏id
     */
    public static final String PAKEGE_DETAIL="redEnvelope/detail";

    /*
    抢包
    orderCode  订单编号
    typeId 游戏id
    userId userId
     */
    public static final String GET_RED_PAKEGE="redEnvelope/grabEnvelope";

    /*
    查看用户名和头像
    rid  请求格式(u-12)
     */
    public static final String GET_RUSER="RongCloud/getRUser";

    /*
    获取红包游戏列表
    typeId     1 禁枪 2扫雷 3牛牛
     */
    public static final String GET_RED_ROOM="gameClassRoom/getRoom";

    /*
    获取融云token
    userId  用户id
     */
    public static final String RONG_TOKEN="RongCloud/getToken";

    /*
    加入房间
    roomId  房间id
    userId  用户id
     */
    public static final String JOIN_ROOM="RongCloud/joinRoom";

    /*
    获取游戏分类列表
    id  游戏id : (红包 :1 , 棋牌:2 ,电子:3,休闲:4)
     */
    public static final  String GAME_CLASSFY_LIST="category/list";

    /*
    用户余额
     参数都在公共参数里 不需要传参
     */
    public static final String USER_MONEY="pay/apiMember/memberMoney";

    /*
    获取房间信息
    id  房间id
     */
    public static final String ROOM_INFO="gameClassRoom/getRoomById";

    /*
    发送扫雷红包
    roomId amount hbNum lotteryNo typeId game
     */
    public static final String SEND_SAOLEI_RED="redEnvelope/sendBombEnvelope";

    /*
    发送禁枪红包
     amount
     hbNum
     lotteryNo
     playingMethod  :0：不中，1：单雷，2：连环雷
     roomId
     typeId
     */
    public static final String SEND_JINQIANG_RED="redEnvelope/sendProhibitGarbEnvelope";
}
