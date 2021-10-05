package com.example.new_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ServerDetailsEntity.MemberInfoVoBean>followEntityArrayList=new ArrayList<>();
    public StoreHistoryAdapter(Context context, ArrayList<ServerDetailsEntity.MemberInfoVoBean> followEntityArrayList) {
        this.context = context;
        this.followEntityArrayList = followEntityArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.mine_history_store_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ServerDetailsEntity.MemberInfoVoBean classificationDetailsEntity = followEntityArrayList.get(position);

        GlideLoadViewUtil.LoadTitleView(context,classificationDetailsEntity.getImage(),myHolder.store_history_avatar_iv);
        myHolder.name_tv.setText(classificationDetailsEntity.getNickname());


        if(classificationDetailsEntity.isShow()){
            myHolder.  delete_selector_iv.setVisibility(View.VISIBLE);
        }else {
            myHolder. delete_selector_iv.setVisibility(View.GONE);
        }
        if(classificationDetailsEntity.isCheck()){
            myHolder. delete_selector_iv.setImageResource(R.drawable.history_check_s);
        }else {
            myHolder.  delete_selector_iv.setImageResource(R.drawable.history_check_n);
        }
        myHolder.history_wrap_constraintLayout.setTag(position);
        myHolder.delete_selector_iv.setTag(position);
        myHolder.delete_selector_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        myHolder.history_wrap_constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        initTopChildRecycler(myHolder.label_recycler,classificationDetailsEntity);
        initChildRecycler(myHolder.store_history_child_recycler,classificationDetailsEntity);
    }
    private void initChildRecycler(RecyclerView childRecycler, ServerDetailsEntity.MemberInfoVoBean classificationDetailsEntity) {
        String label = classificationDetailsEntity.getLabel();
        List<ClassificationDetailsChildEntity> data = new ArrayList<>();
        if(StringMyUtil.isNotEmpty(label)){
            List<String> stringList = Arrays.asList(label.split(","));
            for (int i = 0; i < stringList.size(); i++) {
                ClassificationDetailsChildEntity classificationDetailsChildEntity = new ClassificationDetailsChildEntity();
                classificationDetailsChildEntity.setName(stringList.get(i));
                data.add(classificationDetailsChildEntity);
            }
        }

        ClassificationDetailsChildAdapter classificationDetailsChildAdapter = new ClassificationDetailsChildAdapter(R.layout.classification_details_child_item, data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        childRecycler.setLayoutManager(linearLayoutManager);
        childRecycler.setAdapter(classificationDetailsChildAdapter);

    }
    private void initTopChildRecycler(RecyclerView childRecycler, ServerDetailsEntity.MemberInfoVoBean classificationDetailsEntity) {
        ArrayList<HomeLabelEntity>homeLabelEntityArrayList = new ArrayList<>();
        HonestyMerchantChildAdapter  honestyMerchantChildAdapter  = new HonestyMerchantChildAdapter(R.layout.classification_details_child_item,homeLabelEntityArrayList,true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        childRecycler.setLayoutManager(linearLayoutManager);
        childRecycler.setAdapter(honestyMerchantChildAdapter);
        String officialMark = classificationDetailsEntity.getOfficialMark();
        if(StringMyUtil.isNotEmpty(officialMark)){
            List<String> labelIdList = Arrays.asList(officialMark.split(","));
            List<HomeLabelEntity> homeLabelEntityList = Utils.getHomeLabelEntity();
            for (int j = 0; j < homeLabelEntityList.size(); j++) {
                HomeLabelEntity homeLabelEntity = homeLabelEntityList.get(j);
                for (int k = 0; k < labelIdList.size(); k++) {
                    String id = labelIdList.get(k);
                    if(homeLabelEntity.getId().equals(id)){
                        homeLabelEntityArrayList.add(homeLabelEntity);
                    }
                }
            }
            honestyMerchantChildAdapter.notifyDataSetChanged();
        }
    }

    public void removeDataByPosition(int position){
        if(position>=0&&position<followEntityArrayList.size()){
            followEntityArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,followEntityArrayList.size()-1);
        }

    }
    @Override
    public int getItemCount() {
        return followEntityArrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView store_history_avatar_iv;
        private TextView name_tv;
        private RecyclerView store_history_child_recycler;
        private ImageView delete_selector_iv;
        private ConstraintLayout history_wrap_constraintLayout;
        private RecyclerView label_recycler;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            store_history_avatar_iv=itemView.findViewById(R.id.store_history_avatar_iv);
            name_tv=itemView.findViewById(R.id.name_tv);
            store_history_child_recycler=itemView.findViewById(R.id.store_history_child_recycler);
            delete_selector_iv=itemView.findViewById(R.id.delete_selector_iv);
            history_wrap_constraintLayout=itemView.findViewById(R.id.history_wrap_constraintLayout);
            label_recycler=itemView.findViewById(R.id.label_recycler);
        }
    }
    public interface  OnItemClickListener{
        void  onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener =null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
