package com.example.red_pakege.rong_yun.chat_interface;

import android.view.View;

import io.rong.imkit.model.UIMessage;
import io.rong.imlib.model.MessageContent;

public interface OnMessageClickLintener {
    void onMessageClickLintener(View view, int i, MessageContent messageContent, UIMessage uiMessage);
}
