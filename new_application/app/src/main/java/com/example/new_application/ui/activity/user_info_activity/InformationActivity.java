package com.example.new_application.ui.activity.user_info_activity;



import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.bean.UploadFileEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiImpl;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.mine_fragment_activity.ChooseServerActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.ReleaseServerActivity;
import com.example.new_application.ui.pop.AddServerLabelPop;
import com.example.new_application.ui.pop.ModifyNickNamePop;
import com.example.new_application.ui.pop.TakeCameraPop;
import com.example.new_application.utils.BitmapUtils;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.GetPhotoFromPhotoAlbum;
import com.example.new_application.utils.GlideEngine;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class InformationActivity extends BaseActivity {
    @BindView(R.id.contact_details_group)
    Group contact_details_group;
    @BindView(R.id.avatar_relativeLayout)
    RelativeLayout avatar_relativeLayout;
    @BindView(R.id.nickname_relativeLayout)
    RelativeLayout nickname_relativeLayout;
    @BindView(R.id.contact_details_relativeLayout)
    RelativeLayout contact_details_relativeLayout;
    @BindView(R.id.talegram_etv)
    EditText talegram_etv;
    @BindView(R.id.skype_etv)
    EditText skype_etv;
    @BindView(R.id.whatsApp_etv)
    EditText whatsApp_etv;
    @BindView(R.id.qq_etv)
    EditText qq_etv;
    @BindView(R.id.wx_etv)
    EditText wx_etv;
    @BindView(R.id.save_info_btn)
    Button save_info_btn;
    @BindView(R.id.server_type_relativeLayout)
    RelativeLayout server_type_relativeLayout;
    @BindView(R.id.server_label_tv)
    TextView server_label_tv;
    @BindView(R.id.server_type_content_tv)
    TextView server_type_content_tv;
    @BindView(R.id.nickname_content_tv)
    TextView nickname_content_tv;
    @BindView(R.id.title_iv)
    ImageView title_iv;
    @BindView(R.id.username_content_tv)
    TextView username_content_tv;
    @BindView(R.id.about_us_etv)
    EditText about_us_etv;
    @BindView(R.id.bat_etv)
    EditText bat_etv;
    @BindView(R.id.potato_etv)
    EditText potato_etv;
    TakeCameraPop takeCameraPop;
    private String[] CAMARA_PERMISSIONS ={  Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private Uri uri;
    private File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
    ModifyNickNamePop modifyNickNamePop;
    AddServerLabelPop addServerLabelPop;
    String userType;
    ArrayList<String> selectorServerList = new ArrayList<>();
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private ActivityResultLauncher<Intent> launcherResult;
    @Override
    public int getLayoutId() {
        return R.layout.activity_infor_mation;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userType = Utils.getUserInfo().getUserType();
        CommonToolbarUtil.initToolbar(this,"????????????");
        requestUserInfo();
        launcherResult = createActivityResultLauncher();
    }

    private void requestUserInfo() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.USER_INFO, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
                sp.setUserInfo(result);
                GlideLoadViewUtil.LoadTitleView(InformationActivity.this,userInfoEntity.getImage(),title_iv);
                nickname_content_tv.setText(userInfoEntity.getNickname());
                username_content_tv.setText(userInfoEntity.getUsername());
                String details = userInfoEntity.getDetails();
                if(StringMyUtil.isNotEmpty(details)&Utils.isJsonObject(details)){
                    JSONObject jsonObject = JSONObject.parseObject(details);
                    if(jsonObject.containsKey("aboutUs")){
                        String aboutUs = jsonObject.getString("aboutUs");
                        about_us_etv.setText(aboutUs);
                    }
                }
                String label = userInfoEntity.getLabel();
                String whatsapp = userInfoEntity.getWhatsapp();
                String weixin = userInfoEntity.getWeixin();
                String qq = userInfoEntity.getQq();
                String telegram = userInfoEntity.getTelegram();
                String skype = userInfoEntity.getSkype();
                String bat = userInfoEntity.getBat();
                String potato = userInfoEntity.getPotato();
                if(StringMyUtil.isNotEmpty(label)){
                    server_label_tv.setText(label);
                }
                if(StringMyUtil.isNotEmpty(whatsapp)){
                    whatsApp_etv.setText(whatsapp);
                }
                if(StringMyUtil.isNotEmpty(weixin)){
                    wx_etv.setText(weixin);
                }
                if(StringMyUtil.isNotEmpty(qq)){
                    qq_etv.setText(qq);
                }
                if(StringMyUtil.isNotEmpty(telegram)){
                    talegram_etv.setText(telegram);
                }
                if(StringMyUtil.isNotEmpty(skype)){
                    skype_etv.setText(skype);
                }
                if(StringMyUtil.isNotEmpty(potato)){
                    potato_etv.setText(potato);
                }
                if(StringMyUtil.isNotEmpty(bat)){
                    bat_etv.setText(bat);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestServerType();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (launcherResult != null) {
            launcherResult.unregister();
        }
    }

    private void requestServerType() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.MINE_SERVER_TYPE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                selectorServerList.clear();
                if(Utils.isNotEmptyArray(result)){
                    List<MineServerEntity> mineServerEntityList = JSONArray.parseArray(result, MineServerEntity.class);
                    String content="";
                    for (int i = 0; i <mineServerEntityList.size() ; i++) {
                        content+=mineServerEntityList.get(i).getTwoLevelClassificationName()+",";
                        selectorServerList.add(mineServerEntityList.get(i).getTwoLevelClassificationId());
                    }
                    if(StringMyUtil.isEmptyString(content)){
                        server_type_content_tv.setText("");
                    }else {

                        server_type_content_tv.setText(content.substring(0,content.length()-1));
                    }
                }

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    @OnClick({R.id.save_info_btn,R.id.avatar_relativeLayout,R.id.nickname_relativeLayout,R.id.server_type_relativeLayout,R.id.contact_details_relativeLayout,
            R.id.server_label_linear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.server_label_linear:
                initAddServerLabelPop();
                addServerLabelPop.showAtLocation(save_info_btn, Gravity.CENTER,0,0);
                Utils.darkenBackground(InformationActivity.this,0.7f);
                break;
            case R.id.save_info_btn:
                requestModifyContact();
                break;
            case R.id.avatar_relativeLayout:
                if(takeCameraPop == null){
                    initTakeCameraPop();
                }
                takeCameraPop.showAtLocation(save_info_btn, Gravity.BOTTOM,0,0);
                Utils.darkenBackground(InformationActivity.this,0.7f);
                break;
            case R.id.nickname_relativeLayout:
                initModifyNamePop();
                break;
            case R.id.server_type_relativeLayout:
                ChooseServerActivity.startAcy(InformationActivity.this,selectorServerList);
                break;
            case R.id.contact_details_relativeLayout:
                if(contact_details_group.getVisibility()!=View.VISIBLE){
                    contact_details_group.setVisibility(View.VISIBLE);
                }else {
                    contact_details_group.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     */
    private void requestModifyContact() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateFlag",2);
        data.put("aboutUs",about_us_etv.getText().toString());
        data.put("qq",qq_etv.getText().toString());
        data.put("skype",skype_etv.getText().toString());
        data.put("telegram",talegram_etv.getText().toString());
        data.put("weixin",wx_etv.getText().toString());
        data.put("whatsapp",whatsApp_etv.getText().toString());
        data.put("potato",potato_etv.getText().toString());
        data.put("bat",bat_etv.getText().toString());

        HttpApiUtils.wwwRequest(this, null, RequestUtils.UPDATE_USER_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("????????????");
                requestUserInfo();
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    private void initAddServerLabelPop() {
        addServerLabelPop = new AddServerLabelPop(InformationActivity.this,server_label_tv.getText().toString());
        EditText add_server_label_etv = addServerLabelPop.getAdd_server_label_etv();
        addServerLabelPop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.add_server_label_sure_tv:
                        String labelStr = add_server_label_etv.getText().toString();
                        requestModifyLabel(labelStr);
                        addServerLabelPop.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void requestModifyLabel(String labelStr) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateFlag",22);
        data.put("label",labelStr.replace("???",","));
        HttpApiUtils.wwwRequest(this, null, RequestUtils.UPDATE_USER_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("??????????????????");
                addServerLabelPop.dismiss();
                server_label_tv.setText(labelStr);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initModifyNamePop() {
        modifyNickNamePop = new ModifyNickNamePop(this);
        EditText modify_nick_name_etv = modifyNickNamePop.getModify_nick_name_etv();
        modifyNickNamePop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.modify_nick_name_sure_tv:
                        String nickNmae = modify_nick_name_etv.getText().toString();
                        requestModifyNickName(nickNmae);
                        break;
                    default:
                        break;
                }
            }
        });
        modifyNickNamePop.showAtLocation(save_info_btn, Gravity.CENTER,0,0);
        Utils.darkenBackground(InformationActivity.this,0.7f);
    }

    /**
     * ????????????
     * @param nickNmae
     */
    private void requestModifyNickName(String nickNmae) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateFlag",1);
        data.put("nickname",nickNmae);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.UPDATE_USER_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("??????????????????");
                modifyNickNamePop.dismiss();
                nickname_content_tv.setText(nickNmae);
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    private void initTakeCameraPop() {
        takeCameraPop=new TakeCameraPop(this);
        takeCameraPop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.forbidden_tv:
                        PictureSelectionModel pictureSelectionModel = PictureSelector.create(InformationActivity.this)
                                .openCamera(PictureMimeType.ofImage())// ?????????????????????????????????????????? ??????????????????????????????or??????
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                                .minSelectNum(1)// ??????????????????
                                .closeAndroidQChangeWH(true)//??????????????????????????????????????????????????????true
                                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// ???????????????????????????????????????????????????false
                                .selectionMode(PictureConfig.SINGLE)// ?????? or ??????
                                .isPreviewImage(true)// ?????????????????????
                                .isCamera(false)// ????????????????????????
                                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                                .setCameraImageFormat(PictureMimeType.JPEG) // ????????????????????????,??????.jpeg
                                .setCameraVideoFormat(PictureMimeType.MP4)// ????????????????????????,??????.mp4
                                .setCameraAudioFormat(PictureMimeType.AMR)// ????????????????????????,??????.amr
                                .isEnableCrop(false)// ????????????
                                .isCompress(true)// ????????????
                                .synOrAsy(false)//??????true?????????false ?????? ????????????
                                .isGif(false)// ????????????gif??????
//                                .selectionData(getSelectionData(mAdapter.getData()))// ????????????????????????
                                .cutOutQuality(90)// ?????????????????? ??????100
                                .minimumCompressSize(100);// ????????????kb??????????????????
                        pictureSelectionModel.forResult(launcherResult);
                        takeCameraPop.dismiss();
                        break;
                    case R.id.set_manager_tv:
                        // ????????????
                        PictureSelectionModel model = PictureSelector.create(InformationActivity.this)
                                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                                .isWeChatStyle(true)// ????????????????????????????????????
                                .isMaxSelectEnabledMask(true)// ?????????????????????????????????????????????????????????
                                .setCustomCameraFeatures(CustomCameraView.BUTTON_STATE_BOTH)// ?????????????????????????????????
                                .setCaptureLoadingColor(ContextCompat.getColor(InformationActivity.this, R.color.app_color_blue))
                                .maxSelectNum(1)// ????????????????????????
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
                                .selectionMode(PictureConfig.SINGLE)// ?????? or ??????
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
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(InformationActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }
                            }
                            if(selectList.size()!=0){
                                uploadImg(selectList.get(0).getRealPath());
                            }
                        }
                    }
                });
    }

    /**
     * ????????????
     * @param url
     */
    private void uploadImg(String url) {
        HttpApiImpl.getInstance()
                .uploadFile(url)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseStringObserver<ResponseBody>(){
                    @Override
                    public void onSuccess(String result) {
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
                        if(data!=null){
                            String imageUrl = data.getString("url");
                            requestModifyAvatar(imageUrl);
                        }
                    }

                    @Override
                    public void onFail(String msg) {

                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                        InformationActivity.this.showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        super.onRequestEnd();
                        InformationActivity.this.closeLoading();
                    }
                });
    }

    /**
     * ????????????
     * @param imageUrl
     */
    private void requestModifyAvatar(String imageUrl) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateFlag",0);
        data.put("image",imageUrl);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.UPDATE_USER_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("??????????????????");
                GlideLoadViewUtil.LoadTitleView(InformationActivity.this,imageUrl,title_iv);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}