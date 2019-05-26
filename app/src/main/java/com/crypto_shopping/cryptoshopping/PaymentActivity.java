package com.crypto_shopping.cryptoshopping;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PaymentActivity extends AppCompatActivity {

    private TextView depositAddress;
    private TextView depositTag;
    private Button copy1;
    private Button copy2;
    private Button completePayment;
    private String URL_API;
    private Intent intent;
    private long totalAmount;
    private String transactionID;
    private String orderInfo;

    String fullAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initiliazeViews();
        intent = getIntent();
        totalAmount = intent.getLongExtra("AMOUNT_TO_PAY", 999000000);
        URL_API="https://bitvolo.com/rest/?reference_number="+FirebaseAuth.getInstance().getCurrentUser().getUid()+
        "&coin=XRP&requested_currency=XRP&requested_amount="+ totalAmount+
                "&ipn_url=&custom_field=&api_key=51e3987f2fad88f177a92782190ff0a30c3cc5bfcfd15ae7333a069cb73c01&method=create_transaction";

        LoadData(URL_API);


        fullAddress = intent.getStringExtra("SHIPPING_ADDRESS");



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

                    depositAddress.setText(jsonobject.getString("destination_address"));
                    depositTag.setText(jsonobject.getString("destination_tag"));
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



    public void initiliazeViews(){

    depositAddress = findViewById(R.id.depositAddress);
    depositTag = findViewById(R.id.depositTag);
    copy1 = findViewById(R.id.copy1);
    copy2 = findViewById(R.id.copy2);
    completePayment = findViewById(R.id.completePayment);

    copy1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("address", depositAddress.getText());
            clipboard.setPrimaryClip(clip);
        }
    });

        copy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("tag", depositTag.getText());
                clipboard.setPrimaryClip(clip);
            }
        });

        completePayment.setOnClickListener(new View.OnClickListener() {
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
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").removeValue();
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
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
}
