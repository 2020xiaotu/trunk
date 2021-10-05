package com.example.new_application.ui.activity.user_info_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dlong.rep.dlsidebar.DLSideBar;
import com.example.new_application.R;
import com.example.new_application.adapter.AreaCodeListAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.AreaPhoneBean;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.PinyinUtils;
import com.example.new_application.utils.ReadAssetsJsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

public class AreaSelectActivity extends BaseActivity {
    @BindView(R.id.lv_area)
    ListView lvArea;
    @BindView(R.id.sb_index)
    DLSideBar sbIndex;
    private AreaCodeListAdapter mAdapter;
    private ArrayList<AreaPhoneBean> mBeans = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_area_select;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"选择手机号归属地");
        initView();
        initData();
    }
    private void initView() {
        sbIndex = findViewById(R.id.sb_index);


        // 配置列表适配器
        lvArea.setVerticalScrollBarEnabled(false);
        lvArea.setFastScrollEnabled(false);
        mAdapter = new AreaCodeListAdapter(this, mBeans);
        lvArea.setAdapter(mAdapter);
        lvArea.setOnItemClickListener(mItemClickListener);
        // 配置右侧index
        sbIndex.setOnTouchingLetterChangedListener(mSBTouchListener);
        // 配置搜索
    }

    private void initData() {
        mBeans.clear();
        JSONArray array = ReadAssetsJsonUtil.getJSONArray(getResources().getString(R.string.area_select_json_name), this);
        if (null == array) array = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            AreaPhoneBean bean = new AreaPhoneBean();
            bean.name = array.optJSONObject(i).optString("zh");
            bean.fisrtSpell = PinyinUtils.getFirstSpell(bean.name.substring(0,1));
            bean.name_py = PinyinUtils.getPinYin(bean.name);
            bean.code = array.optJSONObject(i).optString("code");
            bean.locale = array.optJSONObject(i).optString("locale");
            bean.en_name = array.optJSONObject(i).optString("en");
            mBeans.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mBeans, new AreaPhoneBean.ComparatorPY());


        // 数据更新
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 选项点击事件
     */
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<AreaPhoneBean> bs = mAdapter.getList();
            AreaPhoneBean bean =bs.get(position);
            Intent data = new Intent();
            data.putExtra("string",  bean.code);
            setResult(RESULT_OK, data);
            finish();
        }
    };

    /**
     * 右侧index选项监听
     */
    private DLSideBar.OnTouchingLetterChangedListener mSBTouchListener = new DLSideBar.OnTouchingLetterChangedListener() {
        @Override
        public void onTouchingLetterChanged(String str) {
            //触摸字母列时,将品牌列表更新到首字母出现的位置
            if (mBeans.size()>0){
                for (int i=0;i<mBeans.size();i++){
                    if (mBeans.get(i).fisrtSpell.compareToIgnoreCase(str) == 0) {
                        lvArea.setSelection(i);
                        break;
                    }
                }
            }
        }
    };

}