package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crypto_shopping.cryptoshopping.Objects.Product;
import com.crypto_shopping.cryptoshopping.Objects.ProductInCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FinalCartViewHolder extends RecyclerView.ViewHolder {


    View productView;
    FirebaseDatabase mFirebaseDatabase;
    ProductInCart productObject;
    boolean isFav;
    TextView productTitle;
    ImageView productImage;
    TextView productPrice;
    ImageView cancelImage;
    TextView amountText;



    public FinalCartViewHolder(View productView){

        super(productView);

        this.productView = productView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();



    }



    public void setDetails(final Context mContext, final ProductInCart product, final boolean isFavourite){
        isFav = isFavourite;
        productObject = product;

        productTitle = productView.findViewById(R.id.finalCartTitle);
        productImage = productView.findViewById(R.id.finalCartImage);
        productPrice = productView.findViewById(R.id.finalCartPrice);
        amountText = productView.findViewById(R.id.finalCartAmount);


        productTitle.setText(product.getTitle());
        productPrice.setText(Integer.toString(product.getPrice() * product.getAmount()) + " XRP");
        amountText.setText(Integer.toString(product.getAmount()));

        Picasso.get().load(product.getImage()).into(productImage);


    }


}
