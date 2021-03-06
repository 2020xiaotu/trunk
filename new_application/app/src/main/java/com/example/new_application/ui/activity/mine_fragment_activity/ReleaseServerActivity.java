package com.example.new_application.ui.activity.mine_fragment_activity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.FullyGridLayoutManager;
import com.example.new_application.adapter.GridImageAdapter;
import com.example.new_application.adapter.ReleaseServerAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.MineReleaseEntity;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.bean.UploadFileEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiImpl;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.user_info_activity.InformationActivity;
import com.example.new_application.ui.pop.CommonSurePop;
import com.example.new_application.ui.pop.CommonTipPop;
import com.example.new_application.ui.pop.TakeCameraPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.GlideEngine;
import com.example.new_application.utils.Utils;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.manager.PictureCacheManager;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ReleaseServerActivity extends BaseActivity {

    @BindView(R.id.release_content_etv)
    EditText release_content_etv;
    @BindView(R.id.photo_recycler)
    RecyclerView photo_recycler;
    @BindView(R.id.release_title_etv)
    EditText release_title_etv;
    @BindView(R.id.release_type_recycler)
    RecyclerView release_type_recycler;
    @BindView(R.id.contact_tv)
    TextView contact_tv;
    @BindView(R.id.release_btn)
    Button release_btn;
    @BindView(R.id.yikoujia_linear)
    LinearLayout yikoujia_linear;
    @BindView(R.id.yikoujia_etv)
    EditText yikoujia_etv;
    @BindView(R.id.baojiafanwei_linear)
    LinearLayout baojiafanwei_linear;
    @BindView(R.id.baojiafanwei_etv1)
    EditText baojiafanwei_etv1;
    @BindView(R.id.baojiafanwei_etv2)
    EditText baojiafanwei_etv2;
    @BindView(R.id.price_recycler)
    RecyclerView price_recycler;
    @BindView(R.id.deal_type_recycler)
    RecyclerView deal_type_recycler;
    @BindView(R.id.deal_type_tv)
    TextView deal_type_tv;
    @BindView(R.id.deal_type_tip_tv)
    TextView deal_type_tip_tv;
    @BindView(R.id.deal_type_linear)
    LinearLayout deal_type_linear;
    @BindView(R.id.commission_recycler)
    RecyclerView commission_recycler;
    @BindView(R.id.commission_linear)
    LinearLayout commission_linear;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    @BindView(R.id.no_server_tv)
    TextView no_server_tv;
    ReleaseServerAdapter commissionAdapter;
    ArrayList<MineServerEntity>commissionList = new ArrayList<>();
    ReleaseServerAdapter dealTypeAdapter;
    ArrayList<MineServerEntity>dealTypeList = new ArrayList<>();
    ReleaseServerAdapter priceAdapter;
    ArrayList<MineServerEntity>priceList = new ArrayList<>();
    ReleaseServerAdapter releaseServerAdapter;
    ArrayList<MineServerEntity>mineServerEntityArrayList = new ArrayList<>();
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private String[] CAMARA_PERMISSIONS ={  Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private Uri uri;
    private File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
    private MineServerEntity currentMineServerEntity;
    int guaranteeType = 1;//????????????(0???????????????,1???????????????)
    int priceType = 1;//????????????(1???????????????2??????????????????3????????? )
    int commissionType = 0;//????????????(0?????????,1?????????,2???????????? 3??????)(??????????????? ???????????? ??????)
    MineReleaseEntity mineReleaseEntity;
    private CommonSurePop commonSurePop;
    private String ruleContent;
    private GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private PictureParameterStyle mPictureParameterStyle;
    private TakeCameraPop takeCameraPop;
    List<UploadFileEntity> allPhotoList = new ArrayList<>();
    private List<String> attachmentList;//???????????????,????????????????????????

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_server;
    }

    @Override
    public void getIntentData() {
        mineReleaseEntity = (MineReleaseEntity) getIntent().getSerializableExtra("mineReleaseEntity");
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"????????????");
        toolbar_right_tv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText("??????");
        toolbar_right_tv.setTextColor(Color.parseColor("#FA6400"));
        mPictureParameterStyle = Utils.getDefaultStyle(this);
        // ??????????????????onCreate???Fragment onAttach??????????????????java.lang.IllegalStateException??????
        launcherResult = createActivityResultLauncher();
        initPhotoRecycler(savedInstanceState);
        requestRule();
        initServerRecycler();
        initPriceRecycler();
        initDealTypeRecycler();
        initCommissionRecycler();
        initDefaultValue();
        release_content_etv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //????????????EditText????????????EditText??????????????????????????????EditText?????????????????????????????????????????????
                if ((view.getId() == R.id.release_content_etv && Utils.canVerticalScroll(release_content_etv))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initContactDetails();
        requestMineServer();
    }

    /**
     * ????????????ActivityResultLauncher
     *
     * @return
     */
    private ActivityResultLauncher<Intent> createActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == RESULT_OK) {
                            List<LocalMedia>  selectList = PictureSelector.obtainMultipleResult(result.getData());
                            for (LocalMedia media : selectList) {
                                if (media.getWidth() == 0 || media.getHeight() == 0) {
                                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                                        media.setWidth(imageExtraInfo.getWidth());
                                        media.setHeight(imageExtraInfo.getHeight());
                                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(ReleaseServerActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }
                            }

                            List<UploadFileEntity> uploadFileEntityList = new ArrayList<>();
                            for (int i = 0; i < selectList.size(); i++) {
                                UploadFileEntity uploadFileEntity = new UploadFileEntity();
                                uploadFileEntity.setSuccess(false);
                                uploadFileEntity.setLocalMedia(selectList.get(i));
                                uploadFileEntityList.add(uploadFileEntity);
                                allPhotoList.add(uploadFileEntity);
                            }
                            mAdapter.notifyDataSetChanged();

                            for (int i = 0; i < allPhotoList.size(); i++) {
                                uploadImg(allPhotoList.get(i));
                            }
                        }
                    }
                });
    }
    private List<LocalMedia> getSelectionData(List<UploadFileEntity>list){
        List<LocalMedia> localMediaArrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            localMediaArrayList.add(list.get(i).getLocalMedia());
        }
        return localMediaArrayList;
    }
    private void initTakeCameraPop() {
        takeCameraPop=new TakeCameraPop(this);
        takeCameraPop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.forbidden_tv:
                        PictureSelectionModel pictureSelectionModel = PictureSelector.create(ReleaseServerActivity.this)
                                .openCamera(PictureMimeType.ofImage())// ?????????????????????????????????????????? ??????????????????????????????or??????
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                                .minSelectNum(1)// ??????????????????
                                .closeAndroidQChangeWH(true)//??????????????????????????????????????????????????????true
                                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// ???????????????????????????????????????????????????false
                                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ??????
                                .isPreviewImage(true)// ?????????????????????
                                .isCamera(false)// ????????????????????????
                                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                                .setCameraImageFormat(PictureMimeType.JPEG) // ????????????????????????,??????.jpeg
                                .setCameraVideoFormat(PictureMimeType.MP4)// ????????????????????????,??????.mp4
                                .setCameraAudioFormat(PictureMimeType.AMR)// ????????????????????????,??????.amr
                                .isEnableCrop(false)// ????????????
                                .isCompress(true)// ????????????
                                .synOrAsy(false)//??????true?????????false ?????? ????????????
                                .isGif(true)// ????????????gif??????
//                                .selectionData(getSelectionData(mAdapter.getData()))// ????????????????????????
                                .cutOutQuality(90)// ?????????????????? ??????100
                                .minimumCompressSize(100);// ????????????kb??????????????????
                        //????????????
                        if(attachmentList!=null ){
                            pictureSelectionModel.maxSelectNum(5-allPhotoList.size());
                        }else {
                            pictureSelectionModel.maxSelectNum(5);

                        }
                        pictureSelectionModel.forResult(launcherResult);

                        takeCameraPop.dismiss();
                        break;
                    case R.id.set_manager_tv:

                        // ????????????
                        PictureSelectionModel model = PictureSelector.create(ReleaseServerActivity.this)
                                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                                .isWeChatStyle(true)// ????????????????????????????????????
                                .isMaxSelectEnabledMask(true)// ?????????????????????????????????????????????????????????
                                .setCustomCameraFeatures(CustomCameraView.BUTTON_STATE_BOTH)// ?????????????????????????????????
                                .setCaptureLoadingColor(ContextCompat.getColor(ReleaseServerActivity.this, R.color.app_color_blue))
                                .maxSelectNum(5)// ????????????????????????
                                .minSelectNum(1)// ??????????????????
                                .maxVideoSelectNum(1) // ????????????????????????
                                .imageSpanCount(4)// ??????????????????
                                .isReturnEmpty(false)// ????????????????????????????????????????????????
                                .closeAndroidQChangeWH(true)//??????????????????????????????????????????,?????????true
                                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// ??????????????????????????????????????????,?????????false
                                .isAndroidQTransform(true)// ??????????????????Android Q ??????????????????????????????????????????compress(false); && .isEnableCrop(false);??????,????????????
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                                .isOriginalImageControl(false)// ????????????????????????????????????????????????true????????????????????????????????????????????????????????????????????????
                                .isDisplayOriginalSize(true)// ??????????????????????????????isOriginalImageControl true??????
                                .isEditorImage(false)//??????????????????
                                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ??????
                                .isPreviewImage(true)// ?????????????????????
                                .isCamera(true)// ????????????????????????
                                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                                .setCameraImageFormat(PictureMimeType.JPEG) // ????????????????????????,??????.jpeg
                                .setCameraVideoFormat(PictureMimeType.MP4)// ????????????????????????,??????.mp4
                                .setCameraAudioFormat(PictureMimeType.AMR)// ????????????????????????,??????.amr
                                .isEnableCrop(false)// ????????????
                                .isCompress(true)// ????????????
                                .synOrAsy(false)//??????true?????????false ?????? ????????????
                                .isGif(true)// ????????????gif??????
//                                .selectionData(getSelectionData(mAdapter.getData()))// ????????????????????????
                                .cutOutQuality(90)// ?????????????????? ??????100
                                .minimumCompressSize(100);// ????????????kb??????????????????
                        if(attachmentList!=null ){
                            model.maxSelectNum(5-allPhotoList.size());
                        }else {
                            model.maxSelectNum(5);
                        }
                        model.forResult(launcherResult);
                        takeCameraPop.dismiss();
                        break;
                    case R.id.forbidden_cancel_tv:
                        takeCameraPop.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if(takeCameraPop == null){
                initTakeCameraPop();
            }
            takeCameraPop.showAtLocation(photo_recycler, Gravity.BOTTOM,0,0);
            Utils.darkenBackground(ReleaseServerActivity.this,0.7f);

        }
    };

    private void initPhotoRecycler(Bundle savedInstanceState) {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        photo_recycler.setLayoutManager(manager);

        photo_recycler.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(this,allPhotoList, onAddPicClickListener);
        mAdapter.setSelectMax(5);
        photo_recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((v, position) -> {
            switch (v.getId()){
                case R.id.iv_del:
                    /**
                     * ????????????
                     */
                    if (position != RecyclerView.NO_POSITION && allPhotoList.size() > position) {
                        allPhotoList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter. notifyItemRangeChanged(position, allPhotoList.size());
                    }
                    break;
                default:
                    /**
                     * ????????????
                     */
                    List<LocalMedia> selectList = new ArrayList<>();
                    for (int i = 0; i < allPhotoList.size(); i++) {
                        selectList.add(allPhotoList.get(i).getLocalMedia());
                    }
                    if (selectList.size() > 0) {
                        PictureSelector.create(ReleaseServerActivity.this)
                                .themeStyle(R.style.picture_default_style) // xml????????????
                                .setPictureStyle(mPictureParameterStyle)// ???????????????????????????
                                //.setPictureWindowAnimationStyle(animationStyle)// ???????????????????????????
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                                .isNotPreviewDownload(true)// ????????????????????????????????????
                                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(ReleaseServerActivity.this))// ???????????????????????????????????????????????????????????????????????????
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                                .openExternalPreview(position, selectList);

                    }
            }

        });

        // ??????????????????????????????????????????
        BroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
    }
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (BroadcastAction.ACTION_DELETE_PREVIEW_POSITION.equals(action)) {
                // ??????????????????????????????
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapter.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        }
    };
    private void initDefaultValue() {
        if(mineReleaseEntity!=null){
            guaranteeType = mineReleaseEntity.getGuaranteeType();
            priceType = mineReleaseEntity.getPriceType();
            commissionType = mineReleaseEntity.getCommissionType();
            release_content_etv.setText(mineReleaseEntity.getContent());//??????????????????
            String attachment = mineReleaseEntity.getAttachment();
            if(StringMyUtil.isNotEmpty(attachment)){
                /**
                 *??????????????????
                 */
                attachmentList = Arrays.asList(attachment.split(","));
                for (int i = 0; i < attachmentList.size(); i++) {
                    UploadFileEntity uploadFileEntity = new UploadFileEntity();
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(Utils.checkImageUrl(attachmentList.get(i)));
                    uploadFileEntity.setLocalMedia(localMedia);
                    uploadFileEntity.setSuccess(true);
                    uploadFileEntity.setUploadUrl(attachmentList.get(i));
                    allPhotoList.add(uploadFileEntity);
                    mAdapter.notifyDataSetChanged();
                }

            }
            //????????????
            release_title_etv.setText(mineReleaseEntity.getTitle());
            /**
             * ??????????????????
             */
            for (int i = 0; i < priceList.size(); i++) {
                MineServerEntity mineServerEntity = priceList.get(i);
                mineServerEntity.setCheck(false);
                if(mineServerEntity.getPriceType() == mineReleaseEntity.getPriceType()){
                    mineServerEntity.setCheck(true);
                }
            }
            priceAdapter.notifyDataSetChanged();
            if( mineReleaseEntity.getPriceType()==1){
                priceItemClick(0);//????????????
            }else if(mineReleaseEntity.getPriceType() ==2){
                priceItemClick(1);
            }else {
                priceItemClick(2);
            }
            /**
             * ????????????????????????
             */
            if(mineReleaseEntity.getGuaranteeType() == 1){
                /**
                 * ??????????????????????????????
                 */
                commission_linear.setVisibility(View.VISIBLE);
            }else {
                commission_linear.setVisibility(View.GONE);
            }
            for (int i = 0; i < dealTypeList.size(); i++) {
                MineServerEntity mineServerEntity = dealTypeList.get(i);
                mineServerEntity.setCheck(false);
                if(mineServerEntity.getGuaranteeType() == mineReleaseEntity.getGuaranteeType()){
                    mineServerEntity.setCheck(true);
                    guaranteeType = mineServerEntity.getGuaranteeType();
                }
            }
            dealTypeAdapter.notifyDataSetChanged();
            /**
             * ????????????????????????
             */
            for (int i = 0; i < commissionList.size(); i++) {
                MineServerEntity mineServerEntity = commissionList.get(i);
                mineServerEntity.setCheck(false);
                if(mineServerEntity.getCommissionType() == mineReleaseEntity.getCommissionType()){
                    mineServerEntity.setCheck(true);
                    commissionType = mineServerEntity.getCommissionType();
                }
            }
            commissionAdapter.notifyDataSetChanged();

            /**
             * ??????????????????
             */
            contact_tv.setText("?????????");
            contact_tv.setClickable(false);
        }
    }

    public static void startAty(Context context, MineReleaseEntity mineReleaseEntity){
        Intent intent = new Intent(context, ReleaseServerActivity.class);
        intent.putExtra("mineReleaseEntity",mineReleaseEntity);
        context.startActivity(intent);
    }

    public static void startAty(Fragment fragment, MineReleaseEntity mineReleaseEntity,int requestCode){
        Intent intent = new Intent(fragment.getContext(), ReleaseServerActivity.class);
        intent.putExtra("mineReleaseEntity",mineReleaseEntity);
        fragment.startActivityForResult(intent,requestCode);
    }
    private void initCommissionRecycler() {
        commissionAdapter = new ReleaseServerAdapter(R.layout.release_server_item,commissionList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        commission_recycler.setLayoutManager(linearLayoutManager);
        commission_recycler.setAdapter(commissionAdapter);
        commissionAdapter.addChildClickViewIds(R.id.release_server_tv);
        commissionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                for (int i = 0; i < commissionList.size(); i++) {
                    commissionList.get(i).setCheck(false);
                }
                MineServerEntity mineServerEntity = commissionList.get(position);
                mineServerEntity.setCheck(true);
                commissionAdapter.notifyDataSetChanged();

                commissionType = mineServerEntity.getCommissionType();
            }
        });

        addCommissionTypeData();
    }

    private void addCommissionTypeData() {
        commissionList.clear();
        MineServerEntity mineServerEntity = new MineServerEntity();
        mineServerEntity.setTwoLevelClassificationName("?????????");
        mineServerEntity.setCommissionType(0);
        mineServerEntity.setCheck(true);
        commissionList.add(mineServerEntity);
        MineServerEntity mineServerEntity1 = new MineServerEntity();
        mineServerEntity1.setTwoLevelClassificationName("?????????");
        mineServerEntity1.setCommissionType(1);
        commissionList.add(mineServerEntity1);
        MineServerEntity mineServerEntity2 = new MineServerEntity();
        mineServerEntity2.setTwoLevelClassificationName("????????????");
        mineServerEntity2.setCommissionType(2);
        commissionList.add(mineServerEntity2);
        MineServerEntity mineServerEntity3 = new MineServerEntity();
        mineServerEntity3.setTwoLevelClassificationName("??????");
        mineServerEntity3.setCommissionType(3);
        commissionList.add(mineServerEntity3);
        commissionAdapter.notifyDataSetChanged();

    }

    private void initDealTypeRecycler() {
        if(Utils.getSystemParamsEntity().getIsGuarantee().equals("1")){
            /**
             * ????????????????????????
             */
            deal_type_linear.setVisibility(View.VISIBLE);
            deal_type_tv.setVisibility(View.VISIBLE);
            commission_linear.setVisibility(View.VISIBLE);
        }else {
            /**
             * ????????????????????????
             */
            deal_type_linear.setVisibility(View.GONE);
            deal_type_tv.setVisibility(View.GONE);
            commission_linear.setVisibility(View.GONE);
        }
        dealTypeAdapter = new ReleaseServerAdapter(R.layout.release_server_item,dealTypeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        deal_type_recycler.setLayoutManager(linearLayoutManager);
        deal_type_recycler.setAdapter(dealTypeAdapter);
        dealTypeAdapter.addChildClickViewIds(R.id.release_server_tv);
        dealTypeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                for (int i = 0; i < dealTypeList.size(); i++) {
                    dealTypeList.get(i).setCheck(false);
                }
                MineServerEntity mineServerEntity = dealTypeList.get(position);
                mineServerEntity.setCheck(true);
                dealTypeAdapter.notifyDataSetChanged();
                if(mineServerEntity.getGuaranteeType() == 1){
                    /**
                     * ??????????????????????????????
                     */
                    commission_linear.setVisibility(View.VISIBLE);
                }else {
                    commission_linear.setVisibility(View.GONE);
                }
                guaranteeType = mineServerEntity.getGuaranteeType();
            }
        });
        addDealTypeData();
    }

    private void addDealTypeData() {
        dealTypeList.clear();
        MineServerEntity mineServerEntity = new MineServerEntity();
        mineServerEntity.setTwoLevelClassificationName("????????????");
        mineServerEntity.setGuaranteeType(1);
        mineServerEntity.setCheck(true);
        dealTypeList.add(mineServerEntity);
        MineServerEntity mineServerEntity1 = new MineServerEntity();
        mineServerEntity1.setTwoLevelClassificationName("????????????");
        mineServerEntity1.setGuaranteeType(0);
        dealTypeList.add(mineServerEntity1);
        dealTypeAdapter.notifyDataSetChanged();
    }

    private void initPriceRecycler() {
        priceAdapter = new ReleaseServerAdapter(R.layout.release_server_item,priceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        price_recycler.setLayoutManager(linearLayoutManager);
        price_recycler.setAdapter(priceAdapter);
        priceAdapter.addChildClickViewIds(R.id.release_server_tv);
        priceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                for (int i = 0; i < priceList.size(); i++) {
                    priceList.get(i).setCheck(false);
                }
                MineServerEntity mineServerEntity = priceList.get(position);
                mineServerEntity.setCheck(true);
                priceAdapter.notifyDataSetChanged();

                priceItemClick(position);
            }
        });
        MineServerEntity mineServerEntity = new MineServerEntity();
        mineServerEntity.setTwoLevelClassificationName("?????????");
        mineServerEntity.setPriceType(1);
        mineServerEntity.setCheck(true);
        priceList.add(mineServerEntity);
        MineServerEntity mineServerEntity1 = new MineServerEntity();
        mineServerEntity1.setTwoLevelClassificationName("????????????");
        mineServerEntity1.setPriceType(2);
        priceList.add(mineServerEntity1);
        MineServerEntity mineServerEntity2= new MineServerEntity();
        mineServerEntity2.setTwoLevelClassificationName("??????");
        mineServerEntity2.setPriceType(3);
        priceList.add(mineServerEntity2);
        priceAdapter.notifyDataSetChanged();
    }

    private void priceItemClick(int position) {


        if(position==0){
            /**
             * ?????????
             */
            yikoujia_linear.setVisibility(View.VISIBLE);
            baojiafanwei_linear.setVisibility(View.GONE);
            priceType=1;
            if(mineReleaseEntity!=null){
                String price = mineReleaseEntity.getPrice();
                yikoujia_etv.setText(price);
            }
        }else if(position == 1){
            /**
             * ????????????
             */
            yikoujia_linear.setVisibility(View.GONE);
            baojiafanwei_linear.setVisibility(View.VISIBLE);
            priceType=2;
            if(mineReleaseEntity!=null){
                String price = mineReleaseEntity.getPrice();
                if(StringMyUtil.isNotEmpty(price)){
                    String[] split = price.split("-");
                    if(split.length>=2){
                        baojiafanwei_etv1.setText(split[0]);
                        baojiafanwei_etv2.setText(split[1]);
                    }
                }
            }

        }else {
            /**
             * ??????
             */
            yikoujia_linear.setVisibility(View.GONE);
            baojiafanwei_linear.setVisibility(View.GONE);
            priceType=3;
        }
    }

    private void initContactDetails() {
        UserInfoEntity userInfoEntity = Utils.getUserInfo();
        String skype = userInfoEntity.getSkype();
        String telegram = userInfoEntity.getTelegram();
        String weixin = userInfoEntity.getWeixin();
        String qq = userInfoEntity.getQq();
        String whatsapp = userInfoEntity.getWhatsapp();
        if(StringMyUtil.isEmptyString(skype) && StringMyUtil.isEmptyString(telegram) && StringMyUtil.isEmptyString(weixin) && StringMyUtil.isEmptyString(qq) && StringMyUtil.isEmptyString(whatsapp)){
            contact_tv.setText("????????????");
            contact_tv.setClickable(true);
        }else {
            contact_tv.setText("?????????");
            contact_tv.setClickable(false);

        }
    }

    private void initServerRecycler() {
        releaseServerAdapter = new ReleaseServerAdapter(R.layout.release_server_item,mineServerEntityArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        release_type_recycler.setLayoutManager(linearLayoutManager);
        release_type_recycler.setAdapter(releaseServerAdapter);
        releaseServerAdapter.addChildClickViewIds(R.id.release_server_tv);
        releaseServerAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                for (int i = 0; i < mineServerEntityArrayList.size(); i++) {
                    mineServerEntityArrayList.get(i).setCheck(false);
                }
                currentMineServerEntity = mineServerEntityArrayList.get(position);
                currentMineServerEntity.setCheck(true);
                releaseServerAdapter.notifyDataSetChanged();
                String isGuarantee = currentMineServerEntity.getIsGuarantee();
                releaseTypeClick(isGuarantee);
            }
        });

    }

    private void releaseTypeClick(String isGuarantee) {
        /**
         *?????????????????????????????????????????????????????????????????????,?????????????????????
         */
        guaranteeType = 1 ;
        commissionType = 0;
        addDealTypeData();
        addCommissionTypeData();
        /**
         * ???????????????????????????????????????
         */

        if(isGuarantee.equals("1")){
            /**
             * ????????????
             */
            deal_type_linear.setVisibility(View.VISIBLE);
            deal_type_recycler.setVisibility(View.VISIBLE);
            commission_linear.setVisibility(View.VISIBLE);
            deal_type_tip_tv.setVisibility(View.GONE);
        }else {
            deal_type_linear.setVisibility(View.VISIBLE);
            deal_type_recycler.setVisibility(View.GONE);
            commission_linear.setVisibility(View.GONE);
            deal_type_tip_tv.setVisibility(View.VISIBLE);
        }
    }
    private void requestRule() {
        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, 0+"", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HelpChildEntity> helpChildEntities = JSONArray.parseArray(result, HelpChildEntity.class);
                if(helpChildEntities.size()>=1){
                    ruleContent = helpChildEntities.get(0).getContent();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    /**
     * ???????????????????????????
     */
    private void requestMineServer() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.MINE_SERVER_TYPE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                if(Utils.isNotEmptyArray(result)){
                    List<MineServerEntity> mineServerEntityList = JSONArray.parseArray(result, MineServerEntity.class);
                    if(mineServerEntityList.size()==0){
                        no_server_tv.setVisibility(View.VISIBLE);
                        release_type_recycler.setVisibility(View.GONE);
                    }else {
                        no_server_tv.setVisibility(View.GONE);
                        release_type_recycler.setVisibility(View.VISIBLE);
                    }
                    mineServerEntityArrayList.addAll(mineServerEntityList);
                    releaseServerAdapter.notifyDataSetChanged();
                }else {
                    no_server_tv.setVisibility(View.VISIBLE);
                    release_type_recycler.setVisibility(View.GONE);
                }
                /**
                 * ?????????????????????,?????????????????????????????????????????????????????????, ????????????????????????
                 */
                if(mineReleaseEntity!=null){
                    for (int i = 0; i < mineServerEntityArrayList.size(); i++) {
                        MineServerEntity mineServerEntity = mineServerEntityArrayList.get(i);
                        mineServerEntity.setCheck(false);
                        if(mineServerEntity.getTwoLevelClassificationId().equals(mineReleaseEntity.getTwoLevelClassification())){
                            mineServerEntity.setCheck(true);//recyclerView???????????????
                            currentMineServerEntity = mineServerEntity;
//                            releaseTypeClick(mineServerEntity.getIsGuarantee());
                        }
                    }
                    releaseServerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    @OnClick({R.id.contact_tv,R.id.release_btn,R.id.toolbar_right_tv,R.id.no_server_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.no_server_tv:
                startActivity(new Intent(ReleaseServerActivity.this, InformationActivity.class));
                break;
            case R.id.toolbar_right_tv:
                // ????????????
                initRulePop();
                break;
            case R.id.contact_tv:
                /**
                 * ?????????????????????, ??????????????????
                 */
                startActivity(new Intent(ReleaseServerActivity.this, InformationActivity.class));
                break;

            case R.id.release_btn:
                UserInfoEntity userInfoEntity = Utils.getUserInfo();
                String skype = userInfoEntity.getSkype();
                String telegram = userInfoEntity.getTelegram();
                String weixin = userInfoEntity.getWeixin();
                String qq = userInfoEntity.getQq();
                String whatsapp = userInfoEntity.getWhatsapp();
                boolean noContact = StringMyUtil.isEmptyString(skype) && StringMyUtil.isEmptyString(telegram) && StringMyUtil.isEmptyString(weixin) && StringMyUtil.isEmptyString(qq) && StringMyUtil.isEmptyString(whatsapp);
                if(noContact || mineServerEntityArrayList.size()==0){
                    /**
                     * ???????????????????????????????????????????????????????????????????????????
                     */
                    initCompleteInfoPop();
                }else {
                    if (checkParams()) return;
                    if(mineReleaseEntity==null){
                        /**
                         * ??????
                         */
                        requestReleaseServer();
                    }else {
                        /**
                         * ????????????
                         */
                        requestModifyReleaseServer();
                    }
                }

                break;
            default:
                break;
        }
    }

    private void initRulePop() {
        if(commonSurePop == null){
            if(StringMyUtil.isNotEmpty(ruleContent)){
                commonSurePop = new CommonSurePop(ReleaseServerActivity.this,"????????????",ruleContent);
            }else {
                showtoast("????????????");
            }
        }
        if(commonSurePop!=null){
            commonSurePop.showAtLocation(toolbar_right_tv, Gravity.CENTER,0,0);
            Utils.darkenBackground(ReleaseServerActivity.this,0.7f);
        }
    }

    private void requestReleaseServer() {
        HashMap<String, Object> data = new HashMap<>();
        String attachment="";
        for (int i = 0; i < allPhotoList.size(); i++) {
            attachment+=allPhotoList.get(i).getUploadUrl()+",";
        }
        if(StringMyUtil.isNotEmpty(attachment)){
            data.put("attachment",attachment.substring(0,attachment.length()-1));
        }
        data.put("guaranteeType",guaranteeType);
        if(guaranteeType == 1){
            /**
             * ???????????????????????????
             */
            data.put("commissionType",commissionType);
        }
        data.put("priceType",priceType);
        if(priceType==1){
            /**
             * ?????????
             */
            data.put("price",yikoujia_etv.getText().toString());
        }else if(priceType == 2){
            /**
             * ????????????
             */
            data.put("price",baojiafanwei_etv1.getText().toString()+"-"+baojiafanwei_etv2.getText().toString());
        }else {
            /**
             * ??????
             */
            data.put("price","");
        }
        data.put("content",release_content_etv.getText().toString());
        data.put("label", Utils.getUserInfo().getLabel());
        data.put("title",release_title_etv.getText().toString());
        data.put("twoLevelClassification",currentMineServerEntity.getTwoLevelClassificationId());
        HttpApiUtils.wwwRequest(ReleaseServerActivity.this, null, RequestUtils.RELEASE_RESOURCE, data, new HttpApiUtils.OnRequestLintener() {
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

    private void requestModifyReleaseServer() {
        HashMap<String, Object> data = new HashMap<>();
        String attachment="";
        for (int i = 0; i < allPhotoList.size(); i++) {
            attachment+=allPhotoList.get(i).getUploadUrl()+",";
        }
        if(StringMyUtil.isNotEmpty(attachment)){
            data.put("attachment",attachment.substring(0,attachment.length()-1));
        }
        data.put("guaranteeType",guaranteeType);
        if(guaranteeType == 1){
            /**
             * ???????????????????????????
             */
            data.put("commissionType",commissionType);
        }
        data.put("priceType",priceType);
        if(priceType==1){
            /**
             * ?????????
             */
            data.put("price",yikoujia_etv.getText().toString());
        }else if(priceType == 2){
            /**
             * ????????????
             */
            data.put("price",baojiafanwei_etv1.getText().toString()+"-"+baojiafanwei_etv2.getText().toString());
        }else {
            /**
             * ??????
             */
            data.put("price","");
        }
        data.put("content",release_content_etv.getText().toString());
        data.put("label", Utils.getUserInfo().getLabel());
        data.put("id",mineReleaseEntity.getId());
        data.put("title",release_title_etv.getText().toString());
        data.put("twoLevelClassification",currentMineServerEntity.getTwoLevelClassificationId());
        HttpApiUtils.wwwRequest(ReleaseServerActivity.this, null, RequestUtils.MODIFY_RELEASE_RESOURCE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("????????????");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    /**
     * ???????????????????????????????????????????????????????????????????????????
     */
    private void initCompleteInfoPop() {
        CommonTipPop commonTipPop = new CommonTipPop(ReleaseServerActivity.this,ReleaseServerActivity.this,"????????????","????????????????????????????????????","??????????????????");
        commonTipPop.setOnClickLintener(new CommonTipPop.OnClickLintener() {
            @Override
            public void onSureClick(View view) {
                startActivity(new Intent(ReleaseServerActivity.this, InformationActivity.class));
                finish();
            }
        });
        commonTipPop.showAtLocation(release_btn, Gravity.CENTER,0,0);
        Utils.darkenBackground(ReleaseServerActivity.this,0.7f);
    }

    /**
     * ????????????????????????
     * @return
     */
    private boolean checkParams() {
        if(allPhotoList !=null&&initIsWaitUpload()){
            showtoast("???????????????,?????????");
            return true;
        }

        ArrayList<Integer> failPositionList = initFailPositionList();
        if(allPhotoList !=null&& failPositionList.size()!=0){
            String toastContent="";
            for (int i = 0; i < failPositionList.size(); i++) {
                toastContent+=(i+1)+" ,";
            }
            showtoast("???"+toastContent+"?????????????????????,???????????????!");
            return true;
        }

        if(currentMineServerEntity == null){
            showtoast("???????????????????????????");
            return true;
        }
        if(StringMyUtil.isEmptyString(release_content_etv.getText().toString())){
            showtoast("???????????????????????????");
            return true;
        }
        if(StringMyUtil.isEmptyString(release_title_etv.getText().toString())){
            showtoast("???????????????????????????");
            return true;
        }
        return false;
    }

    private boolean initIsWaitUpload() {
        boolean isWaitUpload =false;
        for (int i = 0; i < allPhotoList.size(); i++) {
            boolean success = allPhotoList.get(i).isSuccess();
            if(!success){
                isWaitUpload=true;
            }
        }
        return isWaitUpload;
    }

    private ArrayList<Integer>  initFailPositionList() {
        ArrayList<Integer>failPositionList = new ArrayList<>();
        for (int i = 0; i < allPhotoList.size(); i++) {
            boolean fail = allPhotoList.get(i).isFail();
            if(fail){
                failPositionList.add(i);
            }
        }
        return failPositionList;
    }
    /**
     * ????????????
     * @param uploadFileEntity
     */
    private void uploadImg(UploadFileEntity  uploadFileEntity) {
        if(StringMyUtil.isNotEmpty(uploadFileEntity.getUploadUrl())){
            return;
        }
        String localPath = uploadFileEntity.getLocalMedia().getRealPath();
        HttpApiImpl.getInstance()
                .uploadFile(localPath)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseStringObserver<ResponseBody>(){
                    @Override
                    public void onSuccess(String result) {
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
                        if(data!=null){
                            for (int i = 0; i <allPhotoList.size(); i++) {
                                UploadFileEntity uploadFileEntity = allPhotoList.get(i);
                                if(StringMyUtil.isNotEmpty(uploadFileEntity.getUploadUrl())){
                                    continue;
                                }
                                if( uploadFileEntity.getLocalMedia().getRealPath().equals(localPath)){
                                    uploadFileEntity.setSuccess(true);
                                    uploadFileEntity.setUploadUrl(data.getString("url"));
                                    mAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        for (int i = 0; i < allPhotoList.size(); i++) {
                            UploadFileEntity uploadFileEntity = allPhotoList.get(i);
                            if(StringMyUtil.isNotEmpty(uploadFileEntity.getUploadUrl())){
                                continue;
                            }
                            uploadFileEntity.getLocalMedia().equals(localPath);
                            uploadFileEntity.setFail(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                    }

                    @Override
                    protected void onRequestEnd() {
                        super.onRequestEnd();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (launcherResult != null) {
            launcherResult.unregister();
        }
        BroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver, BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
    }

    private void clearCache() {
        // ?????????????????????????????????????????????????????? ??????:????????????????????????????????? ?????????????????????
        if (PermissionChecker.checkSelfPermission(ReleaseServerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //PictureCacheManager.deleteCacheDirFile(this, PictureMimeType.ofImage());
            PictureCacheManager.deleteAllCacheDirRefreshFile(ReleaseServerActivity.this);
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }
}