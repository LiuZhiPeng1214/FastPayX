package com.huwen.fastpay.FastPayCallBack;

public interface FastPayCallBack {
    void success();
    void loading();
    void faild();
    void cancel();
}
