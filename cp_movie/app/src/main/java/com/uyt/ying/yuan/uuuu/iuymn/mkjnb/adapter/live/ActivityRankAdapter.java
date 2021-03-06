package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.adapter.live;

import androidx.annotation.Nullable;

import com.uyt.ying.yuan.R;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.net.entity.ActivityRankEntity;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.net.entity.RongcloudHBParameter;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.GlideLoadViewUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ActivityRankAdapter extends BaseQuickAdapter<ActivityRankEntity.DataBean, BaseViewHolder> {

    public ActivityRankAdapter(int layoutResId, @Nullable List<ActivityRankEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityRankEntity.DataBean item) {
        GlideLoadViewUtil.LoadNormalView(getContext(), item.getImage(), helper.getView(R.id.iv_item_activityrank));
        // 1趣约;2天降;3专享红包
        int activityType = item.getActivityType();
        RongcloudHBParameter content = item.getContent();
        RongcloudHBParameter.RongcloudHBParameterBean data = content.getRongcloudHBParameter();
        String msg = "";
        String msg2 = "";
        String msg3 = "";
        if (activityType == 1) {
            msg="参与人数: 10000+";
            msg2="中奖几率: 100%";
//            msg3="红包金额: "+ data.getQuYueHBTotalAmount();
            msg3="红包金额: 888+";
        } else if (activityType == 2) {
            msg="VIP"+data.getTjHBGrade()+"可抢玩家红包";
            msg2="VIP"+data.getTjHBPlatGrade()+"可抢官方红包";
            msg3="";
            helper.setGone(R.id.tv_item_activityrank_msg3,false);
        } else if (activityType == 3) {
            msg="千万专享礼包真情回馈";
            msg2="限制玩法VIP"+data.getZxHBGrade()+"以上专享";
            msg3="";
            helper.setGone(R.id.tv_item_activityrank_msg3,false);
        }
        helper.setText(R.id.tv_item_activityrank_msg, msg);
        helper.setText(R.id.tv_item_activityrank_msg2, msg2);
        helper.setText(R.id.tv_item_activityrank_msg3, msg3);
        helper.setImageResource(R.id.iv_item_activityrank, item.getImageResId());


    }

}
