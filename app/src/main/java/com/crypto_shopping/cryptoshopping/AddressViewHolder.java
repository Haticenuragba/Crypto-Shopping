package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crypto_shopping.cryptoshopping.Objects.Address;
import com.crypto_shopping.cryptoshopping.Objects.Orders;
import com.google.firebase.database.FirebaseDatabase;

public class AddressViewHolder extends RecyclerView.ViewHolder {


    FirebaseDatabase mFirebaseDatabase;
    TextView addressTitle;
    TextView fullAddress;
    View addressView;
    Context context;



    public AddressViewHolder(View addressView) {

        super(addressView);

        this.addressView = addressView;


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        addressTitle = addressView.findViewById(R.id.addressTitle);
        fullAddress = addressView.findViewById(R.id.fullAddress);



    }


    public void setDetails(final Context mContext, final Address address, final long totalAmount) {

        addressTitle.setText(address.getTitle());
        fullAddress.setText(address.getFullAddress());
        this.context= mContext;

        addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FinishPaymentActivity.class);
                intent.putExtra("AMOUNT_TO_PAY",  totalAmount);
                intent.putExtra("SHIPPING_ADDRESS", address.getFullAddress());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


}