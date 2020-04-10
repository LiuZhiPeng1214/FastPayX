package com.huwen.fastpay;

import android.app.Activity;

import com.huwen.fastpay.FastPayCallBack.FastPayCallBack;
import com.huwen.fastpay.base.FastPayInfo;
import com.huwen.fastpay.base.FastPayMethod;

public class FastPay {
    public static <T extends FastPayInfo> void pay(FastPayMethod<T> payMethod, Activity mActivity, T payInfo, FastPayCallBack callBack) {
        payMethod.fastPay(mActivity,payInfo,callBack);
    }
}
