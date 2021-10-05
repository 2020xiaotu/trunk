package com.example.red_pakege.rong_yun.plugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
/*
 将自定义的Plugin添加到Extension中,并在getPluginModules()中进行排序
  */
public class MyExtensionModule extends DefaultExtensionModule {

    /*
    添加自定义的Plugin
     */
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> iPluginModuleList = new ArrayList<>();
        //自定义的plugin的显示顺序由添加的顺序决定
        iPluginModuleList.add(new WelfarePlugin());
        iPluginModuleList.add(new JoinPlugin());
        iPluginModuleList.add(new RedPlugin());
        iPluginModuleList.add(new RechargePlugin());
        iPluginModuleList.add(new PlayPlugin());
        iPluginModuleList.add(new RulePlugin());
        iPluginModuleList.add(new HelpPlugin());
        iPluginModuleList.add(new CustomerPlugin());
        iPluginModuleList.add(new PhotoPlugin());
        iPluginModuleList.add(new CameraPlugin());
        iPluginModuleList.add(new MoneyPlugin());
        return iPluginModuleList;
    }



    /*
     如果在表情界面新增表情包,在此方法中添加
     */
    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
