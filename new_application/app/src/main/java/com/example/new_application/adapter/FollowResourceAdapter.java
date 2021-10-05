package com.example.new_application.adapter;

import android.content.Context;
import android.util.Log;
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

import com.example.new_application.R;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.FollowEntity;
import com.example.new_application.utils.SlideLayout;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

import cn.we.swipe.helper.WeSwipeHelper;

public class FollowResourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ClassificationDetailsEntity>followEntityArrayList=new ArrayList<>();
    public FollowResourceAdapter(Context context, ArrayList<ClassificationDetailsEntity> followEntityArrayList) {
        this.context = context;
        this.followEntityArrayList = followEntityArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.follow_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ClassificationDetailsEntity classificationDetailsEntity = followEntityArrayList.get(position);
        myHolder.follow_title_tv.setText(classificationDetailsEntity.getContent());
        myHolder.follow_share_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSlideListener!=null){
                    onSlideListener.onShareListener(position);
                }
            }
        });

        myHolder.follow_delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSlideListener!=null){
                    onSlideListener.onDeleteListener(position);
                }
            }
        });
        myHolder.show_constraintLayout.setTag(position);
        myHolder.show_constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        String priceType = classificationDetailsEntity.getPriceType();
        if(priceType.equals("3")){
           myHolder. price_tv.setText("可议价");
        }else {
            myHolder.  price_tv.setText("¥"+classificationDetailsEntity.getPrice());
        }
        String userType = classificationDetailsEntity.getUserType();
        if(userType.equals("1")){
            //需求
           myHolder. jingxuan_iv.setImageResource(R.drawable.x_icon);
        }else {
            myHolder. jingxuan_iv.setImageResource(R.drawable.g_icon);
        }
        myHolder. two_level_name_tv.setText("【 "+classificationDetailsEntity.getTwoLevelClassificationName()+" 】");
        String dateStr = Utils.getDatePoor2(new Date().getTime(), Long.parseLong(classificationDetailsEntity.getCreatedDate()));
        myHolder.date_tv.setText(dateStr);
        ViewGroup.LayoutParams layoutParams = myHolder.follow_share_tv.getLayoutParams();
        layoutParams.height = myHolder.slide_linear.getLayoutParams().height;
        myHolder.follow_share_tv.setLayoutParams(layoutParams);


        myHolder.follow_title_tv.setText(classificationDetailsEntity.getTitle());

        myHolder. follow_remark_tv.setText(classificationDetailsEntity.getContent());
        initChildRecycler(myHolder.follow_child_recycler,classificationDetailsEntity);
    }
    private void initChildRecycler(RecyclerView childRecycler, ClassificationDetailsEntity classificationDetailsEntity) {
        ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList = classificationDetailsEntity.getClassificationDetailsChildEntityArrayList();
        ClassificationDetailsChildAdapter classificationDetailsChildAdapter = new ClassificationDetailsChildAdapter(R.layout.classification_details_child_item, classificationDetailsChildEntityArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        childRecycler.setLayoutManager(linearLayoutManager);
        childRecycler.setAdapter(classificationDetailsChildAdapter);
        if(classificationDetailsChildEntityArrayList.size()==0){
            childRecycler.setVisibility(View.GONE);
        }else {
            childRecycler.setVisibility(View.VISIBLE);

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
        private TextView follow_title_tv;
        private TextView follow_remark_tv;
        private RecyclerView follow_child_recycler;
        private LinearLayout slide_linear;
        private TextView follow_share_tv;
        private TextView follow_delete_tv;
        private TextView price_tv;
        private TextView two_level_name_tv;
        private TextView date_tv;
        private ConstraintLayout show_constraintLayout;
        private SlideLayout follow_wrap_slideLayout;
        private ImageView jingxuan_iv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            follow_title_tv=itemView.findViewById(R.id.follow_title_tv);
            follow_remark_tv=itemView.findViewById(R.id.follow_remark_tv);
            follow_child_recycler=itemView.findViewById(R.id.follow_child_recycler);
            slide_linear=itemView.findViewById(R.id.slide_linear);
            follow_delete_tv=itemView.findViewById(R.id.follow_delete_tv);
            follow_share_tv=itemView.findViewById(R.id.follow_share_tv);
            price_tv=itemView.findViewById(R.id.price_tv);
            show_constraintLayout=itemView.findViewById(R.id.show_constraintLayout);
            follow_wrap_slideLayout=itemView.findViewById(R.id.follow_wrap_slideLayout);
            jingxuan_iv=itemView.findViewById(R.id.jingxuan_iv);
            two_level_name_tv=itemView.findViewById(R.id.two_level_name_tv);
            date_tv=itemView.findViewById(R.id.date_tv);
        }
    }
    public interface  OnItemClickListener{
        void  onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener =null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface onSlideListener {
        void  onDeleteListener(int position);
        void onShareListener(int position);
    }
    public onSlideListener onSlideListener =null;

    public void setOnSlideListener(onSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }
}
