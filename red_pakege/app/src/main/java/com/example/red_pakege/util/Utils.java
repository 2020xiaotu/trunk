package com.example.red_pakege.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.red_pakege.MyApplication;

import java.math.BigDecimal;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Utils {



    public static View getContentView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0.00" + "M";//清除缓存时,清理完会有0.3Byte左右的缓存,这里直接显示0.00M
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    public static void darkenBackground(Activity activity, Float bgcolor) {
        if(activity==null||activity.isFinishing()){
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        if(bgcolor==1f){
            //恢复屏幕亮度时需要移除flag,否则在切换activity时会有短暂黑屏
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);

    }
/*    public static void copyStr(String content){
        //TODO 使用application进行注册, 避免内存泄漏
        ClipboardManager clipboardManagerUrl= (ClipboardManager) MyApplication.getInstance().getSystemService(CLIPBOARD_SERVICE);//实例化clipboardManager对象
        ClipData mClipDataUrl=  ClipData.newPlainText("inviteCodeUrl", content);//复制文本数据到粘贴板  newPlainText
        clipboardManagerUrl.setPrimaryClip(mClipDataUrl);
}*/
}
