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

public class CartViewHolder extends RecyclerView.ViewHolder {


    View productView;
    FirebaseDatabase mFirebaseDatabase;
    ProductInCart productObject;
    boolean isFav;
    TextView productTitle;
    ImageView productImage;
    TextView productPrice;
    ImageView cancelImage;
    Spinner amountSpinner;
    int selectedAmount;


    public CartViewHolder(View productView){

        super(productView);

        this.productView = productView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();



    }



    public void setDetails(final Context mContext, final ProductInCart product, final boolean isFavourite){
        isFav = isFavourite;
        productObject = product;

        productTitle = productView.findViewById(R.id.cartTitle);
        productImage = productView.findViewById(R.id.cartImage);
        productPrice = productView.findViewById(R.id.cartPrice);
        cancelImage = productView.findViewById(R.id.cancelImage);
        amountSpinner = productView.findViewById(R.id.cartAmountSpinner);


        productTitle.setText(product.getTitle());

        Picasso.get().load(product.getImage()).into(productImage);

        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, items);
        amountSpinner.setAdapter(adapter);
        amountSpinner.setSelection(product.getAmount() - 1);


        amountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedAmount = position + 1;
                product.setAmount(selectedAmount);
                productPrice.setText(Integer.toString(product.getPrice()*product.getAmount())+" ADA");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAmount = product.getAmount();
                productPrice.setText(Integer.toString(product.getPrice()*product.getAmount())+" ADA");
            }
        });








        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users");
                    kRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Cart").child(productObject.getProductID()).removeValue();

            }
        });





        productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productDetailIntent = new Intent(mContext, ProductDetailActivity.class);
                productDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                productDetailIntent.putExtra("PRODUCT", product);
                productDetailIntent.putExtra("IS_FAVOURITE", isFavourite);
                mContext.startActivity(productDetailIntent);
            }
        });
    }


}
