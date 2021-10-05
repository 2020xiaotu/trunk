package com.example.new_application.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;

public class UploadFileEntity implements Parcelable {
    boolean isSuccess = false;//是否上传成功
    LocalMedia localMedia;//相机/相机返回后才赋值的本地图片实体类
    boolean isFail =false;//是否上传失败
    String uploadUrl;//后天返回的网络url

    public UploadFileEntity() {

    }
    protected UploadFileEntity(Parcel in){
        isSuccess = in.readBoolean();
        isFail = in.readBoolean();
        uploadUrl = in.readString();
        localMedia = in.readParcelable(LocalMedia.class.getClassLoader());
    }

    public static final Creator<UploadFileEntity> CREATOR = new Creator<UploadFileEntity>() {
        @Override
        public UploadFileEntity createFromParcel(Parcel in) {
            return new UploadFileEntity(in);
        }

        @Override
        public UploadFileEntity[] newArray(int size) {
            return new UploadFileEntity[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(isSuccess);
        dest.writeBoolean(isFail);
        dest.writeString(uploadUrl);
        dest.writeParcelable(localMedia,0);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public LocalMedia getLocalMedia() {
        return localMedia;
    }

    public void setLocalMedia(LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    public boolean isFail() {
        return isFail;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }
}
