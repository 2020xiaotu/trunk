package com.example.red_pakege.fragment.game_group_fragment;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.red_game_activity.RedPakegeGameActivity;
import com.example.red_pakege.adapter.game_group_adapter.RedPakegeAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RedPakegeGameModel;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.RefreshUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import okhttp3.Headers;

public class RedPakegeGameFragment extends BaseFragment {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RedPakegeAdapter redPakegeAdapter;
    private ArrayList<RedPakegeGameModel>redPakegeGameModelArrayList = new ArrayList<>();

    private int id;
    private  ArrayList<String> gameNameList = new ArrayList<>();
    private  ArrayList<Integer> gameIdList = new ArrayList<>();
    private ArrayList<String>childTabImageList  = new ArrayList<>();

    private ConstraintLayout loadingLinear;
    private LinearLayout nodataLinear;
    private LinearLayout errorLinear;
    private TextView reloadTv;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    public static RedPakegeGameFragment newInstance (int positon,int id ) {
        RedPakegeGameFragment fragment = new RedPakegeGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_pakege_game, container, false);
        getArgumentData();
        bindView(view);
        initRefresh();
        intiRecycle();
        return view;
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestListData(false);
    }

    private void getArgumentData() {
        id=getArguments().getInt("id");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(redPakegeGameModelArrayList!=null){
            redPakegeGameModelArrayList.clear();
            redPakegeGameModelArrayList=null;
        }
    }


    private void intiRecycle() {
        redPakegeAdapter=new RedPakegeAdapter(redPakegeGameModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(redPakegeAdapter);
/*            for (int i = 0; i < 10; i++) {
                redPakegeGameModelArrayList.add(new RedPakegeGameModel("","红包游戏"+i));
            }*/
        requestListData(false);
        redPakegeAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.red_pakege_into_game_tv:
                        RedPakegeGameModel redPakegeGameModel = redPakegeGameModelArrayList.get(position);
                        RedPakegeGameActivity.startAty(getContext(),gameNameList,gameIdList,redPakegeGameModel.getChildId(),childTabImageList,id);
                        break;
                    default:
                }
            }
        });

    }

    private void requestListData(boolean isRefresh) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",id);
        HttpApiUtils.showLoadRequest(getContext(),this, RequestUtil.GAME_CLASSFY_LIST, data,loadingLinear,errorLinear,reloadTv, (View) refreshLayout,false,isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                redPakegeGameModelArrayList.clear();
                childTabImageList.clear();
                gameNameList.clear();
                gameIdList.clear();
                JSONObject data = JSONObject.parseObject(result).getJSONObject("data");
                JSONArray game = data.getJSONArray("game");//游戏分类 (红包 棋牌 电子 休闲.... tab展示的model)
                for (int i = 0; i < game.size(); i++) {
                    JSONObject gameJSONObject = game.getJSONObject(i);
                    Integer id = gameJSONObject.getInteger("id");//游戏大类id
                    String name = gameJSONObject.getString("name");
                    String image = gameJSONObject.getString("image");//大类图片
                    //TODO 设置tab图片
                }
                JSONArray sonGame = data.getJSONArray("sonGame");//子类列表(recycleView 展示的数据)
                int size = sonGame.size();
                RefreshUtil.succse(1,refreshLayout, loadingLinear,nodataLinear,size,false,isRefresh,redPakegeGameModelArrayList);
                for (int i = 0; i < size; i++) {
                    JSONObject sonJSONObject = sonGame.getJSONObject(i);
                    Integer id = sonJSONObject.getInteger("id");
                    String typeName = sonJSONObject.getString("typeName");
                    String image1 = sonJSONObject.getString("image1");//当前页面recycleView的item显示的图片
                    String image2 = sonJSONObject.getString("image2");//点击时需要传入的tab图片
                    childTabImageList.add(image2);
                    redPakegeGameModelArrayList.add(new RedPakegeGameModel(image1,image2,typeName,id));
                    gameNameList.add(typeName.replace("玩法",""));
                    gameIdList.add(id);
                }
                redPakegeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFaild(String msg) {
                RefreshUtil.fail(isRefresh,false,1,refreshLayout);
            }
        });
    }

    private void bindView(View view) {
        refreshLayout=view.findViewById(R.id.red_pakege_refresh);
        recyclerView=view.findViewById(R.id.red_pakege_recycle);
        loadingLinear=view.findViewById(R.id.loading_linear);
        nodataLinear=view.findViewById(R.id.nodata_linear);
        errorLinear=view.findViewById(R.id.error_linear);
        reloadTv=view.findViewById(R.id.reload_tv);

    }
    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtil.initRefreshLoadMore(new SoftReference<>(getContext()),refreshLayout, new RefreshUtil.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestListData(true);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });
    }
}
