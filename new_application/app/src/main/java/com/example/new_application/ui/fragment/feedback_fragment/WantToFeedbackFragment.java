package com.example.new_application.ui.fragment.feedback_fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.net.utils.SystemUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.BuildConfig;
import com.example.new_application.R;
import com.example.new_application.adapter.MeetProblemAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.MeetProblemEntity;
import com.example.new_application.bean.UpdateMineFeedback;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiImpl;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.pop.TakeCameraPop;
import com.example.new_application.utils.BitmapUtils;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.GetPhotoFromPhotoAlbum;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.ImageThumbnail;
import com.example.new_application.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/*
我要反馈
 */
public class WantToFeedbackFragment extends BaseFragment implements View.OnClickListener , TextWatcher {
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
    private ArrayList<MeetProblemEntity> meetModelList=new ArrayList<>();

    private TakeCameraPop addImagePop;
    public static String TAG="WantToFeedbackFragment";
    private String[] CAMARA_PERMISSIONS ={  Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
    private String problemId;
    //拍照返回
    private Uri uri;
    //图片上传返回的url
    private String realPath;
    private boolean waitUploadPhoto =false;
    MeetProblemEntity currentProblemModel;//当前选中的问题


    @Override
    protected void init(Bundle savedInstanceState) {
        initMeetRecycle();
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
        我遇到的问题列表
         */
    private void initMeetRecycle() {
        meetProblemAdapter=new MeetProblemAdapter(R.layout.mine_problem_recycle_item,meetModelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        meetRecycle.setLayoutManager(gridLayoutManager);
        meetRecycle.setAdapter(meetProblemAdapter);
        meetProblemAdapter.addChildClickViewIds(R.id.meet_problem_tv);
        meetProblemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MeetProblemEntity dataBean = meetModelList.get(position);
                currentProblemModel = meetModelList.get(position);
                if(view.isSelected()){
                    dataBean.setStatus(0);
                   meetProblemAdapter. notifyDataSetChanged();
                }else {
                    initItemStatus(position);
                }
            }
        });
        /*
        所有问题列表
         */
        getAllProblem();
    }
    private void initItemStatus(int position) {
        for (int i = 0; i < meetModelList.size(); i++) {
            MeetProblemEntity problemModel = meetModelList.get(i);
            if(position==i){
                problemModel.setStatus(1);
            }else {
                problemModel.setStatus(0);
            }
        }
        meetProblemAdapter. notifyDataSetChanged();
    }
    private void getAllProblem() {
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.MEET_PROBLEM_LIST, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                List<MeetProblemEntity> meetProblemEntities = JSONArray.parseArray(result, MeetProblemEntity.class);
                meetModelList.addAll(meetProblemEntities);
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
                Utils.darkenBackground(getActivity(),0.7f);
                break;
            case R.id.commit_button:
                String str = problemEtv.getText().toString();
                if(StringMyUtil.isEmptyString(str)){
                    showToast("请描述您遇到的问题");
                }else if(str.length()<10||str.length()>100){
                    showToast("问题描述仅限10-100个字");
                }else if(!isSelectorItem()){
                    showToast("请选择问题分类,方便我们更快为您解决问题");
                }else {
                        //提交问题
                    requestFeedback(str);
                }
        }
    }

    private void requestFeedback(String str) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appVersion", BuildConfig.VERSION_NAME);
        data.put("opinionContent",str);
        data.put("deviceIdentifier", SystemUtil.getSystemModel());
        data.put("opinionType",currentProblemModel.getType());
        if(StringMyUtil.isEmptyString(realPath)&&waitUploadPhoto){
            showToast("图片上传中,请稍后");
            return;
        }
        data.put("pictures",null==realPath?"":realPath);
        HttpApiUtils.wwwRequest(getActivity(), null, RequestUtils.COMMIT_FEED_BACK, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                realPath="";
                waitUploadPhoto=false;
                for (int i = 0; i < meetModelList.size(); i++) {
                    meetModelList.get(i).setStatus(0);
                }
                meetProblemAdapter.notifyDataSetChanged();
                addIv.setImageResource(R.drawable.upload_pic_icon);
                problemEtv.setText("");
                lengthTv.setText(0+"/100");
                showToast("反馈成功");
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
        addImagePop=new TakeCameraPop(getContext());
        addImagePop.setmOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDissmiss() {
                addIv.setClickable(true);
            }
        });
        addImagePop.setOnPopClickListener(new BasePopupWindow.OnPopClickListener() {
            @Override
            public void onPopClick(View view) {
                switch (view.getId()){
                    case R.id.forbidden_tv:
                        checkCameraPermission();
                        addImagePop.dismiss();
                        break;
                    case R.id.set_manager_tv:
                        checkPermissions();
                        addImagePop.dismiss();
                        break;
                    case R.id.forbidden_cancel_tv:
                        addImagePop.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /*
   是否有选择问题分类(meetModelList中有status为1,即选中)
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
     * 检查相册权限
     */
    private void checkPermissions() {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(PERMISSIONS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        Log.e(TAG, "init: 权限申请成功");
                       Utils.goPhotoAlbum(WantToFeedbackFragment.this);
                    } else {
                        Log.e(TAG, "init: 权限申请失败");
                        showToast("拒绝必须权限后将无法正常使用app,请手动开启权限");
                    }
                });
    }


    /*
    检查权限
     */
    private void checkCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(CAMARA_PERMISSIONS)
                .subscribe(permission -> {
                    if(permission.granted){
                        uri = Utils.goCamera(WantToFeedbackFragment.this,cameraSavePath);
                    }else {
                        showToast("拒绝必须权限后将无法正常使用app,请手动开启权限");
                    }
                });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String photoPath;
        String s;
        super.onActivityResult(requestCode, resultCode, data);
        //相机返回
        if(requestCode== CommonStr. REQUEST_CAMERA_CODE){
            if(resultCode==RESULT_OK){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath= String.valueOf(cameraSavePath);
//                    showBitMap(String.valueOf(cameraSavePath));
//                    showBitMap(photoPath);
                    s = BitmapUtils.compressReSave(photoPath, getContext(), 400);//图片压缩
                }else {
                    photoPath = uri.getEncodedPath();
                    s = BitmapUtils.compressReSave(photoPath, getContext(), 400);//图片压缩
                }
                waitUploadPhoto =true;
                uploadImg(s);
            }else {
                showToast("未知错误,请重试");
            }

        }
        //相册返回
        else if(requestCode== CommonStr. REQUEST_PHOTO_CODE){
            if(resultCode==RESULT_OK){
                //    private String[] PHOTO_PERMISSIONS={"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
                String realPathFromUri = GetPhotoFromPhotoAlbum.getRealPathFromUri(getContext(), data.getData());
                photoPath = realPathFromUri;
//                showBitMap(realPathFromUri);
                s = BitmapUtils.compressReSave(realPathFromUri, getContext(), 400);//图片压缩
                if (!StringMyUtil.isEmptyString(photoPath)) {
                    waitUploadPhoto =true;
                    uploadImg(s);
                } else {
                    //压缩失败
                    showToast("系统出现错误,请重试");
                }

            }else {
                showToast("图片选取出错,请重试");
            }
        }
    }

    /**
     * 上传图片
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
        选择图片或者拍照返回后,在界面显示图片缩略图
         */
    private void showBitMap(String path) {
        //将拍照的图片取出并缩小后显示在界面上
        Bitmap camorabitmap = BitmapFactory.decodeFile(path);
        if(null!=camorabitmap){
            int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(),camorabitmap.getHeight(), addIv.getWidth(), addIv.getHeight());
            Bitmap bitMap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale,camorabitmap.getHeight() / scale);
            //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
            camorabitmap.recycle();
            addIv.setImageBitmap(bitMap);
        }
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
