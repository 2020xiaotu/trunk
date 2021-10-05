package com.example.red_pakege.adapter.mine_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.R;

public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    String[] titles;
    int icons[];
    LayoutInflater mLayoutInflater;

    public GridViewAdapter(Context mContext,String[]titles,int[]icons) {
        this.mContext = mContext;
        this.titles= titles;
        this.icons = icons;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            //把控件所在的布局文件加载到当前类中
            convertView = mLayoutInflater.inflate(R.layout.item_gridvew,null);


            //生成一个ViewHolder的对象
            holder = new ViewHolder();
            //获取控件对象
            holder.Grid_imageview=convertView.findViewById(R.id.iv_item_gridview);
            holder.Grid_textview=convertView.findViewById(R.id.tv_item_gridview);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //修改空间属性
        holder.Grid_textview.setText(titles[position]);
        holder.Grid_imageview.setImageResource(icons[position]);
        return convertView;

    }
    static class ViewHolder{
        public ImageView Grid_imageview;
        public TextView Grid_textview;
    }


}
