package com.example.red_pakege;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.ArrayMap;


import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.ApiConfig;
import com.example.red_pakege.base.AppBlockCanaryContext;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.rong_yun.even_bus_model.CurrentModel;
import com.example.red_pakege.rong_yun.message.NiuNiuResultMessage;
import com.example.red_pakege.rong_yun.plugin.MyExtensionModule;
import com.example.red_pakege.config.Constants;
import com.example.red_pakege.util.RequestUtil;
import com.github.moduth.blockcanary.BlockCanary;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import okhttp3.Headers;

public class MyApplication extends Application {

    private static  MyApplication mInstance;
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.red);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setPrimaryColorsId(android.R.color.white, R.color.red);//全局设置主题颜色
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        //在这里初始化
        initRxhttp();
        initRongCloud();
        initBugly();
        initFragmention();
        initTimeZone();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    private void initTimeZone() {
        TimeZone chinaTimeZone = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(chinaTimeZone);
    }



    private void initRongCloud(){
        //融云相关初始化
        RongIM.init(this,"c9kqb3rdc4kxj");
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
          /*
        注册自定义的ExtensionModule(点击add按钮后,显示的区域)
         先对 DefaultExtensionModule 进行去重不然 Plugins 下的功能可能会出现重复，例如出现两套 图片、文件、音视频的 Plugin。
         */
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defautMoudule =null;
        if(moduleList!=null){
            for (IExtensionModule module : moduleList) {
                if(module instanceof DefaultExtensionModule){
                    defautMoudule =module;
                    break;
                }
            }
            if(defautMoudule!=null){
                RongExtensionManager.getInstance().unregisterExtensionModule(defautMoudule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
        /*
        消息接受监听
         */
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                //收到牛牛结果红包时,发送通知,如果用户当前在红包详情界面时,自动刷新,请求开奖结果
                if(message.getObjectName().equals("HB:ResultNiuNiu")){
                    MessageContent messageContent = message.getContent();
                    if(messageContent instanceof NiuNiuResultMessage){
                        NiuNiuResultMessage niuNiuResultMessage=(NiuNiuResultMessage)messageContent;
                        EventBus.getDefault().post(new CurrentModel(niuNiuResultMessage.getOrderCode()));
                    }
                }
                //return false 对监听到的消息不做处理,全部留给RongKit处理
                return false;
            }
        });

/**
 * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
 *
 * @param userInfoProvider 用户信息提供者。
 * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
 *                         如果 App 提供的 UserInfoProvider
 *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
 *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
 * @see UserInfoProvider
 */
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {

                return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
//        RongIM.getInstance().setMessageAttachedUserInfo(true);
//        RongIM.getInstance().setCurrentUserInfo(new UserInfo("2","步步",Uri.parse("http://b-ssl.duitang.com/uploads/item/201611/27/20161127214104_eZdhj.jpeg")));
    }


    private UserInfo findUserById(String userId) {
//        UserInfo userInfo = new UserInfo(userId, "啦啦啦", Uri.parse("http://b-ssl.duitang.com/uploads/item/201611/27/20161127214104_eZdhj.jpeg"));
        final UserInfo[] userInfo = new UserInfo[1];
        final HashMap<String, Object>[] data = new HashMap[]{new HashMap<>()};
        data[0].put("rid",userId);
        HttpApiUtils.normalRequest(getInstance(), RequestUtil.GET_RUSER, data[0], new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JSONObject object = JSONObject.parseObject(result);
                if(object.getString("message").equals("成功")){
                    JSONObject jsonObject = object.getJSONObject("data");
                    String avatar = jsonObject.getString("avatar");
                    String userName = jsonObject.getString("userName");
                    userInfo[0] =new UserInfo(userId,userName,Uri.parse(avatar));
                    //每次获取到用户信息都刷新一下
                    RongIM.getInstance().refreshUserInfoCache(userInfo[0]);
                }

            }

            @Override
            public void onFaild(String msg) {

            }
        });

        return userInfo[0];
    }

    private void initRxhttp(){
        ArrayMap<String, String> headMap = new ArrayMap<String, String>();
        //retrofit2 工厂类初始化
        ApiConfig build = new ApiConfig.Builder()
                .setBaseUrl(Constants.BASE_URL)//BaseUrl，这个地方加入后项目中默认使用该url
                .setSucceedCode(200)//成功返回码  200
                .setDefaultTimeout(5000)//响应时间，可以不设置，默认为2000毫秒
                //        .setHeads(headMap)
                .build();
        build.init(this);
    }
    private void initBugly(){
        //   Beta.autoInit = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        Beta.initDelay = 1000;
        Beta.autoCheckUpgrade = true;
        Beta.largeIconId = R.mipmap.ic_launcher;
        Beta.smallIconId = R.mipmap.ic_launcher;
        Beta.defaultBannerId = R.mipmap.ic_launcher;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.showInterruptedStrategy = false;
        Beta.canNotifyUserRestart = true;
        Beta.enableNotification = true;
        Beta.enableHotfix = false;
//        Beta.canShowUpgradeActs.add(TestMainActivity.class);
        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(this, BuildConfig.DEBUG?true:false);
        // 多渠道需求塞入
        //    String channel = WalleChannelReader.getChannel(getApplication());
        Bugly.setAppChannel(this, BuildConfig.FLAVOR);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(this, "a683368fce", BuildConfig.DEBUG?true:false);
    }
    private void initFragmention(){
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                      //   Bugtags.sendException(e);
                    }
                })
                .install();

    }
}
