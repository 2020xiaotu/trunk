package com.zz.live.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.live.MyApplication;
import com.zz.live.R;
import com.zz.live.base.Const;
import com.zz.live.bean.LiveMessageModel;
import com.zz.live.myView.MyClickableSpan;
import com.zz.live.myView.MyImageSpan;
import com.zz.live.myView.UrlImageSpan;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiveChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyClickableSpan.OnClickSpanLintener, View.OnClickListener {
    private Fragment fragment;
    private ArrayList<LiveMessageModel> liveMessageModelArrayList = new ArrayList<>();
    public static  int QUEYUE_RED=1;
    public static int ZHUANXIANG_RED=2;
    public static int PUTONG_RED=3;
    public static int TJ_RED =4;
    public static int NORMAL_RED =5;
    public LiveChatAdapter(Fragment fragment, ArrayList<LiveMessageModel> liveMessageModelArrayList) {
        this.fragment = fragment;
        this.liveMessageModelArrayList = liveMessageModelArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder=null;
        //1 文本  2 红包  3 系统公告 4 分享注单 5 礼物消息 6 进入/退出直播间消息
        switch (viewType){
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_text_message_item,null);
                viewHolder = new TextHolder(view);
                break;
            case 2:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_red_message_item,null);
                viewHolder=new RedHolder(view);
                break;
            case 3:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_notice_message_item,null);
                viewHolder=new NoticeHolder(view);
                break;
            case 4:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_share_message_item,null);
                viewHolder=new ShareHolder(view);
                break;
            case 5:
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_gift_message_item,null);
            viewHolder=new GiftHolder(view);
            break;
            //
            case 6:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new ExitJoinHolder(view);
                break;
            case 7:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new ShareZjHolder(view);
                break;
            case 8:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new FollowHolder(view);
                break;
            case 9:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new ForbiddenHolder(view);
                break;
            case 10:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new RoomManagerHolder(view);
                break;
            case 11:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_notice_message_item,null);
                viewHolder=new ChangeRoomTypeHolder(view);
                break;

            case 13:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_notice_message_item,null);
                viewHolder=new LiveSystemMessageHolder(view);
                break;
            case 14:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_red_message_item,null);
                viewHolder=new NormalRedHolder(view);
                break;
                case 15:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.live_exit_join_message_item,null);
                viewHolder=new RewardAnchorHolder(view);
                break;
                default:
                    break;
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try{
            //1 文本  2 红包  3 系统公告 4 分享注单 5 礼物
            int itemViewType = getItemViewType(position);
            LiveMessageModel liveMessageModel = liveMessageModelArrayList.get(position);
            String userName = liveMessageModel.getUserName();
            String textContent = liveMessageModel.getTextContent();
            String managerType = liveMessageModel.getManagerType();

            String levelIcon = liveMessageModel.getLevelIcon();
            String medalIcon = liveMessageModel.getMedalIcon();
            String titleIcon = liveMessageModel.getTitleIcon();
/**
 * 消息内容colorSpan
 */
            ForegroundColorSpan defaultColorSpan = new ForegroundColorSpan(Color.WHITE);
            /**
             * 用户名colorSpan
             */
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
            switch (itemViewType){
                case 1:
                    TextHolder textHolder= (TextHolder) holder;
//                SpannableString spannableString = new SpannableString("   "+userName + " "+textContent);
//                initLevelIcon(level, spannableString,userName,managerType,true);
                    TextView textNameContentTv = textHolder.textNameContentTv;
                    SpannableStringBuilder spannableStringBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, textNameContentTv);
                    spannableStringBuilder.append(userName ,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    spannableStringBuilder.append(" "+textContent,defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    textNameContentTv.setText(spannableStringBuilder);
                    textNameContentTv.setTag(position);
                    textNameContentTv.setOnClickListener(this);
                    break;
                case 2:
                    RedHolder redHolder =(RedHolder) holder;
                    TextView redContenttv = redHolder.redContenttv;
                    String redPrice = liveMessageModel.getRedPrice();
                    switch (liveMessageModel.getRedType()){
                        //1 趣享  2 专享 3 普通
                        case 1:
                            setRedItem(QUEYUE_RED,userName,redPrice,textContent,redContenttv,position);
                            break;
                        case 2:
                            setRedItem(ZHUANXIANG_RED,userName,redPrice,textContent,redContenttv,position);
                            break;
                        case 3:
                            setRedItem(PUTONG_RED,userName,redPrice,textContent,redContenttv,position);
                            break;
                        case 4:
                            setRedItem(TJ_RED,userName,redPrice,textContent,redContenttv,position);
                            break;
                        default:
                            break;
                    }

                    break;
                case 3:
                    NoticeHolder noticeHolder = (NoticeHolder) holder;
                    noticeHolder.noticeContenttv.setText(textContent);
                    break;
                case 4:
                /*
                主播端投注消息不显示跟投按钮
                 */
                    ShareHolder shareHolder = (ShareHolder) holder;

                    SpannableStringBuilder shareSpanBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, shareHolder.shareContenttv);
                    shareSpanBuilder.append(userName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    shareSpanBuilder.append(" 在 "+liveMessageModel.getTypeName()+"中下注了"+liveMessageModel.getBetAmount()+"元  ",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
       /*         SpannableString gentouSpan = new SpannableString("   ");
                MyImageSpan iconSpanPt = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(BitmapFactory.decodeResource(fragment.getResources(), R.drawable.gt_btn), 40, 18));
                MyClickableSpan shareClickSpan = new MyClickableSpan(textContent, fragment.getContext(), position);
                gentouSpan.setSpan(iconSpanPt,0,gentouSpan.length()-1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                gentouSpan.setSpan(shareClickSpan,0,gentouSpan.length()-1,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                shareSpanBuilder.append(gentouSpan);*/
                    shareHolder.shareContenttv.setText(shareSpanBuilder);
                    //点击跟投
//                shareClickSpan.setOnClickSpanLintener(this);
                    //设置之后才有点击效果
                    shareHolder.shareContenttv.setMovementMethod(LinkMovementMethod.getInstance());
                    shareHolder.shareContenttv.setTag(position);
                    break;
                //礼物消息
                case 5:
                    GiftHolder giftHolde =(GiftHolder)holder;
                    String giftId = liveMessageModel.getGiftId();
                    String giftName = liveMessageModel.getGiftName();
                    String giftNum = liveMessageModel.getGiftNum();
                    SpannableStringBuilder giftSpanBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, giftHolde.giftContenttv);
                    giftSpanBuilder.append(userName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    giftSpanBuilder.append("  送了" + giftName + " x" + giftNum,defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                initLevelIcon(level,giftSpan,userName,managerType,true);
                    giftHolde.giftContenttv.setText(giftSpanBuilder);
                    break;
                //进入退出直播间
                case 6:
                    ExitJoinHolder exitJoinHolder= (ExitJoinHolder) holder;
                    TextView exitJoinCotentTv = exitJoinHolder.exitJoinCotentTv;
                    SpannableStringBuilder joinSpanBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, exitJoinCotentTv);
                    String s ;
                    if(liveMessageModel.getStatus().equals("0")){
                        s="进入直播间";
                    }else {
                        s="退出直播间";
                    }
                    joinSpanBuilder.append(liveMessageModel.getUserName(),foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    joinSpanBuilder.append(s,defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                SpannableString exitSpan = new SpannableString("    "+liveMessageModel.getUserName() + s);
//                initLevelIcon(level, exitSpan,userName,managerType,true);
                    exitJoinCotentTv.setTextColor(Color.WHITE);
                    exitJoinCotentTv.setText(joinSpanBuilder);
                    break;
                case 7:
                    ShareZjHolder shareZjHolder= (ShareZjHolder) holder;
                    TextView zjContentTv = shareZjHolder.zjContentTv;
                    SpannableStringBuilder zjStringBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, zjContentTv);
                    Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), R.drawable.zj_icon);
                    MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(bitmap, 35, 15));
                    zjStringBuilder.append(" ",iconSpan,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    zjStringBuilder.append("   恭喜",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    zjStringBuilder.append(liveMessageModel.getUserNickName(),foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    zjStringBuilder.append("在"+liveMessageModel.getTypeName()+"中奖了",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    zjContentTv.setText(zjStringBuilder);
                    break;
                case 8:
                    FollowHolder followHolder = (FollowHolder) holder;
                    TextView followContentTv = followHolder.followContentTv;
                    SpannableString followSpanStr = new SpannableString(liveMessageModel.getUserName()+" 关注了您");
                    ForegroundColorSpan followColorSpan = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
                    followSpanStr.setSpan(followColorSpan,0,userName.length()+1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    followContentTv.setText(followSpanStr);
                    break;
                case 9:
                    ForbiddenHolder forbiddenHolder = (ForbiddenHolder) holder;
                    TextView forbiddenContentTv = forbiddenHolder.forbiddenContentTv;
                    String isForbidden = liveMessageModel.getIsForbidden();//  1 禁言 0解禁
                    String targetNickName = liveMessageModel.getTargetNickName();//被禁言的用户名
                    SpannableStringBuilder forbiddenStringBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, forbiddenContentTv);
                    String forbiddenStatus = isForbidden.equals("1")?"禁言":"解禁";
                    forbiddenStringBuilder.append(" "+userName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    forbiddenStringBuilder.append("将",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    forbiddenStringBuilder.append(targetNickName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    forbiddenStringBuilder.append(forbiddenStatus+"了",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    forbiddenContentTv.setText(forbiddenStringBuilder);
                    break;
                case 10:
                    RoomManagerHolder roomManagerHolder = (RoomManagerHolder) holder;
                    TextView roomManagerContentTv = roomManagerHolder.roomManagerContentTv;
                    String nickName = liveMessageModel.getTargetNickName();
                    String isRoomManager = liveMessageModel.getIsRoomManager();

                    SpannableStringBuilder roomManagerStringBuilder = initLevelEquipmentIcon( managerType, levelIcon, medalIcon, titleIcon, roomManagerContentTv);

                    roomManagerStringBuilder.append(" "+userName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    roomManagerStringBuilder.append("将",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    roomManagerStringBuilder.append(nickName,foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    String type=isRoomManager.equals("1")?"设为":"取消";
                    roomManagerStringBuilder.append(type+"房管",defaultColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    roomManagerContentTv.setText(roomManagerStringBuilder);
                    break;
                case 11:
                    ChangeRoomTypeHolder changeRoomTypeHolder = (ChangeRoomTypeHolder) holder;
                    String changeContent="直播间已切换为";
                    if(liveMessageModel.getType().equals("1")){
                        changeContent+="免费";
                    }else {
                        changeContent+="收费";
                    }
                    changeRoomTypeHolder.changeContent_tv.setText(changeContent);
                    break;

                case 13:
                    /**
                     * 系统禁言/拉黑消息
                     */
                    LiveSystemMessageHolder systemHolder = (LiveSystemMessageHolder) holder;
                    SpannableStringBuilder systemSpanBuilder = new SpannableStringBuilder();


                    String fieldTxt = "";
                    String field = liveMessageModel.getField();
                    if (field.equals("forbiddenStatus")) {
                        fieldTxt = "禁言";
                    }else if (field.equals("blockStatus")){
                        fieldTxt = "封禁";
                    }else if (field.equals("maintainStatus")){
                        fieldTxt = "封号";
                    }
                    String  cancel = "";
                    if (liveMessageModel.getSystemStatus().equals("0")) {
                        cancel = "解除";
                    }else{
                        cancel = "";
                    }
                    ForegroundColorSpan redColorSpan = new ForegroundColorSpan(Color.RED);
                    systemSpanBuilder.append(liveMessageModel.getUserNickName(),foregroundColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    systemSpanBuilder.append(String.format(" 被系统%s%s",cancel, fieldTxt),redColorSpan,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    systemHolder.contentTv.setText(systemSpanBuilder);
                    break;
                case 14:
                    NormalRedHolder normalRedHolder =(NormalRedHolder) holder;
                    TextView normalRedContentTv = normalRedHolder.redContenttv;
                    String normalRedPrice = liveMessageModel.getRedPrice();
                    setRedItem(NORMAL_RED,userName,normalRedPrice,textContent,normalRedContentTv,position);
                    break;
                case 15:
                    RewardAnchorHolder rewardAnchorHolder = (RewardAnchorHolder) holder;
                    TextView reward_content_tv = rewardAnchorHolder.reward_content_tv;
                    reward_content_tv.setTextColor(Color.parseColor("#FFCB14"));
                    reward_content_tv.setText(String.format("%s打赏您%s元",userName,liveMessageModel.getRewardPrice()));
                    break;
                default:
                    break;  }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * s昵称字体颜色span
     * @param forbiddenSpanStr
     * @param startIndex
     * @param endIndex
     */
    private void setColorForbidden(SpannableString forbiddenSpanStr, int startIndex, int endIndex) {
        ForegroundColorSpan forBiddenColorSpan = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
        forbiddenSpanStr.setSpan(forBiddenColorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 等级图标
     * @param level
     * @param spannableString
     * @param userName
     */
    private void initLevelIcon(int level, SpannableString spannableString,String userName, String managerType,boolean setColor) {
        //身份icon
        int drawType=R.drawable.vip1;
        //等级icon
        int drawLevel=R.drawable.add;
        //避免下游没有传managType报空指针
        managerType=  StringMyUtil.isEmptyString(managerType)?"0":managerType;
        //1 主播 2超管 3房管
        if(managerType.equals("1")){
            //主播没有等级icon
            drawType=R.drawable.jinyan_zb_icon;
        }else if(managerType.equals("2")){
            drawType=R.drawable.vip1;//超管不要标识
//            drawType=R.drawable.chaoguang;
            drawLevel = getLevelDrawble(level);
        }else if(managerType.equals("3")){
            drawType=R.drawable.fangguang;
            drawLevel = getLevelDrawble(level);
        }else {
            //普通用户没有身份icon
            drawLevel = getLevelDrawble(level);
        }
        if(setColor){
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
            spannableString.setSpan(span,3,userName.length()+4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if(drawLevel!=R.drawable.add){
            Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), drawLevel);
//                        MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(),R.drawable.flhb);
            MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(bitmap, 35, 15));
            spannableString.setSpan(iconSpan,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if(drawType!=R.drawable.vip1){
            Bitmap bitmapType = BitmapFactory.decodeResource(fragment.getResources(), drawType);
//                        MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(),R.drawable.flhb);
            MyImageSpan typeSpan = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(bitmapType, 35, 15));
            if(drawLevel!= R.drawable.add){
                spannableString.setSpan(typeSpan,2,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }else {
                spannableString.setSpan(typeSpan,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

    @NotNull
    private SpannableStringBuilder initLevelEquipmentIcon(String managerType, String levelIcon, String medalIcon, String titleIcon, TextView textNameContentTv) {

        managerType = StringMyUtil.isEmptyString(managerType)?"":managerType;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int drawType = R.drawable.sdlu;
        if(managerType.equals("1")){
            //主播没有等级icon
            drawType=R.drawable.jinyan_zb_icon;
        }else if(managerType.equals("2")){
//            drawType=R.drawable.chaoguang;//超管取消
        }else if(managerType.equals("3")){
            drawType=R.drawable.fangguang;
        }else {
            //普通用户没有身份icon
        }
        if(drawType!=R.drawable.sdlu){
            //添加身份icon
            Bitmap bitmapType = BitmapFactory.decodeResource(fragment.getResources(), drawType);
            MyImageSpan typeSpan = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(bitmapType, 35, 15));
            spannableStringBuilder.append(" ",typeSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if(!managerType.equals("1")){
            //不是主播, 添加等级icon 勋章icon medalIcon
            if(StringMyUtil.isNotEmpty(levelIcon)){
                /**
                 * 等级icon
                 */
                UrlImageSpan levelUrlImageSpan = new UrlImageSpan(fragment.getContext(), Utils.checkImageUrl(levelIcon), textNameContentTv,35,15,R.drawable.touming);
                spannableStringBuilder.append(" ", levelUrlImageSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(" ");
            }
            if(StringMyUtil.isNotEmpty(medalIcon)){
                /**
                 * 勋章icon
                 */
                UrlImageSpan medalUrlImageSpan = new UrlImageSpan(fragment.getContext(), Utils.checkImageUrl(medalIcon), textNameContentTv,15,15,R.drawable.touming2);
                spannableStringBuilder.append(" ", medalUrlImageSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(" ");
            }

            if(StringMyUtil.isNotEmpty(titleIcon)){
                /**
                 * 称号牌icon
                 */
                List<String> titleIconList = Arrays.asList(titleIcon.split(","));
                for (int i = 0; i < titleIconList.size(); i++) {
                    UrlImageSpan titleUrlImageSpan = new UrlImageSpan(fragment.getContext(), Utils.checkImageUrl(titleIconList.get(i)), textNameContentTv,35,15,R.drawable.touming);
                    spannableStringBuilder.append(" ", titleUrlImageSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//称号牌icon
                    spannableStringBuilder.append(" ");
                }
            }
        }


        return spannableStringBuilder;
    }
    private int getLevelDrawble(int level) {
        int drawType;
        int[] levelIcon = Const.getLevelIcon(MyApplication.getInstance());
        drawType = levelIcon[level - 1];
        return drawType;
    }

    /**
     * 红包消息 bindview赋值
     * @param redType 红包类型
     * @param userName 用户名
     * @param redPrice 抢包金额
     * @param textContent 文字内容
     * @param contentTv holder.textView
     */
    public void setRedItem(int redType, String userName, String redPrice, String textContent, TextView contentTv, int position){
        if(redType==QUEYUE_RED||
                redType==ZHUANXIANG_RED||
                redType == TJ_RED||
                redType == NORMAL_RED){
            SpannableString redString;
            int drawble;
            if(redType==QUEYUE_RED){
                redString = new SpannableString(" 恭喜本直播间 "+userName+"抢到了"+redPrice+"元邀请红包。");
                drawble = R.drawable.flhb;
            }else if(redType == ZHUANXIANG_RED) {
                redString = new SpannableString(" 恭喜本直播间 "+userName+"抢到了"+redPrice+"元专享红包。");
                drawble = R.drawable.zxhb;
            }else if(redType == TJ_RED){
                //天降
                redString = new SpannableString(" 恭喜本直播间 "+userName+"抢到了"+redPrice+"元天降红包。");
                drawble = R.drawable.tjhb_xs;
            }else {
                //todo 抢到普通红包发送的消息(后面加的需求)
                redString = new SpannableString(" 恭喜本直播间 "+userName+"抢到了"+redPrice+"元普通红包。");
                drawble = R.drawable.pthb;
            }

            //用户名字体颜色span
            ForegroundColorSpan userNameSpan = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
            redString.setSpan(userNameSpan,8,8+userName.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //红包icon span
            //先将图片转为bitmap, 设置图片大小后再设置span
            Bitmap bitmap = BitmapFactory.decodeResource(fragment.getResources(), drawble);
//                        MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(),R.drawable.flhb);
            MyImageSpan iconSpan = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(bitmap, 64, 16));
            redString.setSpan(iconSpan,0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    //主播端不需要点击领取红包
/*            if(redType == QUEYUE_RED||redType == ZHUANXIANG_RED){
                //点击领取红包字体颜色span
                ForegroundColorSpan getRedSpan = new ForegroundColorSpan(Color.parseColor("#F73D3D"));
                redString.setSpan(getRedSpan,redString.length()-7,redString.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //点击领取红包点击 span
                MyClickableSpan redClickSpan = new MyClickableSpan(textContent, fragment.getContext(),position);
                redString.setSpan(redClickSpan,redString.length()-7,redString.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                redClickSpan.setOnClickSpanLintener(this);
            }*/


            contentTv.setText(redString);
            //设置之后才有分段点击效果
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
            contentTv.setTag(position);
        }else {
            SpannableString redStringPt = new SpannableString(" "+userName+" 发了一个普通红包。");

            //用户名字体颜色span
            ForegroundColorSpan userNameSpanPt = new ForegroundColorSpan(Color.parseColor("#FFCB14"));
            redStringPt.setSpan(userNameSpanPt,1,1+userName.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //红包icon span
            MyImageSpan iconSpanPt = new MyImageSpan(fragment.getContext(), Utils.getNewBitmap(BitmapFactory.decodeResource(fragment.getResources(), R.drawable.pthb), 64, 16));
            redStringPt.setSpan(iconSpanPt,0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //主播端不需要点击领取红包

/*            //点击领取红包字体颜色span
            ForegroundColorSpan getRedSpanPt = new ForegroundColorSpan(Color.parseColor("#F73D3D"));
            redStringPt.setSpan(getRedSpanPt,redStringPt.length()-7,redStringPt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //点击领取红包点击 span
            MyClickableSpan redClickSpanPt = new MyClickableSpan(textContent, fragment.getContext(),position);
            redStringPt.setSpan(redClickSpanPt,redStringPt.length()-7,redStringPt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            redClickSpanPt.setOnClickSpanLintener(this);*/

            contentTv.setText(redStringPt);
            //设置之后才有分段点击效果
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
            contentTv.setTag(position);
        }
    }
    @Override
    public int getItemViewType(int position) {
        //1 文本  2 红包  3 系统公告 4 分享注单 5礼物消息 6 进入退出消息 7 中奖信息 8关注消息  9 禁言消息 10 房管消息 11 房间类型切换
        LiveMessageModel liveMessageModel = liveMessageModelArrayList.get(position);
        String objectName = liveMessageModel.getObjectName();
        if(objectName.equals(CommonStr.TEXT_MESSAGE)){
            return 1;
        }else if(objectName.equals(CommonStr.RED_MESSAGE)){
            return 2;
        }else if(objectName.equals(CommonStr.NOTICE_MESSAGE)){
            return 3;
        }else if(objectName.equals(CommonStr.SHARE_MESSAGE)){
            return 4;
        }else if(objectName.equals(CommonStr.GIFT_MESSAGE)){
            return 5;
        } else if(objectName.equals(CommonStr.EXIT_JOIN_MESSAGE)){
            return 6;
        }else if(objectName.equals(CommonStr.SHARE_ZJ_MESSAGE)){
            return 7;
        }else if(objectName.equals(CommonStr.FOLLOW_MESSAGE)){
            return 8;
        }else if(objectName.equals(CommonStr.FORBIDDEN_MESSAGE)){
            return 9;
        }
        else if(objectName.equals(CommonStr.ROOM_MANAGER_MESSAGE)){
            return 10;
        } else if(objectName.equals(CommonStr.CHANGE_ROOM_TYPE_MESSAGE)) {
            return 11;
        }else if(objectName.endsWith(CommonStr.LIVE_SYSTEM_MESSAGE)){
            return 13;
        } else if(objectName.endsWith(CommonStr.NORMAL_RED_MESSAGE)){
            return 14;
        } else if(objectName.endsWith(CommonStr.REWARD_ANCHOR)){
            return 15;
        }else {
            return 16;
        }

    }
    @Override
    public int getItemCount() {
        return liveMessageModelArrayList.size();
    }


    @Override
    public void onClickSpanLintener(View view, int position) {
            if(onClickSpanLintener!=null){
                onClickSpanLintener.onClickSpanLintener(view,position);
            }
    }

    @Override
    public void onClick(View v) {
        if(myOnItemClickLintener!=null){
            myOnItemClickLintener.onItemClickListener(v, (Integer) v.getTag());
        }
    }

    public class TextHolder extends RecyclerView.ViewHolder{
        TextView textNameContentTv;
        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textNameContentTv=itemView.findViewById(R.id.text_name_content_tv);
        }
    }
    public class NoticeHolder extends RecyclerView.ViewHolder{
        TextView noticeContenttv;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            noticeContenttv=itemView.findViewById(R.id.notice_name_content_tv);
        }
    }

    public class ShareHolder extends RecyclerView.ViewHolder{
        TextView shareContenttv;
        public ShareHolder(@NonNull View itemView) {
            super(itemView);
            shareContenttv=itemView.findViewById(R.id.share_name_content_tv);
        }
    }

    public class RedHolder extends RecyclerView.ViewHolder{
        TextView redContenttv;
        public RedHolder(@NonNull View itemView) {
            super(itemView);
            redContenttv=itemView.findViewById(R.id.red_name_content_tv);
        }
    }

    public class GiftHolder extends RecyclerView.ViewHolder{
        TextView giftContenttv;
        public GiftHolder(@NonNull View itemView) {
            super(itemView);
            giftContenttv=itemView.findViewById(R.id.gift_content_tv);
        }
    }

    public class ExitJoinHolder extends RecyclerView.ViewHolder{
        TextView exitJoinCotentTv;
        public ExitJoinHolder(@NonNull View itemView) {
            super(itemView);
            exitJoinCotentTv=itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public class ShareZjHolder extends RecyclerView.ViewHolder{
        TextView zjContentTv;
        public ShareZjHolder(@NonNull View itemView) {
            super(itemView);
            zjContentTv=itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public class FollowHolder extends RecyclerView.ViewHolder{
        TextView followContentTv;
        public FollowHolder(@NonNull View itemView) {
            super(itemView);
            followContentTv=itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public class ForbiddenHolder extends RecyclerView.ViewHolder{
        TextView forbiddenContentTv;
        public ForbiddenHolder(@NonNull View itemView) {
            super(itemView);
            forbiddenContentTv=itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public class RoomManagerHolder extends RecyclerView.ViewHolder{
        TextView roomManagerContentTv;
        public RoomManagerHolder(@NonNull View itemView) {
            super(itemView);
            roomManagerContentTv=itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public class ChangeRoomTypeHolder extends RecyclerView.ViewHolder{
        TextView changeContent_tv;
        public ChangeRoomTypeHolder(@NonNull View itemView) {
            super(itemView);
            changeContent_tv =itemView.findViewById(R.id.notice_name_content_tv);
        }
    }
    public class LiveSystemMessageHolder extends RecyclerView.ViewHolder{
        TextView contentTv;
        public LiveSystemMessageHolder(@NonNull View itemView) {
            super(itemView);
            contentTv =itemView.findViewById(R.id.notice_name_content_tv);
        }
    }

    public class NormalRedHolder extends RecyclerView.ViewHolder{
        TextView redContenttv;
        public NormalRedHolder(@NonNull View itemView) {
            super(itemView);
            redContenttv=itemView.findViewById(R.id.red_name_content_tv);
        }
    }
    public class RewardAnchorHolder extends RecyclerView.ViewHolder{
        TextView reward_content_tv;
        public RewardAnchorHolder(@NonNull View itemView) {
            super(itemView);
            reward_content_tv =itemView.findViewById(R.id.exit_join_content_tv);
        }
    }
    public interface  MyOnItemClickLintener {
        void onItemClickListener(View view, int position);

    }
    public MyOnItemClickLintener myOnItemClickLintener;

    public void setMyOnItemClickLintener(MyOnItemClickLintener myOnItemClickLintener) {
        this.myOnItemClickLintener = myOnItemClickLintener;
    }

    public interface OnClickSpanLintener{
        void onClickSpanLintener(View view, int position);
    }
    public OnClickSpanLintener onClickSpanLintener;

    public void setOnClickSpanLintener(OnClickSpanLintener onClickSpanLintener) {
        this.onClickSpanLintener = onClickSpanLintener;
    }
}
