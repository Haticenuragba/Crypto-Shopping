package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crypto_shopping.cryptoshopping.Objects.Orders;
import com.google.firebase.database.FirebaseDatabase;

public class OrderViewHolder extends RecyclerView.ViewHolder {


    FirebaseDatabase mFirebaseDatabase;
    TextView orderInfoView;
    TextView orderStatus;
    View orderView;



    public OrderViewHolder(View orderView) {

        super(orderView);

        this.orderView=orderView;


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        orderInfoView = orderView.findViewById(R.id.orderInfo);
        orderStatus = orderView.findViewById(R.id.orderStatus);


    }


    public void setDetails(final Context mContext, Orders order) {

        orderInfoView.setText(order.getOrderInfo());
        orderStatus.setText(order.getPaymentStatus());

    }

}