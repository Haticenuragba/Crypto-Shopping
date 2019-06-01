package com.crypto_shopping.cryptoshopping.Objects;

import java.io.Serializable;

public class Orders implements Serializable {
    private String orderInfo;
    private String transactionID;
    private int paymentStatus;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String depositAddress;
    private String depositTag;

    public String getDepositAddress() {
        return depositAddress;
    }

    public void setDepositAddress(String depositAddress) {
        this.depositAddress = depositAddress;
    }

    public String getDepositTag() {
        return depositTag;
    }

    public void setDepositTag(String depositTag) {
        this.depositTag = depositTag;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    private String fullAddress;
    private long totalAmount;



    public String getPaymentStatus() {

        if(paymentStatus == 1){
            return "Waiting for payment";

        }
        else if(paymentStatus == 2){
            return "Payment is completed, your order is preparing";
        }
        else{
            return "Your order is sent by cargo";
        }
    }

    public void setPaymentStatus(int paymentStatus) {
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
