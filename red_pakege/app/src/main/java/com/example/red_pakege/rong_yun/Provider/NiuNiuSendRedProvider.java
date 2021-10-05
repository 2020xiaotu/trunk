package com.example.red_pakege.rong_yun.Provider;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.rong_yun.chat_interface.OnMessageClickLintener;
import com.example.red_pakege.rong_yun.message.NiuNiuRedMessage;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

@ProviderTag(
        messageContent = NiuNiuRedMessage.class,
        showReadState = true
)
public class NiuNiuSendRedProvider extends IContainerItemProvider.MessageProvider<NiuNiuRedMessage> {
//    Context context;
    OnMessageClickLintener onMessageClickLintener;
/*    public NiuNiuSendRedProvider(Context context, OnMessageClickLintener onMessageClickLintener) {
        this.context = context;
        this.onMessageClickLintener = onMessageClickLintener;
    }*/

    public NiuNiuSendRedProvider(OnMessageClickLintener onMessageClickLintener) {
        this.onMessageClickLintener = onMessageClickLintener;
    }

    public NiuNiuSendRedProvider() {
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.niuniu_redpackage_message_item, null);
        ViewHolder holder = new ViewHolder();
        //牛牛相关
        holder.userNameTv = (TextView) view.findViewById(R.id.niuniu_send_user_name_tv);
        holder.amountTv = (TextView) view.findViewById(R.id.niuniu_send_amount_tv);
        holder.typeTv = (TextView) view.findViewById(R.id.niuniu_send_type_tv);
        holder.bgConstraaintLayout=view.findViewById(R.id.red_pakege_bg_constraintLayout);
        holder.tipTv=view.findViewById(R.id.niuniu_red_info_tv);

        //扫雷相关
        /*
        TextView saoleiAmountTv,saoleiTypeTv,saoleiTipTv;
        ConstraintLayout saoleiSendConstraLayout;
         */
        holder.saoleiAmountTv=view.findViewById(R.id.saolei_send_amount_tv);
        holder.saoleiTypeTv=view.findViewById(R.id.saolei_send_type_tv);
        holder.saoleiTipTv=view.findViewById(R.id.saolei_red_info_tv);
        holder.saoleiSendConstraLayout=view.findViewById(R.id.saolei_red_pakege_bg_constraintLayout);

        /*
               TextView fuLisendRedTipTv, fuLireceiveRedIv,fuLitypeTv;
        ConstraintLayout fuLiSendConstraLayout;
         */
        //福利相关
        holder.fuLisendRedTipTv=view.findViewById(R.id.fuli_send_red_info_tv);
        holder.fuLireceiveRedIv=view.findViewById(R.id.fuli_receive_red_info_tv);
        holder.fuLitypeTv=view.findViewById(R.id.fuli_send_type_tv);
        holder.fuLiSendConstraLayout=view.findViewById(R.id.fuli_red_pakege_bg_constraintLayout);
        /*
                TextView jinQiangiAmountTv,jinJQiangTypeTv,jinQiangTipTv;
        ConstraintLayout jinQiangSendConstraLayout;
        ImageView jinQiangRightIv;
         */
        //禁枪相关
        holder.jinQiangiAmountTv=view.findViewById(R.id.jinqiang_send_amount_tv);
        holder.jinJQiangTypeTv=view.findViewById(R.id.jinqiang_send_type_tv);
        holder.jinQiangTipTv=view.findViewById(R.id.jinqiang_red_info_tv);
        holder.jinQiangSendConstraLayout=view.findViewById(R.id.jinqiang_red_pakege_bg_constraintLayout);
        holder.jinQiangRightIv=view.findViewById(R.id.jinqiang_right_iv);
        view.setTag(holder);
        return view;
    }
        //TODO 后台所有发红包的objectName都是一样的,所以要在newView中,将所有红包类型的视图都加载进来,然后在bindView中根据hnType来显示隐藏,并做逻辑处理
    @Override
    public void bindView(View view, int i, NiuNiuRedMessage redPackageMessage, UIMessage message) {
        int messageId = message.getMessage().getMessageId();
        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        Integer hbType = redPackageMessage.getHbType();
        boolean localGain = redPackageMessage.isGain();
        boolean localOver = redPackageMessage.isOver();
        boolean localPstDue = redPackageMessage.isPastDue();
        boolean localQB = redPackageMessage.isQB();
        //使用融云自带的用户名 这里将自定义的用户名textView隐藏
        UserInfo userInfo = message.getUserInfo();
        String extra = message.getExtra();
        JSONObject jsonObject = JSONObject.parseObject(extra);
        if(null==jsonObject){
           jsonObject=new JSONObject();
        }
        Boolean isGain = jsonObject.getBoolean("isGain");//已领取
        Boolean isOver = jsonObject.getBoolean("isOver");//已抢完
        Boolean isPastDue = jsonObject.getBoolean("isPastDue");//已过期
        Boolean isQB = jsonObject.getBoolean("isQB");//是否能抢包
        if(null==isOver){
            isOver=false;
        }
        if(null==isGain){
            isGain=false;
        }
        if(null==isQB){
            isQB=true;
        }
        if(null==isPastDue){
            isPastDue=false;
        }

        if(userInfo!=null){
            holder.userNameTv.setText(userInfo.getName());
        }
        holder.userNameTv.setVisibility(View.GONE);
        if(hbType==3){//牛牛红包
            holder.saoleiSendConstraLayout.setVisibility(View.GONE);
            holder.fuLiSendConstraLayout.setVisibility(View.GONE);
            holder.bgConstraaintLayout.setVisibility(View.VISIBLE);
            holder.jinQiangSendConstraLayout.setVisibility(View.GONE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
                //已抢过红包 或者 红包已过期都设为淡色图片 (红包过期时点击弹出pop,已抢过红包直接跳转红包详情)
                if(!isQB||!localQB){
                    holder.bgConstraaintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_send_sel));
                }else {
                    holder.bgConstraaintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_send_nor));
                }
            } else {
                if(!isQB||!localQB){
                    holder.bgConstraaintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_receive_sel));
                }else {
                    holder.bgConstraaintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_receive_red_nor));
                }
            }
            //AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。



            holder.typeTv.setText(redPackageMessage.getHbName());
            holder.amountTv.setText(redPackageMessage.getHbMsg());
            setTipTv(isGain, isOver, isPastDue, holder.tipTv,localGain,localOver,localPstDue);

        }else if(hbType==2){//扫雷红包
            holder.bgConstraaintLayout.setVisibility(View.GONE);
            holder.fuLiSendConstraLayout.setVisibility(View.GONE);
            holder.saoleiSendConstraLayout.setVisibility(View.VISIBLE);
            holder.jinQiangSendConstraLayout.setVisibility(View.GONE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
                //已抢过红包 或者 红包已过期都设为淡色图片 (红包过期时点击弹出pop,已抢过红包直接跳转红包详情)
                if(!isQB||!localQB){
                    holder.saoleiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_send_saoleisel));
                }else {
                    holder.saoleiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_send_saolei_nor));
                }
            } else {
                if(!isQB||!localQB){
                    holder.saoleiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_receive_saolei_sel));
                }else {
                    holder.saoleiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_receive_saolei_nor));
                }
            }
            holder.saoleiTypeTv.setText(redPackageMessage.getHbName());
            holder.saoleiAmountTv.setText(redPackageMessage.getHbMsg());
            setTipTv(isGain, isOver, isPastDue, holder.saoleiTipTv,localGain,localOver,localPstDue);
        }else if(hbType==4){//福利红包
            holder.bgConstraaintLayout.setVisibility(View.GONE);
            holder.saoleiSendConstraLayout.setVisibility(View.GONE);
            holder.fuLiSendConstraLayout.setVisibility(View.VISIBLE);
            holder.jinQiangSendConstraLayout.setVisibility(View.GONE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
                holder.fuLisendRedTipTv.setVisibility(View.VISIBLE);
                holder.fuLireceiveRedIv.setVisibility(View.INVISIBLE);
                //已抢过红包 或者 红包已过期都设为淡色图片 (红包过期时点击弹出pop,已抢过红包直接跳转红包详情)
                if(!isQB||!localQB){
                    holder.fuLiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_send_sel));
                }else {
                    holder.fuLiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_send_nor));
                }
                setTipTv(isGain, isOver, isPastDue, holder.fuLisendRedTipTv,localGain,localOver,localPstDue);
            } else {
                holder.fuLisendRedTipTv.setVisibility(View.INVISIBLE);
                holder.fuLireceiveRedIv.setVisibility(View.VISIBLE);
                if(!isQB||!localQB){
                    holder.fuLiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_receive_sel));
                }else {
                    holder.fuLiSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_receive_nor));
                }
                setTipTv(isGain, isOver, isPastDue, holder.fuLireceiveRedIv,localGain,localOver,localPstDue);
            }

            holder.fuLitypeTv.setText(redPackageMessage.getHbName());
        }else if(hbType==1){//禁枪红包
            holder.bgConstraaintLayout.setVisibility(View.GONE);
            holder.fuLiSendConstraLayout.setVisibility(View.GONE);
            holder.saoleiSendConstraLayout.setVisibility(View.GONE);
            holder.jinQiangSendConstraLayout.setVisibility(View.VISIBLE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
                //已抢过红包 或者 红包已过期都设为淡色图片 (红包过期时点击弹出pop,已抢过红包直接跳转红包详情)
                if(!isQB||!localQB){
                    holder.jinQiangSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_send_saoleisel));
                }else {
                    holder.jinQiangSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_send_saolei_nor));
                }
            } else {
                if(!isQB||!localQB){
                    holder.jinQiangSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_receive_saolei_sel));
                }else {
                    holder.jinQiangSendConstraLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_receive_saolei_nor));
                }
            }
            holder.jinJQiangTypeTv.setText(redPackageMessage.getHbName());
            holder.jinQiangiAmountTv.setText(redPackageMessage.getHbMsg());
            setTipTv(isGain, isOver, isPastDue, holder.jinQiangTipTv,localGain,localOver,localPstDue);
        }
    }

    public void setTipTv(Boolean isGain , Boolean isOver, Boolean isPastDue, TextView tipTv,Boolean localGain,Boolean localOver,Boolean localPastDue) {
        if(null==isGain){
            isGain=false;
        }
        if (isGain||localGain) {//isGain
            tipTv.setText("红包已领取");
        } else if (isOver||localGain) {
            tipTv.setText("红包已领完");
        } else if (isPastDue||localGain) {
            tipTv.setText("红包已过期");
        } else {
            tipTv.setText("查看红包");
        }
    }

    @Override
    public Spannable getContentSummary(NiuNiuRedMessage niuNiuRedMessage) {
        return new SpannableString(niuNiuRedMessage.getHbMsg());
    }

    @Override
    public void onItemClick(View view, int i, NiuNiuRedMessage redPackageMessage, UIMessage uiMessage) {
        if(onMessageClickLintener!=null){
            onMessageClickLintener.onMessageClickLintener(view, i, redPackageMessage, uiMessage);
        }
    }
/*    @Override
    public void onItemLongClick(View view, int i, NiuNiuRedMessage redPackageMessage, UIMessage uiMessage) {
        //实现长按删除等功能，直接复制融云其他provider的实现
        String[] items1;//复制，删除
        items1 = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};

        OptionsPopupDialog.newInstance(view.getContext(), items1).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
            public void onOptionsItemClicked(int which) {
                if (which == 0) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(content.getContent());//这里是自定义消息的消息属性
                } else if (which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, (RongIMClient.ResultCallback) null);
                }
            }
        }).show();

    }*/

    private static class ViewHolder {
        //牛牛红包相关控件
        TextView userNameTv, amountTv, typeTv,tipTv;
        ConstraintLayout bgConstraaintLayout;
        //扫雷红包相关控件
        TextView saoleiAmountTv,saoleiTypeTv,saoleiTipTv;
        ConstraintLayout saoleiSendConstraLayout;
        //福利红包相关控件
        TextView fuLisendRedTipTv, fuLireceiveRedIv,fuLitypeTv;
        ConstraintLayout fuLiSendConstraLayout;
        //禁枪红包相关控件
        TextView jinQiangiAmountTv,jinJQiangTypeTv,jinQiangTipTv;
        ConstraintLayout jinQiangSendConstraLayout;
        ImageView jinQiangRightIv;
    }


}
