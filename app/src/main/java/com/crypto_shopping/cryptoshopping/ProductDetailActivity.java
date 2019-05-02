package com.crypto_shopping.cryptoshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.crypto_shopping.cryptoshopping.Objects.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    Intent intentThatStartedThisActivity;
    Product product;
    TextView productTitle;
    ImageView productImage;
    TextView productPrice;
    TextView productDescription;
    ImageView favouritesImage;

   FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    boolean isFavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        intentThatStartedThisActivity = getIntent();

        product = (Product) intentThatStartedThisActivity.getSerializableExtra("PRODUCT");

        isFavourite = intentThatStartedThisActivity.getBooleanExtra("IS_FAVOURITE", true);

        initializeViews();

    }

    private void initializeViews(){
        productTitle = findViewById(R.id.productDetailTitle);
        productImage = findViewById(R.id.productDetailImage);
        productPrice = findViewById(R.id.productDetailPrice);
        productDescription = findViewById(R.id.productDescription);
        favouritesImage = findViewById(R.id.favouritesDetailImage);

        productTitle.setText(product.getTitle());
        Picasso.get().load(product.getImage()).into(productImage);
        productPrice.setText(Integer.toString(product.getPrice()));
        productDescription.setText(product.getDesc());



        if(isFavourite){

            favouritesImage.setImageResource(R.drawable.ic_heart_filled);
        }
        else{
            favouritesImage.setImageResource(R.drawable.ic_heart_empty);
        }

        favouritesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavourite){
                    favouritesImage.setImageResource(R.drawable.ic_heart_empty);
                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users");
                    kRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Favourites").child(product.getProductID()).removeValue();
                    isFavourite = false;
                }
                else{
                    favouritesImage.setImageResource(R.drawable.ic_heart_filled);
                    DatabaseReference kRef = mFirebaseDatabase.getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Favourites").child(product.getProductID());
                    kRef.child("desc").setValue(product.getDesc());
                    kRef.child("title").setValue(product.getTitle());
                    kRef.child("price").setValue(product.getPrice());
                    kRef.child("image").setValue(product.getImage());
                    kRef.child("productID").setValue(product.getProductID());
                    isFavourite = true;

                }
            }
        });
    }
}
