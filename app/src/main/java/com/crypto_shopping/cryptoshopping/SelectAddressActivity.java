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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crypto_shopping.cryptoshopping.Objects.Address;
import com.crypto_shopping.cryptoshopping.Objects.Orders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;


public class SelectAddressActivity extends AppCompatActivity {


    RecyclerView addressRecyclerView;
    EditText titleEdit;
    EditText fullAddressEdit;
    Button addAddressButton;
    long totalAmount;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        titleEdit = findViewById(R.id.titleEdit);
        fullAddressEdit = findViewById(R.id.addressEdit);
        addAddressButton = findViewById(R.id.addAddress);

        Intent intent = getIntent();
        totalAmount = intent.getLongExtra("AMOUNT_TO_PAY", 999000000);



        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString();
                String address = fullAddressEdit.getText().toString();
                if (title.matches("") || address.matches("")){
                    Toast.makeText(getApplicationContext(), "Title and full adress should be filled.", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address")
                            .child(title).child("fullAddress").setValue(address);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address")
                            .child(title).child("title").setValue(title);
                }
            }
        });


        connectFirebase();


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }






    private void connectFirebase(){
        Log.v( "Girdii","Girdii");
        FirebaseRecyclerAdapter<Address, AddressViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Address, AddressViewHolder>(

                Address.class,
                R.layout.address_row,
                AddressViewHolder.class,
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address")){

            @Override
            protected void populateViewHolder(AddressViewHolder viewHolder, Address address, int position) {

                Log.v("sssss", address.getTitle());

                viewHolder.setDetails(getApplicationContext(), address, totalAmount);


            }






        };
        Log.v( "Girdii","Girdii2");

        addressRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

}



