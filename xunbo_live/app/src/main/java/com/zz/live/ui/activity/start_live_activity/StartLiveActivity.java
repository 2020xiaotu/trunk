package com.zz.live.ui.activity.start_live_activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.faceunity.nama.FURenderer;
import com.faceunity.nama.IFURenderer;
import com.google.gson.Gson;
import com.lovense.sdklibrary.Lovense;
import com.lovense.sdklibrary.LovenseToy;
import com.zz.live.BuildConfig;
import com.zz.live.R;
import com.zz.live.bean.LiveGiftSVGAEntity;
import com.zz.live.bean.ProvinceJsonBean;
import com.zz.live.bean.SystemMessageModel;
import com.zz.live.bean.ToyConnectEvent;
import com.zz.live.myView.gift.AssetsSvgaManage;
import com.zz.live.ui.activity.main_tab_activity.ToyActivity;
import com.zz.live.ui.beauty.CameraPush;
import com.faceunity.nama.ui.BeautyControlView;
import com.faceunity.nama.ui.FaceUnityView;
import com.faceunity.nama.utils.CameraUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import com.opensource.svgaplayer.SVGAImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zz.live.MyApplication;

import com.zz.live.base.BaseActivity;
import com.zz.live.base.BasePopupWindow;
import com.zz.live.base.Const;
import com.zz.live.bean.ChangeRoomTypeEntity;
import com.zz.live.bean.ChatUserEntity;
import com.zz.live.bean.GameTypeEnum;
import com.zz.live.bean.HomeClassfyEntity;
import com.zz.live.bean.LastLotteryEntity;
import com.zz.live.bean.LiveMessageModel;
import com.zz.live.bean.LiveParamEntity;
import com.zz.live.bean.LotteryEntitiy;
import com.zz.live.bean.NatureEntity;
import com.zz.live.bean.OpenNoMulBean;
import com.zz.live.bean.UserInfoEntity;
import com.zz.live.bean.UserMoneyEntity;
import com.zz.live.bean.ZjMessageModel;
import com.zz.live.bean.evenBus.CountDownEven;
import com.zz.live.bean.evenBus.leaveAppEvenModel;
import com.zz.live.bean.evenBus.StopPushEvenModel;
import com.zz.live.myView.AutoLineFeedLayoutManager;
import com.zz.live.myView.gift.GiftSendModel;
import com.zz.live.myView.gift.GiftView;
import com.zz.live.myView.gift.JoinSvgaMage;
import com.zz.live.myView.gift.GiftSvgaManage;
import com.zz.live.net.api.HttpApiImpl;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.activity.start_live_activity.bottom_fragment.BottomDialogFragment;
import com.zz.live.ui.activity.mine_fragment_activity.MineDetailsActivity;
import com.zz.live.ui.activity.play_live_activity.EditPanel;
import com.zz.live.ui.activity.play_live_activity.LiveChatFragment;
import com.zz.live.ui.activity.start_live_activity.rank_fragment.LiveRankDialogFragment;
import com.zz.live.ui.adapter.ChatUserAdapter;
import com.zz.live.ui.adapter.OpenNoMulAdapter;
import com.zz.live.ui.beauty.CameraPushImpl;
import com.zz.live.ui.beauty.PusherBeautyKit;
import com.zz.live.ui.pop.BeautyPop;
import com.zz.live.ui.pop.ChangeRoomTypePop;
import com.zz.live.ui.pop.ChooseNaturePop;
import com.zz.live.ui.pop.CommonChoosePop;
import com.zz.live.ui.pop.CommonTipPop;
import com.zz.live.ui.pop.LiveMorePop;
import com.zz.live.ui.pop.SetBusinessCardPop;
import com.zz.live.ui.pop.SetRoomTypePop;
import com.zz.live.ui.pop.TakeCameraPop;
import com.zz.live.ui.rongyun.RongLibUtils;
import com.zz.live.ui.rongyun.message.ForbiddenMessage;
import com.zz.live.ui.rongyun.message.LiveExitAndJoinMessage;
import com.zz.live.ui.rongyun.message.LiveFollowMessage;
import com.zz.live.ui.rongyun.message.LiveGiftMessage;
import com.zz.live.ui.rongyun.message.LiveNormalRedMessage;
import com.zz.live.ui.rongyun.message.LiveRedMessage;
import com.zz.live.ui.rongyun.message.LiveRewardMessage;
import com.zz.live.ui.rongyun.message.LiveShareBetMessage;
import com.zz.live.ui.rongyun.message.LiveSystemMessage;
import com.zz.live.ui.rongyun.message.LiveTextMessage;
import com.zz.live.ui.rongyun.message.RoomManageMessage;
import com.zz.live.ui.rongyun.message.SwichMoneyMessage;
import com.zz.live.utils.AESUtil;
import com.zz.live.utils.BitmapUtils;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.CustomPopupWindow;
import com.zz.live.utils.DrawerLeftEdgeSize;
import com.zz.live.utils.GetJsonDataUtil;
import com.zz.live.utils.GetPhotoFromPhotoAlbum;
import com.zz.live.utils.GlideLoadViewUtil;
import com.zz.live.utils.HeightProvider;
import com.zz.live.utils.ImageThumbnail;
import com.zz.live.utils.KeyboardHeightProvider;
import com.zz.live.utils.RequestUtils;
import com.zz.live.utils.SharePreferencesUtil;
import com.zz.live.utils.StatusBarUtil;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.SystemUtil;
import com.zz.live.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import cn.nodemedia.NodeCameraView;
import cn.nodemedia.NodePublisher;
import cn.nodemedia.NodePublisherDelegate;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.ChatRoomInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import pub.devrel.easypermissions.EasyPermissions;

import static android.graphics.BitmapFactory.decodeResource;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static cn.nodemedia.NodePublisher.AUDIO_PROFILE_HEAAC;
import static com.tencent.rtmp.TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO;
import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION;
import static com.zz.live.bean.GameTypeEnum.MARKSIX;
import static com.zz.live.bean.GameTypeEnum.valueOf;
import static com.zz.live.utils.DateUtil.timeMode;
import static io.rong.imlib.RongIMClient.ErrorCode.FORBIDDEN_IN_CHATROOM;

public class StartLiveActivity extends BaseActivity implements BasePopupWindow.OnPopClickListener,
        EasyPermissions.PermissionCallbacks,  TXLivePusher.VideoCustomProcessListener,
        BasePopupWindow.OnRecycleItemClick, FURenderer.OnTrackStatusChangedListener, SensorEventListener {
    private String TAG="StartLiveActivity";
    @BindView(R.id.pusher_tx_cloud_view)
    TXCloudVideoView mPusherView;
    @BindView(R.id.node_pusher_view)
    NodeCameraView node_pusher_view;
    @BindView(R.id.beauty_iv)
    ImageView beauty_iv;
    @BindView(R.id.live_constrainLayout_group)
    Group live_constrainLayout_group;
    @BindView(R.id.start_live_btn)
    Button start_live_btn;
    @BindView(R.id.change_camera_iv)
    ImageView change_camera_iv;
    @BindView(R.id.start_live_titile_tv)
    TextView start_live_titile_tv;
    @BindView(R.id.start_live_title_etv)
    EditText start_live_title_etv;
    @BindView(R.id.start_live_constraintLayout)
    ConstraintLayout start_live_constraintLayout;
    @BindView(R.id.add_title_frameLayout)
    FrameLayout add_title_frameLayout;
    @BindView(R.id.choose_channel_iv)
    ImageView choose_channel_iv;
    @BindView(R.id.choose_channel_tv)
    TextView choose_channel_tv;
    @BindView(R.id.cover_iv)
    ImageView cover_iv;
    @BindView(R.id.linearLayout7)
    public LinearLayout linearLayout7;
    @BindView(R.id.live_edit_panel)
    EditPanel editPanel;
    @BindView(R.id.drawwe_linear)
    ConstraintLayout drawwe_linear;
    /*  @BindView(R.id.iv_avatar)
      ImageView iv_avatar;
      @BindView(R.id.tv_name)
      TextView tv_name;
      @BindView(R.id.tv_id)
      TextView tv_id;*/
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.giftView)
    public GiftView giftView;
    @BindView(R.id.money_tv)
    TextView money_tv;
    @BindView(R.id.tv_countdown)
    TextView tv_countdown;
    @BindView(R.id.svga_imageview)
    public SVGAImageView svgaImageView;
    @BindView(R.id.pusher_drawerLayout)
    DrawerLayout pusher_drawerLayout;
    @BindView(R.id.bottom_pop_iv)
    ImageView iv_bottomgift;
    @BindView(R.id.meiyan_iv)
    ImageView iv_centre;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.choose_lottery_tv)
    TextView choose_lottery_tv;
    @BindView(R.id.more_iv)
    ImageView more_iv;
    @BindView(R.id.choose_lottery_iv)
    ImageView choose_lottery_iv;
    @BindView(R.id.lottrry_open_result_constraintLayout)
    ConstraintLayout lottrry_open_result_constraintLayout;
    @BindView(R.id.open_result_top_layout)
    ConstraintLayout open_result_top_layout;
    @BindView(R.id.tv_lottery_name)
    TextView tv_lottery_name;
    @BindView(R.id.tv_lottery_qishu)
    TextView tv_lottery_qishu;
    @BindView(R.id.top_close_iv)
    ImageView top_close_iv;
    @BindView(R.id.dismiss_open_result_iv)
    ImageView dismiss_open_result_iv;
    @BindView(R.id.recy_lottery)
    RecyclerView recy_lottery;
    @BindView(R.id.wrap_frameLayout)
    FrameLayout wrap_frameLayout;
    private OpenNoMulAdapter mOpenNoMulAdapter;
    private List<OpenNoMulBean> mOpenNoMulList = new ArrayList<>();//??????????????????
    @BindView(R.id.countdown_iv)
    GifImageView countdown_iv;
    @BindView(R.id.live_time_tv)
    TextView live_time_tv;
    @BindView(R.id.iv_lottery)
    ImageView iv_lottery;
    @BindView(R.id.set_business_tv)
    TextView set_business_tv;
    @BindView(R.id.set_business_iv)
    ImageView set_business_iv;
    @BindView(R.id.ll_gift)
    LinearLayout ll_gift;
    @BindView(R.id.room_type_tv)
    TextView room_type_tv;
    @BindView(R.id.room_type_iv)
    ImageView room_type_iv;
    @BindView(R.id.change_room_type_iv)
    ImageView change_room_type_iv;
    @BindView(R.id.nature_tv)
    TextView nature_tv;
    @BindView(R.id.nature_iv)
    ImageView nature_iv;
    @BindView(R.id.recy_renshu)
    RecyclerView recy_renshu;
    @BindView(R.id.speed_tv)
    TextView speed_tv;
    @BindView(R.id.rank_linear)
    LinearLayout rank_linear;
    @BindView(R.id.face_unity_view)
    FaceUnityView faceUnityView;
    ChatUserAdapter chatUserAdapter;
    ArrayList<ChatUserEntity>chatUserEntityArrayList= new ArrayList<>();
    @BindView(R.id.join_svga_imageview)
    SVGAImageView join_svga_imageview;
    @BindView(R.id.test_svga_imageview)
    SVGAImageView test_svga_imageview;
    @BindView(R.id.area_iv)
    ImageView area_iv;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.face_wrap_linear)
    LinearLayout face_wrap_linear;
    @BindView(R.id.face_top_linear)
    LinearLayout face_top_linear;
    @BindView(R.id.count_down_fail_refresh_tv)
    TextView count_down_fail_refresh_tv;
    @BindView(R.id.count_down_fail_loading_iv)
    GifImageView count_down_fail_loading_iv;
    @BindView(R.id.toy_tv)
    TextView toy_tv;
    @BindView(R.id.toy_iv)
    ImageView toy_iv;
    @BindView(R.id.toy_disconnect_tv)
    TextView toy_disconnect_tv;
    CommonChoosePop toll2FreeTipPop;
    CommonChoosePop free2TollTipPop;
    private KeyboardHeightProvider mKeyboardHeightProvider;

    private  ArrayList<LiveGiftMessage>gearMessageList = new ArrayList<>();
    /**
     * ??????????????? ??????????????????????????????,???????????????????????????????????????
     */
    boolean isPusher =false;
    //    TXLivePusher mLivePusher;
    private CameraPush mLivePusher;
    // ???????????????????????? RTMP ??????
    private boolean  mIsGettingRTMPURL = false;
    //??????????????????
    private PusherBeautyKit manager;
    //????????????pop
    BeautyPop beautyPop;
    //????????????pop
    CommonChoosePop commonChoosePop;
    //???????????????id
    private String categoryId;
    //??????url
    private String cover;
    //????????????id
    private String gameClassId="";
    //????????????????????????
    private String title;
    private String area;
    TakeCameraPop takeCameraPop;
    private File cameraSavePath;
    //????????????
    private Uri uri;
    private int CAMARE_REQUEST_CODE = 1;//????????????????????????
    private int PHOTO_REQUEST_CODE = 2;//????????????????????????
    private int SKIP_CHOOSE_CHANNEL=3;//??????????????????????????????
    private int SKIP_CHOOSE_LOTTERY=4;//??????????????????????????????
    private int SKIP_TOY=5;//????????????
    LiveChatFragment liveChatFragment;
    private String[] PERMISSIONS={"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
            ,"android.permission.READ_EXTERNAL_STORAGE"};
    private String streamName;
    UserInfoEntity.DataBean userDataBean;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private GiftSvgaManage giftSvgaManage;
    private JoinSvgaMage joinGiftSvgaMage;
    public AssetsSvgaManage assetsSvgaManage;
    //??????????????????
    private int totalMemberCount;
    //???????????????
    private int mostHighMemberCount;
    private JSONObject pushObject;
    private String rtmpPushUrl;
    private PublishSubject onlineNumObservable=PublishSubject.create();
    private LiveMorePop liveMorePop;
    //??????????????????????????????(??????liveMorePop???????????????)
    boolean isBackCamera = false;
    //????????????????????????
    private boolean isOnLight =false;
    //??????????????????
    private boolean isMute =false;
    //???????????????been
    private LotteryEntitiy.GameClasslistBean dataBean;
    private String lastqishu;//????????????
    private String qishu;//??????
    private boolean isWaitopen = true;
    private String nowQishu;
    private long jgTime = 3000;
    private String tqtime;
    private long millionSeconds;
    private Long shijiancha;
    private long countTime;
    private long liveTime;
    private String isStart;
    private List<String> openResultList;
    private boolean showOpenResult=false;
    private Timer openResultTimer;
    boolean isPausePusher=false;
    private CustomPopupWindow customPopupWindow = new CustomPopupWindow();
    //??????????????????
    int watchRadio;
    private int[] avatatArray;
    private SetBusinessCardPop setBusinessCardPop;
    //????????????????????????
    private String busonessCard;
    //???????????????????????????pop
    SetRoomTypePop unPusherSetRoomTypePop;
    //???????????????????????????
    SetRoomTypePop pusherChangeRoomTypePop;
    // ????????????(1??????2??????)
    int roomType=1;
    //    ??????????????????
    private String roomAmount="0";
    //??????????????????pop
    ChangeRoomTypePop changeRoomTypePop;



    ChooseNaturePop  chooseNaturePop;
    private ArrayList<NatureEntity> natureEntityArrayList;
    private String natureStr;
    private boolean countDownStatus =true;

    private LiveRankDialogFragment liveRankDialogFragment;
    private int mFrontCameraOrientation;

    private List<ProvinceJsonBean> provinceOptionsItems = new ArrayList<>();//??????jsonBean list
    private ArrayList<ArrayList<String>> cityOptionsItems = new ArrayList<>();//??????jsonBean list
    private ArrayList<ArrayList<ArrayList<String>>> areaOptionsItems = new ArrayList<>();//??????jsonBean list
    boolean isCityLoaded=false;
    private MyHandler handler = new MyHandler();
    //???????????????????????????(5???????????????)
    private ArrayList<LiveMessageModel> commitLiveMessageModelList = new ArrayList<>();
    //?????????????????????????????????
    private LiveMessageModel currentTextMessage;
    private TextView mTvTrackStatus;
    private BeautyControlView beautyControlView;
    private FURenderer mFURenderer;
    private int mCameraFacing = IFURenderer.CAMERA_FACING_FRONT;
    private long currentTime;
    private boolean mIsFirstFrame = true;
    private SensorManager mSensorManager;

    public static Boolean USE_TX=true;
    private NodePublisher nodePublisher;

    private int countDownFailCount=0;
    private PublishSubject countDownFailSubject =PublishSubject.create();
    private int isUseToy=0;
    private String toyId;
    private boolean isGearing = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_live;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                initJsonData();
            }
        }).start();
        mFrontCameraOrientation = CameraUtils.getCameraOrientation(Camera.CameraInfo.CAMERA_FACING_FRONT);
//        mKeyboardHeightProvider = new KeyboardHeightProvider(this);
        if(USE_TX){

            mPusherView.setVisibility(VISIBLE);
            node_pusher_view.setVisibility(GONE);
            requestTxLiveParam();
        }else {
            mPusherView.setVisibility(View.GONE);
            node_pusher_view.setVisibility(VISIBLE);
            /**
             * ?????????????????????
             */
            nodePublisher = new NodePublisher(this,"NjAyNWM1MDAtNGU0ZDUzMjEtY29tLnp6LmxpdmU=-vfRf5M5IU1O8d3zSKZtprvw0kQIKeyHtrjn8da6hDbJ1jVBvi" +
                    "3Cnvigwatg4FhKkmnfjuaDygXTVnofz1x/L7g7/NpbfodxMXGKjblYfd5w8pgCbkEesAqZahjB1igdfQ/IVMUmknsGhyhZKS0LA38tAq6sFJ/yEchMMcFKjgZnae+dpEK4rpzvpsPRgxhb" +
                    "7+l/vIWHZ7eHnG+AsHdbgeIFAfyRZAtTJm2OuZoB2V3d8eZZHzOLFYdqU1r2RP9uVNbCpb9r1VmrVcIvGGLPffkTyWeZ6dbtbG45bubH4OJku4xYG3tvH6B/zCspk95sAOIu9Jwmp7OxYR" +
                    "Zvniy3GBQ==");
        }
        userDataBean=  Utils.getUserInfoBean();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        initDrawerLayout();
        checkPermissions();



        /**
         * ?????????????????? (????????????????????????????????????,????????????????????????, ????????????????????????????????????,??????????????????)
         */
//        initPusher();
        /**
         * ????????????fragment
         */
        initLiveChatFragment();
        /**
         * ??????????????????
         */
        observableOnlineNum();
        /**
         * ?????????????????????????????????????????????
         */
        observableCountFailCount();
        /**
         * ????????????????????????????????????
         */
        setDefaultData();

        editPanel.setStartLiveActivity(this);
        editPanel.setRoomId(sp.getRoomId());
        /**
         * ?????????????????????
         */
        Lovense.getInstance(getApplication()).setDeveloperToken("xK23V1OSkxI9bYAZbhb1edh6IZ3wes8/etYnus4mt8ZM+Yg0OAQOXwQf/kG2oDY3");

    }

    private void setDefaultData() {
        /**
         * ??????????????????
         */
        setDefaultBusinessCard();
        /**
         * ????????????????????????
         */
        setDefaultNature();
        /**
         * ??????????????????
         */
        setDefaultLottery();
        /**
         * ??????????????????
         */
        setDefaultCover();
        /**
         * ??????????????????
         */
        setDefaultTitle();
        /**
         * ??????????????????
         */
//        setDefaultCategory();
        /**
         * ??????????????????
         */
        setDefaultArea();
    }

    private void setDefaultArea() {
        area = SharePreferencesUtil.getString(MyApplication.getInstance(),CommonStr.LIVE_AREA,"");
        area_tv.setText(StringMyUtil.isEmptyString(area)?"????????????":area);
    }

    private void setDefaultCategory() {
        String channelName = SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CHOOSE_CHANNEL_NAME, "");
        choose_channel_tv.setText(StringMyUtil.isEmptyString(channelName)?"??????":channelName);
        categoryId = SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CHOOSE_CHANNEL_ID, "");
    }

    private void setDefaultTitle() {
        title = SharePreferencesUtil.getString(MyApplication.getInstance(),CommonStr.LIVE_TITLE,"");
        if(StringMyUtil.isNotEmpty(title)){
            start_live_title_etv.setText(title);
        }
    }

    private void setDefaultCover() {
        cover = SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CHOOSE_LIVE_COVER, "");
        if(StringMyUtil.isNotEmpty(cover)){
            GlideLoadViewUtil.LoadNormalView(this,cover,cover_iv);
        }
    }

    private void setDefaultLottery() {
        String bean = SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CHOOSE_LOTTERY_BEAN, "");
        if(StringMyUtil.isNotEmpty(bean)){
            dataBean = JSONObject.parseObject(bean, LotteryEntitiy.GameClasslistBean.class);
            choose_lottery_tv.setText(StringMyUtil.isEmptyString(dataBean.getTypename())?"??????":dataBean.getTypename());
            gameClassId = dataBean.getId();
        }else {
            //?????????????????????,????????????????????????
            requestLotteryList();
        }
    }

    private void setDefaultNature() {
        String natureContent = SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CHOOSE_NATURE, "");
        nature_tv.setText(StringMyUtil.isEmptyString(natureContent) ? "????????????" : natureContent);
        natureStr= StringMyUtil.isEmptyString(natureContent) ? "" : natureContent;
    }

    /**
     * ??????????????????
     */
    private void setDefaultBusinessCard() {
        String forbidCallingCard = Utils.getUserInfoBean().getForbidCallingCard();
        String callingCard = Utils.getUserInfoBean().getCallingCard();
        set_business_tv.setText(StringMyUtil.isEmptyString(callingCard)?"??????":callingCard);
        String businness = set_business_tv.getText().toString();
        if(!businness.equals("??????")){
            busonessCard = businness;
        }

        if(forbidCallingCard.equals("1")){
            busonessCard="??????";
        }
    }
    private void requestLotteryList() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("isAnchorUse",1);
        HttpApiUtils.cpNormalRequest(this, null, RequestUtils.CP_LOTTERY_LIST, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                LotteryEntitiy lotteryEntitiy = JSONObject.parseObject(result, LotteryEntitiy.class);
                List<LotteryEntitiy.GameClasslistBean> data = lotteryEntitiy.getGameClasslist();
                for (int i = 0; i < data.size(); i++) {
                    LotteryEntitiy.GameClasslistBean gameClasslistBean = data.get(i);
                    String typename = gameClasslistBean.getTypename();
                    if(typename.equals("????????????")){
                        dataBean = gameClasslistBean;
                        String id = gameClasslistBean.getId();
                        choose_lottery_tv.setText(typename);
                        gameClassId = id;
                        SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_LOTTERY_BEAN, JSON.toJSONString(dataBean));//?????????????????????bean
                    }
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    /**
     * ??????????????????licence  key
     */
    private void requestTxLiveParam() {
        HttpApiUtils.wwwNormalRequest(this, null,RequestUtils.TX_LIVE_PARAMS, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                LiveParamEntity liveParamEntity = JSONObject.parseObject(result, LiveParamEntity.class);
                LiveParamEntity.DataBean dataBean = liveParamEntity.getData();
                //licenceURL
                String licenceURL = null;
                try {
                    licenceURL = AESUtil.decrypt(dataBean.getSecretId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //licenceKey
                String licenceKey = null;
                try {
                    licenceKey = AESUtil.decrypt(dataBean.getSecretKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                licenceKey="e1bc56d0a725d1493b1571080150c5c1";
                licenceURL="http://license.vod2.myqcloud.com/license/v1/20c37a4ebe586dac402197156034a548/TXLiveSDK.licence";
                //?????????????????????sdk
                TXLiveBase.getInstance().setLicence(StartLiveActivity.this, licenceURL, licenceKey);
            }
            @Override
            public void onFail(String msg) {
            }
        });
    }

    /**
     * ??????????????????
     * @param game
     * @param typeId
     */
    public void requestOpenResult(int game, int typeId) {
        if(runnableRequestOpen!=null&&handler!=null){
            handler.removeCallbacks(runnableRequestOpen);
        }
        Context mContext = MyApplication.getInstance();
        String lastLottery_url = "";
        Resources res = mContext.getResources();
        String[] lastLotterys = res.getStringArray(R.array.lastLottery_cpmovie);
        if (game > 0 && game <= lastLotterys.length) {
            lastLottery_url = lastLotterys[game - 1];
        }
        new HttpApiImpl(SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CP_BASE_URL, BuildConfig.API_HOST2))
                .getLastLottery(lastLottery_url,typeId)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseStringObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String result) {
                        countDownFailCount=0;
                        countDownFailSubject.onNext(countDownFailCount);
                        if(runnableRequestOpen!=null&&handler!=null){
                            handler.post(runnableRequestOpen);
                        }
                        String resultData = Utils.initOldCpData("", result);
                        initCountDownData(resultData, game);
                    }
                    @Override
                    public void onFail(String msg) {
                        countDownFailCount++;
                        countDownFailSubject.onNext(countDownFailCount);
                        if(runnableRequestOpen!=null&&handler!=null){
                            handler.post(runnableRequestOpen);
                        }
                        System.out.println("failCount  ??????????????????");
                    }
                });
    }

    /**
     *
     * ?????????????????????
     * @param resultData
     * @param game
     */
    private void initCountDownData(String resultData, int game) {
        JSONArray jsonArray = null;
        switch (valueOf(game)) {
            case KUAISAN:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("gameLotterylist"));
                break;
            case SSC:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("sscaiLotterylist"));
                break;
            case RACE:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("raceLotterylist"));
                break;
            case MARKSIX:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("marksixLotterylist"));
                break;
            case DANDAN:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("danLotterylist"));
                break;
            case HAPPY8:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("happyLotterylist"));
                break;
            case LUCKFARM:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("farmLotterylist"));
                break;
            case HAPPY10:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("happytenLotterylist"));
                break;
            case XUANWU:
                jsonArray = JSONObject.parseArray(JSONObject.parseObject(resultData).getString("xuanwuLotterylist"));
                break;
        }

        if(jsonArray.size()!=0){
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String lotteryqishu = jsonObject.getString("lotteryqishu");
            if(!StringMyUtil.isEmptyString(nowQishu)){
                if(Long.parseLong(lotteryqishu)==(Long.parseLong(nowQishu)-1)){
                    isWaitopen=false;
                    String lotteryNo = jsonObject.getString("lotteryNo");
                    String[] split = lotteryNo.split(",");
                    openResultList = Arrays.asList(split);
                    List<String> NoList = Arrays.asList(lotteryNo.split(","));
                    List<String> AnimalList = null;
                    List<String> ColorList = null;
                    /**
                     * ?????????????????? ??????AnimalList ??????ColorList
                     */
                    if (valueOf(game) == MARKSIX) {
                        AnimalList = Arrays.asList(jsonObject.getString("animal").split(","));
                        ColorList = Arrays.asList(jsonObject.getString("color").split(","));
                    }
                    /**
                     * entity ??????
                     */
                    LastLotteryEntity lastLotteryEntity = new LastLotteryEntity();
                    lastLotteryEntity.setGame(game);
                    lastLotteryEntity.setLotteryNo(lotteryNo);
                    lastLotteryEntity.setLotteryqishu(lotteryqishu);
                    lastLotteryEntity.setNoList(NoList);
                    lastLotteryEntity.setAnimalList(AnimalList);
                    lastLotteryEntity.setColorList(ColorList);

                    List<OpenNoMulBean> openNoMulList = new ArrayList<>();
                    int[] shaizis = Const.getShaziArray(this);
                    if (game == GameTypeEnum.KUAISAN.getValue()) {
                        for (String str : lastLotteryEntity.getNoList()) {
                            OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                            openNoMulBean.setName(String.valueOf(shaizis[Integer.parseInt(str) - 1]));
                            openNoMulBean.setGame(game);
                            openNoMulBean.setItemType(1);
                            openNoMulList.add(openNoMulBean);
                        }
                    } else if (game == GameTypeEnum.RACE.getValue()) {
                        for (String str : lastLotteryEntity.getNoList()) {
                            OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                            openNoMulBean.setName(str);
                            openNoMulBean.setGame(game);
                            openNoMulBean.setItemType(2);
                            openNoMulList.add(openNoMulBean);
                        }
                    } else if (game == MARKSIX.getValue()) {
                        for (int i = 0; i < lastLotteryEntity.getNoList().size(); i++) {
                            OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                            openNoMulBean.setName(lastLotteryEntity.getNoList().get(i));
                            openNoMulBean.setAnimal(lastLotteryEntity.getAnimalList().get(i));
                            openNoMulBean.setColor(lastLotteryEntity.getColorList().get(i));
                            openNoMulBean.setGame(game);
                            openNoMulBean.setItemType(3);
                            openNoMulList.add(openNoMulBean);
                        }
                        //??????+???
                        OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                        openNoMulBean.setName("+");
                        openNoMulBean.setGame(game);
                        openNoMulBean.setItemType(3);
                        openNoMulList.add(6,openNoMulBean);
                    } else if(game == GameTypeEnum.DANDAN.getValue()){
                        int heZhi=0;
                        for (int i = 0; i < lastLotteryEntity.getNoList().size(); i++) {
                            String name = lastLotteryEntity.getNoList().get(i);
                            OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                            openNoMulBean.setName(name);
                            openNoMulBean.setGame(game);
                            openNoMulBean.setItemType(5);
                            openNoMulList.add(openNoMulBean);
                            heZhi+=Integer.parseInt(name);
                        }
                        OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                        openNoMulBean.setName("+");
                        openNoMulBean.setGame(game);
                        openNoMulBean.setItemType(5);
                        openNoMulList.add(1,openNoMulBean);
                        openNoMulList.add(3,openNoMulBean);

                        OpenNoMulBean openNoMulBean2 = new OpenNoMulBean();
                        openNoMulBean2.setName("=");
                        openNoMulBean2.setGame(game);
                        openNoMulBean2.setItemType(5);
                        openNoMulList.add(5,openNoMulBean2);

                        OpenNoMulBean openNoMulBeanHeZhi = new OpenNoMulBean();
                        openNoMulBeanHeZhi.setName(heZhi+"");
                        openNoMulBeanHeZhi.setGame(game);
                        openNoMulBeanHeZhi.setItemType(5);
                        openNoMulList.add(6,openNoMulBeanHeZhi);

                    } else {
                        for (String str : lastLotteryEntity.getNoList()) {
                            OpenNoMulBean openNoMulBean = new OpenNoMulBean();
                            openNoMulBean.setName(str);
                            openNoMulBean.setGame(game);
                            openNoMulBean.setItemType(4);
                            openNoMulList.add(openNoMulBean);
                        }

                    }
                    if(mOpenNoMulAdapter!=null){
                        mOpenNoMulList.clear();
                        mOpenNoMulList.addAll(openNoMulList);
                        mOpenNoMulAdapter.notifyDataSetChanged();
                        tv_lottery_qishu.setText(lotteryqishu+"?????????");
                        tv_lottery_name.setText(dataBean.getTypename());

                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(lottrry_open_result_constraintLayout);
                        lottrry_open_result_constraintLayout.setVisibility(VISIBLE);
                        if(openResultTimer!=null){
                            openResultTimer.cancel();
                        }
                        openResultTimer   =new Timer();
                        openResultTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(lottrry_open_result_constraintLayout!=null){
                                            YoYo.with(Techniques.SlideOutRight)
                                                    .duration(700)
                                                    .playOn(lottrry_open_result_constraintLayout);
                                            lottrry_open_result_constraintLayout.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                            }
                        },1000*10);
                    }
                }else {
                    isWaitopen=true;
                }
            }
        }else{
            Log.e(TAG, "onSuccess: ??????????????????" );
        }

    }

    /**
     * ???????????????99
     *
     */
    public void getCountDown(GifImageView gifImageView) {
        if(runnableTime!=null&&handler!=null){
            handler.removeCallbacks(runnableTime);
        }
        if(gifImageView!=null){
            gifImageView.setVisibility(VISIBLE);
        }
        countDownStatus=false;
        showLoadingLinear();
        Context mContext = MyApplication.getInstance();
        String countdown_url = "";
        Resources res = mContext.getResources();
        String[] countdowns = res.getStringArray(R.array.countdown_cpmovie);
        int game = dataBean.getGame();
        int type_id = dataBean.getType_id();
        if (game <= countdowns.length) {
            countdown_url = countdowns[game - 1];
        }
        new HttpApiImpl(SharePreferencesUtil.getString(MyApplication.getInstance(), CommonStr.CP_BASE_URL,BuildConfig.API_HOST2))
                .getCountDown(countdown_url, type_id)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                . subscribe(new BaseStringObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String result) {
                        countDownFailCount=0;
                         countDownFailSubject.onNext(countDownFailCount);
                        if(runnableTime!=null&&handler!=null){
                            handler.post(runnableTime);
                        }
                        if(gifImageView!=null){
                            gifImageView.setVisibility(GONE);
                        }
                        LogUtils.e("onSuccess " + result);
                        String resultData = Utils.initOldCpData("", result);

                        JSONObject jsonObject = JSONObject.parseObject(resultData);
                        String stoptimestr = jsonObject.getString("stoptimestr");//?????????????????????
                        nowQishu = jsonObject.getString("qishu");//????????????
                        if(StringMyUtil.isEmptyString(nowQishu)) {//????????????,????????????

                        }else{
//                            stoptv.setVisibility(View.GONE);

                            if(runnableTime==null){
                                handler.postDelayed(runnableTime,300);
                            }
                            if(runnableRequestOpen==null){
                                handler.postDelayed(runnableRequestOpen, jgTime);
                            }
                            tqtime = jsonObject.getString("tqtime");//????????????(?????????????????????????????????)
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                millionSeconds = simpleDateFormat.parse(stoptimestr).getTime();//?????????????????????
                                long nowTime = System.currentTimeMillis();//????????????
                                shijiancha = SharePreferencesUtil.getLong(StartLiveActivity.this,"shijiancha",0l);//?????????????????????????????????
                                countTime = millionSeconds + shijiancha - nowTime -(Long.parseLong(tqtime)*1000);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showTimeLInear();
                                    }
                                },600);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownStatus=true;
                    }

                    @Override
                    public void onFail(String msg) {
                        countDownFailCount++;
                        System.out.println("failCount  ???????????????");
                        countDownFailSubject.onNext(countDownFailCount);
                        if(runnableTime!=null&&handler!=null){
                            handler.post(runnableTime);
                        }
                        if(gifImageView!=null){
                            gifImageView.setVisibility(GONE);
                        }
                        LogUtils.e(msg);
                        countDownStatus=true;
                    }
                });
    }

    /**
     * ??????????????????
     */
    Runnable runnableRequestOpen =new Runnable() {
        @Override
        public void run() {
            if(isWaitopen&&countDownFailCount<=6){
                handler.removeCallbacks(runnableRequestOpen);
                requestOpenResult(dataBean.getGame(),dataBean.getType_id());
            }
            handler.postDelayed(runnableRequestOpen,jgTime);
        }
    };
    /**
     * ???????????????????????????
     */
    Runnable commitMessageRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "????????????  ???????????????");
            if(currentTextMessage==null){
                //currentTextMessage???????????????????????????,commitTextMessageArrayList??????????????????????????????
                requestCommitMessage(0);
                Log.e(TAG, "????????????  currentTextMessage==null");
            }else {
                //currentTextMessage????????????????????????,?????????currentTextMessage??????????????????
                if(commitLiveMessageModelList.size()!=0){
                    int currentIndex = commitLiveMessageModelList.indexOf(currentTextMessage);
                    if(currentIndex!=commitLiveMessageModelList.size()-1){//?????????????????????add???????????????????????????
                        requestCommitMessage(currentIndex+1);
                        Log.e(TAG, "????????????  currentTextMessage?????????   "+currentIndex);
                    }
                }
            }
            handler.postDelayed(this,5000);
        }
    };

    Runnable watchNumRunnable  = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(this);
            HashMap<String, Object> data = new HashMap<>();
            data.put("streamId",streamName);
            data.put("views",totalMemberCount<=0?1:totalMemberCount);
            HttpApiUtils.normalRequest(StartLiveActivity.this, null, RequestUtils.ONLINE_WATCH_NUM, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result) {
                    handler.postDelayed(watchNumRunnable,30*1000);
                    Log.e(TAG, "onSuccess: ?????????????????? :"+ data.get("views") +"    streamId:"+streamName);
                }

                @Override
                public void onFail(String msg) {
                    handler.postDelayed(watchNumRunnable,30*1000);
                    Log.e(TAG, "onSuccess: ?????????????????? :"+ data.get("views") +"     streamId:"+streamName);
                }
            });

        }
    };

    Runnable refreshAmountRunnable = new Runnable() {
        @Override
        public void run() {
            requestMoney(true);
        }
    };

    /**
     * ????????????????????????
     * @param startIndex ????????????list????????????
     */
    private void requestCommitMessage(int startIndex) {
        if(commitLiveMessageModelList.size()!=0){
            //?????????????????????????????????????????????????????????
            handler.removeCallbacks(commitMessageRunnable);
            HashMap<String, Object> data = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            //???????????????????????????????????????(?????????????????????????????????????????????????????????)
            LiveMessageModel lastLiveMessageModel=null;
            for (int i = startIndex; i < commitLiveMessageModelList.size() ; i++) {
                LiveMessageModel liveMessageModel = commitLiveMessageModelList.get(i);
                if(i==commitLiveMessageModelList.size()-1){
                    lastLiveMessageModel=liveMessageModel;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("anchorId", Utils.getUserInfoBean().getId());//??????id
                jsonObject.put("anchorName",Utils.getUserInfoBean().getNickname());//????????????
                jsonObject.put("content",liveMessageModel.getTextContent());//????????????
                jsonObject.put("nickName",getTextMessageNickName(liveMessageModel));//????????????????????????(nickName)
                jsonObject.put("rongId",liveMessageModel.getRcUserId());//?????????????????????id
                jsonObject.put("roomId",sp.getRoomId());//??????id
                jsonObject.put("userName",getTextMessageUserName(liveMessageModel));//???????????????????????????
                if(liveMessageModel.getMessageDirection() == io.rong.imlib.model.Message.MessageDirection.RECEIVE){
                    jsonObject.put("platformCode",liveMessageModel.getZkCode());//??????code
                }else {
                    //?????????????????????????????????,platformCode?????????zhubo
                    jsonObject.put("platformCode","zhubo");
                }
                jsonArray.add(jsonObject);
            }
            data.put("req",jsonArray.toJSONString());

            String content="";
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                content+=(jsonObject.getString("content")+",");

            }

            Log.e(TAG, "requestCommitMessage: ?????????????????????======"+content );

            LiveMessageModel finalLastLiveMessageModel = lastLiveMessageModel;
            HttpApiUtils.normalRequest(StartLiveActivity.this, null, RequestUtils.COMMIT_MESSAGE, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result) {
                    //???????????????currentTextMessage??????????????????????????????????????????
                    currentTextMessage = finalLastLiveMessageModel;
                    handler.postDelayed(commitMessageRunnable,5000);
                    Log.e(TAG, "???????????? ??????    ");
                }
                @Override
                public void onFail(String msg) {
                    handler.postDelayed(commitMessageRunnable,5000);
                    Log.e(TAG, "???????????? ??????   ");
                }
            });
        }
    }

    /**
     * ?????????????????????
     */
    Runnable liveTimeRunnable = new Runnable(){
        @Override
        public void run() {
            liveTime++;
            int mHour = (int) ((liveTime  ) / (60 * 60));  //  ???3600 ??????  ????????????
            int mMin = (int) (((liveTime  ) % (60 * 60)) / 60);//  ???3600 ????????????60 ??????????????????
            int mSecond = (int) ((liveTime ) % 60); //  ???60 ??????  ??????????????????
            String str_time = timeMode(mHour) + ":" + timeMode(mMin) + ":" + timeMode(mSecond);
            live_time_tv.setText(str_time);
            handler.postDelayed(this,1000);
        }
    };
    /**
     * ??????????????????
     */
    Runnable toyStatusRunnable = new Runnable() {
        @Override
        public void run() {
            if(StringMyUtil.isNotEmpty(toyId)){
                boolean connected = Lovense.getInstance(getApplication()).isConnected(toyId);
                if(connected){
                    toy_disconnect_tv.setVisibility(GONE);
                }else {
                    toy_disconnect_tv.setVisibility(GONE);
                }
            }
            handler.postDelayed(this,3000);
        }
    };
    /**
     * ???????????????timer?????????
     */
    Runnable runnableTime = new Runnable() {
        @Override
        public void run() {
            if(countDownFailCount>6){
                return;
            }
            if (countTime<=0){
                if (isWaitopen){
                    if(runnableRequestOpen!=null){
                        handler.removeCallbacks(runnableRequestOpen);
                    }
                    if(countDownStatus){
                        handler.postDelayed(runnableRequestOpen,jgTime);
                    }
                }

                getCountDown(null);
            }else {
                countTime = millionSeconds + shijiancha - System.currentTimeMillis() -(Long.parseLong(tqtime)*1000);
//                countTime = countTime - 1000;
                int mHour = (int) ((countTime / 1000) / (60 * 60));  //  ???3600 ??????  ????????????
                int mMin = (int) (((countTime / 1000) % (60 * 60)) / 60);//  ???3600 ????????????60 ??????????????????
                int mSecond = (int) ((countTime / 1000) % 60); //  ???60 ??????  ??????????????????
                String str_time;
                if (mHour == 0) {
                    str_time = timeMode(mMin) + ":" + timeMode(mSecond);
                } else {
                    str_time = timeMode(mHour) + ":" + timeMode(mMin) + ":" + timeMode(mSecond);
                }
                tv_countdown.setText(str_time);
                if(countTime<=1){
                    countTime=0;
                    isWaitopen=true;
                }
                EventBus.getDefault().postSticky(new CountDownEven(dataBean.getTypename(),nowQishu,countTime));
            }

            handler.postDelayed(runnableTime,300);
        }
    };


    Runnable onlinePeopleRunnable = new Runnable() {
        @Override
        public void run() {
            getChatRoomNum();
            handler.postDelayed(this,3000);
        }
    };
    /**
     * ??????????????????vip1????????????????????????
     */
    Runnable vip1JoinRunnable = new Runnable() {
        @Override
        public void run() {
            LiveMessageModel liveMessageModel = new LiveMessageModel();
            liveMessageModel.setManagerType("0");//????????????
            liveMessageModel.setLevelIcon("");
            liveMessageModel.setUserName(generateAccount(8));//?????????
            liveMessageModel.setStatus("0");//???????????????
            liveChatFragment.addJoinItem(liveMessageModel);
            handler.postDelayed(this,1000);
        }
    };

    //?????????????????????????????????????????????,
    public  String generateAccount(int length) {

        String val = "";
        Random random = new Random();

        //??????length??????????????????????????????
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //????????????????????????
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //???????????????????????????????????????
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return "??????"+val;
    }
    /**
     * Rxjava ??????totalMemberCount,???????????????????????????
     */
    private void observableOnlineNum() {
        onlineNumObservable
                .observeOn(AndroidSchedulers.mainThread())//??????????????????
                .subscribeOn(Schedulers.io())//?????????io??????
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer integer) {
                        //???????????????????????????
                        int showCount = integer * watchRadio;
                        chatUserEntityArrayList.clear();
                            /*
                            ????????????????????????60???, ???????????????????????????, ??????????????????60???
                             */
                        for (int i = 0; i < (showCount>=60?60:showCount); i++) {
                            int nextInt = new Random().nextInt(20);
                            if(chatUserEntityArrayList.size()< 60){
                                chatUserEntityArrayList.add(new ChatUserEntity(avatatArray[nextInt]));
                            }
                        }
                        chatUserAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * ??????????????????????????????????????????
     */
    private void observableCountFailCount() {
        countDownFailSubject
                .observeOn(AndroidSchedulers.mainThread())//??????????????????
                .subscribeOn(Schedulers.io())//?????????io??????
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer integer) {
                        if(count_down_fail_refresh_tv!=null){
                            if(countDownFailCount>6){
                                if(count_down_fail_refresh_tv.getVisibility()!=VISIBLE){
                                    count_down_fail_refresh_tv.setVisibility(VISIBLE);
                                }
                            }else {
                                if(count_down_fail_refresh_tv.getVisibility()!=GONE){
                                    count_down_fail_refresh_tv.setVisibility(GONE);
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * ????????????recycler
     */
    private void initOnlineRecycler() {
        watchRadio = SharePreferencesUtil.getInt(this,CommonStr.WATCH_RADIO,10);
        avatatArray = Const.getAvatatArray(this);
        chatUserAdapter = new ChatUserAdapter(R.layout.item_chatuser,chatUserEntityArrayList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recy_renshu.setLayoutManager(layoutManager);
        recy_renshu.setAdapter(chatUserAdapter);
    }

    /**
     * ???????????????
     */
    private void initSvgaImageView() {
        giftSvgaManage = new GiftSvgaManage(this,svgaImageView);

    }

    /**
     * ?????????view?????????
     */
    private void initGiftView() {
        giftView.setViewCount(3);
        giftView.init();
    }

    /**
     * ??????????????????fragment
     */
    private void initLiveChatFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        liveChatFragment= (LiveChatFragment) fragmentManager.findFragmentById(R.id.live_chat_fragment);
    }

    /**
     * ??????????????????
     * @param isRefreshAmount  ?????????????????????????????? ????????????????????????????????????????????????????????????
     */
    private void requestMoney(boolean isRefreshAmount) {
        if(refreshAmountRunnable!=null){
            handler.removeCallbacks(refreshAmountRunnable);
        }
        HttpApiUtils.wwwNormalRequest(this, null,RequestUtils.USER_MONEY, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                UserMoneyEntity userMoneyEntity = JSONObject.parseObject(result, UserMoneyEntity.class);
                String giftAmount = userMoneyEntity.getData().getGiftAmount();
                if(!isRefreshAmount){
                    money_tv.setText(giftAmount);
                }else {
                    handler.postDelayed(refreshAmountRunnable,60*1000);
                }
            }

            @Override
            public void onFail(String msg) {
                if(isRefreshAmount){
                    handler.postDelayed(refreshAmountRunnable,60*1000);
                }
            }
        });
    }
/*
    private void setViewMarginTop() {
        //??????toolBar???view???marginTop()
        ViewGroup.LayoutParams layoutParams = pusher_drawerLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams1.setMargins(0, getStatusBarHeight(this),0,0);
        pusher_drawerLayout.setLayoutParams(layoutParams1);
    }
*/

    @Override
    public boolean  onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isPusher){
                //???????????????????????????
                initFinishLivePop();
                return true;
            }else {
                return super.onKeyDown(keyCode, event);
            }

        }
        //?????????????????????????????????
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ??????????????????
     */
    private void initFinishLivePop() {
        if(commonChoosePop ==null){
            commonChoosePop =new CommonChoosePop(this,this,"????????????","???????????????????");
        }
        commonChoosePop.showAtLocation(beauty_iv, Gravity.CENTER,0,0);
        commonChoosePop.setOnClickLintener(new CommonChoosePop.OnClickLintener() {
            @Override
            public void onSureClick(View view) {
                commonChoosePop.dismiss();
                FinishLiveActivity.startAty(StartLiveActivity.this,streamName,mostHighMemberCount,watchRadio,roomAmount,"");
//                finish();
                finish();
            }
        });
    }

    private void requestStopLiveData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("onLine",mostHighMemberCount);
        data.put("streamName",streamName );
        HttpApiUtils.wwwRequest(this,null, RequestUtils.FINISH_LIVE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: ????????????,??????????????????????????????" );
            }

            @Override
            public void onFail(String msg) {
                Log.e(TAG, "onFail: ????????????,??????????????????????????????" );
            }
        });
    }
    @Override
    protected void initStatusBar() {
        super.initStatusBar();
//        initNavigateBar();
        StatusBarUtil.setColor(this, Color.WHITE);
        StatusBarUtil.setDarkMode(this);
    }
    public  void fullScreen(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }
    /**
     * ???????????????
     */
    private void initDrawerLayout() {
        DrawerLeftEdgeSize.setRightEdge(this,pusher_drawerLayout, 1f);
        //?????????????????????
        pusher_drawerLayout.setScrimColor(Color.TRANSPARENT);
        //??????????????????
        pusher_drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //?????????????????????????????????,???????????????????????????pop(?????????????????????????????????????????????)
//        beautyPop =  new BeautyPop(StartLiveActivity.this,StartLiveActivity.this,manager);
/*        beautyViewHolder = BeautyViewHolderFactory.getBeautyViewHolder(this, wrap_frameLayout);	//??????beautyViewHolder
        beautyViewHolder.setEffectListener(this);//????????????????????????
        beautyViewHolder.setMhBeautyManager(mhBeautyManager);*/
//???mhBeautyManager?????????mhBeautyManager???????????????????????????????????????????????????mhBeautyManager?????????null
//        beautyViewHolder.show(); //??????????????????
    }

    /**
     * @param context
     */
    public static void  startAty(Context context){
        context.startActivity(new Intent(context,StartLiveActivity.class));
    }

    /**
     * ????????????(?????????????????????????????????activity)
     */
    private void checkPermissions() {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE};
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(PERMISSIONS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        Log.e(TAG, "init: ??????????????????");
//                        initBeautyView();
                        initPusher();

                    } else {
                        Log.e(TAG, "init: ??????????????????");
                        showtoast("??????????????????????????????????????????app");
                        finish();
                    }
                });
    }


    /**
     * ??????????????????????????????
     */
    private  void initPusher() {
        FURenderer.setup(this);
        mFURenderer = new FURenderer
                .Builder(this)
                .setInputTextureType(FURenderer.INPUT_TEXTURE_2D)
                .setInputImageOrientation(CameraUtils.getCameraOrientation(mCameraFacing))
//                .setOnTrackStatusChangedListener(this)
                .setRunBenchmark(true)
                .build();
        faceUnityView.setModuleManager(mFURenderer);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if(USE_TX){
            //????????? TXLivePusher ?????? ????????????
            TXLivePushConfig mLivePushConfig = new TXLivePushConfig();

            mLivePusher = new CameraPushImpl(this, mPusherView);
            mLivePusher.getTXLivePusher().setVideoProcessListener(this);
            //??????????????????????????????????????????
            mLivePushConfig.setPauseImg(24*60*60*60,5);
            // ?????????????????????
            mLivePusher.getTXLivePusher().setVideoQuality(VIDEO_QUALITY_SUPER_DEFINITION,false,false);
          /*
         ?????????????????????????????? config ???????????????
         ?????????????????????????????????????????????
         */
            mLivePushConfig.setPauseFlag(PAUSE_FLAG_PAUSE_VIDEO);
            Bitmap bitmap = decodeResource(getResources(), R.drawable.pause_publish);
            mLivePushConfig.setPauseImg(bitmap);
            mLivePusher.getTXLivePusher().setConfig(mLivePushConfig);
            //???????????????????????????
            mLivePusher.getTXLivePusher().startCameraPreview(mPusherView);

            mLivePusher.getTXLivePusher().setPushListener(new ITXLivePushListener() {
                @Override
                public void onPushEvent(int event, Bundle bundle) {
                    Log.e(TAG, "onPushEvent: pusherId:"+bundle.getString("EVT_MSG")+"   CODE :"+event );
                    if (event == TXLiveConstants.PUSH_WARNING_SERVER_DISCONNECT) {
//                      //??????????????????
                        /**
                         * ???????????????
                         */
                        FinishLiveActivity.startAty(StartLiveActivity.this,streamName,mostHighMemberCount,watchRadio,roomAmount,"???????????????????????????");
                        finish();
                    }else if(event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT){
//                        ???????????????????????????????????????????????????????????????????????????
                        mLivePusher.getTXLivePusher().stopPusher();
                        mLivePusher.getTXLivePusher().startPusher(rtmpPushUrl);
                    }else if(event == TXLiveConstants.PUSH_WARNING_NET_BUSY){
                        //????????????,??????
                        showtoast("????????????????????????????????????????????????????????????????????????");
                    }else if(event ==  TXLiveConstants.PUSH_ERR_INVALID_ADDRESS ){
                        //??????????????????, ?????????????????????
                        CommonTipPop commonTipPop = new CommonTipPop(StartLiveActivity.this,"????????????","??????????????????,?????????????????????");
                        commonTipPop.setmOnDissmissListener(new BasePopupWindow.OnDissmissListener() {
                            @Override
                            public void onDissmiss() {
                                FinishLiveActivity.startAty(StartLiveActivity.this,streamName,mostHighMemberCount,watchRadio,roomAmount,"");
                                finish();
                            }
                        });
                        commonTipPop.showAtLocation(pusher_drawerLayout,Gravity.CENTER,0,0);
                    }
                }

                @Override
                public void onNetStatus(Bundle bundle) {
                    int net_speed = bundle.getInt("NET_SPEED");
                    if(null!=speed_tv){
                        if(net_speed<100) {
                            speed_tv.setTextColor(getResources().getColor(R.color.red));
                        }else {
                            speed_tv.setTextColor(getResources().getColor(R.color.green));
                        }
                        speed_tv.setText( Utils.getNetFileSizeDescription(net_speed*1024));
                    }


                }
            });
        }else {


/*            nodePublisher.setNodePublisherVideoTextureDelegate(new NodePublisherVideoTextureDelegate() {
                private int flags;

                @Override
                public void onCreateTextureCallback(NodePublisher streamer) {

                }

                @Override
                public void onChangeTextureCallback(NodePublisher streamer, boolean isFront, int cameraOri, int windowOri) {
                    flags = mFURenderer.getFlag(isFront);
                    mFURenderer.setRotationMode(cameraOri);
                }

                @Override
                public void onDestroyTextureCallback(NodePublisher streamer) {

                }

                @Override
                public int onDrawTextureCallback(NodePublisher streamer, int textureID, int width, int height, boolean isFront, int cameraOri) {
//                    return textureID;
                    return mFURenderer.setTextureID(flags,width,height,textureID);

                }
            });*/


            /**
             * ????????????
             */
            nodePublisher.setNodePublisherDelegate(new NodePublisherDelegate() {
                @Override
                public void onEventCallback(NodePublisher streamer, int event, String msg) {
                    System.out.println("dsdsdsdsdsd  "+event);
                }
            });
            /**
             *?????????????????????
             * ??????view
             * ???????????????
             * ??????????????????
             */
            nodePublisher.setCameraPreview(node_pusher_view,NodePublisher.CAMERA_FRONT,false);


            /**
             * ????????????
             */
            nodePublisher.setVideoParam(NodePublisher.VIDEO_PPRESET_16X9_720,30,200*1024*8,NodePublisher.VIDEO_PROFILE_MAIN,false);

            /**
             * ???????????????
             */
//            nodePublisher.setKeyFrameInterval(1);

            /**
             * ????????????
             */
            nodePublisher.setAudioParam(32000,AUDIO_PROFILE_HEAAC);

            /**
             * ????????????????????????
             */
            nodePublisher.setVideoEnable(true);

            /**
             * ????????????????????????
             */
            nodePublisher.setAudioEnable(true);

            /**
             * ????????????????????????
             */
            nodePublisher.setHwEnable(true);

            /**
             * ?????????????????????
             */
            nodePublisher.startPreview();

        }
    }

    /**
     * ???????????????????????????????????????????????????,??????/??????????????????
     * @param leaveAppEvenModel
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void leaveBackApp(leaveAppEvenModel leaveAppEvenModel){
        if(leaveAppEvenModel.isBack2App()){
            //app????????????,??????????????????
            if(mLivePusher!=null){
                mLivePusher.getTXLivePusher().resumePusher();
            }
        }else {
            //app????????????,??????????????????
            if(mLivePusher!=null){
                mLivePusher.getTXLivePusher().pausePusher();
            }
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToyConnectEvent mToyConnectEvent) {
        int connect = mToyConnectEvent.getConnect();
        isUseToy = connect;
        toyId = mToyConnectEvent.getId();
        if(toy_tv!=null&&toy_disconnect_tv!=null){
            if(connect==1){
                toy_tv.setText("???????????????");
            }else {
                toy_tv.setText("???????????????");
            }
        }

    }
    /**
     * ??????????????????????????????????????????????????? ????????????
     * @param stopPushEvenModel
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopPush(StopPushEvenModel stopPushEvenModel){
        if(stopPushEvenModel.isStopPush()){
            Log.e(TAG, "stopPush: ????????????????????????" );
            finish();
        }
    }
    /**
     * ??????????????????????????????
     */
    private void requestPushSuccess() {
        HashMap<String, Object> data = new HashMap<>();
        if(pushObject!=null){
            data.put("id",pushObject.getString("id"));
            data.put("pushUrl",rtmpPushUrl);
            data.put("streamName",streamName);
            data.put("nature",natureStr);

            HttpApiUtils.request(StartLiveActivity.this,null, RequestUtils.PUSH_SUCCESS, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result) {
                    initPushSuccess();
                }

                @Override
                public void onFail(String msg) {
                }
            });
        }
    }

    /**
     * ????????????????????????
     */
    private void initPushSuccess() {
        showtoast("??????????????????");
        isPusher =true;
        //????????????, ????????????????????????, ????????????
        live_constrainLayout_group.setVisibility(View.GONE);
        start_live_constraintLayout.setVisibility(View.GONE);
        pusher_drawerLayout.openDrawer(GravityCompat.END);
        //??????????????????
        pusher_drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        /**
         ?????????????????????
         */
        initGiftView();
        /**
         ?????????????????????
         */
        initSvgaImageView();
        /**
         * ????????????
         */
        requestMoney(false);
        /**
         * ????????????recycler
         */
        initOnlineRecycler();
        /**
         * ?????????????????????
         */
        initCpData();
        /**
         * ????????????
         */
        initRongYun();
        /**
         * ??????????????????
         */
        handler.postDelayed(liveTimeRunnable,1000);
        /**
         * ????????????????????????????????????
         */
        handler.postDelayed(toyStatusRunnable,3000);

        /**
         * ???5???????????????????????????????????????
         */
        handler.postDelayed(commitMessageRunnable,5000);

        /**
         * ???30s??????????????????????????????????????????
         */
        handler.postDelayed(watchNumRunnable,30*1000);

        /**
         * ?????????????????????????????????,???????????????????????????????????????
         */
        handler.postDelayed(refreshAmountRunnable,60*1000);




//        initNavigateBar();
    }


    public void initNavigateBar() {
        ImmersionBar immersionBar = ImmersionBar.with(this);
        immersionBar
                .titleBarMarginTop(pusher_drawerLayout)
                .statusBarColor(R.color.transparent)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .navigationBarColor(R.color.transparent)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)  //???????????????????????????
                .init();
    }


    /**
     * ???????????????,cp????????????????????????
     */
    private void initCpData() {
        //????????????????????????
        if(runnableTime!=null){
            handler.removeCallbacks(runnableTime);
        }
        handler.postDelayed(runnableTime,300);
        //??????????????????
        GlideLoadViewUtil.cpLoadNormalView(this,dataBean.getImage(),iv_lottery);
        /**
         * ????????????recyclerView
         */
        mOpenNoMulAdapter = new OpenNoMulAdapter(mOpenNoMulList);
        AutoLineFeedLayoutManager layoutManager = new AutoLineFeedLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);
        recy_lottery.setLayoutManager(layoutManager);
        recy_lottery.setItemAnimator(new DefaultItemAnimator());
        recy_lottery.setHasFixedSize(true);
        recy_lottery.setAdapter(mOpenNoMulAdapter);
    }

    /**
     * ?????? RTMP ????????????
     */
    private void getRTMPPusherFromServer( ) {
        if (mIsGettingRTMPURL) return;
        mIsGettingRTMPURL = true;
        HashMap<String, Object> data = new HashMap<>();
//        data.put("categoryId",categoryId);//??????id
        data.put("cover",cover);//??????
        data.put("deviceInfo", SystemUtil.getSystemModel());//????????????
        data.put("deviceNo", SystemUtil.getUniqueId(StartLiveActivity.this));//?????????
        data.put("title",title);//???????????????
        data.put("gameClassId",gameClassId);
        data.put("area",area.contains("??????")?"":area);
        data.put("type",roomType);//????????????(1??????2??????3??????4??????)
        data.put("amount",roomAmount);
        data.put("isUseToy",isUseToy);
        HttpApiUtils.request(this,null, RequestUtils.START_LIVE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                //???????????????????????????
                SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.LIVE_TITLE,title);
                mIsGettingRTMPURL = false;
                pushObject = JSONObject.parseObject(result).getJSONObject("data");
                String playUrl = pushObject.getString("pushUrl");
                try {
                    rtmpPushUrl = AESUtil.decrypt(playUrl);//????????????
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    streamName = AESUtil.decrypt(pushObject.getString("streamName"));//????????????
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(USE_TX){
                    int ret  = mLivePusher.getTXLivePusher().startPusher(rtmpPushUrl.trim());
//                int ret  = 0; //?????????,??????????????????
                    if(ret==0){
                        Log.e(TAG, "????????????");
                        requestPushSuccess();
                    }else {
                        if (ret == -5) {
                            Log.i(TAG, "startRTMPPush: license ????????????");
                            showtoast("license????????????");
                        }else if (ret == -1) {
                            Log.i(TAG, "????????????");
                            //????????????????????????????????????(??????????????????????????????)
                            requestStopLiveData();
                            showtoast("????????????,?????????");
                        }
                    }
                }else {
                    nodePublisher.setOutputUrl(rtmpPushUrl);
                    String livePassword = pushObject.getString("livePassword");
                    try {
                        nodePublisher.setCryptoKey(AESUtil.decrypt(livePassword));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int start = nodePublisher.start();
                    Log.e(TAG, "nodePublisher.start()"+ start );
                    if(start==0){
                        Log.e(TAG, "????????????");
                        requestPushSuccess();
                    }else {
                        requestStopLiveData();
                        showtoast("????????????,?????????");
                    }

                }

            }

            @Override
            public void onFail(String msg) {
                mIsGettingRTMPURL = false;
            }
        });
    }

    /**
     * ????????????????????????????????????????????????
     */
    private void initRongYun() {
        joinGiftSvgaMage = new JoinSvgaMage(this,join_svga_imageview);
        assetsSvgaManage = new AssetsSvgaManage(this,test_svga_imageview);
        RongLibUtils.addEventHandler(handler);
        joinChatRoom();
        editPanel.setInputLinstener();
    }

    /**
     * ???????????????
     */
    private void joinChatRoom() {
        RongLibUtils.joinChatRoom(-1, sp.getRoomId(), new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                /*
                ?????????????????????
                 */
                Log.e(TAG, "?????????????????????" );
//            ??????????????????????????????message
                LiveExitAndJoinMessage msgContent = new LiveExitAndJoinMessage(userDataBean.getNickname(), "0");
                msgContent.setUserInfoJson(RongLibUtils.setUserInfo(StartLiveActivity.this));
                RongLibUtils.sendMessage(sp.getRoomId(), msgContent);
//             ?????????????????????
                handler.postDelayed(onlinePeopleRunnable,3000);

                //VIP1??????????????????????????????
                handler.postDelayed(vip1JoinRunnable,1000);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //?????????????????????
                Log.e(TAG, "?????????????????????" );
            }
        });
    }

    /**
     ???????????????????????????
     */
    private void getChatRoomNum() {
        RongIMClient.getInstance().getChatRoomInfo(sp.getRoomId(), 0,
                ChatRoomInfo.ChatRoomMemberOrder.RC_CHAT_ROOM_MEMBER_ASC, new RongIMClient.ResultCallback<ChatRoomInfo>() {
                    @Override
                    public void onSuccess(ChatRoomInfo chatRoomInfo) {
                        //??????????????????
                        totalMemberCount = chatRoomInfo.getTotalMemberCount();
                        if(totalMemberCount>mostHighMemberCount){
                            mostHighMemberCount=totalMemberCount;
                        }
                        onlineNumObservable.onNext(totalMemberCount);

                        if(tv_num!=null){
                            tv_num.setText(totalMemberCount*watchRadio+"???");
                        }
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
    }


    /**
     *??????????????????
     */
    private void handlerGiftGear() {
        if(gearMessageList.size()!=0){
            LiveGiftMessage liveGiftMessage = gearMessageList.get(0);
            Lovense.getInstance(getApplication()).sendCommand(toyId, LovenseToy.COMMAND_VIBRATE, Integer.parseInt(liveGiftMessage.getGear()));
            isGearing =true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Lovense.getInstance(getApplication()).sendCommand(toyId, LovenseToy.COMMAND_VIBRATE, 0);
                    if(gearMessageList.size()!=0){
                        gearMessageList.remove(0);
                    }
                    isGearing =false;
                    handlerGiftGear();
                }
            },Integer.parseInt(liveGiftMessage.getGearTime())*1000);
        }
    }

    /** ?????????????????????,??????recyclerView itemModel???userInfoJson
     * @param messageModel
     * @param userInfoJson
     */
    private boolean initUserInfo(LiveMessageModel messageModel, String userInfoJson,String sendUserId) {
            if (StringMyUtil.isNotEmpty(userInfoJson)&&Utils.isJsonObject(userInfoJson)) {
                JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
                String userId = jsonObject.getString("userId");
                String name = jsonObject.getString("name");
                String level = jsonObject.getString("level");
                if(Utils.isNotInt(level)){
                    return true;
                }
                String portrait = jsonObject.getString("portrait");
                String platUserId = jsonObject.getString("platUserId");
                String managerType = jsonObject.getString("managerType");
                String medalIcon = jsonObject.getString("medalIcon");// ??????icon URL
                String levelIcon = jsonObject.getString("levelIcon");// ????????????URL
                String titleIcon = jsonObject.getString("titleIcon");// ??????URL ????????????????????????????????? ???
                String levelSVGA = jsonObject.getString("levelSVGA");// ????????????URL
                String mountSVGA = jsonObject.getString("mountSVGA");// ????????????URL
                if(StringMyUtil.isNotEmpty(medalIcon)&&!medalIcon.contains(Utils.getCpFirstImgurl())){
                    medalIcon = "";
                }
                if(StringMyUtil.isNotEmpty(levelIcon)&&!levelIcon.contains(Utils.getCpFirstImgurl())){
                    levelIcon = "";
                }
                if(StringMyUtil.isNotEmpty(titleIcon)&&!titleIcon.contains(Utils.getCpFirstImgurl())){
                    titleIcon = "";
                }
                if(StringMyUtil.isNotEmpty(levelSVGA)&&!levelSVGA.contains(Utils.getCpFirstImgurl())){
                    levelSVGA = "";
                }
                if(StringMyUtil.isNotEmpty(mountSVGA)&&!mountSVGA.contains(Utils.getCpFirstImgurl())){
                    mountSVGA = "";
                }
                String mountName = jsonObject.getString("mountName");// ?????????
                messageModel.setUserName(StringMyUtil.initEmptyStr(name));
                messageModel.setLevel(StringMyUtil.initEmptyStr(level,"1"));
                messageModel.setPortrait(StringMyUtil.initEmptyStr(portrait));
                messageModel.setManagerType(StringMyUtil.initEmptyStr(managerType));
                messageModel.setUserInfoJson(StringMyUtil.initEmptyStr(userInfoJson));
                messageModel.setPlatUserId(StringMyUtil.initEmptyStr(platUserId));
                messageModel.setMedalIcon(StringMyUtil.initEmptyStr(medalIcon));
                messageModel.setLevelIcon(StringMyUtil.initEmptyStr(levelIcon));
                messageModel.setTitleIcon(StringMyUtil.initEmptyStr(titleIcon));
                messageModel.setLevelSVGA(StringMyUtil.initEmptyStr(levelSVGA));
                messageModel.setMountSVGA(StringMyUtil.initEmptyStr(mountSVGA));
                messageModel.setMountName(StringMyUtil.initEmptyStr(mountName));
                if(messageModel.getManagerType().equals("1") && Utils.isNotLong(sendUserId)){
                    return true;
                }
                return  false;

            }

        return true;
    }
    private String getTextMessageNickName(LiveMessageModel LiveMessageModel){
        String userInfoJson = LiveMessageModel.getUserInfoJson();
        if(StringMyUtil.isNotEmpty(userInfoJson)){
            JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
            return jsonObject.getString("name");
        }
        return "";
    }
    private String getTextMessageUserName(LiveMessageModel LiveMessageModel){
        String userInfoJson = LiveMessageModel.getUserInfoJson();
        if(StringMyUtil.isNotEmpty(userInfoJson)){
            JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
            return jsonObject.getString("platUserId");
        }
        return "";
    }
    /**
     * ???????????????,???????????????????????????,?????????????????????
     * @param view
     * @param event
     */
    @OnTouch(R.id.drawwe_linear)
    public void onTouch(View view, MotionEvent event){
        editPanel.editLinear.setVisibility(GONE);
        editPanel.inputClickLinear.setVisibility(VISIBLE);
        linearLayout7.setVisibility(VISIBLE);
        Utils.hideSoftKeyBoard(this);
    }


    @OnClick({R.id.pusher_tx_cloud_view,R.id.beauty_iv,R.id.start_live_btn,R.id.change_camera_iv,
            R.id.choose_channel_tv,R.id.choose_channel_iv,R.id.add_title_frameLayout,R.id.bottom_pop_iv,R.id.meiyan_iv,
            R.id.iv_close,R.id.choose_lottery_iv,R.id.choose_lottery_tv,R.id.more_iv,R.id.dismiss_open_result_iv,R.id.iv_lottery ,
            R.id.ll_gift,R.id.set_business_iv,R.id.set_business_tv,R.id.top_close_iv,R.id.room_type_tv,R.id.room_type_iv,
            R.id.change_room_type_iv,R.id.start_live_constraintLayout,R.id.nature_tv,R.id.nature_iv,R.id.rank_linear,
            R.id.face_top_linear,R.id.area_iv,R.id.area_tv,R.id.count_down_fail_refresh_tv,R.id.toy_iv,R.id.toy_tv,R.id.toy_disconnect_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toy_iv:
            case R.id.toy_tv:
            case R.id.toy_disconnect_tv:
                startActivity(new Intent(StartLiveActivity.this, ToyActivity.class));
                break;
            case R.id.count_down_fail_refresh_tv:
                getCountDown(count_down_fail_loading_iv);
                requestOpenResult(dataBean.getGame(),dataBean.getType_id());
                break;
            case R.id.area_iv:
            case R.id.area_tv:
                showCityPickerVIew();
                break;
            case R.id.rank_linear:
                initLiveRankDialogFragment();
                break;
            case R.id.nature_tv:
            case R.id.nature_iv:
                initChooseNaturePop();
                chooseNaturePop.showAtLocation(nature_iv,Gravity.BOTTOM,0,0);
                Utils.darkenBackground(StartLiveActivity.this,0.7f);
                break;
            //??????????????????
            case R.id.change_room_type_iv:
                initChangeRoomTypePop();
                break;
            //??????????????????
            case R.id.room_type_tv:
            case R.id.room_type_iv:
                initUnPusherSetRoomTypePop();
                break;
            //?????????????????? ???????????? ??????????????????
            case R.id.beauty_iv:
                live_constrainLayout_group.setVisibility(View.GONE);
                face_wrap_linear.setVisibility(VISIBLE);
                break;
            //??????????????????
            case R.id.start_live_btn:
                if(checkPush()){
                    getRTMPPusherFromServer();
                }
                break;
            //???????????????
            case R.id.change_camera_iv:
                isBackCamera=!isBackCamera;
                if(USE_TX){
                    mLivePusher.switchCamera();

                    mCameraFacing = IFURenderer.CAMERA_FACING_FRONT - mCameraFacing;
                    if (mFURenderer != null) {
                        mFURenderer.onCameraChanged(mCameraFacing, CameraUtils.getCameraOrientation(mCameraFacing));
                        if (mFURenderer.getMakeupModule() != null) {
                            mFURenderer.getMakeupModule().setIsMakeupFlipPoints(mCameraFacing == IFURenderer.CAMERA_FACING_FRONT ? 0 : 1);
                        }
                    }
                }else {
                    nodePublisher.switchCamera();
                }

                break;
            //????????????,???????????????
            case R.id.drawwe_linear:
                editPanel.editLinear.setVisibility(GONE);
                editPanel.inputClickLinear.setVisibility(VISIBLE);
                linearLayout7.setVisibility(VISIBLE);
                Utils.hideSoftKeyBoard(this);
                break;
            //????????????
            case R.id.choose_channel_iv:
            case R.id.choose_channel_tv:
                startActivityForResult(new Intent(StartLiveActivity.this,ChooseChannelActivity.class),SKIP_CHOOSE_CHANNEL);
                break;
            //????????????
            case R.id.add_title_frameLayout:
                if(takeCameraPop ==null){
                    takeCameraPop = new TakeCameraPop(StartLiveActivity.this);
//                    takeCameraPop.initPop();
                    takeCameraPop.setOnPopClickListener(this);
                }
                Utils.darkenBackground(StartLiveActivity.this,0.7f);
                takeCameraPop.showAtLocation(choose_channel_iv, Gravity.BOTTOM,0,0);
                break;
            //????????????
            case R.id.iv_close:
                initFinishLivePop();
                break;
            //????????????
            case R.id.choose_lottery_iv:
            case R.id.choose_lottery_tv:
                startActivityForResult(new Intent(StartLiveActivity.this,ChooseLotteryActivity.class),SKIP_CHOOSE_LOTTERY);
                break;
            //????????????(???????????????.?????????.???????????????)
            case R.id.more_iv:
                initLiveMorePop();
                liveMorePop.showAtLocation(more_iv,Gravity.BOTTOM,0,0);
                break;
                /*
                ??????????????????
                 */
            case R.id.meiyan_iv:
//                beautyPop =  new BeautyPop(StartLiveActivity.this,StartLiveActivity.this,manager);
                drawwe_linear.setVisibility(GONE);//??????????????????
                face_wrap_linear.setVisibility(VISIBLE);
         /*       if(!beautyViewHolder.isShowed()){
                    beautyViewHolder.show();
                }*/
//                beautyPop.showAtLocation(beauty_iv, Gravity.BOTTOM,0,0);
/*                beautyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        pusher_drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        drawwe_linear.setVisibility(View.VISIBLE);//??????????????????
                    }
                });*/
                break;
            case R.id.face_top_linear:
                if(isPusher){
                    //???????????????,????????????
                    drawwe_linear.setVisibility(VISIBLE);//??????????????????

                }else {
                    //?????????,???????????????????????????
                    live_constrainLayout_group.setVisibility(VISIBLE);
                }
                face_wrap_linear.setVisibility(GONE);
                break;
            //?????????????????????????????????
            case R.id.dismiss_open_result_iv:
                lottrry_open_result_constraintLayout.setVisibility(View.INVISIBLE);
                break;
            //????????????
            case R.id.iv_lottery:
                initOpenResult();
                break;
            //????????????
            case R.id.ll_gift:
                MineDetailsActivity.startAty(StartLiveActivity.this,3);
                break;
            //???????????? ?????? ?????????pop
            case R.id.bottom_pop_iv:
                BottomDialogFragment bottomDialogFragment = new BottomDialogFragment(liveChatFragment.liveMessageModelArrayList);
                bottomDialogFragment.show(getSupportFragmentManager(),"IntegralDetailsDialog");
                break;
            //????????????
            case R.id.set_business_iv:
            case R.id.set_business_tv:
                if(Utils.getUserInfoBean().getForbidCallingCard().equals("1")){
                    canNotSetBussiness();
                }else {
                    initSetBussinessop();
                    setBusinessCardPop.showAtLocation(set_business_iv,Gravity.CENTER,0,0);
                    Utils.darkenBackground(StartLiveActivity.this,0.7f);
                }
                break;
            //?????????????????????????????????
            case R.id.top_close_iv:
                finish();
                break;
            default:
                break;
        }
    }

    private void showCityPickerVIew() {
        if(isCityLoaded){
            OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //?????????????????????????????????????????????
                    String opt1tx = provinceOptionsItems.size() > 0 ?
                            provinceOptionsItems.get(options1).getPickerViewText() : "";

                    String opt2tx = cityOptionsItems.size() > 0
                            && cityOptionsItems.get(options1).size() > 0 ?
                            cityOptionsItems.get(options1).get(options2) : "";

                    String opt3tx = cityOptionsItems.size() > 0
                            && areaOptionsItems.get(options1).size() > 0
                            && areaOptionsItems.get(options1).get(options2).size() > 0 ?
                            areaOptionsItems.get(options1).get(options2).get(options3) : "";

                    String tx = opt1tx + opt2tx + opt3tx;
                    Log.e(TAG, "onOptionsSelect: "+ tx);
                    area_tv.setText(opt2tx);
                    area = opt2tx;
                    SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.LIVE_AREA,String.format("%s-%s",opt1tx,opt2tx));

                }
            })
                    .setTitleText("????????????")
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //???????????????????????????
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(provinceOptionsItems, cityOptionsItems, areaOptionsItems);//???????????????
            pvOptions.show();
        }else {
            showtoast("????????????????????????,?????????");
        }
    }

    private void canNotSetBussiness() {
        CommonTipPop commonTipPop = new CommonTipPop(StartLiveActivity.this,
                "??????", "????????????????????????,?????????!???????????????????????????????????????????????????????????????!");
        commonTipPop.showAtLocation(set_business_tv, Gravity.CENTER,0,0);
        Utils.darkenBackground(this,0.7f);
    }

    private void initLiveRankDialogFragment() {
//        LiveRankDialogFragment liveRankDialogFragment = new LiveRankDialogFragment(LiveFragment.this);
        liveRankDialogFragment = new LiveRankDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("anchorAccount","mLiveData.getAnchorAccount()");
        liveRankDialogFragment.setArguments(bundle);
        liveRankDialogFragment.show(getSupportFragmentManager(),"liveRankDialogFragment");
    }
    private void initSetBussinessop() {
        if(setBusinessCardPop==null){
            setBusinessCardPop = new SetBusinessCardPop(StartLiveActivity.this);
            setBusinessCardPop.setOnPopClickListener(this);
            setBusinessCardPop.setmOnDissmissListener(new BasePopupWindow.OnDissmissListener() {
                @Override
                public void onDissmiss() {
//                    ImmersionBar.with(StartLiveActivity.this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
                }
            });
        }
    }

    private void initChooseNaturePop() {
        if(chooseNaturePop==null){
            chooseNaturePop = new ChooseNaturePop(StartLiveActivity.this,false);
            chooseNaturePop.setOnPopItemClick(this);
            natureEntityArrayList = chooseNaturePop.getNatureEntityArrayList();
        }
    }

    private void initChangeRoomTypePop() {
        changeRoomTypePop = new ChangeRoomTypePop(StartLiveActivity.this,true,roomType);
        changeRoomTypePop.setOnPopClickListener(StartLiveActivity.this);
        changeRoomTypePop.showAtLocation(more_iv,Gravity.BOTTOM,0,0);
        Utils.darkenBackground(StartLiveActivity.this,0.7f);
    }

    /**
     * ??????????????????
     */
    private void initUnPusherSetRoomTypePop() {
        if(unPusherSetRoomTypePop==null){
            unPusherSetRoomTypePop = new SetRoomTypePop(StartLiveActivity.this,roomType);
            unPusherSetRoomTypePop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
                @Override
                public void onPopClick(View view) {
                    switch (view.getId()){
                        case R.id.set_room_type_free_tv:
                            room_type_tv.setText("????????????(??????)");
                            roomType=1;
                            unPusherSetRoomTypePop.dismiss();

                            break;
                        case R.id.set_room_type_sure_tv:
                            String amountToString = unPusherSetRoomTypePop.set_amount_etv.getText().toString();
                            roomAmount = (StringMyUtil.isEmptyString(amountToString)?"0": amountToString);
                            BigDecimal bigDecimal = new BigDecimal(roomAmount);
                            if(bigDecimal.compareTo(BigDecimal.ZERO)<=0||bigDecimal.compareTo(new BigDecimal(1000))==1){
                                showtoast("?????????0.1-1000?????????");
                            }else {
                                roomType=2;
                                room_type_tv.setText("????????????(?????? "+ roomAmount+"???/10??????"+")");
                                unPusherSetRoomTypePop.dismiss();

                            }
                            break;
                        default:
                            break;
                    }
                }
            });
            unPusherSetRoomTypePop.setmOnDissmissListener(new BasePopupWindow.OnDissmissListener() {
                @Override
                public void onDissmiss() {
                    Utils.darkenBackground(StartLiveActivity.this,1f);
                    Utils.hideSoftKeyBoard(StartLiveActivity.this);
                }
            });
        }

        unPusherSetRoomTypePop.showAtLocation(room_type_tv,Gravity.CENTER,0,-300);
        Utils.showKeyboard(unPusherSetRoomTypePop.set_amount_etv);
        Utils.darkenBackground(StartLiveActivity.this,0.7f);
    }

    private void initLiveMorePop() {
        if(liveMorePop==null){
            liveMorePop = new LiveMorePop(StartLiveActivity.this);
            liveMorePop.setOnPopClickListener(this);
        }
    }

    /**
     * ??????????????????????????????
     */
    private boolean checkPush() {
/*        if(StringMyUtil.isEmptyString(categoryId)){
            showtoast("???????????????");
            return false;
        }*/
        if(StringMyUtil.isEmptyString(cover)){
            showtoast("???????????????");
            return false;
        }
        if(StringMyUtil.isEmptyString(gameClassId)){
            showtoast("???????????????");
            return false;
        }
        if(StringMyUtil.isEmptyString(title)){
            showtoast("??????????????????");
            return  false;
        }
        if(Utils.getUserInfoBean().getForbidCallingCard().equals("0")){
            if(StringMyUtil.isEmptyString(busonessCard)){
                showtoast("???????????????");
                return false;
            }
        }
        if(StringMyUtil.isEmptyString(natureStr)){
            showtoast("?????????????????????");
            return  false;
        }
        if(StringMyUtil.isEmptyString(area)){
            showtoast("???????????????");
            return  false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String photoPath;
        String s = null;
        if(requestCode==SKIP_CHOOSE_CHANNEL&&resultCode==RESULT_OK){
            /*
            ??????????????????
             */
            HomeClassfyEntity.DataBean dataBean = (HomeClassfyEntity.DataBean) data.getSerializableExtra("dataBean");
            categoryId= dataBean.getId();
            choose_channel_tv.setText(dataBean.getName());
            SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_CHANNEL_NAME,dataBean.getName());
            SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_CHANNEL_ID,categoryId);

        } else if(requestCode==CAMARE_REQUEST_CODE){
            /*
            ???????????????
             */
            if(USE_TX){
                mLivePusher.getTXLivePusher().startCameraPreview(mPusherView);//???????????????????????????
            }else {
                nodePublisher.startPreview();
            }
            if(resultCode==RESULT_OK){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath = String.valueOf(cameraSavePath);
                    //???????????????????????????
//                    showBitMap(String.valueOf(cameraSavePath));
                    s = BitmapUtils.compressReSave(photoPath, this, 400);//????????????
                } else {
                    photoPath = uri.getEncodedPath();
                    s = BitmapUtils.compressReSave(photoPath, this, 400);//????????????
                }
                // ????????????
                if(StringMyUtil.isEmptyString(s)){
                    showtoast("??????????????????, ?????????");
                    return;
                }
                upLoadImg(s);
            }
        }
        else if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            /*
            ???????????????
             */
            String realPathFromUri = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            //???????????????????????????
            s = BitmapUtils.compressReSave(realPathFromUri, StartLiveActivity.this, 400);//????????????
            if(StringMyUtil.isEmptyString(s)){
                showtoast("??????????????????, ?????????");
                return;
            }
            if (!StringMyUtil.isEmptyString(realPathFromUri)) {
                upLoadImg(s);
            } else {
                showtoast("??????????????????,?????????");
            }
        }else if(requestCode==SKIP_CHOOSE_LOTTERY&&resultCode==RESULT_OK){
            /*
            ??????????????????
             */
            dataBean = (LotteryEntitiy.GameClasslistBean) data.getSerializableExtra("dataBean");
            String typeName = dataBean.getTypename();
            gameClassId = dataBean.getId();
            choose_lottery_tv.setText(typeName);
            SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_LOTTERY_BEAN, JSON.toJSONString(dataBean));//?????????????????????bean


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * ????????????Pop
     */
    private void initOpenResult() {
//        int game = 2;//mViewModel.getCpLiveData().getValue().getGame();
//        int typeId = 1;//mViewModel.getCpLiveData().getValue().getTypeId();
        int game =dataBean.getGame();
        int typeId= dataBean.getType_id();
        switch (GameTypeEnum.valueOf(game)) {
            case KUAISAN:
                customPopupWindow.initKuaiSanTodayResult(new WeakReference<>(this), typeId, true);
                customPopupWindow.initKuaiSanTodayResultData(this, beauty_iv, typeId);
                //    customPopupWindow.showKuaiSanTodayResultPop(beauty_iv,this);
                break;
            case SSC:
            case XUANWU:
                customPopupWindow.initSscTodayResultPop(this, game, typeId, true);
                customPopupWindow.initSscTodayResultData(this, game, typeId, beauty_iv);
                break;
            case RACE:
                customPopupWindow.initRaceTodayResultPop(this, game, typeId, true);
                customPopupWindow.initRaceTodayResultData(this, game, typeId, beauty_iv);
                break;
            case MARKSIX:
                customPopupWindow.initMarksixTodayResultPop(this, game, typeId, true);
                customPopupWindow.initMarksixTodayResultData(this, game, typeId, beauty_iv);
                break;
            case DANDAN:
                customPopupWindow.initPcddTodayResult(this, typeId, true);
                customPopupWindow.initPcddTodayResultData(this, beauty_iv, typeId);
                break;
            case HAPPY8:
                customPopupWindow.initHappy8TodayResult(this, typeId, true);
                customPopupWindow.initPcddTodayResultData(this, beauty_iv, typeId);
                break;
            case LUCKFARM:
                customPopupWindow.initHappy10TodayResult(this, game, typeId, true);
                customPopupWindow.initfarmTodayResultData(this, beauty_iv, typeId);
                break;
            case HAPPY10:
                customPopupWindow.initHappy10TodayResult(this, game, typeId, true);
                customPopupWindow.initHappy10TodayResultData(this, beauty_iv, typeId);
                break;
            default:
                break;
        }

    }
    /**
     * ????????????
     * @param fliePath  ????????????????????????
     */
    private void upLoadImg(String fliePath) {
        HttpApiImpl.getInstance()
                .uploadFile(fliePath)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseStringObserver<ResponseBody>(){
                    @Override
                    public void onSuccess(String result) {
                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
                        com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
                        if(data!=null){
                            //????????????
                            cover = data.getString("url");
                            //??????????????????
                            SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_LIVE_COVER,cover);
                            //????????????
                            GlideLoadViewUtil.LoadNormalView(StartLiveActivity.this,cover,cover_iv);
                        }

                    }

                    @Override
                    public void onFail(String msg) {
                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                        showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        super.onRequestEnd();
                        closeLoading();
                    }
                });
    }

    /**
     * ???????????????????????????
     * @param Editable
     */
    @OnTextChanged(value=R.id.start_live_title_etv,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void  onTextChanged(Editable Editable ){
        String afterChangeStr = Editable.toString();
//        start_live_titile_tv.setText(StringMyUtil.isEmptyString(afterChangeStr)?"????????????":afterChangeStr);
        title = afterChangeStr;

    }
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
//        data.put("liveId",)
        RongLibUtils.removeEventHandler(handler);
        /**
         * ???????????????
         */
        if(USE_TX){
            if(mLivePusher!=null){
                Log.e(TAG, "onDestroy: ????????????" );
                mLivePusher.getTXLivePusher().stopCameraPreview(true);//?????????????????????
                mLivePusher.getTXLivePusher().stopPusher();//????????????
            }
        }else {
            if(nodePublisher!=null){
                nodePublisher.stopPreview();
                nodePublisher.stop();
                nodePublisher.release();
            }
        }
        /**
         * ???????????????
         */
        RongLibUtils.quitChatRoom(sp.getRoomId(), new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: ?????????????????????" );
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "onError: ?????????????????????" );
            }
        });
        /**
         ????????????
         */

        if(editPanel!=null){
            HeightProvider heightProvider = editPanel.getHeightProvider();
            if(heightProvider!=null&& heightProvider.isShowing()){
                heightProvider.dismiss();
            }
        }
        /**
         * ??????????????????
         */
        if(toyId!=null){
            if (Lovense.getInstance(getApplication()).isConnected(toyId)) {
                Lovense.getInstance(getApplication()).disconnect(toyId);
            }
        }
        super.onDestroy();
    }

    /**
     * pop??????????????????
     * @param view
     */
    @Override
    public void onPopClick(View view) {
        switch (view.getId()){
            case R.id.forbidden_tv:
                //????????????
                takeCameraPop.dismiss();
                checkCameraPermission();
                break;
            case R.id.set_manager_tv:
                //????????????
                takeCameraPop.dismiss();
                checkPhotoPermission();
                break;
            case R.id.swich_camare_iv:
            case  R.id.swich_camare_tv:
                //???????????????
                initSwichCamare();
                break;
            case R.id.swich_video_iv:
            case R.id.swich_video_tv:
                initVideoPusher();
                break;
            case R.id.swich_microphone_iv:
            case R.id.swich_microphone_tv:
                //???????????????
                initSwitchMicophone();
                break;

            case R.id.swich_light_iv:
            case R.id.swich_light_tv:
                //???????????????
                initSwichLight();
                break;
            //????????????
            case R.id.set_business_card_sure_tv:
                setBusinessCard();
                break;
            case R.id.set_business_card_cancel_tv:
                if(setBusinessCardPop!=null){
                    setBusinessCardPop.dismiss();
                }
                break;
            //??????????????????
            case R.id.change_room_type_iv:
            case R.id.change_room_type_tv:
                if(roomType==2){
                    // ?????????????????????,?????????????????????,????????????pop
                    initToll2FreePop();
                }else {
                    if(roomAmount.equals("0")){
                        //???????????????????????????,???????????????????????????pop
                        initPusherSetRoomTypePop();
                    }else {
                        //  ????????????????????????,????????????
                        initFree2TollPop();
                    }
                }

                break;
            default:
                break;
        }
    }
    /**
     * pop recyclerView ???????????????
     * @param view
     * @param position
     */
    @Override
    public void onPopItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.nature_content_tv:
                /*
                ??????pop item??????
                 */
                String content = natureEntityArrayList.get(position).getContent();
                SharePreferencesUtil.putString(MyApplication.getInstance(),CommonStr.CHOOSE_NATURE,content);
                setDefaultNature();
                chooseNaturePop.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * ???????????????????????????pop
     */
    private void initFree2TollPop() {
        if(free2TollTipPop==null){
            free2TollTipPop = new CommonChoosePop(StartLiveActivity.this,StartLiveActivity.this,"????????????","??????????????????????????????");
            free2TollTipPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(changeRoomTypePop!=null){
                        changeRoomTypePop.dismiss();
                    }
                }
            });
            free2TollTipPop.setOnClickLintener(new CommonChoosePop.OnClickLintener() {
                @Override
                public void onSureClick(View view) {
                    requestChangeRoomType(2);
                }
            });
        }
        free2TollTipPop.showAtLocation(room_type_tv, Gravity.CENTER,0,-300);
        Utils.darkenBackground(StartLiveActivity.this,0.7f);
    }

    /**
     * ???????????????????????????pop
     */
    private void initToll2FreePop() {
        if(toll2FreeTipPop==null){
            toll2FreeTipPop = new CommonChoosePop(StartLiveActivity.this,StartLiveActivity.this,"????????????","??????????????????????????????");
            toll2FreeTipPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(changeRoomTypePop!=null){
                        changeRoomTypePop.dismiss();
                    }
                }
            });
            toll2FreeTipPop.setOnClickLintener(new CommonChoosePop.OnClickLintener() {
                @Override
                public void onSureClick(View view) {
                    requestChangeRoomType(1);
                }
            });
        }
        toll2FreeTipPop.showAtLocation(room_type_tv, Gravity.CENTER,0,-300);
        Utils.darkenBackground(StartLiveActivity.this,0.7f);
    }

    /**
     * ?????????,??????????????????????????????,???????????????????????????pop
     */
    private void initPusherSetRoomTypePop() {
        pusherChangeRoomTypePop = new SetRoomTypePop(StartLiveActivity.this,true);
        pusherChangeRoomTypePop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.set_room_type_free_tv:
                        initChangeRoomType(1,true);
                        break;
                    case R.id.set_room_type_sure_tv:
                        initChangeRoomType(2,false);
                        break;
                    default:
                        break;
                }
            }
        });
        pusherChangeRoomTypePop.showAtLocation(room_type_tv, Gravity.CENTER,0,-300);
        Utils.darkenBackground(StartLiveActivity.this,0.7f);
    }

    private void initChangeRoomType( int requestType,boolean isFree) {
        if(isFree){
            if (roomType == 1) {
                pusherChangeRoomTypePop.dismiss();
                changeRoomTypePop.dismiss();
            } else {
                requestChangeRoomType(requestType);
            }
        }else {
            String amountToString = pusherChangeRoomTypePop.set_amount_etv.getText().toString();
            roomAmount = (StringMyUtil.isEmptyString(amountToString)?"0": amountToString);
            BigDecimal bigDecimal = new BigDecimal(roomAmount);
            if(bigDecimal.compareTo(BigDecimal.ZERO)<=0||bigDecimal.compareTo(new BigDecimal(1000))==1){
                showtoast("?????????0.1-1000?????????");
            }else {
                if (roomType == 2) {
                    pusherChangeRoomTypePop.dismiss();
                    changeRoomTypePop.dismiss();
                } else {
                    requestChangeRoomType(requestType);
                }
            }

        }

    }

    /**
     * ????????????????????????
     * @param type ??????????????????????????????type  1 ?????? 2 ??????
     */
    private void requestChangeRoomType(int type) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("amount", type==1?0:roomAmount);
        data.put("streamName", streamName);
        data.put("type", type);
        HttpApiUtils.wwwRequest(StartLiveActivity.this, null, RequestUtils.CHANGE_ROOM_TYPE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                ChangeRoomTypeEntity changeRoomTypeEntity = JSONObject.parseObject(result, ChangeRoomTypeEntity.class);
                //String type, String zkCode
                int nowType = changeRoomTypeEntity.getData().getType();
                int type=0;
                roomType= nowType;
                if(nowType==1){
                    showtoast("?????????????????????????????????");
                    type=1;
                }else {
                    showtoast("?????????????????????????????????");
                    type=2;
                }
                SwichMoneyMessage swichMoneyMessage = new SwichMoneyMessage(type + "", "");
                swichMoneyMessage.setUserInfoJson(RongLibUtils.setUserInfo(StartLiveActivity.this));
                RongLibUtils.sendMessage(sp.getRoomId(),swichMoneyMessage);

                if(pusherChangeRoomTypePop!=null){
                    pusherChangeRoomTypePop.dismiss();
                }
                if(changeRoomTypePop!=null){
                    changeRoomTypePop.dismiss();
                }
                if(toll2FreeTipPop!=null){
                    toll2FreeTipPop.dismiss();
                }
                if(free2TollTipPop!=null){
                    free2TollTipPop.dismiss();
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    /**
     * ????????????
     */
    private void setBusinessCard() {
        busonessCard = setBusinessCardPop.set_business_card_etv.getText().toString();
        if(StringMyUtil.isEmptyString(busonessCard)){
            showtoast("????????????????????????");
        }else {
            modifyBusinessCard();
        }
    }
    /**
     * ????????????
     * @param
     */
    private void modifyBusinessCard( ) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("callingCard",busonessCard);
        HttpApiUtils.request(this,null, RequestUtils.MODIFY_USERINFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                showtoast("????????????");
                set_business_tv.setText(busonessCard);
                //??????????????????????????????
                UserInfoEntity.DataBean userInfoBean = Utils.getUserInfoBean();
                userInfoBean.setCallingCard(busonessCard);
                Utils.setUserInfoBean(userInfoBean);
                setBusinessCardPop.dismiss();
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    /**
     * ???????????????
     */
    private void initVideoPusher() {
        if(isPausePusher){
            //????????????????????????,??????????????????
            if(USE_TX){
                mLivePusher.getTXLivePusher().resumePusher();
            }else {
                nodePublisher.setVideoEnable(true);
            }
            liveMorePop. swich_video_iv.setImageResource(R.drawable.gbsxt);
            liveMorePop.swich_video_tv.setText("???????????????");
        }else{
            //????????????????????????,????????????????????????
            if(USE_TX){
                mLivePusher.getTXLivePusher().pausePusher();
            }else {
                nodePublisher.setVideoEnable(false);
            }
            liveMorePop. swich_video_iv.setImageResource(R.drawable.kqsxt);
            liveMorePop.swich_video_tv.setText("???????????????");
            //??????????????????,???????????????
            if(USE_TX){
                mLivePusher.getTXLivePusher().turnOnFlashLight(false);
            }else {
                nodePublisher.setFlashEnable(false);
            }
            if(liveMorePop!=null){
                //pop???????????????????????????
                liveMorePop.swich_light_iv.setImageResource(R.drawable.kqsgd);
                liveMorePop. swich_light_tv.setText("???????????????");
            }

        }
        isPausePusher=!isPausePusher;
    }

    /**
     * ?????????????????????????????????
     */
    private void showTimeLInear() {
        isStart=dataBean.getIsStart()+"";
        if(isStart.equals("0")){
//            is_stop_tv.setVisibility(View.VISIBLE);
            tv_countdown.setVisibility(View.GONE);
            countdown_iv.setVisibility(View.GONE);
        }else {
            tv_countdown.setVisibility(VISIBLE);
            countdown_iv.setVisibility(View.GONE);
//            is_stop_tv.setVisibility(View.GONE);
        }
    }

    /**
     * ?????????????????????londing??????
     */
    private void showLoadingLinear() {
        isStart=dataBean.getIsStart()+"";
        if (isStart.equals("0")) {
            countdown_iv.setVisibility(View.GONE);
            tv_countdown.setVisibility(View.GONE);
//            is_stop_tv.setVisibility(View.VISIBLE);
        } else {
            countdown_iv.setVisibility(VISIBLE);
            tv_countdown.setVisibility(View.GONE);
//            is_stop_tv.setVisibility(View.GONE);
        }
    }
    /**
     ???????????????
     */
    private void initSwitchMicophone() {
        if(USE_TX){
            mLivePusher.getTXLivePusher().setMute(!isMute);
        }else {
            nodePublisher.setAudioEnable(!isMute);
        }
        if(isMute){
            //??????????????????
            liveMorePop.swich_microphone_iv.setImageResource(R.drawable.gbmkf);
            liveMorePop.swich_microphone_tv.setText("???????????????");
        }else {
            //??????????????????
            liveMorePop.swich_microphone_iv.setImageResource(R.drawable.kqmkf);
            liveMorePop.swich_microphone_tv.setText("???????????????");
        }
        isMute=!isMute;
    }

    /**
     * ???????????????
     */
    private void initSwichLight() {
        if(isBackCamera){
            /*
            ????????????????????????
             */
            if(USE_TX){
                mLivePusher.getTXLivePusher().turnOnFlashLight(!isOnLight);
            }else {
                nodePublisher.setFlashEnable(!isOnLight);
            }
            //???????????????????????????????????????
            if(isOnLight){
                //?????????????????????
                liveMorePop.swich_light_iv.setImageResource(R.drawable.kqsgd);
                liveMorePop.swich_light_tv.setText("???????????????");
            }else {
                //?????????????????????
                liveMorePop.swich_light_iv.setImageResource(R.drawable.gbsgd);
                liveMorePop.swich_light_tv.setText("???????????????");
            }
            //?????????????????????
            isOnLight=!isOnLight;
        }else {
            /*
            ????????????????????????
             */
            showtoast("??????????????????????????????????????????");
        }
    }

    /**
     * ???????????????
     */
    private void initSwichCamare() {
        //???????????????
        if(USE_TX){
            mLivePusher.switchCamera();
            mCameraFacing = IFURenderer.CAMERA_FACING_FRONT - mCameraFacing;
            if (mFURenderer != null) {
                mFURenderer.onCameraChanged(mCameraFacing, CameraUtils.getCameraOrientation(mCameraFacing));
                if (mFURenderer.getMakeupModule() != null) {
                    mFURenderer.getMakeupModule().setIsMakeupFlipPoints(mCameraFacing == IFURenderer.CAMERA_FACING_FRONT ? 0 : 1);
                }
            }
        }else {
            nodePublisher.switchCamera();
        }

        //????????????????????????????????????
        isBackCamera=!isBackCamera;
        //????????????????????????,????????????????????????,
        if(!isBackCamera){
            if(USE_TX){
                mLivePusher.getTXLivePusher().turnOnFlashLight(false);
            }else {
                nodePublisher.setFlashEnable(false);
            }
            //???????????????????????????????????????pop???????????????
            if(liveMorePop!=null){
                //pop???????????????????????????
                liveMorePop.swich_light_iv.setImageResource(R.drawable.kqsgd);
                liveMorePop. swich_light_tv.setText("???????????????");
            }

        }

    }
    private void initJsonData() {//????????????

        /**
         * ?????????assets ????????????Json??????????????????????????????????????????????????????
         * ???????????????????????????
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//??????assets????????????json????????????

        ArrayList<ProvinceJsonBean> provinceJsonBean = parseData(JsonData);//???Gson ????????????

        /**
         * ??????????????????
         *
         * ???????????????????????????JavaBean????????????????????????????????? IPickerViewData ?????????
         * PickerView?????????getPickerViewText????????????????????????????????????
         */
        provinceOptionsItems = provinceJsonBean;
        for (int i = 0; i < provinceJsonBean.size(); i++) {//????????????
            ArrayList<String> cityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < provinceJsonBean.get(i).getCityList().size(); c++) {//??????????????????????????????
                String cityName = provinceJsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//????????????
                ArrayList<String> city_AreaList = new ArrayList<>();//??????????????????????????????

                //??????????????????????????????????????????????????????????????????null ?????????????????????????????????????????????
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(provinceJsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//??????????????????????????????
            }

            /**
             * ??????????????????
             */
            cityOptionsItems.add(cityList);

            /**
             * ??????????????????
             */
            areaOptionsItems.add(province_AreaList);
            isCityLoaded=true;
        }


    }
    public ArrayList<ProvinceJsonBean> parseData(String result) {//Gson ??????
        ArrayList<ProvinceJsonBean> detail = new ArrayList<>();
        try {
            org.json.JSONArray data = new org.json.JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }
    /**
     ??????????????????(????????????????????????????????????,????????????????????????)
     */
    private void checkCameraPermission() {
        //????????????,????????????
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            goCamera();
        } else {
            //???????????????????????????????????????
            EasyPermissions.requestPermissions(this, "????????????????????????????????????", CAMARE_REQUEST_CODE,PERMISSIONS);
        }
    }
    /**
     ??????????????????
     */
    private void checkPhotoPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            goPhotoAlbum();
        } else {
            //???????????????????????????????????????
            EasyPermissions.requestPermissions(this, "????????????????????????????????????", PHOTO_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
    //??????????????????(????????????)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     ??????????????????
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //????????????????????????
        if (requestCode == CAMARE_REQUEST_CODE) {
            goCamera();
        }
        //????????????????????????
        else if (requestCode == PHOTO_REQUEST_CODE) {
            goPhotoAlbum();
        }
    }

    /**
     ??????????????????
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showtoast("????????????????????????????????????????????????");
    }

    /**
     *??????????????????
     */
    private void goCamera() {
        /**
         * ????????????????????????????????????????????????,??????????????????????????? ????????????????????????????????????
         * ????????????: Permission Denial:opening provider......
         */
        if(USE_TX){
            mLivePusher.getTXLivePusher().stopCameraPreview(true);
        }else {
            nodePublisher.stopPreview();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getApplication().getPackageName()+".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //????????????startActivityForResult ????????????getActivity().startActivityForResult, activity onActivityResult????????????super.onActivityResult(requestCode, resultCode, data)???
        startActivityForResult(intent, CAMARE_REQUEST_CODE);
    }

    /**
     ????????????
     */
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }
    /**
     * ??????????????????????????????,??????imageView?????????????????????,????????????????????????
     * @param path
     */
    private void showBitMap(String path) {
        //??????????????????????????????????????????????????????
        Bitmap camorabitmap = BitmapFactory.decodeFile(path);
        if (null != camorabitmap) {
            int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(), camorabitmap.getHeight(), cover_iv.getWidth(), cover_iv.getHeight());
            Bitmap bitMap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale, camorabitmap.getHeight() / scale);
            //??????Bitmap????????????????????????????????????????????????????????????out of memory??????
            camorabitmap.recycle();
            Glide.with(this)
                    .load(bitMap)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(cover_iv);
        }
    }

    /*  ---------------------------------------------------------?????????????????????  start  */
    @Override
    public int onTextureCustomProcess(int texture, int width, int height) {

        if (mIsFirstFrame) {
//            Log.d(TAG, "onTextureCustomProcess: texture:" + i + ", width:" + i1 + ", height:" + i2);
            mFURenderer.onSurfaceCreated();
            mIsFirstFrame = false;
            return 0;
        }
        //??????s6?????????????????????
        if (System.currentTimeMillis() - currentTime < 200){
            mFURenderer.onDrawFrameSingleInput(texture, width, height);
        }
        long start = System.nanoTime();
        int texId = mFURenderer.onDrawFrameSingleInput(texture, width, height);
        long renderTime = System.nanoTime() - start;
        return texId;
    }

    @Override
    public void onTextureDestoryed() {
        mFURenderer.onSurfaceDestroyed();
        mIsFirstFrame = true;
    }
    @Override
    public void onDetectFacePoints(float[] floats) {
    }
    /*  ---------------------------------------------------------?????????????????????  end  */


    @Override
    public void onTrackStatusChanged(int type, int status) {
        Log.i(TAG, "onTrackStatusChanged() called with: type = [" + type + "], status = [" + status + "]");
        if (mTvTrackStatus == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTrackStatus.setText(type == FURenderer.TRACK_TYPE_FACE ? R.string.toast_not_detect_face : R.string.toast_not_detect_face_or_body);
                mTvTrackStatus.setVisibility(status > 0 ? View.INVISIBLE : VISIBLE);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        if (Math.abs(x) > 3 || Math.abs(y) > 3) {
            if (Math.abs(x) > Math.abs(y)) {
                mFURenderer.onDeviceOrientationChanged(x > 0 ? 270 : 90);
            } else {
                mFURenderer.onDeviceOrientationChanged(y > 0 ? 0 : 180);
            }
        }
    }
        private void requestForbidden(String user_id) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("chatRoomId",sp.getRoomId());
            data.put("gagType",6);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", "");
            jsonObject.put("nickName","[????????????]");
            jsonObject.put("userSpeakContent","[????????????]");
            jsonObject.put("remark","[????????????]");
            jsonObject.put("rongId",user_id);
            jsonObject.put("platformCode","??????");
            jsonObject.put("userName","[????????????]");
            jsonArray.add(jsonObject);
            data.put("members",jsonArray);
        HttpApiUtils.normalRequest(this, null, RequestUtils.FORBIDDEN, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: ??????????????????" );
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    static class DiaLogListener implements DialogInterface.OnClickListener {
        WeakReference<StartLiveActivity> activityWeakReference;
        DiaLogListener(StartLiveActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            StartLiveActivity activity = activityWeakReference.get();
            if (activity == null)return;
            switch (which){
                case -1:
                    dialog.cancel();
                    activity.finish();
                    break;
                case -2:
                    dialog.cancel();
                    break;
            }
        }
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if(message.obj instanceof io.rong.imlib.model.Message ){
                io.rong.imlib.model.Message obj = (io.rong.imlib.model.Message) message.obj;
                String targetId = obj.getTargetId();//?????????????????????????????????
                //?????????????????????????????????
                if(targetId.equals(sp.getRoomId())){
                    MessageContent content = obj.getContent();
                    switch (message.what) {
                        //?????????????????????
                        case RongLibUtils.MESSAGE_ARRIVED:
                        case RongLibUtils.MESSAGE_SENT:
                            try {
                                /**
                                 *??????????????????
                                 */
                                LiveMessageModel messageModel = new LiveMessageModel();
                                messageModel.setMessageDirection(obj.getMessageDirection());
                                String senderUserId = obj.getSenderUserId();
                                messageModel.setRcUserId(senderUserId);
                                if (content instanceof LiveTextMessage) {
                                    LiveTextMessage liveTextMessage = (LiveTextMessage) content;
                                    if(initUserInfo(messageModel, liveTextMessage.getUserInfoJson(),senderUserId)){
                                        requestForbidden(senderUserId);
                                        return;
                                    }

                                    messageModel.setObjectName(CommonStr.TEXT_MESSAGE);
                                    messageModel.setTextContent(liveTextMessage.getContent());
                                    messageModel.setZkCode(liveTextMessage.getZkCode());
                                    liveChatFragment.addItem(messageModel);
                                    //????????????,????????????pop?????????
                                    EventBus.getDefault().post(messageModel);
                                    //??????????????????
                                    commitLiveMessageModelList.add(messageModel);

                                } else if (content instanceof LiveShareBetMessage) {
                                    /**
                                     *      ????????????
                                     */
                                    LiveShareBetMessage liveShareBetMessage = (LiveShareBetMessage) content;
                                    String type_id = liveShareBetMessage.getType_id();
                                    String rule_id = liveShareBetMessage.getRule_id();
                                    String lotmoney = liveShareBetMessage.getLotmoney();
                                    String[] typeidSplit = type_id.split(",");
                                    String[] ruleidSplit = rule_id.split(",");
                                    String[] lotmoneySplit = lotmoney.split(",");
                                    boolean isPass = true;
                                    for (int i = 0; i < typeidSplit.length; i++) {
                                        if(Utils.isNotLong(typeidSplit[i])){
                                            isPass = false;
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < ruleidSplit.length; i++) {
                                        if(Utils.isNotLong(ruleidSplit[i])){
                                            isPass = false;
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < lotmoneySplit.length; i++) {
                                        if(Utils.isNotInt(lotmoneySplit[i])){
                                            isPass = false;
                                            break;
                                        }
                                    }
                                    if(Utils.isNotInt(liveShareBetMessage.getPointGrade())||
                                       Utils.isNotLong(liveShareBetMessage.getLotteryqishu()) ||
                                       Utils.isNotInt(liveShareBetMessage.getGame()) ||
                                       !isPass ||
                                       initUserInfo(messageModel, liveShareBetMessage.getUserInfoJson(),senderUserId)){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    messageModel.setObjectName(CommonStr.SHARE_MESSAGE);
                                    messageModel.setZkCode(liveShareBetMessage.getZkCode());
                                    messageModel.setLevel(liveShareBetMessage.getPointGrade());
                                    messageModel.setUserName(liveShareBetMessage.getNickname());
                                    String betMoney = liveShareBetMessage.getLotmoney();
                                    List<String> amountAmountList = Arrays.asList(betMoney.split(","));
                                    int betAMount = 0 ;
                                    for (int i = 0; i < amountAmountList.size(); i++) {
                                        betAMount += Integer.parseInt(amountAmountList.get(i));
                                    }
                                    messageModel.setBetAmount(betAMount+"");
                                    messageModel.setTypeName(liveShareBetMessage.getTypename());
                                    messageModel.setBetQiShu(liveShareBetMessage.getLotteryqishu());
                                    messageModel.setBetName(liveShareBetMessage.getName());
                                    messageModel.setBetGroupName(liveShareBetMessage.getGroupname());
                                    messageModel.setType_id(liveShareBetMessage.getType_id());
                                    messageModel.setGame(liveShareBetMessage.getGame());
                                    messageModel.setReluId(liveShareBetMessage.getRule_id());
                                    liveChatFragment.addItem(messageModel);
                                } else if (content instanceof LiveRedMessage) {
                                    /**
                                     * ???????????????
                                     */
                                    LiveRedMessage liveRedMessage = (LiveRedMessage) content;
                                    int redType = liveRedMessage.getRedType();
                                    if(Utils.isNotDouble(liveRedMessage.getRedPrice()) ||
                                            Utils.isNotLong(liveRedMessage.getRedId()) ||
                                            Utils.isNotInt(liveRedMessage.getRedType()+"")){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    //3????????????????????? userInfoJson???  1 2 ??????????????????qpUserName???
                                    if (redType == 3) {
                                        if(initUserInfo(messageModel, liveRedMessage.getUserInfoJson(),senderUserId)){
                                            requestForbidden(senderUserId);
                                            return;
                                        }
                                    } else {
                                        messageModel.setUserName(liveRedMessage.getQbUserName());
                                    }
                                    messageModel.setObjectName(CommonStr.RED_MESSAGE);
                                    messageModel.setRedType(redType);
                                    messageModel.setRedPrice(liveRedMessage.getRedPrice());
                                    messageModel.setRedId(Long.parseLong(liveRedMessage.getRedId()));
                                    liveChatFragment.addItem(messageModel);

                                } else if (content instanceof LiveGiftMessage) {
                                    /**
                                     *????????????
                                     */
//                            ?????????????????????  ??????   ---------------------
                                    LiveGiftMessage liveGiftMessage = (LiveGiftMessage) content;
                                    String userInfoJson = liveGiftMessage.getUserInfoJson();
                                    if(initUserInfo(messageModel, userInfoJson,senderUserId) ||
                                       Utils.isNotLong(liveGiftMessage.getGiftId()) ||
                                       Utils.isNotInt(liveGiftMessage.getGiftNum()) ||
                                       Utils.isNotDouble(liveGiftMessage.getGiftPrice()) ||
                                       Utils.isNotInt(liveGiftMessage.getGear()) ||
                                       Utils.isNotInt(liveGiftMessage.getGearTime())){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    /**
                                     * ?????????????????????,???????????????,????????????
                                     */
                                    String gear = liveGiftMessage.getGear();
                                    String gearTime = liveGiftMessage.getGearTime();
                                    if(StringMyUtil.isNotEmpty(gear)&&!gear.equals("0")&&StringMyUtil.isNotEmpty(gearTime)&&!gearTime.equals("0")&&isUseToy==1&&StringMyUtil.isNotEmpty(toyId)){
                                        gearMessageList.add(liveGiftMessage);
                                    }
                                    if(!isGearing){
                                        handlerGiftGear();
                                    }
                                    messageModel.setZkCode(liveGiftMessage.getZkCode());
                                    messageModel.setObjectName(CommonStr.GIFT_MESSAGE);
                                    messageModel.setGiftId(liveGiftMessage.getGiftId());
                                    String giftNum = liveGiftMessage.getGiftNum();
                                    messageModel.setGiftNum(giftNum);
                                    messageModel.setGiftName(liveGiftMessage.getGiftName());
                                    String giftPrice = liveGiftMessage.getGiftPrice();
                                    BigDecimal singlePrice = new BigDecimal(giftPrice);
                                    BigDecimal oldPrice = singlePrice .multiply(new BigDecimal(giftNum));
                                    liveChatFragment.addItem(messageModel);
                                    String toString = money_tv.getText().toString();
                                    BigDecimal oldNum = new BigDecimal(toString.contains("-") ? "0" : toString);
                                    money_tv.setText((oldNum.add(oldPrice))+"");
                                    //????????????,????????????pop?????????
                                    EventBus.getDefault().post(messageModel);
//                            ?????????????????????  ??????   ---------------------
//     ??????????????????   ??????  -----------------------
                                    String giftUrl = liveGiftMessage.getGiftUrl();//??????????????????
                                    String giftIcon = liveGiftMessage.getGiftIcon();
                                    if(giftUrl.contains("http") || giftIcon.contains("http")){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    JSONObject jsonObject = JSONObject.parseObject(userInfoJson);//????????????
                                    int giftCount = 0;
                                    try {
                                        giftCount = Integer.parseInt(giftNum);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    GiftSendModel giftSendModel = new GiftSendModel(giftCount);
                                    giftSendModel.setGiftRes(giftIcon);
                                    giftSendModel.setNickname(jsonObject.getString("name"));
                                    giftSendModel.setSig("??????" + liveGiftMessage.getGiftName());
                                    String portrait = jsonObject.getString("portrait");
                                    giftSendModel.setUserAvatarRes(portrait);
                                    giftView.addGift(giftSendModel);
                                    if(StringMyUtil.isNotEmpty(giftUrl)) {//?????????????????????????????????, ??????svga??????(????????????????????????????????????,?????????????????????????????????????????????????????????)
                                        if(!jsonObject.getString("userId").equals(userDataBean.getId())){
                                            LiveGiftSVGAEntity liveGiftSVGAEntity = new LiveGiftSVGAEntity(Utils.getFirstImgurl(MyApplication.getInstance())+giftUrl, null);
                                            giftSvgaManage.startAnimator(liveGiftSVGAEntity);
                                        }
                                    }
//                            ??????????????????   ??????  -----------------------
                                } else if (content instanceof LiveExitAndJoinMessage) {
                                    /**
                                     * ??????/?????????????????????
                                     */
                                    LiveExitAndJoinMessage liveExitAndJoinMessage = (LiveExitAndJoinMessage) content;
                                    if(initUserInfo(messageModel, liveExitAndJoinMessage.getUserInfoJson(),senderUserId) ||
                                       Utils.isNotInt(liveExitAndJoinMessage.getStatus())){
                                        requestForbidden(senderUserId);
                                        return;
                                    }

                                    messageModel.setZkCode(liveExitAndJoinMessage.getZkCode());
                                    messageModel.setObjectName(CommonStr.EXIT_JOIN_MESSAGE);
                                    String userName = liveExitAndJoinMessage.getUserName();
                                    userName = StringMyUtil.isEmptyString(userName)?"***":userName;
                                    messageModel.setUserName(userName);
                                    String status = liveExitAndJoinMessage.getStatus();
                                    messageModel.setStatus(status);
                                    String userNickName = SharePreferencesUtil.getString(MyApplication.getInstance(), "userNickName", "");
                                    if(status.equals("0")){//???????????????,??????????????????1
                                        if(!userName.equals(userNickName)){
                    /*                totalMemberCount++;
                                    if(totalMemberCount>mostHighMemberCount){
                                        mostHighMemberCount=totalMemberCount;
                                    }

                                    tv_num.setText(totalMemberCount*watchRadio+"???");*/
                                        }
//                                liveChatFragment.addItem(messageModel);
                                        liveChatFragment.addJoinItem(messageModel);
                                        String level1 = messageModel.getLevel();
                                        int level=1;
                                        if(Utils.isInt(level1)){
                                            level = Integer.parseInt(StringMyUtil.isEmptyString(level1) ? "1" : level1);
                                        }
                                        String mountSVGA = messageModel.getMountSVGA();
                                        String levelSVGA = messageModel.getLevelSVGA();
                                        if(StringMyUtil.isNotEmpty(mountSVGA)){
                                            //?????????????????????, ??????????????????View??????????????????
                                            LiveGiftSVGAEntity liveGiftSVGAEntity = new LiveGiftSVGAEntity(Utils.checkImageUrl(mountSVGA), messageModel);
                                            giftSvgaManage.startAnimator(liveGiftSVGAEntity);
                                        }else {
                                            if(level>=2){
                                                joinGiftSvgaMage.startAnimator(messageModel,Utils.cpCheckImageUrl(levelSVGA));
                                            }
                                        }

                                    }else {//???????????????????????????-1
                                        if(!userName.equals(userNickName)){
                         /*           totalMemberCount--;
                                    if(totalMemberCount>mostHighMemberCount){
                                        mostHighMemberCount=totalMemberCount;
                                    }
                                    tv_num.setText(totalMemberCount*watchRadio+"???");*/
                                            //????????????????????????
//                                    liveChatFragment.addItem(messageModel);
                                        }
                                    }
                                    onlineNumObservable.onNext(totalMemberCount);
                                }else if(content instanceof TextMessage){
                                    /**
                                     ?????????????????????(?????????????????????,???????????????????????????????????????)
                                     */
                                    TextMessage textMessage= (TextMessage) content;

                                    String textContent = textMessage.getContent();
                                    
                                    if(!Utils.isJsonObject(textContent)||(!senderUserId.contains(CommonStr.RONG_ID_STR) && !senderUserId.equals("1"))){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    ZjMessageModel zjMessageModel = JSONObject.parseObject(textContent, ZjMessageModel.class);
                                    String isBack = zjMessageModel.getIsBack();
                                    messageModel.setObjectName(isBack);
                                    messageModel.setUserNickName(zjMessageModel.getUserNickName());
                                    messageModel.setTypeName(zjMessageModel.getTypename());
                                    messageModel.setUserName(zjMessageModel.getUserNickName());
                                    if(zjMessageModel.getUser_id()!=0){//user_id???0???, ????????????????????????, ????????????????????????
                                        liveChatFragment.addItem(messageModel);
                                    }
                                }else if(content instanceof LiveSystemMessage){
                                    /**
                                     * ????????????/????????????
                                     */
                                    if(!senderUserId.contains(CommonStr.RONG_ID_STR) && !senderUserId.equals("1")){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    LiveSystemMessage liveSystemMessage = (LiveSystemMessage) content;
                                    SystemMessageModel systemMessageModel = JSONObject.parseObject(liveSystemMessage.getContent(), SystemMessageModel.class);
                                    messageModel.setZkCode(systemMessageModel.getPlatformCode());
                                    messageModel.setObjectName(CommonStr.LIVE_SYSTEM_MESSAGE);
                                    messageModel.setUserNickName(systemMessageModel.getUserNickName());
                                    messageModel.setSystemStatus(systemMessageModel.getStatus());
                                    messageModel.setField(systemMessageModel.getField());
                                    liveChatFragment.addItem(messageModel);
                                }
                                else if(content instanceof LiveFollowMessage){
                                    /**
                                     *????????????
                                     */
                                    LiveFollowMessage liveFollowMessage = (LiveFollowMessage) content;
                                    if(initUserInfo(messageModel,liveFollowMessage.getUserInfoJson(),senderUserId)){
                                        requestForbidden(senderUserId);
                                        return;
                                    }

                                    messageModel.setObjectName(CommonStr.FOLLOW_MESSAGE);
                                    liveChatFragment.addItem(messageModel);

                                }else if(content instanceof ForbiddenMessage){
                                    /**
                                     * ????????????
                                     */
                                    ForbiddenMessage forbiddenMessage = (ForbiddenMessage) content;
                                    if(initUserInfo(messageModel,forbiddenMessage.getUserInfoJson(),senderUserId) ||
                                       Utils.isNotInt(forbiddenMessage.getIsForbidden())){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    messageModel.setObjectName(CommonStr.FORBIDDEN_MESSAGE);
                                    messageModel.setZkCode(forbiddenMessage.getZkCode());
                                    messageModel.setTargetNickName(forbiddenMessage.getTargetNickName());
                                    messageModel.setIsForbidden(forbiddenMessage.getIsForbidden());
                                    if(!messageModel.getManagerType().equals("2")){
                                        liveChatFragment.addItem(messageModel);
                                    }
                                }else if (content instanceof RoomManageMessage){
                                    /**
                                     * ??????????????????
                                     */
                                    RoomManageMessage roomManageMessage = (RoomManageMessage) content;
                                    if(initUserInfo(messageModel,roomManageMessage.getUserInfoJson(),senderUserId) ||
                                       Utils.isNotInt(roomManageMessage.getIsRoomManager())){
                                        requestForbidden(senderUserId);
                                        return;
                                    }
                                    messageModel.setObjectName(CommonStr.ROOM_MANAGER_MESSAGE);
                                    messageModel.setZkCode(roomManageMessage.getZkCode());
                                    messageModel.setTargetNickName(roomManageMessage.getTargetNickName());
                                    messageModel.setIsRoomManager(roomManageMessage.getIsRoomManager());
                                    liveChatFragment.addItem(messageModel);
                                }else if(content instanceof SwichMoneyMessage){
                                    /**
                                     * ?????????????????? ??????
                                     */
                                    SwichMoneyMessage swichMoneyMessage = (SwichMoneyMessage) content;
                                    if(initUserInfo(messageModel,swichMoneyMessage.getUserInfoJson(),senderUserId) ||
                                       Utils.isNotInt(swichMoneyMessage.gettype()) ||
                                       Utils.isNotLong(senderUserId)){
                                       requestForbidden(senderUserId);
                                        return;
                                    }

                                    messageModel.setObjectName(CommonStr.CHANGE_ROOM_TYPE_MESSAGE);
                                    messageModel.setZkCode(swichMoneyMessage.getZkCode());
                                    messageModel.setType(swichMoneyMessage.gettype());
                                    liveChatFragment.addItem(messageModel);
                                }else if(content instanceof LiveNormalRedMessage){
                                    /**
                                     * ?????????????????????
                                     */
                                    LiveNormalRedMessage liveNormalRedMessage = (LiveNormalRedMessage) content;
                                    if(initUserInfo(messageModel,liveNormalRedMessage.getUserInfoJson(),senderUserId) ||
                                       Utils.isNotDouble(liveNormalRedMessage.getRedPrice()) ||
                                       Utils.isNotLong(liveNormalRedMessage.getRedId())){
                                       requestForbidden(senderUserId);
                                       return;
                                    }

                                    messageModel.setObjectName(CommonStr.NORMAL_RED_MESSAGE);
                                    messageModel.setUserInfoJson(liveNormalRedMessage.getUserInfoJson());
                                    messageModel.setUserNickName(liveNormalRedMessage.getQbUserName());
                                    messageModel.setRedPrice(liveNormalRedMessage.getRedPrice());
                                    messageModel.setZkCode(liveNormalRedMessage.getZkCode());
                                    messageModel.setRedId(Long.parseLong(liveNormalRedMessage.getRedId()));
                                    liveChatFragment.addItem(messageModel);
                                }else if(content instanceof LiveRewardMessage){
                                    LiveRewardMessage liveRewardMessage = (LiveRewardMessage) content;
                                    String rewardMoney = liveRewardMessage.getRewardMoney();
                                    if(initUserInfo(messageModel,liveRewardMessage.getUserInfoJson(),senderUserId) || Utils.isNotDouble(rewardMoney)){
                                        return;
                                    }
                                    BigDecimal singlePrice = new BigDecimal(rewardMoney);
                                    String toString = money_tv.getText().toString();
                                    if(!toString.contains("-")){
                                        BigDecimal oldNum = new BigDecimal(toString);
                                        money_tv.setText((oldNum.add(singlePrice))+"");
                                    }
                                    messageModel.setObjectName(CommonStr.REWARD_ANCHOR);
                                    messageModel.setUserInfoJson(liveRewardMessage.getUserInfoJson());
                                    messageModel.setRewardPrice(rewardMoney);
                                    liveChatFragment.addItem(messageModel);

                                }
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //??????????????????
                        case RongLibUtils.MESSAGE_SEND_ERROR:
                            int arg1 = message.arg1;
                            if(arg1==FORBIDDEN_IN_CHATROOM.getValue()){
                                if(!(content instanceof LiveExitAndJoinMessage)){
                                    //?????????(?????????????????????????????????????????????)
                                    showtoast("????????????????????????????????????????????????");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
