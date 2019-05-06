package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crypto_shopping.cryptoshopping.Objects.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends RecyclerView.ViewHolder {


    View productView;
    FirebaseDatabase mFirebaseDatabase;
    Product productObject;
    boolean isFav;
    TextView productTitle;
    ImageView productImage;
    TextView productPrice;
    ImageView favouritesImage;

    public ProductViewHolder(View productView){

        super(productView);

        this.productView = productView;

        mFirebaseDatabase = FirebaseDatabase.getInstance();



    }



    public void setDetails(final Context mContext, final Product product, final boolean isFavourite){


        isFav = isFavourite;
        productObject = product;
        productTitle = productView.findViewById(R.id.productTitle);
        productImage = productView.findViewById(R.id.productImage);
        productPrice = productView.findViewById(R.id.productPrice);
        favouritesImage = productView.findViewById(R.id.favouritesImage);



        if(isFavourite){
            favouritesImage.setImageResource(R.drawable.ic_heart_filled);
        }
        else{
            favouritesImage.setImageResource(R.drawable.ic_heart_empty);
        }

        productTitle.setText(product.getTitle());
        productPrice.setText(Integer.toString(product.getPrice())+" ADA");
        Picasso.get().load(product.getImage()).into(productImage);


        favouritesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav){
                    favouritesImage.setImageResource(R.drawable.ic_heart_empty);
                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users");
                    kRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Favourites").child(productObject.getProductID()).removeValue();
                    isFav = false;
                    Toast.makeText(mContext, product.getTitle() + " removed from favourites", Toast.LENGTH_SHORT).show();
                }
                else{
                    favouritesImage.setImageResource(R.drawable.ic_heart_filled);
                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Favourites").child(productObject.getProductID());
                    kRef.child("desc").setValue(product.getDesc());
                    kRef.child("title").setValue(product.getTitle());
                    kRef.child("price").setValue(product.getPrice());
                    kRef.child("image").setValue(product.getImage());
                    kRef.child("productID").setValue(product.getProductID());
                    isFav = true;
                    Toast.makeText(mContext, product.getTitle() + " added to favourites", Toast.LENGTH_SHORT).show();


                }
            }
        });



        productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productDetailIntent = new Intent(mContext, ProductDetailActivity.class);
                productDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                productDetailIntent.putExtra("PRODUCT", product);
                productDetailIntent.putExtra("IS_FAVOURITE", isFav);
                mContext.startActivity(productDetailIntent);
            }
        });
    }


}