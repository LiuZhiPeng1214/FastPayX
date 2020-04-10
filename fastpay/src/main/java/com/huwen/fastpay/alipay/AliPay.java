package com.huwen.fastpay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.huwen.fastpay.FastPayCallBack.FastPayCallBack;
import com.huwen.fastpay.base.FastPayMethod;
import com.socks.library.KLog;

import java.util.Map;

public class AliPay implements FastPayMethod<AliPayInfoImpli> {
    private Activity activity;
    private AliPayInfoImpli infoImpli;
    private static FastPayCallBack mCallBack;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @Override
    public void fastPay(Activity activity, AliPayInfoImpli fastPayInfo, FastPayCallBack callBack) {
        this.activity = activity;
        this.infoImpli = fastPayInfo;
        mCallBack = callBack;
        payV2();
    }
    //支付宝支付业务
    private void payV2() {
        Runnable payRunnalbe = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String,String> result = alipay.payV2(infoImpli.getOrderInfo(),true);
                KLog.i("msp",result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnalbe);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult(); //同步返回需要验证的消息
                    String resultStatus = payResult.getResultStatus();
                    KLog.e("resultInfo",resultInfo);
                    KLog.e("resultInfo",resultStatus);
                    //判断resultStatus 为9000代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (mCallBack != null) {
                            mCallBack.success();
                        }
                    }else {
                        //判断resultStatus 为8000代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        // 最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus,"8000")) {
                            mCallBack.loading();
                        }
                        //判断resultStatus 为6001代表取消支付
                        else if (TextUtils.equals(resultStatus,"6001")) {
                            if (mCallBack != null) {
                                mCallBack.cancel();
                            }
                        }else {
                            //其他值为支付失败
                            if (mCallBack != null) {
                                mCallBack.faild();
                            }
                        }
                    }
                    break;
            }
        }
    };
}
