package com.crypto_shopping.cryptoshopping;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crypto_shopping.cryptoshopping.Objects.ProductInCart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.crypto_shopping.cryptoshopping.MainActivity.favourites;

public class FinishPaymentActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    long totalAmount;
    TextView totalPriceText;
    Button proceedToCheckOutButton;
    String fullAdress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_payment);
        mRecyclerView=findViewById(R.id.cartRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalPriceText = findViewById(R.id.totalPrice);
        proceedToCheckOutButton = findViewById(R.id.proceedToCheckOut);
        Intent intent = getIntent();
        totalAmount = intent.getLongExtra("AMOUNT_TO_PAY", 999000000);
        fullAdress = intent.getStringExtra("SHIPPING_ADDRESS");
        proceedToCheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("AMOUNT_TO_PAY",  totalAmount);
                intent.putExtra("SHIPPING_ADDRESS", fullAdress);
                startActivity(intent);
            }
        });

        totalPriceText.setText(Long.toString(totalAmount));
    }





    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ProductInCart,FinalCartViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProductInCart, FinalCartViewHolder>(

                ProductInCart.class,
                R.layout.finish_cart_row,
                FinalCartViewHolder.class,
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart")) {

            protected void populateViewHolder(FinalCartViewHolder productViewHolder, final ProductInCart product, int position) {
                productViewHolder.setDetails(getApplicationContext(), product, true);

            }
        };



        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }




}




