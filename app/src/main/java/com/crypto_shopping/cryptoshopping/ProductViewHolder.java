package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductViewHolder extends RecyclerView.ViewHolder {


    View productView;

    public ProductViewHolder(View productView){

        super(productView);

        this.productView = productView;



    }



    public void setDetails(Context mContext, String title, int Price, String description, String image){

        TextView productTitle = productView.findViewById(R.id.productTitle);
        TextView productDescription = productView.findViewById(R.id.productDescription);
        ImageView productImage = productView.findViewById(R.id.productImage);
        TextView productPrice = productView.findViewById(R.id.productPrice);

        productTitle.setText(title);
        productDescription.setText(description);
        productPrice.setText(Integer.toString(Price)+"ADA");
        Picasso.get().load(image).into(productImage);










    }


}
