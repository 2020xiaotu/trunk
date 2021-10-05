package com.example.new_application.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HonestyMerchantAdapter extends BaseQuickAdapter<ServerDetailsEntity.MemberInfoVoBean, BaseViewHolder> {

    public HonestyMerchantAdapter(int layoutResId, @Nullable List<ServerDetailsEntity.MemberInfoVoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServerDetailsEntity.MemberInfoVoBean honestyMerchantEntity) {
        ImageView merchant_iv = baseViewHolder.getView(R.id.merchant_iv);
        ImageView is_honesty_iv = baseViewHolder.getView(R.id.merchant_iv);
        ImageView free_consult_iv = baseViewHolder.getView(R.id.merchant_iv);
        TextView merchant_name_tv = baseViewHolder.getView(R.id.merchant_name_tv);
        TextView good_at_tv = baseViewHolder.getView(R.id.good_at_tv);
        String label = honestyMerchantEntity.getLabel();
        good_at_tv.setText(label);
        RecyclerView honesty_label_recycler = baseViewHolder.getView(R.id.honesty_label_recycler);
        GlideLoadViewUtil.LoadCornersView(getContext(),honestyMerchantEntity.getImage(),5,R.drawable.mr_cxsj,merchant_iv);
        merchant_name_tv.setText(honestyMerchantEntity.getNickname());
        initChildRecycler(honesty_label_recycler,honestyMerchantEntity);
    }

    private void initChildRecycler(RecyclerView honesty_label_recycler, ServerDetailsEntity.MemberInfoVoBean honestyMerchantEntity) {
        ArrayList<HomeLabelEntity> childList = new ArrayList<>();
        String officialMark = honestyMerchantEntity.getOfficialMark();
        if(StringMyUtil.isNotEmpty(officialMark)){
            String[] split = officialMark.split(",");
            List<String> labelIdList = Arrays.asList(split);
            List<HomeLabelEntity> homeLabelEntityList = Utils.getHomeLabelEntity();
            for (int j = 0; j < homeLabelEntityList.size(); j++) {
                HomeLabelEntity homeLabelEntity = homeLabelEntityList.get(j);
                for (int k = 0; k < labelIdList.size(); k++) {
                    String id = labelIdList.get(k);
                    if(homeLabelEntity.getId().equals(id)){
                        childList.add(homeLabelEntity);
                    }
                }
            }
        }

        HonestyMerchantChildAdapter honestyMerchantChildAdapter = new HonestyMerchantChildAdapter(R.layout.honesty_child_item,childList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        honesty_label_recycler.setLayoutManager(linearLayoutManager);
        honesty_label_recycler.setAdapter(honestyMerchantChildAdapter);
    }
}
