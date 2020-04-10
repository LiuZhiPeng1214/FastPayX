package com.huwen.fastpay.alipay;

import com.huwen.fastpay.base.FastPayInfo;

/**
 *  包含支付宝的 支付类型和支付信息
 */
public class AliPayInfoImpli implements FastPayInfo {
    public String orderInfo;
    public String authInfo;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }
}
