package com.zz.live.ui.beauty;

/**
 * Http状态异常[自定义抛出的异常]
 */
public class HttpStatusException extends Exception {
    public HttpStatusException(String detailMessage) {
        super(detailMessage);
    }
}
