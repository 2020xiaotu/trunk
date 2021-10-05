package com.example.red_pakege.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.add_activity.CreateGroupActivity;
import com.example.red_pakege.activity.add_activity.HbAgentCenterActivity;

public class AddPopUtil {
    private static LinearLayout agentCenterLinear;
    private static LinearLayout newPersonLinear;
    private static LinearLayout playRuleLinear;
    private static LinearLayout createGroupLinear;
    private static PopupWindow addPop;

    public  static PopupWindow initAddPop(Context context){
        View popView = LayoutInflater.from(context).inflate(R.layout.add_popupwindow,null);
        agentCenterLinear=popView.findViewById(R.id.add_agent_center_linear);
        newPersonLinear=popView.findViewById(R.id.add_new_person_linear);
        playRuleLinear=popView.findViewById(R.id.add_play_rule_linear);
        createGroupLinear=popView.findViewById(R.id.add_create_group_linear);
        addPop =new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        addPop.setAnimationStyle(R.style.popAlphaanim0_3);
        addPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground((Activity)context,1f);
            }
        });
        return addPop;
    }
    public static void showMenuPop(Context context, View targetView){
        if(addPop!=null){
            addPop.showAsDropDown(targetView, 15,-15);
            Utils.darkenBackground((Activity) context,0.7f);
        }
    }
    public static void clickAgentLinear(Context context){
        if(agentCenterLinear!=null){
            agentCenterLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,HbAgentCenterActivity.class));
                    addPop.dismiss();
                }
            });
        }
    }
    public static void clickCreateGroupLinear(Context context){
        if(createGroupLinear!=null){
            createGroupLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,CreateGroupActivity.class));
                    addPop.dismiss();
                }
            });
        }
    }
    public static void clickPlayRuleLinear(Context context){
        if(playRuleLinear!=null){
            playRuleLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPop.dismiss();
                }
            });
        }
    }
    public static void clickNewPersonLinear(Context context){
        if(newPersonLinear!=null){
            newPersonLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPop.dismiss();
                }
            });
        }
    }
}
