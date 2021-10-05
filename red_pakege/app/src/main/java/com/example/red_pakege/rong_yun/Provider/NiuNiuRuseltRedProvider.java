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
import androidx.fragment.app.Fragment;

import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.rong_yun.chat_interface.OnMessageClickLintener;
import com.example.red_pakege.rong_yun.even_bus_model.CurrentModel;
import com.example.red_pakege.rong_yun.message.NiuNiuResultMessage;
import com.example.red_pakege.util.GlideLoadViewUtil;

import io.rong.eventbus.EventBus;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ProviderTag(
        messageContent = NiuNiuResultMessage.class,
        showReadState = true,//
        centerInHorizontal = true

)
public class NiuNiuRuseltRedProvider extends IContainerItemProvider.MessageProvider<NiuNiuResultMessage> {

    OnMessageClickLintener onMessageClickLintener;
    Fragment fragment;
//    OnCurrentTimeLintener onCurrentTimeLintener  = new
/*
    public NiuNiuRuseltRedProvider(OnMessageClickLintener onMessageClickLintener, Fragment fragment) {
        this.onMessageClickLintener = onMessageClickLintener;
        this.fragment = fragment;
    }*/

    public NiuNiuRuseltRedProvider(OnMessageClickLintener onMessageClickLintener) {
        this.onMessageClickLintener = onMessageClickLintener;
    }

    public NiuNiuRuseltRedProvider() {

    }

/*    public NiuNiuRuseltRedProvider(OnMessageClickLintener onMessageClickLintener) {
        this.onMessageClickLintener = onMessageClickLintener;
    }*/

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.niuniu_result_item_layout,null);
        ViewHolder holder = new ViewHolder();
        holder.bankWinCountTv=view.findViewById(R.id.bank_win_amount_tv);
        holder.playerWinCountTv=view.findViewById(R.id.player_win_amount_tv);
        holder.userNameTv=view.findViewById(R.id.niuniu_result_username_tv);
        holder.titleIv=view.findViewById(R.id.niuniu_result_title_iv);
        holder.bankNiuJiIv=view.findViewById(R.id.niuniu_result_bank_niuji_iv);
        holder.bottomLinear=view.findViewById(R.id.niuniu_result_bottom_linear);
        holder.bankAllKillIv=view.findViewById(R.id.niuniu_all_kill_iv);
        view.setTag(holder);
    /*    view.setTag(1,holder);
        view.setTag(2,viewGroup);*/

        return view;
    }

    @Override
    public void bindView(View view, int i, NiuNiuResultMessage niuNiuResultMessage, UIMessage uiMessage) {
/*     ViewHolder holder = (ViewHolder) view.getTag(1);
     ViewGroup viewGroup = (ViewGroup) view.getTag(2);*/
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.bankWinCountTv.setText(niuNiuResultMessage.getBankerWinCount()+"");
        holder.playerWinCountTv.setText(niuNiuResultMessage.getPlayerWinCount()+"");
        holder.userNameTv.setText(niuNiuResultMessage.getUserName());
//        GlideLoadViewUtil.FLoadCornersView(fragment,niuNiuResultMessage.getAvatar(),12,holder.titleIv);
        GlideLoadViewUtil.LoadCornersView(MyApplication.getInstance(),niuNiuResultMessage.getAvatar(),12,holder.titleIv);
        Integer bankerNiuJi = niuNiuResultMessage.getBankerNiuJi();
        if(bankerNiuJi==1){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_1);
        } else if(bankerNiuJi==2){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_2);
        }else if(bankerNiuJi==3){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_3);
        }else if(bankerNiuJi==4){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_4);
        }else if(bankerNiuJi==5){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_5);
        }else if(bankerNiuJi==6){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_6);
        }else if(bankerNiuJi==7){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_7);
        }else if(bankerNiuJi==8){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_8);
        }else if(bankerNiuJi==9){
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_9);
        }else {
            holder.bankNiuJiIv.setImageResource(R.drawable.ic_cow_10);

        }
        Integer all = niuNiuResultMessage.getAll();
        ImageView bankAllKillIv = holder.bankAllKillIv;
        if(all==-1){
            bankAllKillIv.setImageResource(R.drawable.ic_bank_fail);
            bankAllKillIv.setVisibility(View.VISIBLE);
        }else if(all==0){
            bankAllKillIv.setVisibility(View.GONE);
        }else {
            bankAllKillIv.setImageResource(R.drawable.ic_bank_win);
            bankAllKillIv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public Spannable getContentSummary(NiuNiuResultMessage niuNiuResultMessage) {
        return new SpannableString(niuNiuResultMessage.getUserName());
    }

    @Override
    public void onItemClick(View view, int i, NiuNiuResultMessage niuNiuResultMessage, UIMessage uiMessage) {
        if(onMessageClickLintener!=null){
            onMessageClickLintener.onMessageClickLintener( view, i,niuNiuResultMessage,uiMessage);
        }
    }

    private static class ViewHolder {
        TextView bankWinCountTv, playerWinCountTv, userNameTv;
        ImageView titleIv,bankNiuJiIv,bankAllKillIv;
        ConstraintLayout bottomLinear;
    }

}
