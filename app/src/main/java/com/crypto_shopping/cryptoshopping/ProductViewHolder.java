package com.crypto_shopping.cryptoshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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



    public void setDetails(Context mContext, Product product,  boolean isFavourite){
         isFav = isFavourite;
         productObject = product;
         productTitle = productView.findViewById(R.id.productTitle);
         productImage = productView.findViewById(R.id.productImage);
         productPrice = productView.findViewById(R.id.productPrice);
         favouritesImage = productView.findViewById(R.id.favouritesImage);


        if(isFavourite){
            Log.v("heyy", "heyy");
            favouritesImage.setImageResource(R.drawable.ic_heart_filled);
        }
        else{
            favouritesImage.setImageResource(R.drawable.ic_heart_empty);
        }

        productTitle.setText(product.getTitle() );
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
                    Log.v("aaaa", "girdi");
                    isFav = false;
                }
                else{
                    favouritesImage.setImageResource(R.drawable.ic_heart_filled);
                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users");
                    kRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Favourites").child(productObject.getProductID()).setValue(true);
                    isFav = true;

                }
            }
        });


    }


}
