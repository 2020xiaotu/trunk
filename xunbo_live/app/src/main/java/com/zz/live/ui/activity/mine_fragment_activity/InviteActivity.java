package com.zz.live.ui.activity.mine_fragment_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.live.MyApplication;
import com.zz.live.R;
import com.zz.live.base.BaseActivity;
import com.zz.live.utils.CommonToolbarUtil;
import com.zz.live.utils.QRCodeUtil;
import com.zz.live.utils.SavePhoto;
import com.zz.live.utils.ShareUtils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class InviteActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.invite_code_tv)
    TextView invite_code_tv;
    @BindView(R.id.qr_code_iv)
    ImageView qr_code_iv;
    @BindView(R.id.rule_one_tv)
    TextView rule_one_tv;
    @BindView(R.id.rule_two_tv)
    TextView rule_two_tv;
    @BindView(R.id.rule_three_tv)
    TextView rule_three_tv;
    @BindView(R.id.rule_four_tv)
    TextView rule_four_tv;
    @BindView(R.id.rule_five_tv)
    TextView rule_five_tv;
    @BindView(R.id.invite_tip_tv)
    TextView invite_tip_tv;
    @BindView(R.id.save_qr_code_tv)
    TextView save_qr_code_tv;
    @BindView(R.id.copy_tv)
    TextView copy_tv;
    @BindView(R.id.share_tv)
    TextView share_tv;

    private String[] PERMISSIONS={"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_PERMISSONS_CODE=1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initQrCode("dsdsdsdsdsdsdsjdkshdks jijdksah permissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissionpermissiondsahksahfaskisuksdhdfjthflpturkilyjeroyhtrftjnkyhtolt lp.yrp; jolot[pktp[;hjm");
//        qr_code_iv.setBackgroundColor(Color.RED);
   }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        CommonToolbarUtil.initStatusBarColor(this);
        CommonToolbarUtil.initToolbar(this,"??????");
    }

    /**
     * ???????????????
     * @param inviteCodeShareUrl ????????????
     */
    private void initQrCode(String inviteCodeShareUrl) {
//        Bitmap qrImage = QRCodeUtil.createQRImage(inviteCodeShareUrl, 500, 500);
//        Bitmap qrImage = QRCodeUtil.createQRImage("https://www.baidu.com", 200, 200);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 166, 166);
        qr_code_iv.setImageBitmap(mBitmap);
    }
    @OnClick({R.id.save_qr_code_tv,R.id.copy_tv,R.id.share_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.save_qr_code_tv:
                checkReadPermission();
                break;
            case R.id.copy_tv:
                //?????????
                //??????application????????????, ??????????????????
                ClipboardManager clipboardManagerUrl= (ClipboardManager) MyApplication.getInstance().getSystemService(CLIPBOARD_SERVICE);//?????????clipboardManager??????
                ClipData mClipDataUrl=  ClipData.newPlainText("inviteCode", invite_code_tv.getText().toString());//??????????????????????????????  newPlainText
                clipboardManagerUrl.setPrimaryClip(mClipDataUrl);
                showtoast("?????????????????????");
                break;
            case R.id.share_tv:
                // ????????????
                ShareUtils.start2Share(InviteActivity.this,"sdsdsdsdsds");
                break;
                default:
                    break;
        }
    }

    private void checkReadPermission() {
        //???????????????,???????????????
        if(EasyPermissions.hasPermissions(this,PERMISSIONS)){
            saveQrCode();
        }else {
            //????????????,????????????
            EasyPermissions.requestPermissions(this,"??????????????????????????????",REQUEST_PERMISSONS_CODE,PERMISSIONS);
        }
    }
    /*
   ???????????????
    */
    private void saveQrCode() {
        try {
            SavePhoto savePhoto = new SavePhoto(this);
            savePhoto.SaveBitmapFromView(findViewById(R.id.qr_code_iv));
            showtoast("?????????????????????");
            setResult(RESULT_OK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode==REQUEST_PERMISSONS_CODE){
            saveQrCode();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showtoast("????????????????????????,?????????????????????,????????????????????????");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //????????????????????????????????????'????????????'???????????????????????????????????????????????????dialog?????????
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onNetChange(boolean netWorkState) {

    }
}
