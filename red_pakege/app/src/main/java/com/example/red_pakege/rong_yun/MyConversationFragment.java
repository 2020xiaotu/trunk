package com.example.red_pakege.rong_yun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.rong_yun.Provider.NiuNiuRuseltRedProvider;
import com.example.red_pakege.rong_yun.Provider.NiuNiuSendRedProvider;
import com.example.red_pakege.rong_yun.activity.red_pakege_detail_activity.FuLiRedPakegeDetailActivity;
import com.example.red_pakege.rong_yun.activity.red_pakege_detail_activity.NiuNiuRedPakegeDetailActivity;
import com.example.red_pakege.rong_yun.activity.red_pakege_detail_activity.SaoLeiRedPakegeDetailActivity;
import com.example.red_pakege.rong_yun.adapter.MyMessageListAdapter;
import com.example.red_pakege.rong_yun.chat_interface.OnMessageClickLintener;
import com.example.red_pakege.rong_yun.message.NiuNiuRedMessage;
import com.example.red_pakege.rong_yun.message.NiuNiuResultMessage;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.CheckJsonUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import io.rong.imkit.InputBar;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import okhttp3.Headers;

/*
???????????????Fragment
 */
public class MyConversationFragment extends ConversationFragment implements OnMessageClickLintener, View.OnClickListener {
    //        private OnShowAnnounceListener onShowAnnounceListener;
    private static String TAG ="MyConversationFragment";
    private OnExtensionChangeListener onExtensionChangeListener; //???????????? ??????????????????
    private RongExtension rongExtension;
    private ListView listView;
    private Conversation.ConversationType mConversationType;
    private String mTargetId;
    private String title;
    //?????????pop
    private PopupWindow getPakegePop;
    private ImageView getPakegePopExitIv,getPakegePopTitleIv,getPakegePopOpenIv,pakegeButtomIv;
    private TextView getPakegePopNameTv,getPakegePopTypeTv,getPakegeTipTv,showPakegeDetailTv;

    //?????????????????????????????????id
    private String orderCode;
    private Integer typeId;
    private int messageId;
    //???????????????item ?????????
    private ConstraintLayout constraintLayout;
    //?????????????????????????????????(???????????? ?????? ??????????????????)
    private TextView redTipTv;
    //??????????????????????????????
    private boolean isSend;
    private MessageContent messageContent;
    private enum RedType{
        NIUNIU , SAOLEI,FULI;
    }
    RedType redType;
    private enum PakegeStatus{
        isGain,isQB,isOver,isPastDue
    }
    PakegeStatus pakegeStatus;
    private  MyMessageListAdapter myMessageListAdapter;
    private     NiuNiuSendRedProvider niuNiuSendRedProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getIntentData(getActivity().getIntent());//??????intent????????????
        RelativeLayout rootView = addRightLinear(view);//????????????LinearLayout
        initRongExtension(view); //??????RongExtension???????????????
        registerMessageAndProvider();//??????????????????????????????
        return rootView;
    }

    private void initGetPakegePop() {
        View view = LayoutInflater.from(getMyContext()==null?MyApplication.getInstance():getMyContext()).inflate(R.layout.get_redpakege_pop,null);
        getPakegePopExitIv=view.findViewById(R.id.get_redpakege_pop_exit_iv);//?????????????????????
        getPakegePopTitleIv=view.findViewById(R.id.get_pakege_pop_title_iv);//????????????
        getPakegePopNameTv=view.findViewById(R.id.get_pakege_pop_name_tv);//?????????
        getPakegePopTypeTv=view.findViewById(R.id.get_pakege_pop_type_tv);//??????info,(xx??????????????????/????????????)
        getPakegePopOpenIv=view.findViewById(R.id.get_pakege_pop_open_iv);//??????????????????
        getPakegeTipTv=view.findViewById(R.id.get_pakege_pop_tip_tv);//????????????????????????tv
        pakegeButtomIv=view.findViewById(R.id.get_redpakege_buttom_iv);//????????????iv ?????????????????????
        showPakegeDetailTv=view.findViewById(R.id.show_pakege_detail_tv);//??????????????????tv ?????????????????????
        getPakegePopExitIv.setOnClickListener(this);
        getPakegePopOpenIv.setOnClickListener(this);
        showPakegeDetailTv.setOnClickListener(this);
        getPakegePop=new PopupWindow(view, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT,true);
        getPakegePop.setAnimationStyle(R.style.pop_scale_animation);
        getPakegePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(getActivity(),1f);
            }
        });
    }
    /*
  ??????????????????????????????(????????????????????????????????????????????????,????????????Application?????????)
   */
    private void registerMessageAndProvider() {
        //        niuNiUSendRedProvider=new NiuNiuSendRedProvider(getMyContext(),this);
        //????????????
        RongIM.registerMessageType(NiuNiuRedMessage.class);
        niuNiuSendRedProvider = new NiuNiuSendRedProvider(this);

        RongIM.registerMessageTemplate(niuNiuSendRedProvider);
        //??????????????????
        RongIM.registerMessageType(NiuNiuResultMessage.class);
//        RongIM.registerMessageTemplate(new NiuNiuRuseltRedProvider(this,this));
        RongIM.registerMessageTemplate(new NiuNiuRuseltRedProvider(this));
    }
    /*
    ?????????????????? ?????? ?????? ?????? linearLayout
     */
    @NotNull
    private RelativeLayout addRightLinear(View view) {
        RelativeLayout rootView = (RelativeLayout) view;
        View linearLayout = LayoutInflater.from(getMyContext()).inflate(R.layout.conversation_right_linear,null);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeParams.setMargins(0,24,24,0);
        rootView.addView(linearLayout,relativeParams);
        return rootView;
    }

    @Override
    public void onMessageClickLintener(View view, int i, MessageContent messageContent, UIMessage uiMessage) {
        UserInfo userInfo = uiMessage.getUserInfo();
        this.messageContent =messageContent;
        messageId=uiMessage.getMessage().getMessageId();
   /*     String name = userInfo.getName();
        String imageUrl = userInfo.getPortraitUri().toString();*/
        //??????????????????
        if(messageContent instanceof NiuNiuRedMessage){
            if(((NiuNiuRedMessage) messageContent).getHbType()==3){
                constraintLayout = view.findViewById(R.id.red_pakege_bg_constraintLayout);
                redTipTv = view.findViewById(R.id.niuniu_red_info_tv);
                isSend = uiMessage.getMessageDirection() == Message.MessageDirection.SEND;
                //TODO  ??????????????????
                clickNiuNiuRed((NiuNiuRedMessage) messageContent);
            }else if(((NiuNiuRedMessage) messageContent).getHbType()==2){
                //??????????????????
                constraintLayout = view.findViewById(R.id.saolei_red_pakege_bg_constraintLayout);
                redTipTv = view.findViewById(R.id.saolei_red_info_tv);
                isSend = uiMessage.getMessageDirection() == Message.MessageDirection.SEND;
                clickSaoLeiRed( messageContent,uiMessage);//???????????? ??????????????????
            }else if(((NiuNiuRedMessage) messageContent).getHbType()==4){
                //??????????????????
                constraintLayout = view.findViewById(R.id.fuli_red_pakege_bg_constraintLayout);
                isSend = uiMessage.getMessageDirection() == Message.MessageDirection.SEND;
                if(isSend){
                    redTipTv = view.findViewById(R.id.fuli_send_red_info_tv);
                }else {
                    redTipTv = view.findViewById(R.id.fuli_receive_red_info_tv);
                }
                clickFuLiRed( messageContent,uiMessage);//???????????? ??????????????????
            }

        }
        //??????????????????
        else if(messageContent instanceof  NiuNiuResultMessage){
            //TODO ??????????????????
            NiuNiuRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);

        }

    }

    /**
     * ??????????????????
     * @param messageContent  ?????????item message model
     */
    private void clickNiuNiuRed(NiuNiuRedMessage messageContent) {
        NiuNiuRedMessage niuNiuRedMessage= messageContent;
        boolean clicked = niuNiuRedMessage.isClicked();
        orderCode = niuNiuRedMessage.getOrderCode();
        typeId = niuNiuRedMessage.getHbType();
        redType= RedType.NIUNIU;
        if(getPakegePop==null){
            initGetPakegePop();
        }
        if(clicked){
            NiuNiuRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
        }else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("orderCode", niuNiuRedMessage.getOrderCode());
            data.put("typeId", niuNiuRedMessage.getHbType());
            HttpApiUtils.request(getMyContext(), RequestUtil.CAIBAO, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result, Headers headers) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    JSONObject messageInfo = jsonObject.getJSONObject("data");
                    Boolean isQB = messageInfo.getBoolean("isQB");
                    String info = messageInfo.getString("info");
                    String infoCopy = "???????????????";
                    if(info.equals("?????????????????????")){
                        infoCopy="???????????????";
                    }else if(info.equals("??????????????????")){
                        infoCopy="???????????????";
                    }
                    if(!isQB){
                        getPakegePopTypeTv.setVisibility(View.INVISIBLE);
                        getPakegePopOpenIv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.INVISIBLE);
                        showPakegeDetailTv.setVisibility(View.VISIBLE);
                        if (isSend) {//??????????????????
                            if(!niuNiuRedMessage.isClicked()){
                                constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_send_sel));
                                if(info.equals("?????????????????????")){
                                    niuNiuRedMessage.setOver(true);
//                                    setMessageStatus(0);
                                }if(info.equals("??????????????????")){
                                    niuNiuRedMessage.setClicked(true);
//                                    setMessageStatus(1);
                                }
                                niuNiuRedMessage.setTip(info);
                                redTipTv.setText(infoCopy);
                            }
                        }else {
                            constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.niuniu_receive_sel));
                            if(info.equals("?????????????????????")){
                                niuNiuRedMessage.setOver(true);
//                                setMessageStatus(0);
                            }if(info.equals("??????????????????")){
                                niuNiuRedMessage.setClicked(true);
//                                setMessageStatus(1);
                            }
                            redTipTv.setText(infoCopy);
                        }
                    }else {
                        getPakegePopTypeTv.setVisibility(View.VISIBLE);
                        getPakegePopOpenIv.setVisibility(View.VISIBLE);
                        showPakegeDetailTv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.VISIBLE);
                        getPakegePopTypeTv.setText(info);
                    }
                    getPakegeTipTv.setText(info);
                    //TODO ???????????????????????????
       /*         getPakegePopNameTv.setText(name);
                GlideLoadViewUtil.FLoadCornersView(MyConversationFragment.this,imageUrl,12,getPakegePopTitleIv);*/
                    if(!getPakegePop.isShowing()){

                        getPakegePop.showAtLocation(listView, Gravity.CENTER,0,0);
                        Utils.darkenBackground(getActivity(),0.7f);
                    }
                }

                @Override
                public void onFaild(String msg) {
                    System.out.println(msg);
                }
            });
        }
    }

    private void setMessageStatus(JSONObject messageInfo ) {
        Boolean isQB = messageInfo.getBoolean("isQB");//??????????????????
        Boolean isOver = messageInfo.getBoolean("isOver");//????????????
        Boolean isPastDue = messageInfo.getBoolean("isPastDue");//????????????
        Boolean isGain = messageInfo.getBoolean("isGain");//????????????
        HashMap<String, Object> data = new HashMap<>();
        data.put("isQB",isQB);
        data.put("isOver",isOver);
        data.put("isPastDue",isPastDue);
        data.put("isGain",isGain);
        RongIM.getInstance().setMessageExtra(messageId, JSONObject.toJSONString(data), new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                System.out.println(aBoolean);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                System.out.println(errorCode + " sdsdsd");

            }
        });
    }


    /**
     * ??????????????????
     * @param messageContent  ?????????item message model
     */
    private void clickSaoLeiRed(MessageContent messageContent,UIMessage uiMessage) {
        String extra = uiMessage.getMessage().getExtra();
        JSONObject jsonObject = JSONObject.parseObject(extra);
        if(null==jsonObject){
            jsonObject=new JSONObject();
        }
        Boolean isGain = jsonObject.getBoolean("isGain");
        if(null==isGain){
            isGain=false;
        }
        NiuNiuRedMessage niuNiuRedMessage= (NiuNiuRedMessage) messageContent;
        orderCode = niuNiuRedMessage.getOrderCode();
        typeId = niuNiuRedMessage.getHbType();
        redType= RedType.SAOLEI;
        if(getPakegePop==null){
            initGetPakegePop();
        }
        if(isGain){
            SaoLeiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
        }else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("orderCode", niuNiuRedMessage.getOrderCode());
            data.put("typeId", niuNiuRedMessage.getHbType());
            HttpApiUtils.request(getMyContext(), RequestUtil.CAIBAO, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result, Headers headers) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    JSONObject messageInfo = jsonObject.getJSONObject("data");
                    //??????????????????
                    setMessageStatus(messageInfo);

                    Boolean isQB = messageInfo.getBoolean("isQB");//??????????????????
                    Boolean isOver = messageInfo.getBoolean("isOver");//????????????
                    Boolean isPastDue = messageInfo.getBoolean("isPastDue");//????????????
                    Boolean isGain = messageInfo.getBoolean("isGain");//??????????????????
                    String avatar = messageInfo.getString("avatar");//??????
                    String userName = messageInfo.getString("userName");//?????????
                    String info = messageInfo.getString("info");//????????????
                    String info2 = messageInfo.getString("info2");//??????- ??????
                    //???????????????????????????(?????????????????????,??????????????????????????????????????????.???BindView????????????setMessageExtra()????????????????????????,??????????????????????????????,????????????????????????????????????)
                    setLocalMessageStatus(isQB, isOver, isPastDue, null==isGain?false:isGain, niuNiuRedMessage);

//                    RongIM.getInstance().setMessageExtra(messageId,);
                    if(!isQB){
                        getPakegePopTypeTv.setVisibility(View.INVISIBLE);
                        getPakegePopOpenIv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.INVISIBLE);
                        showPakegeDetailTv.setVisibility(View.VISIBLE);
                        getPakegeTipTv.setText(info);
                        if (isSend) {//??????????????????
                            constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_send_saoleisel));
                        }
                        else {
                            constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_receive_saolei_sel));
                        }
                    setTipTv(isGain, isOver, isPastDue,redTipTv);
                    }else {//????????????
                        getPakegePopTypeTv.setVisibility(View.VISIBLE);
                        getPakegePopOpenIv.setVisibility(View.VISIBLE);
                        showPakegeDetailTv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.VISIBLE);
                        getPakegePopTypeTv.setText(info);
                        getPakegeTipTv.setText("????????????,????????????");
                    }

                    //TODO ???????????????????????????
       /*         getPakegePopNameTv.setText(name);
                GlideLoadViewUtil.FLoadCornersView(MyConversationFragment.this,imageUrl,12,getPakegePopTitleIv);*/
                    if(!getPakegePop.isShowing()){
                        getPakegePop.showAtLocation(listView, Gravity.CENTER,0,0);
                        Utils.darkenBackground(getActivity(),0.7f);
                    }
                }

                @Override
                public void onFaild(String msg) {
                    System.out.println(msg);
                }
            });
        }
    }

    /**
     * ??????????????????
     * @param messageContent  ?????????item message model
     */
    private void clickFuLiRed(MessageContent messageContent,UIMessage uiMessage) {
        String extra = uiMessage.getMessage().getExtra();
        JSONObject jsonObject = JSONObject.parseObject(extra);
        if(null==jsonObject){
            jsonObject=new JSONObject();
        }
        Boolean isGain = jsonObject.getBoolean("isGain");
        if(null==isGain){
            isGain=false;
        }
        NiuNiuRedMessage niuNiuRedMessage= (NiuNiuRedMessage) messageContent;
        orderCode = niuNiuRedMessage.getOrderCode();
        typeId = niuNiuRedMessage.getHbType();
        redType= RedType.FULI;
        if(getPakegePop==null){
            initGetPakegePop();
        }
        if(isGain){
            FuLiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
        }else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("orderCode", niuNiuRedMessage.getOrderCode());
            data.put("typeId", niuNiuRedMessage.getHbType());
            HttpApiUtils.request(getMyContext(), RequestUtil.CAIBAO, data, new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result, Headers headers) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    JSONObject messageInfo = jsonObject.getJSONObject("data");
                    //??????????????????
                    setMessageStatus(messageInfo);
                    Boolean isQB = messageInfo.getBoolean("isQB");//??????????????????
                    Boolean isOver = messageInfo.getBoolean("isOver");//????????????
                    Boolean isPastDue = messageInfo.getBoolean("isPastDue");//????????????
                    Boolean isGain = messageInfo.getBoolean("isGain");//??????????????????
                    String avatar = messageInfo.getString("avatar");//??????
                    String userName = messageInfo.getString("userName");//?????????
                    String info = messageInfo.getString("info");//????????????
                    String info2 = messageInfo.getString("info2");//??????- ??????
                    //???????????????????????????(?????????????????????,??????????????????????????????????????????.???BindView????????????setMessageExtra()????????????????????????,??????????????????????????????,????????????????????????????????????)
                    setLocalMessageStatus(isQB, isOver, isPastDue, null==isGain?false:isGain, niuNiuRedMessage);
//                    RongIM.getInstance().setMessageExtra(messageId,);
                    if(!isQB){
                        getPakegePopTypeTv.setVisibility(View.INVISIBLE);
                        getPakegePopOpenIv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.INVISIBLE);
                        showPakegeDetailTv.setVisibility(View.VISIBLE);
                        getPakegeTipTv.setText(info);
                        if (isSend) {//??????????????????
                            constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_send_sel));
                        }
                        else {
                            constraintLayout.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.fuli_receive_sel));
                        }
                        setTipTv(isGain, isOver, isPastDue,redTipTv);
                    }else {//????????????
                        getPakegePopTypeTv.setVisibility(View.VISIBLE);
                        getPakegePopOpenIv.setVisibility(View.VISIBLE);
                        showPakegeDetailTv.setVisibility(View.INVISIBLE);
                        pakegeButtomIv.setVisibility(View.VISIBLE);
                        getPakegePopTypeTv.setText(info);
                        getPakegeTipTv.setText("????????????,????????????");
                    }

                    //TODO ???????????????????????????
       /*         getPakegePopNameTv.setText(name);
                GlideLoadViewUtil.FLoadCornersView(MyConversationFragment.this,imageUrl,12,getPakegePopTitleIv);*/
                    if(!getPakegePop.isShowing()){
                        getPakegePop.showAtLocation(listView, Gravity.CENTER,0,0);
                        Utils.darkenBackground(getActivity(),0.7f);
                    }
                }

                @Override
                public void onFaild(String msg) {
                }
            });
        }
    }


    private void setTipTv(Boolean isGain, Boolean isOver, Boolean isPastDue, TextView redTipTv) {
        if(null==isGain){
            isGain=false;
        }
        if (isGain) {//isGain
            redTipTv.setText("???????????????");
        } else if (isOver) {
            redTipTv.setText("???????????????");
        } else if (isPastDue) {
            redTipTv.setText("???????????????");
        } else {
            redTipTv.setText("????????????");
        }
    }

    /**
     * ???????????????????????????(?????????????????????,??????????????????????????????????????????.???BindView????????????setMessageExtra()????????????????????????,
     * ??????????????????????????????,????????????????????????????????????)
     * @param isQB  ???????????????
     * @param isOver
     * @param isPastDue
     * @param isGain
     * @param niuNiuRedMessage
     */
    private void setLocalMessageStatus(Boolean isQB, Boolean isOver, Boolean isPastDue, Boolean isGain, NiuNiuRedMessage niuNiuRedMessage) {
        niuNiuRedMessage.setGain(isGain);
        niuNiuRedMessage.setOver(isOver);
        niuNiuRedMessage.setPastDue(isPastDue);
        niuNiuRedMessage.setQB(isQB);
    }

    /*
    ---------------------------------??????ui ???????????????-------------------------------???------------------------------??????ui ???????????????----------------------------------------------------------------
    ---------------------------------??????ui ???????????????-------------------------------???------------------------------??????ui ???????????????---------------------------------------------------------------
    ---------------------------------??????ui ???????????????-------------------------------???------------------------------??????ui ???????????????---------------------------------------------------------------
     */
        /*
        ??????onResolveAdapter??????,??????????????????????????????adapter
         */
    @Override
    public MessageListAdapter onResolveAdapter(Context context) {
        myMessageListAdapter = new MyMessageListAdapter(getMyContext());
        return myMessageListAdapter;
    }

    /**
     * ??? Intent ????????? ?????????????????????????????? Uri
     */
    private void getIntentData(Intent intent) {

        Uri data = intent.getData();
        //??????id(??????????????????id ??????????????????id)
        mTargetId = data.getQueryParameter("targetId");
        //????????????????????????
        mConversationType = Conversation.ConversationType.valueOf(data.getLastPathSegment().toUpperCase(Locale.getDefault()));
        //??????
        title=data.getQueryParameter("title");
    }
    /*
    ??????RongExtension???????????????
     */
    private void initRongExtension(View view) {
        rongExtension = (RongExtension) view.findViewById(io.rong.imkit.R.id.rc_extension);
        rongExtension.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //????????????????????????????????????
                if (onExtensionChangeListener != null) {
                    onExtensionChangeListener.onExtensionHeightChange(rongExtension.getHeight());
                }
            }
        });
             /*
             STYLE_SWITCH_CONTAINER_EXTENSION: SCE ??????/??????+??????????????????+????????????
             STYLE_CONTAINER: C ??????????????????+??????
             STYLE_CONTAINER_EXTENSION : CE ??????????????????+??????+????????????
             STYLE_EXTENSION_CONTAINER : EC ????????????+??????+??????????????????
             STYLE_SWITCH_CONTAINER :SC ??????+??????+??????????????????
              */
        rongExtension.setInputBarStyle(InputBar.Style.STYLE_CONTAINER_EXTENSION);
        View messageListView = findViewById(view, io.rong.imkit.R.id.rc_layout_msg_list);
        listView = findViewById(messageListView, io.rong.imkit.R.id.rc_list);
    }
    /**
     * ??????????????????????????????
     */
    private void setMessageListLast() {
        if (!rongExtension.isExtensionExpanded()) {
            listView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listView.requestFocusFromTouch();
                    listView.setSelection(listView.getCount());
                }
            }, 100);
        }
    }
    /**
     * ?????????Plugin ?????????????????????
     *
     * @param v
     * @param extensionBoard
     */
    @Override
    public void onPluginToggleClick(View v, ViewGroup extensionBoard) {
        // ?????????????????? Plugin ???+???????????????????????? ???????????????????????????????????????
        if (onExtensionChangeListener != null) {
            onExtensionChangeListener.onPluginToggleClick(v, extensionBoard);
        }
        setMessageListLast();
    }
    /**
     * ????????????????????????????????????
     *
     * @param v
     * @param extensionBoard
     */
    @Override
    public void onEmoticonToggleClick(View v, ViewGroup extensionBoard) {
        // ????????????????????????????????????????????? ???????????????????????????????????????
        setMessageListLast();
    }

    /**
     * ????????????????????????
     * @param v
     * @param text
     */
    @Override
    public void onSendToggleClick(View v, String text) {
        super.onSendToggleClick(v, text);
        Log.e(TAG, "onSendToggleClick: "+"v.getId()=  "+v.getId()+"---------text=  "+text );
    }

    @Override
    public void onExtensionExpanded(int h) {
        super.onExtensionExpanded(h);
        if (onExtensionChangeListener != null) {
            onExtensionChangeListener.onExtensionExpanded(h);
        }
    }

    @Override
    public void onExtensionCollapsed() {
        super.onExtensionCollapsed();
        if (onExtensionChangeListener != null) {
            onExtensionChangeListener.onExtensionCollapsed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_redpakege_pop_exit_iv:
                getPakegePop.dismiss();
                break;
            case R.id.get_pakege_pop_open_iv:
                HashMap<String, Object> data = new HashMap<>();
                data.put("orderCode",orderCode);
                data.put("typeId",typeId);
//                data.put("userId","4");
                HttpApiUtils.normalRequest(getMyContext(), RequestUtil.GET_RED_PAKEGE, data, new HttpApiUtils.OnRequestLintener() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("data");
                        Boolean isSuccess = CheckJsonUtil.checkBooleanJson(jsonObject, "isSuccess");
                        Boolean isGain = CheckJsonUtil.checkBooleanJson(jsonObject, "isGain");
                        Boolean isPastDue = CheckJsonUtil.checkBooleanJson(jsonObject, "isPastDue");
                        Boolean isOver = CheckJsonUtil.checkBooleanJson(jsonObject, "isOver");
                        Boolean isQB = CheckJsonUtil.checkBooleanJson(jsonObject, "isQB");
                        String info = jsonObject.getString("info");
                        if(isSuccess){
                            if(typeId==3){
                                NiuNiuRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                                getPakegePop.dismiss();
                            }else if(typeId==2){
                                SaoLeiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                                getPakegePop.dismiss();
                            }else if(typeId==4){
                                FuLiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                                getPakegePop.dismiss();
                            }
                        }else {
                            getPakegeTipTv.setText(info);
                        }

                        HashMap<String, Object> data = new HashMap<>();
                        data.put("isQB",isQB);
                        data.put("isGain",isGain);
                        data.put("isPastDue",isPastDue);
                        data.put("isOver",isOver);
                        setMessageStatus(new JSONObject(data));
                        NiuNiuRedMessage niuNiuRedMessage = (NiuNiuRedMessage) messageContent;
                        //Boolean isQB, Boolean isOver, Boolean isPastDue, Boolean isGain, NiuNiuRedMessage niuNiuRedMessage
                        setLocalMessageStatus(isQB,isOver,isPastDue,isGain,niuNiuRedMessage);

                        switch (redType){
                            case NIUNIU:
                                if (isSend) {//??????????????????
                                 constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.niuniu_send_sel));
                                }else {
                                    constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.niuniu_receive_sel));
                                }
                                break;
                            case SAOLEI:
                                if (isSend) {//??????????????????
                                    constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.ic_send_saoleisel));
                                }else {
                                    constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.ic_receive_saolei_sel));
                                }
                                break;
                            case FULI:
                                if (isSend) {//??????????????????
                                        constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.fuli_send_sel));
                                }else {
                                    constraintLayout.setBackground(getMyContext().getResources().getDrawable(R.drawable.fuli_receive_sel));
                                }
                                break;
                            default:
                                break;
                        }


                    }

                    @Override
                    public void onFaild(String msg) {
                    }
                });
                break;
            case R.id.show_pakege_detail_tv:
                if(typeId==3){
                    NiuNiuRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                }else if (typeId==2){
                    SaoLeiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                }else if(typeId==4){
                    FuLiRedPakegeDetailActivity.startAty(getMyContext(),orderCode,typeId);
                }
                getPakegePop.dismiss();
                break;

            default:
                break;
        }
    }
    private Context  getMyContext(){
     return null==getContext()?MyApplication.getInstance():getContext();
    }


    public interface OnExtensionChangeListener {

        void onExtensionHeightChange(int h);

        void onExtensionExpanded(int h);

        void onExtensionCollapsed();

        void onPluginToggleClick(View v, ViewGroup extensionBoard);
    }

}
