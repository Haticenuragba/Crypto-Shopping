package com.crypto_shopping.cryptoshopping;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crypto_shopping.cryptoshopping.Objects.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProductsFragment extends Fragment {

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);

    }



    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView=getActivity().findViewById(R.id.productsRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Product");


    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(

                Product.class,
                R.layout.product_row,
                ProductViewHolder.class,
                mRef){


            protected void populateViewHolder ( ProductViewHolder productViewHolder, Product product, int position  ){


                    productViewHolder.setDetails(getActivity().getApplicationContext(), product.getTitle(), product.getPrice(), product.getImage());

              if(product.getProductID()==1){


                  DatabaseReference kRef = mFirebaseDatabase.getReference("Users");



                  kRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(("8")).setValue(true);


              }


            }

        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
