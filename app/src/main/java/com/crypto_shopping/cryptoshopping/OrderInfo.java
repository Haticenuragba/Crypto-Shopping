package com.crypto_shopping.cryptoshopping;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.crypto_shopping.cryptoshopping.Objects.Orders;
import com.crypto_shopping.cryptoshopping.Objects.Product;
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

public class OrderInfo extends AppCompatActivity {

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
    private TextView xrpToSend;
    private TextView detailedOrderInfo;
    private TextView date;
    private TextView paymentStatus;
    private Button copy;

    String fullAddress;
    Intent intentThatStartedThisActivity;
    Orders order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        initiliazeViews();

        intentThatStartedThisActivity = getIntent();

        order = (Orders) intentThatStartedThisActivity.getSerializableExtra("ORDER");

        Log.v("ORDERRR",order.getDepositAddress());

        totalAmount = order.getTotalAmount();
        depositAddress.setText(order.getDepositAddress());
        depositTag.setText(order.getDepositTag());
        xrpToSend.setText(Long.toString(totalAmount)+  " XRP");
        fullAddress = order.getFullAddress();
        detailedOrderInfo.setText(order.getOrderInfo());
        date.setText(order.getDate());
        paymentStatus.setText(order.getPaymentStatus());




    }





    public void initiliazeViews(){
        detailedOrderInfo = findViewById(R.id.detailedorderInfo);
        date = findViewById(R.id.date);
        paymentStatus = findViewById(R.id.paymentStatus);


        depositAddress = findViewById(R.id.depositAddress);
        depositTag = findViewById(R.id.depositTag);
        copy1 = findViewById(R.id.copy1);
        copy2 = findViewById(R.id.copy2);
        copy = findViewById(R.id.copy);
        completePayment = findViewById(R.id.completePayment);
        xrpToSend = findViewById(R.id.xrpToSend);

        copy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("address", depositAddress.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Deposit address is copied to clipboard.", Toast.LENGTH_SHORT).show();

            }
        });

        copy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("tag", depositTag.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Deposit tag is copied to clipboard.", Toast.LENGTH_SHORT).show();

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("price", xrpToSend.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "XRP amount is copied to clipboard.", Toast.LENGTH_SHORT).show();

            }
        });

        completePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                onBackPressed();





            }
        });



    }

    public void returnHomePageFromPayment(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
