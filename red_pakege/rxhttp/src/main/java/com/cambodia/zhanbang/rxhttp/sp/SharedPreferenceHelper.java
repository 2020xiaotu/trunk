package com.cambodia.zhanbang.rxhttp.sp;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;

/**
 * created  by ganzhe on 2019/9/26.
 */
public interface SharedPreferenceHelper {
    /**
     * 设置登录状态
     *
     * @param loginStatus
     */
    void setLoginStatus(boolean loginStatus);

    /**
     * 获取登录状态
     * @return
     */
    boolean getLoginStatus();

    /**
     * 设置phonenum
     * @param strPhone
     */
    void setPhoneNum(String strPhone);

    /**
     * 获取phoneNum
     */
    String getPhoneNum();

    /**
     * 设置 获取token
     * @param token
     */
    void setToken(String token);

    String getToken();

    //消息通知开关
    void setMsgSwitch(boolean msgSwitchStatus);
    boolean getMsgSwitch();
    //声音提示开关
    void setVoiceSwitch(boolean voiceSwitchStatus);
    boolean getVoiceSwitch();
    /**
     * 设置获取  token_refresh
     * @param token_refresh
     */
    void setTokenRefresh(String token_refresh);

    String getTokenRefresh();

    void putObject(String key,Object object);
    Object getObject(String key,Object object);

    void setUserInfo(String  userinfo);
    String getUserInfo();

    void setShiJianCha(Long  shiJianCha);
    long getShiJianCha();

    void setUserId(int userId);
    int getUserId();

    void setImageDomin(String imageDomin);
    String getImageDomin();

    void ClearSp();

}
