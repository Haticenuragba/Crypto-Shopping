package com.crypto_shopping.cryptoshopping;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CartFragment extends Fragment {

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    RecyclerView mRecyclerView;
    long totalAmount = 0;
    TextView totalPriceText;
    Button proceedToCheckOutButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView=getActivity().findViewById(R.id.cartRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        totalPriceText = getActivity().findViewById(R.id.totalPrice);
        proceedToCheckOutButton = getActivity().findViewById(R.id.proceedToCheckOut);

        proceedToCheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("AMOUNT_TO_PAY",  totalAmount);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").addValueEventListener(new ValueEventListener() {



            public void onDataChange(DataSnapshot dataSnapshot) {
                totalAmount = 0;
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if(snapshot.child("amount").getValue()!=null && snapshot.child("price").getValue()!=null){

                            totalAmount += (long)snapshot.child("amount").getValue() * (long)snapshot.child("price").getValue() ;

                        }

                    }
                    totalPriceText.setText(Long.toString(totalAmount));
                }

                Log.v("DEDEDE",Long.toString(totalAmount));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


            FirebaseRecyclerAdapter<ProductInCart,CartViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProductInCart, CartViewHolder>(

                    ProductInCart.class,
                    R.layout.product_in_cart_row,
                    CartViewHolder.class,
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart")) {

                protected void populateViewHolder(CartViewHolder productViewHolder, final ProductInCart product, int position) {
                    productViewHolder.setDetails(getActivity().getApplicationContext(), product, true);

                }
            };



            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }




}



