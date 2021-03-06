package com.zz.live.ui.fragment.feedback_fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zz.live.MyApplication;
import com.zz.live.R;
import com.zz.live.base.BaseActivity;
import com.zz.live.base.BaseFragment;
import com.zz.live.bean.CommonProblemModel;
import com.zz.live.bean.UpdateMineFeedback;
import com.zz.live.net.api.HttpApiImpl;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.activity.start_live_activity.StartLiveActivity;
import com.zz.live.ui.adapter.CommonAdapter;
import com.zz.live.ui.adapter.MeetProblemAdapter;
import com.zz.live.utils.AppUtil;
import com.zz.live.utils.BitmapUtils;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.CommonToolbarUtil;
import com.zz.live.utils.DeviceUtils;
import com.zz.live.utils.GetPhotoFromPhotoAlbum;
import com.zz.live.utils.GlideLoadViewUtil;
import com.zz.live.utils.ImageThumbnail;
import com.zz.live.utils.ProgressDialogUtil;
import com.zz.live.utils.RequestUtils;
import com.zz.live.utils.SharePreferencesUtil;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.Utils;
import com.zz.live.utils.VersionUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

/*
????????????
 */
public class WantToFeedbackFragment extends BaseFragment implements View.OnClickListener , EasyPermissions.PermissionCallbacks, TextWatcher {
    @BindView(R.id.commit_button)
    Button commitBtn;
    @BindView(R.id.problem_etv)
    EditText problemEtv;
    @BindView(R.id.add_image)
    ImageView addIv;
    @BindView(R.id.mine_problem_recycle)
    RecyclerView meetRecycle;
    @BindView(R.id.edit_text_length_tv)
    TextView lengthTv;
    private MeetProblemAdapter meetProblemAdapter;
    private ArrayList<CommonProblemModel.DataBean> meetModelList=new ArrayList<>();

    private PopupWindow addImagePop;
    private TextView takeCameratv;
    private TextView choosePhototv;
    private TextView cancelTv;
    public static String TAG="WantToFeedbackFragment";
    private String CAMERA_PERMISSION= Manifest.permission.CAMERA;
    private String[] PHOTO_PERMISSIONS={  Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String[] CAMARA_PERMISSIONS ={  Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private int REQUEST_CAMERA_CODE=1;
    private int REQUEST_PHOTO_CODE=2;
    private int READ_EXTERNAL_CODE=3;
    private File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
    private String problemId;
    //????????????
    private Uri uri;
    //?????????????????????url
    private String realPath;
    private boolean havePhoto=false;
    CommonProblemModel.DataBean problemModel;//?????????????????????


    @Override
    protected void init(Bundle savedInstanceState) {
        initMeetRecy();
        problemEtv.addTextChangedListener(this);
        commitBtn.setOnClickListener(this);
        addIv.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_want_to_feedback;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
        ????????????????????????
         */
    private void initMeetRecy() {
        meetProblemAdapter=new MeetProblemAdapter(meetModelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        meetRecycle.setLayoutManager(gridLayoutManager);
        meetRecycle.setAdapter(meetProblemAdapter);
        meetProblemAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                problemModel = meetModelList.get(position);
                problemId=problemModel.getType()+"";
            }
        });
        /*
        ??????????????????
         */
        getAllProblem();
    }

    private void getAllProblem() {

        HashMap<String, Object> data = new HashMap<>();
        HttpApiUtils.request(getActivity(), this, RequestUtils.FEEDBACK_TYPE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                CommonProblemModel commonProblemModel = JSONObject.parseObject(result, CommonProblemModel.class);
                List<CommonProblemModel.DataBean> dataBeans = commonProblemModel.getData();
                meetModelList.addAll(dataBeans);
                meetProblemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_image:
                addIv.setClickable(false);
                if(addImagePop==null){
                    initAddImagePop();
                }
                addImagePop.showAtLocation(meetRecycle, Gravity.BOTTOM,0,0);
                ProgressDialogUtil.darkenBackground(getActivity(),0.7f);
                break;
            case R.id.forbidden_tv:
                checkCameraPermission();
                addImagePop.dismiss();
                break;
            case R.id.set_manager_tv:
//                checkPhotoPermisson();
                checkPermissions();
                addImagePop.dismiss();
                break;
            case R.id.forbidden_cancel_tv:
                addImagePop.dismiss();
                break;
            case R.id.commit_button:
                String str = problemEtv.getText().toString();
                if(StringMyUtil.isEmptyString(str)){
                    showtoast("???????????????????????????");
                }else if(str.length()<10||str.length()>100){
                    showtoast("??????????????????10-100??????");
                }else if(!isSelectorItem()){
                    showtoast("?????????????????????,????????????????????????????????????");
                }else {
                        //????????????
                    ruquestFeedback(str);
                }
        }
    }

    private void ruquestFeedback(String str) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appVersion", VersionUtils.getAppVersionName(MyApplication.getInstance()));
        data.put("opinionContent",str);
//                    data.put("deviceNumber", SystemUtil.getDeviceBrand()+";"+ SystemUtil.getSystemModel()+"("+uniqueId+")");//????????????
//                    data.put("movieId",feedBackActivity.movieId);
        data.put("opinionType",problemModel.getType());
        if(StringMyUtil.isEmptyString(realPath)&&havePhoto){
            showtoast("???????????????,?????????");
            return;
        }
        data.put("pictures",null==realPath?"":realPath);
        HttpApiUtils.request(getActivity(), null, RequestUtils.FEED_BACK, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                realPath="";
                havePhoto=false;
                for (int i = 0; i < meetModelList.size(); i++) {
                    meetModelList.get(i).setStatus(0);
                }
                meetProblemAdapter.notifyDataSetChanged();
                addIv.setImageResource(R.drawable.add_image);
                problemEtv.setText("");
                lengthTv.setText(0+"/100");
                showtoast("????????????");
                UpdateMineFeedback event = new UpdateMineFeedback();
                event.setUpdate(true);
                EventBus.getDefault().postSticky(event);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initAddImagePop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.take_camera_pop_layout,null);
        takeCameratv=view.findViewById(R.id.forbidden_tv);
        choosePhototv=view.findViewById(R.id.set_manager_tv);
        cancelTv=view.findViewById(R.id.forbidden_cancel_tv);
        takeCameratv.setOnClickListener(this);
        choosePhototv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        addImagePop=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        addImagePop.setAnimationStyle(R.style.down_to_up150);
        addImagePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addIv.setClickable(true);
                ProgressDialogUtil.darkenBackground(getActivity(),1f);
            }
        });
    }
    /*
   ???????????????????????????(meetModelList??????status???1,?????????)
     */
    private boolean isSelectorItem(){
        for (int i = 0; i < meetModelList.size(); i++) {
            int status = meetModelList.get(i).getStatus();
            if(status==1){
                return  true;
            }
        }
        return  false;
    }

    /**
     * ??????????????????
     */
    private void checkPermissions() {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(PERMISSIONS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        Log.e(TAG, "init: ??????????????????");
                        goPhotoAlbum();
                    } else {
                        Log.e(TAG, "init: ??????????????????");
                        showtoast("??????????????????????????????????????????app");
                    }
                });
    }


    /*
    ????????????
     */
    private void checkCameraPermission() {
        if(EasyPermissions.hasPermissions(getContext(), CAMARA_PERMISSIONS)){
            goCamera();
        }else {
            EasyPermissions.requestPermissions(this,"????????????????????????????????????",REQUEST_CAMERA_CODE, CAMARA_PERMISSIONS);
        }
    }

    private void checkPhotoPermisson(){
        if(EasyPermissions.hasPermissions(getContext(),PHOTO_PERMISSIONS)){
            goPhotoAlbum();
        }else {
            EasyPermissions.requestPermissions(this,"????????????????????????????????????",REQUEST_PHOTO_CODE,PHOTO_PERMISSIONS);
        }
    }
    //??????????????????
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(getContext(), getActivity().getApplication().getPackageName()+".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //????????????startActivityForResult ????????????getActivity().startActivityForResult, activity onActivityResult????????????super.onActivityResult(requestCode, resultCode, data)???
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
//        getActivity().startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    /*
????????????
 */
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PHOTO_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String photoPath;
        String s;
        super.onActivityResult(requestCode, resultCode, data);
        //????????????
        if(requestCode==REQUEST_CAMERA_CODE){
            if(resultCode==RESULT_OK){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath= String.valueOf(cameraSavePath);
//                    showBitMap(String.valueOf(cameraSavePath));
//                    showBitMap(photoPath);
                    s = BitmapUtils.compressReSave(photoPath, getContext(), 400);//????????????
                }else {
                    photoPath = uri.getEncodedPath();
                    s = BitmapUtils.compressReSave(photoPath, getContext(), 400);//????????????
                }
                havePhoto=true;
                uploadImg(s);
            }else {
                showtoast("????????????,?????????");
            }

        }
        //????????????
        else if(requestCode==REQUEST_PHOTO_CODE){
            if(resultCode==RESULT_OK){
                //    private String[] PHOTO_PERMISSIONS={"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
                String realPathFromUri = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(), data.getData());
                photoPath = realPathFromUri;
//                showBitMap(realPathFromUri);
                s = BitmapUtils.compressReSave(realPathFromUri, getContext(), 400);//????????????
                if (!StringMyUtil.isEmptyString(photoPath)) {
                    havePhoto=true;
                    uploadImg(s);
                } else {
                    //????????????
                    showtoast("??????????????????,?????????");
                }

            }else {
                showtoast("??????????????????,?????????");
            }
        }
    }
            /*
            ????????????
             */
    private void uploadImg(String url) {
/*        HttpApiUtils.upload(getActivity(), RequestUtil.UPLOAD_IMAGE, url, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                 realPath = jsonObject.getString("realUrl");
                GlideLoadViewUtil.FLoadNormalView(WantToFeedbackFragment.this, Utils.checkImageUrl(realPath),addIv);
            }
            @Override
            public void onFailed(String msg) {
             havePhoto=false;
            }
        });*/
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
                            realPath = data.getString("url");
                            GlideLoadViewUtil.FLoadNormalView(WantToFeedbackFragment.this,realPath,addIv);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                    }

                    @Override
                    protected void onRequestStart() {
                        super.onRequestStart();
                        if(getActivity() instanceof BaseActivity){
                            BaseActivity baseActivity = (BaseActivity) getActivity();
                            baseActivity.showLoading();
                        }
                    }

                    @Override
                    protected void onRequestEnd() {
                        super.onRequestEnd();
                        if(getActivity() instanceof BaseActivity){
                            BaseActivity baseActivity = (BaseActivity) getActivity();
                            baseActivity.closeLoading();
                        }
                    }
                });
    }


        /*
        ?????????????????????????????????,??????????????????????????????
         */
    private void showBitMap(String path) {
        //??????????????????????????????????????????????????????
        Bitmap camorabitmap = BitmapFactory.decodeFile(path);
        if(null!=camorabitmap){
            int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(),camorabitmap.getHeight(), addIv.getWidth(), addIv.getHeight());
            Bitmap bitMap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale,camorabitmap.getHeight() / scale);
            //??????Bitmap????????????????????????????????????????????????????????????out of memory??????
            camorabitmap.recycle();
            addIv.setImageBitmap(bitMap);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode==REQUEST_CAMERA_CODE){
            goCamera();
        }else if(requestCode==REQUEST_PHOTO_CODE){
            goPhotoAlbum();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showtoast("???????????????,??????????????????????????????");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = problemEtv.getText().toString().length();
        if(length<10||length>100){
            if(length!=0){
                String str="<font color=\"#FF0000\">"+length+"</font>"+"/100";
                lengthTv.setText(Html.fromHtml(str));
            }
        }else {
            lengthTv.setText(length+"/100");
        }
    }
}
