package com.example.new_application.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ContactAdapter;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.utils.RouteUtils;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;

public class ContactPop extends BasePopupWindow {
    RecyclerView contact_recycler;
    ContactAdapter contactAdapter;
    ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
    public ContactPop(Context context,ArrayList<ContactEntity>contactEntityArrayList) {
        super(context);
        this.contactEntityArrayList = contactEntityArrayList;
        setAnimationStyle(R.anim.down_to_up_in_150);
        initRecycler();
    }

    private void initRecycler() {
        contactAdapter = new ContactAdapter(R.layout.contact_item_layout,contactEntityArrayList);
        contact_recycler.setLayoutManager(new LinearLayoutManager(mContext));
        contact_recycler.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
        contactAdapter.addChildClickViewIds(R.id.contact_now_btn,R.id.copy_contact_btn);
        contactAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ContactEntity contactEntity = contactEntityArrayList.get(position);
                String value = contactEntity.getValue();
                String name = contactEntity.getName();
                switch (view.getId()){
                    case R.id.contact_now_btn:
                        if(name.equalsIgnoreCase("telegram")){
                            Utils.telegramShare(mContext,value );
                        }else if(name.equalsIgnoreCase("whatsapp")){
                            Utils.whatsAppShare(mContext,value);
                        }else if(name.equalsIgnoreCase("qq")){
                            Utils.qqShare(mContext,value);
                        }else if(name.equalsIgnoreCase("微信")){
                            Utils.weChatShare(mContext,value);
                        }else if(name.equalsIgnoreCase("skype")){
                            Utils.skypeShare(mContext,value);
                        }
                        //todo 蝙蝠 土豆跳转
                        break;
                    case R.id.copy_contact_btn:
                        if(name.equalsIgnoreCase("telegram")){
                            Utils.copyStr("copy_contact_btn", value.startsWith("@")?value:"@"+value);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.contact_pop_layout,null);
        contact_recycler = rootView.findViewById(R.id.contact_recycler);
    }
}
