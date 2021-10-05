package com.example.new_application.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.AllClassificationParentAdapter;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.AllOneLevelEntity;
import com.example.new_application.bean.AllTwoLevelEntity;
import com.example.new_application.bean.ClassificationEntity;
import com.example.new_application.bean.ClassificationParentEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerHallActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllClassificationPop extends BasePopupWindow {
    RecyclerView all_classification_pop_recycler;
    Fragment fragment;
    AllClassificationParentAdapter allClassificationParentAdapter;
    ArrayList<AllOneLevelEntity> allOneLevelEntityArrayList = new ArrayList<>();
    private List<ClassificationParentEntity> classificationParentEntityList;
    ServerHallActivity serverHallActivity;
    public AllClassificationPop(Context context, Fragment fragment) {
        super(context);
        this.fragment = fragment;
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        Activity activity = (Activity) mContext;
        if(activity instanceof ServerHallActivity){
            serverHallActivity = (ServerHallActivity) activity;
        }
        initRecycler();
        bindView();
        requestServerList();
    }
    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.all_classification_pop_layout,null);
    }
    private void bindView() {
        rootView.findViewById(R.id.dismiss_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllClassificationPop.this.dismiss();
            }
        });
        rootView.findViewById(R.id.reset_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < allOneLevelEntityArrayList.size(); i++) {
                    ArrayList<AllTwoLevelEntity> childList = allOneLevelEntityArrayList.get(i).getChildList();
                    for (int j = 0; j < childList.size(); j++) {
                        AllTwoLevelEntity allTwoLevelEntity = childList.get(j);
                        allTwoLevelEntity.setCheck(false);
                        if(j==0){
                            allTwoLevelEntity.setCheck(true);
                        }
                    }
                }
                allClassificationParentAdapter.notifyDataSetChanged();
            }
        });
        rootView.findViewById(R.id.sure_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTwoLevelEntity checkOneLevelChildEntity =null;
                AllTwoLevelEntity checkTwoLevelChildEntity =null;
                AllTwoLevelEntity checkDealTypeChildEntity =null;
                AllClassificationPop.this.dismiss();
                if(allOneLevelEntityArrayList.size()>=3){
                    AllOneLevelEntity oneLevelParentEntity = allOneLevelEntityArrayList.get(0);
                    AllOneLevelEntity twoLevelParentEntity = allOneLevelEntityArrayList.get(1);
                    AllOneLevelEntity dealTypeEntity = allOneLevelEntityArrayList.get(2);
                    for (int i = 0; i < oneLevelParentEntity.getChildList().size(); i++) {
                       AllTwoLevelEntity  allTwoLevelEntity = oneLevelParentEntity.getChildList().get(i);
                        if(allTwoLevelEntity.isCheck() == true){
                            checkOneLevelChildEntity =allTwoLevelEntity;
                            break;
                        }
                    }

                    for (int i = 0; i < twoLevelParentEntity.getChildList().size(); i++) {
                        AllTwoLevelEntity  allTwoLevelEntity = twoLevelParentEntity.getChildList().get(i);
                        if(allTwoLevelEntity.isCheck() == true){
                            checkTwoLevelChildEntity =allTwoLevelEntity;
                            break;
                        }
                    }
                    for (int i = 0; i < dealTypeEntity.getChildList().size(); i++) {
                        AllTwoLevelEntity allTwoLevelEntity = dealTypeEntity.getChildList().get(i);
                        if(allTwoLevelEntity.isCheck() == true){
                            checkDealTypeChildEntity =allTwoLevelEntity;
                            break;
                        }
                    }
                }
                if(checkOneLevelChildEntity!=null){
                    serverHallActivity.setOneLevelClassification(checkOneLevelChildEntity.getId());
                }

                if(checkTwoLevelChildEntity!=null){
                    serverHallActivity.setTwoLevelClassification(checkTwoLevelChildEntity.getId());
                }
                if(checkDealTypeChildEntity!=null){
                    serverHallActivity.setGuaranteeType(checkDealTypeChildEntity.getGuaranteeType());
                }
                 serverHallActivity.postEvenBus();
                dismiss();
            }
        });
    }

    private void initRecycler() {
        all_classification_pop_recycler = rootView.findViewById(R.id.all_classification_pop_recycler);
        allClassificationParentAdapter = new AllClassificationParentAdapter(R.layout.all_classification_parent_item, allOneLevelEntityArrayList);
        all_classification_pop_recycler.setLayoutManager(new LinearLayoutManager(mContext));
        all_classification_pop_recycler.setAdapter(allClassificationParentAdapter);
        allClassificationParentAdapter.addChildClickViewIds(R.id.all_classification_parent_linear);
        allClassificationParentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                /**
                 * 点击收起/展开
                 */
                AllOneLevelEntity allOneLevelEntity = allOneLevelEntityArrayList.get(position);
                allOneLevelEntity.setOpen(!allOneLevelEntity.isOpen());
                allClassificationParentAdapter.notifyDataSetChanged();
            }
        });
        allClassificationParentAdapter.setOnItemClickListener(new AllClassificationParentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int parentPosition, int childPosition) {
                AllOneLevelEntity allOneLevelEntity = allOneLevelEntityArrayList.get(parentPosition);
                List<AllTwoLevelEntity> childList = allOneLevelEntity.getChildList();
                AllTwoLevelEntity allTwoLevelEntity = childList.get(childPosition);

                if(parentPosition==0){
                    AllOneLevelEntity twoLevelParentEntity = allOneLevelEntityArrayList.get(1);
                    ArrayList<AllTwoLevelEntity>oneLevelChildList = new ArrayList<>();
                    AllTwoLevelEntity custumItem = new AllTwoLevelEntity("","全部");
                    custumItem.setCheck(true);
                    oneLevelChildList.add(custumItem);
                    /**
                     * 点击一级分类中childRecycler的item , 筛选对应的二级分类
                     */
                    if(StringMyUtil.isEmptyString(allTwoLevelEntity.getId())&&allOneLevelEntityArrayList.size()>=2){
                        /**
                         * 为空时即选中全部安妮,二级类显示全部
                         */
                        for (int i = 0; i < classificationParentEntityList.size(); i++) {
                            /**
                             * 添加所有一级分类列表数据(childRecyclerView数据)
                             */
                            ClassificationParentEntity classificationParentEntity = classificationParentEntityList.get(i);
                            List<ClassificationParentEntity.ChildListBean> childListBeanList = classificationParentEntity.getChildList();
                            for (int j = 0; j < childListBeanList.size(); j++) {
                                ClassificationParentEntity.ChildListBean childListBean = childListBeanList.get(j);
                                String id = childListBean.getId();
                                String name = childListBean.getName();
                                AllTwoLevelEntity childEntity = new AllTwoLevelEntity(id, name);
                                oneLevelChildList.add(childEntity);
                            }
                        }
                        twoLevelParentEntity.setChildList(oneLevelChildList);
                        allOneLevelEntityArrayList.set(1,twoLevelParentEntity);

                    }else {
                        /**
                         * 不为空, 筛选点击的对应二级分类
                         */
                        for (int i = 0; i < classificationParentEntityList.size(); i++) {
                            ClassificationParentEntity classificationParentEntity = classificationParentEntityList.get(i);
                            if(classificationParentEntity.getId().equals(allTwoLevelEntity.getId())){
                                List<ClassificationParentEntity.ChildListBean> childListBeanList = classificationParentEntity.getChildList();
                                for (int j = 0; j < childListBeanList.size(); j++) {
                                    ClassificationParentEntity.ChildListBean childListBean = childListBeanList.get(j);
                                    String id = childListBean.getId();
                                    String name = childListBean.getName();
                                    AllTwoLevelEntity childEntity = new AllTwoLevelEntity(id, name);
                                    oneLevelChildList.add(childEntity);
                                }
                            }

                        }
                        twoLevelParentEntity.setChildList(oneLevelChildList);
                        allOneLevelEntityArrayList.set(1,twoLevelParentEntity);
                    }

                }else {
                    /**
                     * 点击二级分类或交易模式的item
                     */


                }
                allClassificationParentAdapter.notifyDataSetChanged();
            }
        });



    }
    private void requestServerList() {
        HttpApiUtils.wwwNormalRequest((Activity)mContext, fragment, RequestUtils.ALL_CLASSIFICATION, new HashMap<String, Object>(),  new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                /**
                 * 添加第一条parentRecyclerView数据(一级分类)
                 */
                AllOneLevelEntity oneLevelParentEntity = new AllOneLevelEntity("一级分类");
                ArrayList<AllTwoLevelEntity>oneLevelChildList = new ArrayList<>();
                /**
                 * 添加全部按钮
                 */
                AllTwoLevelEntity parentEntity = new AllTwoLevelEntity("","全部");
                parentEntity.setCheck(true);
                oneLevelChildList.add(parentEntity);
                classificationParentEntityList = JSONArray.parseArray(result, ClassificationParentEntity.class);
                for (int i = 0; i < classificationParentEntityList.size(); i++) {
                    /**
                     * 添加所有一级分类列表数据(childRecyclerView数据)
                     */
                    ClassificationParentEntity classificationParentEntity = classificationParentEntityList.get(i);
                    String name = classificationParentEntity.getName();
                    String id = classificationParentEntity.getId();

                    AllTwoLevelEntity allTwoLevelEntity = new AllTwoLevelEntity(id, name);
                    oneLevelChildList.add(allTwoLevelEntity);
                }
                oneLevelParentEntity.setChildList(oneLevelChildList);
                allOneLevelEntityArrayList.add(oneLevelParentEntity);

                /**
                 * 添加第二条parentRecyclerView数据(二级分类)
                 */
                AllOneLevelEntity twoLevelParentEntity = new AllOneLevelEntity("二级分类");
                ArrayList<AllTwoLevelEntity>twoLevelChildList = new ArrayList<>();

                AllTwoLevelEntity allTwoLevelEntity = new AllTwoLevelEntity("","全部");
                allTwoLevelEntity.setCheck(true);
                twoLevelChildList.add(parentEntity);
                for (int i = 0; i < classificationParentEntityList.size(); i++) {
                    /**
                     * 添加所有二级分类数据
                     */
                    ClassificationParentEntity classificationParentEntity = classificationParentEntityList.get(i);
                    List<ClassificationParentEntity.ChildListBean> childList = classificationParentEntity.getChildList();
                    for (int j = 0; j < childList.size(); j++) {
                        ClassificationParentEntity.ChildListBean childListBean = childList.get(j);
                        String id = childListBean.getId();
                        String name = childListBean.getName();
                        AllTwoLevelEntity childEntity = new AllTwoLevelEntity(id, name);
                        twoLevelChildList.add(childEntity);
                    }
                }
                twoLevelParentEntity.setChildList(twoLevelChildList);
                allOneLevelEntityArrayList.add(twoLevelParentEntity);
                /**
                 * 添加交易模式
                 */
                AllOneLevelEntity dealTypeParentEntity = new AllOneLevelEntity("交易模式");
                ArrayList<AllTwoLevelEntity>dealTypeChildList = new ArrayList<>();

                //交易模式(0：自行交易,1：担保交易)
                AllTwoLevelEntity allDealTypeEntity = new AllTwoLevelEntity("", "全部");
                allDealTypeEntity.setGuaranteeType(3);//ServerHallFragment 请求时, 为3不传交易模式
                allDealTypeEntity.setCheck(true);
                dealTypeChildList.add(allDealTypeEntity);
                AllTwoLevelEntity zixingEntity = new AllTwoLevelEntity("", "自行交易");
                zixingEntity.setGuaranteeType(0);
                dealTypeChildList.add(zixingEntity);
                AllTwoLevelEntity danbaoEntiy = new AllTwoLevelEntity("", "担保交易");
                danbaoEntiy.setGuaranteeType(1);
                dealTypeChildList.add(danbaoEntiy);
                dealTypeParentEntity.setChildList(dealTypeChildList);
                allOneLevelEntityArrayList.add(dealTypeParentEntity);

                allClassificationParentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

}
