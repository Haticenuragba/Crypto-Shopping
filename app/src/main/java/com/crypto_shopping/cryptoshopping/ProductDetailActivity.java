package com.crypto_shopping.cryptoshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Button cartButton;

   FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    boolean isFavourite;
    int selectedAmount;


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
        cartButton = findViewById(R.id.addToCartButton);

        productTitle.setText(product.getTitle());
        Picasso.get().load(product.getImage()).into(productImage);
        productPrice.setText(Integer.toString(product.getPrice()));
        productDescription.setText(product.getDesc());

        Spinner dropdown = findViewById(R.id.amountSpinner);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);



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
                    Toast.makeText(getApplicationContext(), product.getTitle() + " removed from favourites", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), product.getTitle() + " added to favourites", Toast.LENGTH_SHORT).show();


                }
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedAmount = position + 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAmount = 1;
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference kRef = mFirebaseDatabase.getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Cart").child(product.getProductID());
                kRef.child("desc").setValue(product.getDesc());
                kRef.child("title").setValue(product.getTitle());
                kRef.child("price").setValue(product.getPrice());
                kRef.child("image").setValue(product.getImage());
                kRef.child("productID").setValue(product.getProductID());
                kRef.child("amount").setValue(selectedAmount);
                Toast.makeText(getApplicationContext(), product.getTitle() + " added to cart", Toast.LENGTH_SHORT).show();





            }
        });

    }


}
