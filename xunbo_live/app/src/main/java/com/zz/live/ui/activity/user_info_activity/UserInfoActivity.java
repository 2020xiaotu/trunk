package com.zz.live.ui.activity.user_info_activity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zz.live.BuildConfig;
import com.zz.live.R;
import com.zz.live.base.BaseActivity;
import com.zz.live.base.BasePopupWindow;
import com.zz.live.bean.UserInfoEntity;
import com.zz.live.net.api.HttpApiImpl;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.activity.main_tab_activity.LoginActivity;
import com.zz.live.ui.pop.ModifyNickNamePop;
import com.zz.live.ui.pop.ModifySexPop;
import com.zz.live.ui.pop.TakeCameraPop;
import com.zz.live.utils.BitmapUtils;
import com.zz.live.utils.ClearCache;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.CommonToolbarUtil;
import com.zz.live.utils.GetPhotoFromPhotoAlbum;
import com.zz.live.utils.GlideLoadViewUtil;
import com.zz.live.utils.ImageThumbnail;
import com.zz.live.utils.RequestUtils;
import com.zz.live.utils.SharePreferencesUtil;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.Utils;
import com.zz.live.utils.VersionUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

public class UserInfoActivity extends BaseActivity implements BasePopupWindow.OnPopClickListener,EasyPermissions.PermissionCallbacks {
    @BindView(R.id.title_iv)
    ImageView title_iv;
    @BindView(R.id.account_tv)
    TextView account_tv;
    @BindView(R.id.nickname_tv)
    TextView nickname_tv;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.login_psd_tv)
    TextView login_psd_tv;
    @BindView(R.id.pay_psd_tv)
    TextView pay_psd_tv;
    @BindView(R.id.user_title_relativeLayout)
    RelativeLayout user_title_relativeLayout;
    @BindView(R.id.account_relativeLayout)
    RelativeLayout account_relativeLayout;
    @BindView(R.id.nickname_relativeLayout)
    RelativeLayout nickname_relativeLayout;
    @BindView(R.id.sex_relativeLayout)
    RelativeLayout sex_relativeLayout;
    @BindView(R.id.login_psd_relativeLayout)
    RelativeLayout login_psd_relativeLayout;
    @BindView(R.id.pay_psd_relativeLayout)
    RelativeLayout pay_psd_relativeLayout;
    @BindView(R.id.exit_login_btn)
    Button exit_login_btn;
    @BindView(R.id.nickname_next_iv)
    ImageView nickname_next_iv;
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;


    TakeCameraPop takeCameraPop;
    private File cameraSavePath;
    //????????????
    private Uri uri;
    private int CAMARE_REQUEST_CODE = 1;//????????????????????????
    private int PHOTO_REQUEST_CODE = 2;//????????????????????????
    private String[] PERMISSIONS={"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    ModifyNickNamePop modifyNickNamePop;
    ModifySexPop modifySexPop;
    private int nicknameCount;

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        CommonToolbarUtil.initStatusBarColor(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        CommonToolbarUtil.initToolbar(this,"????????????");
        requestUserInfo();
        if(BuildConfig.DEBUG){
            version_code_tv.setText("????????????:?????????_"+ VersionUtils.getAppVersionName(this));
        }else {
            version_code_tv.setText("????????????:"+ VersionUtils.getAppVersionName(this));
        }
    }
    private void requestUserInfo() {
        HttpApiUtils.request(this,null, RequestUtils.USER_INFO, new HashMap<String,Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                SharePreferencesUtil.putString(UserInfoActivity.this, CommonStr.USER_INFO,result);
                UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
                UserInfoEntity.DataBean userInfoEntityData = userInfoEntity.getData();
                nickname_tv.setText(userInfoEntityData.getNickname());
                account_tv.setText(userInfoEntityData.getUsername());
                int sex = userInfoEntityData.getSex();
                if(sex==0){
                    sex_tv.setText("???");
                }else {
                    sex_tv.setText("???");
                }
                GlideLoadViewUtil.LoadTitleView(UserInfoActivity.this,userInfoEntityData.getImage(),title_iv);
                UserInfoEntity.DataBean userInfoBean = Utils.getUserInfoBean();
                nicknameCount = userInfoBean.getNicknameCount();
                if(nicknameCount<=0){
                    nickname_next_iv.setVisibility(View.INVISIBLE);
                }else {
                    nickname_next_iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    @OnClick({R.id.user_title_relativeLayout,R.id.account_relativeLayout,R.id.nickname_relativeLayout,R.id.sex_relativeLayout,R.id.login_psd_relativeLayout,R.id.pay_psd_relativeLayout,R.id.exit_login_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.user_title_relativeLayout:
                if(takeCameraPop ==null){
                    takeCameraPop = new TakeCameraPop(UserInfoActivity.this);
//                    takeCameraPop.initPop();
                    takeCameraPop.setOnPopClickListener(this);
                }
                Utils.darkenBackground(UserInfoActivity.this,0.7f);
                takeCameraPop.showAtLocation(user_title_relativeLayout, Gravity.BOTTOM,0,0);
                break;
            case R.id.nickname_relativeLayout:
                if(nicknameCount>0){
                    if(modifyNickNamePop==null){
                        modifyNickNamePop=new ModifyNickNamePop(UserInfoActivity.this);
                        modifyNickNamePop.setOnPopClickListener(this);
                    }
                    modifyNickNamePop.showAtLocation(nickname_relativeLayout,Gravity.CENTER,0,0);
                    Utils.darkenBackground(UserInfoActivity.this,0.7f);
                }else{
                    showtoast("????????????????????????");
                    nickname_next_iv.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.sex_relativeLayout:
                if(modifySexPop==null){
                    modifySexPop=new ModifySexPop(UserInfoActivity.this);
//                    modifySexPop.initPop();
                    modifySexPop.setOnPopClickListener(this);
                }
                Utils.darkenBackground(UserInfoActivity.this,0.7f);
                modifySexPop.showAtLocation(nickname_relativeLayout,Gravity.BOTTOM,0,0);
                break;
            case R.id.login_psd_relativeLayout:
                ModifyPasswordActivity.startAty(UserInfoActivity.this,1);
                break;
            case R.id.pay_psd_relativeLayout:
                ModifyPasswordActivity.startAty(UserInfoActivity.this,2);
                break;
            case R.id.exit_login_btn:
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                ClearCache.clearCache(UserInfoActivity.this);
                break;
            default:
                break;
        }
    }
    @Override
    public void onNetChange(boolean netWorkState) {

    }

    @Override
    public void onPopClick(View view) {
        switch (view.getId()){
            case R.id.forbidden_tv:
                takeCameraPop.dismiss();
                checkCameraPermission();
                break;
            case R.id.set_manager_tv:
                takeCameraPop.dismiss();
                checkPhotoPermission();
                break;
            case R.id.set_business_card_cancel_tv:
                modifyNickNamePop.dismiss();
                break;
            case R.id.set_business_card_sure_tv:
                String newNickName = modifyNickNamePop.modify_nickname_etv.getText().toString();
                if(checkNicknameEtv(newNickName)&&nicknameCount!=0){
                    modifyNickname(newNickName);
                }
                break;
            case R.id.man_tv:
                modifySex(0);
                break;
            case R.id.woman_tv:
                modifySex(1);
                break;
            default:
                break;
        }
    }
    /**
     * ????????????
     * @param newNickName
     */
    private void modifyNickname(String newNickName) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("nickname",newNickName);
        HttpApiUtils.request(UserInfoActivity.this, null,RequestUtils.MODIFY_USERINFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                showtoast(jsonObject.getString("msg"));
                modifyNickNamePop.dismiss();
                nickname_tv.setText(newNickName);
                nickname_next_iv.setVisibility(View.INVISIBLE);
                nicknameCount=0;
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    /**
     * ????????????
     * @param sex
     */
    private void modifySex(int sex) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("sex",sex);
        HttpApiUtils.request(UserInfoActivity.this,null, RequestUtils.MODIFY_USERINFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                showtoast(jsonObject.getString("msg"));
                modifySexPop.dismiss();
                if(sex==0){
                    sex_tv.setText("???");
                }else{
                    sex_tv.setText("???");
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    /**
     * ????????????
     * @param image
     */
    private void modifyTitle(String image) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("image",image);
        HttpApiUtils.request(UserInfoActivity.this,null, RequestUtils.MODIFY_USERINFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                showtoast(jsonObject.getString("msg"));
                GlideLoadViewUtil.LoadTitleView(UserInfoActivity.this,image,title_iv);
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    public boolean checkNicknameEtv(String newNickName){

        if(StringMyUtil.isEmptyString(newNickName)){
            showtoast("??????????????????");
            return false;
        }else if(newNickName.length()<1||newNickName.length()>10){
            showtoast("??????????????????(1-10???)");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String photoPath;
        String s = null;
        //???????????????
        if(requestCode==CAMARE_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath = String.valueOf(cameraSavePath);
                    //???????????????????????????
                    showBitMap(String.valueOf(cameraSavePath));
                    s = BitmapUtils.compressReSave(photoPath, this, 400);//????????????
                } else {
                    photoPath = uri.getEncodedPath();
                    s = BitmapUtils.compressReSave(photoPath, this, 400);//????????????
                }
                // ????????????
                upLoadImg(s);
            }
        }
        //???????????????,????????????
        else if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            String realPathFromUri = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            //???????????????????????????
            showBitMap(realPathFromUri);
            s = BitmapUtils.compressReSave(realPathFromUri, UserInfoActivity.this, 400);//????????????
            if (!StringMyUtil.isEmptyString(realPathFromUri)) {
                upLoadImg(s);
            } else {
                showtoast("??????????????????,?????????");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upLoadImg(String fliePath) {
        HttpApiImpl.getInstance()
                .uploadFile(fliePath)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseStringObserver<ResponseBody>(){

                    @Override
                    public void onSuccess(String result) {
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
                        if(data!=null){
                            //????????????
                            String url = data.getString("url");
                            modifyTitle(url);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        System.out.println(msg);
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
     * ??????????????????????????????,??????imageView?????????????????????,????????????????????????
     * @param path
     */
    private void showBitMap(String path) {
        //??????????????????????????????????????????????????????
        Bitmap camorabitmap = BitmapFactory.decodeFile(path);
        if (null != camorabitmap) {
            int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(), camorabitmap.getHeight(), title_iv.getWidth(), title_iv.getHeight());
            Bitmap bitMap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale, camorabitmap.getHeight() / scale);
            //??????Bitmap????????????????????????????????????????????????????????????out of memory??????
            camorabitmap.recycle();
            Glide.with(this)
                    .load(bitMap)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(title_iv);
        }
    }

    /*
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
    /*
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

    /*
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

    /*
    ??????????????????
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showtoast("????????????????????????????????????????????????");
    }

    //??????????????????
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getApplication().getPackageName() + ".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMARE_REQUEST_CODE);
    }
    /*
    ????????????
     */
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }
}
