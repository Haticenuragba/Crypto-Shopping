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
import com.crypto_shopping.cryptoshopping.Objects.ProductInCart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment {

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    RecyclerView mRecyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView=getActivity().findViewById(R.id.cartRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

    }

    @Override
    public void onStart() {
        super.onStart();

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







