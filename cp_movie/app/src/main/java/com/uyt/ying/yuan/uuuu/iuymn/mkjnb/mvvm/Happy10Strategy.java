package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.mvvm;

import com.uyt.ying.yuan.R;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live.GameTypeEnum;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.model.BetPopAllModel;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.model.Happy10Entity;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Happy10Strategy implements GameRuleStrategy {

    @Override
    public BetPopAllModel parseBetPopAllData(String str, GameTypeEnum typeEnum) {

        /**
         * 第一球  第二球  第八球
         */
        Gson gson = new GsonBuilder().serializeNulls().create();
        Happy10Entity happy10Entity = gson.fromJson(str,Happy10Entity.class);
        List<Happy10Entity.RulelistAllBean> rulelistAll = happy10Entity.getRulelistAll();

        BetPopAllModel betPopAllModel = new BetPopAllModel();
        List<BetPopAllModel.BetPopTabModel> betPopTabModelList = new ArrayList<>();
        List<BetPopAllModel.BetPopChildModel> betPopChildModelList = new ArrayList<>();

        /**
         * id 标志位置 通过indx 自+
         */
        int index = 0;

        for (int i=0;i<rulelistAll.size();i++) {
            //从isboth =1中筛选 第一球  第二球   第八球  取4个
            if (rulelistAll.get(i).getIsboth() == 1) {
                List<Happy10Entity.Childbean> childList =    gson.fromJson(rulelistAll.get(i).getRulelist(),
                        new TypeToken<List<Happy10Entity.Childbean>>() {}.getType());
                int cout =0;
                for (int j=0;j<childList.size();j++){
                    if (childList.get(j).getGroupname().equals(Utils.getString(R.string.diyiqiu))){
                        Happy10Entity.Childbean bean = childList.get(j);
                        BetPopAllModel.BetPopChildModel betPopChildModel = new BetPopAllModel.BetPopChildModel();
                        betPopChildModel.setId(index);
                        betPopChildModel.setParentId(1);
                        betPopChildModel.setRuleId(bean.getId());
                        betPopChildModel.setGroupname(Utils.getString(R.string.diyiqiu));
                        betPopChildModel.setName(bean.getName());
                        betPopChildModel.setOdds(bean.getOdds().setScale(3, BigDecimal.ROUND_HALF_UP));
                        betPopChildModel.setSelect(false);
                        betPopChildModelList.add(betPopChildModel);
                        index++;
                        cout++;
                        if (cout>=4){
                            break;
                        }
                    }
                }

                cout =0;
                for (int j=0;j<childList.size();j++){
                    if (childList.get(j).getGroupname().equals(Utils.getString(R.string.dierqiu))){
                        Happy10Entity.Childbean bean = childList.get(j);
                        BetPopAllModel.BetPopChildModel betPopChildModel = new BetPopAllModel.BetPopChildModel();
                        betPopChildModel.setId(index);
                        betPopChildModel.setParentId(2);
                        betPopChildModel.setRuleId(bean.getId());
                        betPopChildModel.setGroupname(Utils.getString(R.string.dierqiu));
                        betPopChildModel.setName(bean.getName());
                        betPopChildModel.setOdds(bean.getOdds().setScale(3, BigDecimal.ROUND_HALF_UP));
                        betPopChildModel.setSelect(false);
                        betPopChildModelList.add(betPopChildModel);
                        index++;
                        cout++;
                        if (cout>=4){
                            break;
                        }
                    }
                }

                cout =0;
                for (int j=0;j<childList.size();j++){
                    if (childList.get(j).getGroupname().equals(Utils.getString(R.string.disanqiu))){
                        Happy10Entity.Childbean bean = childList.get(j);
                        BetPopAllModel.BetPopChildModel betPopChildModel = new BetPopAllModel.BetPopChildModel();
                        betPopChildModel.setId(index);
                        betPopChildModel.setParentId(3);
                        betPopChildModel.setRuleId(bean.getId());
                        betPopChildModel.setGroupname(Utils.getString(R.string.disanqiu));
                        betPopChildModel.setName(bean.getName());
                        betPopChildModel.setOdds(bean.getOdds().setScale(3, BigDecimal.ROUND_HALF_UP));
                        betPopChildModel.setSelect(false);
                        betPopChildModelList.add(betPopChildModel);
                        index++;
                        cout++;
                        if (cout>=4){
                            break;
                        }
                    }
                }

            }
        }

        BetPopAllModel.BetPopTabModel betPopTabModel1 = new BetPopAllModel.BetPopTabModel();
        betPopTabModel1.setId(1);
        betPopTabModel1.setName(Utils.getString(R.string.diyiqiu));
        betPopTabModel1.setSelect(false);

        BetPopAllModel.BetPopTabModel betPopTabModel2 = new BetPopAllModel.BetPopTabModel();
        betPopTabModel2.setId(2);
        betPopTabModel2.setName(Utils.getString(R.string.dierqiu));
        betPopTabModel2.setSelect(false);

        BetPopAllModel.BetPopTabModel betPopTabModel3 = new BetPopAllModel.BetPopTabModel();
        betPopTabModel3.setId(3);
        betPopTabModel3.setName(Utils.getString(R.string.disanqiu));
        betPopTabModel3.setSelect(false);

        betPopTabModelList.add(betPopTabModel1);
        betPopTabModelList.add(betPopTabModel2);
        betPopTabModelList.add(betPopTabModel3);

        betPopAllModel.setBetPopTabModelList(betPopTabModelList);
        betPopAllModel.setBetPopChildModelList(betPopChildModelList);

        return betPopAllModel;
    }
}
