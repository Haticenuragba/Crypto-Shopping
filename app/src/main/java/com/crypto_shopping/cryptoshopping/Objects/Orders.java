package com.crypto_shopping.cryptoshopping.Objects;

public class Orders {
    private String orderInfo;
    private String transactionID;
    private boolean paymentStatus;

    public String getPaymentStatus() {

        if(paymentStatus){
            return "Payment is completed, your order is preparing";

        }
        else{
            return "Waiting for payment";
        }
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
