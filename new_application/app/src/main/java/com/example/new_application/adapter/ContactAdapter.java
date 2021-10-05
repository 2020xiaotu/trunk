package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ContactEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContactAdapter extends BaseQuickAdapter<ContactEntity, BaseViewHolder> {
    public ContactAdapter(int layoutResId, @Nullable List<ContactEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ContactEntity contactEntity) {
        baseViewHolder.setText(R.id.contact_name_tv,contactEntity.getName()+": ");
        baseViewHolder.setText(R.id.contact_value_tv,contactEntity.getValue());

    }
}
