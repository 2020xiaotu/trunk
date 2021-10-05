package com.example.red_pakege.adapter.agent_center_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.red_pakege.R;
import com.example.red_pakege.model.ReportModel;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ReportModel> reportModelArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    private int TYPE_ONE=111;
    private int TYPE_TWO=222;
    public ReportAdapter(ArrayList<ReportModel> reportModelArrayList) {
        this.reportModelArrayList = reportModelArrayList;
    }

    @NonNull
    @Override
    public  RecyclerView.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view ;
        if (viewType == TYPE_FOOTER) {
//            VIEW_FOOTER.setOnClickListener(this);
                viewHolder=new ItemOneHolder(VIEW_FOOTER);
            return viewHolder;
        } else if (viewType == TYPE_HEADER) {
//            VIEW_HEADER.setOnClickListener(this);
            viewHolder=new ItemOneHolder(VIEW_HEADER);
            return viewHolder;
        }else{
            if(viewType==TYPE_ONE){
                view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.report_recycle_item_one,parent,false);
                viewHolder=new ItemOneHolder(view);
            }else {
                view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.report_recycle_item_two,parent,false);
                viewHolder=new ItemTwoHolder(view);
            }

            return viewHolder;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            int itemViewType = getItemViewType(position);
            if (haveHeaderView()) position--;
            ReportModel reportModel = reportModelArrayList.get(position);
            if(itemViewType==TYPE_ONE){
             ItemOneHolder itemOneHolder= (ItemOneHolder) holder;
             itemOneHolder.titleTv.setText(reportModel.getContent());
            }else if(itemViewType==TYPE_TWO){
                ItemTwoHolder itemTwoHolder = (ItemTwoHolder) holder;
                itemTwoHolder.reportAmounTv.setText(reportModel.getAmount());
                itemTwoHolder.reportContentTv.setText(reportModel.getContent());
                //TODO 图片的显示
            }

        }
    }

    @Override
    public int getItemCount() {
        int count = (reportModelArrayList == null ? 0 : reportModelArrayList.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            if(position%5==1){
                return TYPE_ONE;
            }else {
                return TYPE_TWO;
            }

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(isHeaderView(position)||isFooterView(position)||position%5==1){
                        return ((GridLayoutManager) layoutManager) .getSpanCount();
                    }else {
                        return 1;
                    }
         /*           return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;*/
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }
    public class ItemOneHolder extends  RecyclerView.ViewHolder{
        TextView titleTv;
        public ItemOneHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.report_title_tv);
        }
    }
    public class ItemTwoHolder extends RecyclerView.ViewHolder {
        ImageView reportIv;
        TextView reportAmounTv;
        TextView reportContentTv;
        public ItemTwoHolder(@NonNull View itemView) {
            super(itemView);
            reportIv=itemView.findViewById(R.id.report_item_imageview);
            reportAmounTv=itemView.findViewById(R.id.report_amount_tv);
            reportContentTv=itemView.findViewById(R.id.report_concont_tv);
        }
    }

}
