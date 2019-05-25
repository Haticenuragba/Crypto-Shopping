package com.crypto_shopping.cryptoshopping;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crypto_shopping.cryptoshopping.Objects.Orders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;


public class AccountFragment extends Fragment {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    RecyclerView mRecyclerView;
    private String paymentStatus;
    private  String transactionID;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView=getActivity().findViewById(R.id.ordersRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    public void LoadData(String URL_DATA){

        StringRequest myRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonobject=new JSONObject(response).getJSONObject("data");
                    transactionID = jsonobject.getString("transaction_id");
                    Boolean isCompleted = jsonobject.getBoolean("is_completed");
                    Log.v("COMPP",Boolean.toString(isCompleted));


                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("paymentStatus").setValue(isCompleted);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("ERROR",error.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(myRequest);



    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Orders, OrderViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(

                Orders.class,
                R.layout.order_row,
                OrderViewHolder.class,
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders")){

            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Orders order, int position) {
                String URL_API="https://bitvolo.com/rest/?transaction_id="+order.getTransactionID()+"&reference_number=&hash=&api_key=51e3987f2fad88f177a92782190ff0a30c3cc5bfcfd15ae7333a069cb73c01&method=get_transaction_info";
                LoadData(URL_API);
                viewHolder.setDetails(getActivity().getApplicationContext(), order);



            }






        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

}



