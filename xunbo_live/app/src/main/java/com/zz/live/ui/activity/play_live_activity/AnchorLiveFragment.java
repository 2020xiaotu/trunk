package com.zz.live.ui.activity.play_live_activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.opensource.svgaplayer.SVGAImageView;
import com.zz.live.MyApplication;
import com.zz.live.R;
import com.zz.live.base.BaseFragment;
import com.zz.live.bean.LiveGiftSVGAEntity;
import com.zz.live.bean.LiveMessageModel;
import com.zz.live.bean.UserInfoEntity;
import com.zz.live.myView.gift.GiftSendModel;
import com.zz.live.myView.gift.GiftView;
import com.zz.live.myView.gift.GiftSvgaManage;
import com.zz.live.ui.rongyun.RongLibUtils;
import com.zz.live.ui.rongyun.message.LiveExitAndJoinMessage;
import com.zz.live.ui.rongyun.message.LiveGiftMessage;
import com.zz.live.ui.rongyun.message.LiveRedMessage;
import com.zz.live.ui.rongyun.message.LiveShareBetMessage;
import com.zz.live.ui.rongyun.message.LiveTextMessage;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.DrawerLeftEdgeSize;
import com.zz.live.utils.SharePreferencesStr;
import com.zz.live.utils.SharePreferencesUtil;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.Utils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.ChatRoomInfo;
import io.rong.imlib.model.MessageContent;

import static android.view.View.GONE;

public class AnchorLiveFragment extends BaseFragment implements Handler.Callback{
    public static String TAG="LiveFragment";
    @BindView(R.id.live_drawerLayout)
    DrawerLayout live_drawerLayout;
    @BindView(R.id.live_cover_iv)
    ImageView live_cover_iv;
    @BindView(R.id.play_fail_linear)
    LinearLayout play_fail_linear;
    @BindView(R.id.clear_screen_iv)
    ImageView clear_screen_iv;
    @BindView(R.id.linearLayout7)
    public LinearLayout linearLayout7;
    @BindView(R.id.live_edit_panel)
    EditPanel editPanel;
    @BindView(R.id.drawwe_linear)
    ConstraintLayout drawwe_linear;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.giftView)
    public GiftView giftView;
    @BindView(R.id.money_tv)
    TextView money_tv;
    @BindView(R.id.svga_imageview)
    public SVGAImageView svgaImageView;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private LiveChatFragment liveChatFragment;
    private GiftSvgaManage giftSvgaManage;
    UserInfoEntity.DataBean userDataBean;
    private Handler handler = new Handler(this);
    private int totalMemberCount;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_anchor;
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        userDataBean= Utils.getUserInfoBean();
        /**
         * ????????? drawlayout
         */
//        editPanel.setAnchorLiveFragment(this);
        live_cover_iv.setVisibility(View.VISIBLE);
        live_drawerLayout.setScrimColor(0x00000000);
        DrawerLeftEdgeSize.setRightEdge(getActivity(),live_drawerLayout, 1f);
        live_drawerLayout.openDrawer(GravityCompat.END);
        initLiveChatFragment();
        initSvgaImageView();
        editPanel.setRoomId(sp.getRoomId());
        initRongYun();
        editPanel.setInputLinstener();
    }

    /**
     *
     * @param position
     * @param url
     * @param coverUrl
     * @return
     */
    public static AnchorLiveFragment newInstence(int position, String url, String coverUrl ){
        AnchorLiveFragment anchorLiveFragment = new AnchorLiveFragment();
        Bundle newBundle = new Bundle();
        newBundle.putInt(CommonStr.POSITION,position);
        newBundle.putString("url",url);
        newBundle.putString("coverUrl",coverUrl);
        anchorLiveFragment.setArguments(newBundle);
        return  anchorLiveFragment;
    }

    private void initRongYun() {
        RongLibUtils.addEventHandler(handler);
        joinChatRoom();
    }
    private void joinChatRoom() {
        RongLibUtils.joinChatRoom(0, sp.getRoomId(), new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                /*
                ?????????????????????
                 */
                Log.e(TAG, "?????????????????????" );
//            ??????????????????????????????message
                LiveExitAndJoinMessage msgContent = new LiveExitAndJoinMessage(userDataBean.getNickname(), "0");
                msgContent.setUserInfoJson(RongLibUtils.setUserInfo(getContext()));
                RongLibUtils.sendMessage(sp.getRoomId(), msgContent);
//             ?????????????????????
              getChatRoomNum();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //?????????????????????
                Log.e(TAG, "?????????????????????" );
            }
        });
    }
        /*
  ???????????????????????????
    */
   private void getChatRoomNum() {
        RongIMClient.getInstance().getChatRoomInfo(sp.getRoomId(), 20, ChatRoomInfo.ChatRoomMemberOrder.RC_CHAT_ROOM_MEMBER_ASC, new RongIMClient.ResultCallback<ChatRoomInfo>() {
            @Override
            public void onSuccess(ChatRoomInfo chatRoomInfo) {
                //??????????????????
                 totalMemberCount = chatRoomInfo.getTotalMemberCount();
                //???????????????
                if(tv_num!=null){
                    tv_num.setText(totalMemberCount+"???");
                }
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
    /**
     * ????????????
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }
        private void initSvgaImageView() {
        giftSvgaManage = new GiftSvgaManage(getContext(),svgaImageView);
    }
    private void initLiveChatFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        liveChatFragment= (LiveChatFragment) fragmentManager.findFragmentById(R.id.live_chat_fragment);
    }
      @OnTouch(R.id.drawwe_linear)
        public void onTouch(View view, MotionEvent event){
            editPanel.editLinear.setVisibility(GONE);
            editPanel.inputClickLinear.setVisibility(View.VISIBLE);
            linearLayout7.setVisibility(View.VISIBLE);
        }
    @OnClick({R.id.clear_screen_iv,R.id.drawwe_linear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.clear_screen_iv:
                clear_screen_iv.setVisibility(GONE);
                SharePreferencesUtil.putBoolean(getContext(),SharePreferencesStr.SHOW_CLEAR_SCREEN,false);
                break;
            case R.id.drawwe_linear:
                editPanel.editLinear.setVisibility(GONE);
                editPanel.inputClickLinear.setVisibility(View.VISIBLE);
                linearLayout7.setVisibility(View.VISIBLE);
                break;
                default:
                    break;
        }
    }

    /**
     * ??????????????????
     *
     * @param message
     * @return
     */
    @Override
    public boolean handleMessage(Message message) {
        if(message.obj instanceof io.rong.imlib.model.Message ){
            io.rong.imlib.model.Message obj = (io.rong.imlib.model.Message) message.obj;
            String targetId = obj.getTargetId();//?????????????????????????????????
            //?????????????????????????????????
            if(targetId.equals(sp.getRoomId())){
                MessageContent content = obj.getContent();
                switch (message.what) {
                    //?????????????????????
                    case RongLibUtils.MESSAGE_ARRIVED:
                    case RongLibUtils.MESSAGE_SENT:

                        /*??????????????????*/

                        LiveMessageModel messageModel = new LiveMessageModel();
                        if (content instanceof LiveTextMessage) {
                            LiveTextMessage liveTextMessage = (LiveTextMessage) content;
                            Utils. initUserInfo(messageModel, liveTextMessage.getUserInfoJson());
                            messageModel.setObjectName(CommonStr.TEXT_MESSAGE);
                            messageModel.setTextContent(liveTextMessage.getContent());
                            liveChatFragment.addItem(messageModel);

                        /*    ????????????*/

                        } else if (content instanceof LiveShareBetMessage) {
                            LiveShareBetMessage liveShareBetMessage = (LiveShareBetMessage) content;
                            messageModel.setObjectName(CommonStr.SHARE_MESSAGE);
                            messageModel.setLevel(liveShareBetMessage.getPointGrade());
                            messageModel.setUserName(liveShareBetMessage.getNickname());
                            String betMoney = liveShareBetMessage.getLotmoney();
                            List<String> amountAmountList = Arrays.asList(betMoney.split(","));
                            messageModel.setBetAmount((Integer.parseInt(amountAmountList.get(0))*amountAmountList.size())+"");
                            messageModel.setTypeName(liveShareBetMessage.getTypename());
                            messageModel.setBetQiShu(liveShareBetMessage.getLotteryqishu());
                            messageModel.setBetName(liveShareBetMessage.getName());
                            messageModel.setBetGroupName(liveShareBetMessage.getGroupname());
                            messageModel.setType_id(liveShareBetMessage.getType_id());
                            messageModel.setGame(liveShareBetMessage.getGame());
                            messageModel.setReluId(liveShareBetMessage.getRule_id());
                            liveChatFragment.addItem(messageModel);

                         /*   ????????????*/

                        } else if (content instanceof LiveRedMessage) {
                            LiveRedMessage liveRedMessage = (LiveRedMessage) content;
                            int redType = liveRedMessage.getRedType();
                            //3????????????????????? userInfoJson???  1 2 ??????????????????qpUserName???
                            if (redType == 3) {
                                Utils.initUserInfo(messageModel, liveRedMessage.getUserInfoJson());
                            } else {
                                messageModel.setUserName(liveRedMessage.getQbUserName());
                            }
                            messageModel.setObjectName(CommonStr.RED_MESSAGE);
                            messageModel.setRedType(redType);
                            messageModel.setRedPrice(liveRedMessage.getRedPrice());
                            messageModel.setRedId(Integer.parseInt(liveRedMessage.getRedId()));
                            liveChatFragment.addItem(messageModel);

                   /*         ????????????*/

                        } else if (content instanceof LiveGiftMessage) {
//                            ?????????????????????  ??????   ---------------------
                                    LiveGiftMessage liveGiftMessage = (LiveGiftMessage) content;
                            String userInfoJson = liveGiftMessage.getUserInfoJson();
                            Utils.  initUserInfo(messageModel, userInfoJson);
                            messageModel.setObjectName(CommonStr.GIFT_MESSAGE);
                            messageModel.setGiftId(liveGiftMessage.getGiftId());
                            String giftNum = liveGiftMessage.getGiftNum();
                            messageModel.setGiftNum(giftNum);
                            messageModel.setGiftName(liveGiftMessage.getGiftName());
                            String giftPrice = liveGiftMessage.getGiftPrice();
                            int singlePrice = Integer.parseInt(giftPrice);
                            int oldPrice = singlePrice * Integer.parseInt(giftNum);
                            liveChatFragment.addItem(messageModel);
                            String toString = money_tv.getText().toString();
                            if(!toString.equals("- - - -")){
                                int oldNum = Integer.parseInt(toString);
                                money_tv.setText((oldNum+oldPrice)+"");
                            }
//                            ?????????????????????  ??????   ---------------------
//                                    ??????????????????   ??????  -----------------------
                                    String giftUrl = liveGiftMessage.getGiftUrl();//??????????????????
                            JSONObject jsonObject = JSONObject.parseObject(userInfoJson);//????????????
                            if (StringMyUtil.isEmptyString(giftUrl)) {//????????????????????????,??????????????????. ??????????????????????????????????????????
                                int giftCount = 0;
                                try {
                                    giftCount = Integer.parseInt(giftNum);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                GiftSendModel giftSendModel = new GiftSendModel(giftCount);
                                giftSendModel.setGiftRes(liveGiftMessage.getGiftIcon());
                                giftSendModel.setNickname(jsonObject.getString("name"));
                                giftSendModel.setSig("??????" + liveGiftMessage.getGiftName());
                                String portrait = jsonObject.getString("portrait");
                                giftSendModel.setUserAvatarRes(portrait);
                                giftView.addGift(giftSendModel);

                            }else {//?????????????????????????????????, ??????svga??????(????????????????????????????????????,?????????????????????????????????????????????????????????)
                                if(!jsonObject.getString("userId").equals(userDataBean.getId())){
                                    LiveGiftSVGAEntity liveGiftSVGAEntity = new LiveGiftSVGAEntity(Utils.getFirstImgurl(MyApplication.getInstance())+giftUrl, null);
                                    giftSvgaManage.startAnimator(liveGiftSVGAEntity);
                                }
                            }
//                            ??????????????????   ??????  -----------------------
                            //??????/?????????????????????
                        } else if (content instanceof LiveExitAndJoinMessage) {
                            LiveExitAndJoinMessage liveExitAndJoinMessage = (LiveExitAndJoinMessage) content;
                            messageModel.setObjectName(CommonStr.EXIT_JOIN_MESSAGE);
                            String userName = liveExitAndJoinMessage.getUserName();
                            messageModel.setUserName(userName);
                            String status = liveExitAndJoinMessage.getStatus();
                            messageModel.setStatus(status);
                            String userNickName = SharePreferencesUtil.getString(MyApplication.getInstance(), "userNickName", "");
                            if(status.equals("0")){//???????????????,??????????????????1
                                if(!userName.equals(userNickName)){
                                    totalMemberCount++;
                                    tv_num.setText(totalMemberCount+"???");
                                }
                                liveChatFragment.addItem(messageModel);
                            }else {//???????????????????????????-1
                                if(!userName.equals(userNickName)){
                                    totalMemberCount--;
                                    tv_num.setText(totalMemberCount+"???");
                                    liveChatFragment.addItem(messageModel);
                                }
                            }
                        }
                        break;
                    //??????????????????
                    case RongLibUtils.MESSAGE_SEND_ERROR:
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
    }



    /**
    ????????????????????????,?????????????????????(???????????????????????????????????????????????????????????????)
    ??????????????????????????????????????? ????????????????????????view???setOnKeyListener????????????

    nickname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    //???????????????
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nickname.getWindowToken(), 0);
                    //?????????View???????????????????????????????????????
                    getFocus();
                }0
                return false;
            }
        });
     */
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RongLibUtils.removeEventHandler(handler);
    }
}
