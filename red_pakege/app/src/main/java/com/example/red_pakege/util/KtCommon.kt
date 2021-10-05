package com.example.red_pakege.util

import android.widget.TextView

// 编写一个扩展方法
fun TextView.checkBlank(message: String): String? {
    val text = this.text.toString()
    if (text.isBlank()) {
        ToastUtils.showToast(message)
        return null
    }
    return text
}