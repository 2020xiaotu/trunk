package com.example.new_application.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuaranteeOrderAdapter extends BaseQuickAdapter<GuaranteeOrderEntity, BaseViewHolder> {
    public GuaranteeOrderAdapter(int layoutResId, @Nullable List<GuaranteeOrderEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GuaranteeOrderEntity guaranteeOrderEntity) {
        Button order_left_btn = baseViewHolder.getView(R.id.order_left_btn);
        Button order_right_btn = baseViewHolder.getView(R.id.order_right_btn);
        TextView order_status_tv = baseViewHolder.getView(R.id.order_status_tv);
        TextView order_person_tv = baseViewHolder.getView(R.id.order_person_tv);
        String status = guaranteeOrderEntity.getStatus();
        baseViewHolder.setText(R.id.order_id_tv,"编号: "+guaranteeOrderEntity.getOrderCode());
        baseViewHolder.setText(R.id.order_title_tv,guaranteeOrderEntity.getTitle());
        baseViewHolder.setText(R.id.order_content_tv,guaranteeOrderEntity.getContent());
        baseViewHolder.setText(R.id.order_price_tv,"¥"+guaranteeOrderEntity.getPrice());
        if(status.equals("0")){
            order_left_btn.setVisibility(View.VISIBLE);
            order_right_btn.setVisibility(View.VISIBLE);
            order_status_tv.setText("用户委托中");
            if(guaranteeOrderEntity.getUser_id().equals(Utils.getUserInfo().getId())){
                order_left_btn.setText("修改");
                order_right_btn.setText("取消担保");
            }else {
                order_left_btn.setText("取消担保");
                order_right_btn.setText("同意担保");
            }
        }else {
            order_left_btn.setVisibility(View.INVISIBLE);
            order_right_btn.setVisibility(View.INVISIBLE);
            if(status.equals("1")){
                order_status_tv.setText("平台审核中");
            }else if(status.equals("2")){
                order_status_tv.setText("平台担保中");
            }else if(status.equals("3")){
                order_status_tv.setText("平台拒绝担保");
            }else if(status.equals("4")){
                order_status_tv.setText("用户取消订单");
            }else if(status.equals("11")){
                order_status_tv.setText("担保成功");
            }else if(status.equals("12")){
                order_status_tv.setText("担保失败");
            }else {
                order_status_tv.setText("未知");
            }
        }
        String value = initUserName(guaranteeOrderEntity);
        order_person_tv.setText(value);
    }

    private String initUserName(GuaranteeOrderEntity guaranteeOrderEntity) {
        String value = "";
        if(guaranteeOrderEntity.getId().equals(Utils.getUserInfo().getId())){
            //我发起的担保
            if(guaranteeOrderEntity.getUserType().equals("1")){
                //我是买家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示卖家（对方）联系方式
                    value = guaranteeOrderEntity.getSellerLink();
                }else {
                    //站内，显示卖家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }else {
                 //我是卖家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示买家（对方）联系方式
                    value = guaranteeOrderEntity.getBuyerLink();
                }else {
                    //站内，显示买家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }
        }else {
            //对方发起的担保
            if(guaranteeOrderEntity.getUserType().equals("1")){
                //对方是买家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示买家（对方）联系方式
                    value = guaranteeOrderEntity.getBuyerLink();
                }else {
                    //站内，显示买家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }else {
                //对方是卖家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示卖家（对方）联系方式
                    value = guaranteeOrderEntity.getSellerLink();
                }else {
                    //站内，显示卖家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }
        }
        return value;
    }
}
