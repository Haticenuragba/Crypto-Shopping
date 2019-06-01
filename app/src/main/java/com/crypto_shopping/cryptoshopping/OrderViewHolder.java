package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.content.Intent;
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


    public void setDetails(final Context mContext, final Orders order) {

        orderInfoView.setText(order.getTotalAmount()+" XRP");
        orderStatus.setText(order.getPaymentStatus());

        orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productDetailIntent = new Intent(mContext, OrderInfo.class);
                productDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                productDetailIntent.putExtra("ORDER", order);
                mContext.startActivity(productDetailIntent);
            }
        });

    }

}