package com.huwen.fastpay.base;

import android.app.Activity;

import com.huwen.fastpay.FastPayCallBack.FastPayCallBack;

public interface FastPayMethod<T extends FastPayInfo> {
    void fastPay(Activity activity, T fastPayInfo, FastPayCallBack callBack);
}
