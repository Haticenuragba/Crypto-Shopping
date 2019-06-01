package com.crypto_shopping.cryptoshopping;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crypto_shopping.cryptoshopping.Objects.ProductInCart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.crypto_shopping.cryptoshopping.MainActivity.favourites;

public class FinishPaymentActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    long totalAmount;
    TextView totalPriceText;
    Button proceedToCheckOutButton;
    String fullAddress;
    private String orderInfo;
    private String URL_API;
    private String depositAddress;
    private String depositTag;
    private String transactionID;





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
        fullAddress = intent.getStringExtra("SHIPPING_ADDRESS");

        URL_API="https://bitvolo.com/rest/?reference_number="+FirebaseAuth.getInstance().getCurrentUser().getUid()+
                "&coin=XRP&requested_currency=XRP&requested_amount="+ totalAmount+
                "&ipn_url=&custom_field=&api_key=51e3987f2fad88f177a92782190ff0a30c3cc5bfcfd15ae7333a069cb73c01&method=create_transaction";

        LoadData(URL_API);

        totalPriceText.setText(Long.toString(totalAmount) + " XRP");

        proceedToCheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {



                    public void onDataChange(DataSnapshot dataSnapshot) {
                        orderInfo = "";
                        if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                if(snapshot.child("amount").getValue()!=null && snapshot.child("price").getValue() != null){

                                    orderInfo += snapshot.child("amount").getValue() +" X "+ snapshot.child("title").getValue() +"\n";

                                }

                            }
                        }

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("orderInfo").setValue(orderInfo);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("transactionID").setValue(transactionID);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("fullAddress").setValue(fullAddress);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("depositAddress").setValue(depositAddress);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("depositTag").setValue(depositTag);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(transactionID).child("totalAmount").setValue(totalAmount);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").removeValue();

                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        intent.putExtra("AMOUNT_TO_PAY",  totalAmount);
                        intent.putExtra("SHIPPING_ADDRESS", fullAddress);
                        intent.putExtra("DEPOSIT_ADDRESS", depositAddress);
                        intent.putExtra("DEPOSIT_TAG", depositTag);
                        totalPriceText.setText("0");
                        startActivity(intent);



                        Toast.makeText(getApplicationContext(), "Your order added", Toast.LENGTH_SHORT).show();

                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });





            }
        });


    }




    public void LoadData(String URL_DATA){
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Redirecting...");
        progressDialog.show();
        StringRequest myRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonobject=new JSONObject(response).getJSONObject("data");

                    depositAddress = jsonobject.getString("destination_address");
                    depositTag = jsonobject.getString("destination_tag");
                    transactionID = jsonobject.getString("transaction_id");
                    progressDialog.dismiss();

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);



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



    public void returnHomePageFromFinishPayment(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
