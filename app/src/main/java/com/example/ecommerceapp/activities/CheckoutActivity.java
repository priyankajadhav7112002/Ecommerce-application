package com.example.ecommerceapp.activities;

import static com.example.ecommerceapp.model.Cart.totalPrice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.databinding.ActivityCheckoutBinding;
import com.example.ecommerceapp.model.Cart;
import com.example.ecommerceapp.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class CheckoutActivity extends AppCompatActivity  {

    ActivityCheckoutBinding binding;
    ArrayList<Cart> cart_products;
    CartAdapter adapter;
    private String name,phoneNo,address,cityName,postalCode;
    final int tax = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        cart_products = (ArrayList<Cart>) getIntent().getSerializableExtra("List");


        binding.checkoutSubtotal.setText(String.format("PKR %.2f",totalPrice));
        totalPrice = (totalPrice * tax / 100) + totalPrice;
        binding.total.setText(String.format("PKR "+totalPrice));
        adapter = new CartAdapter(this,cart_products);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.checkoutCartList.setLayoutManager(layoutManager);
        binding.checkoutCartList.addItemDecoration(itemDecoration);
        binding.checkoutCartList.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.nameBox.getText().toString();
                address = binding.addressBox.getText().toString();
                cityName = binding.cityName.getText().toString();
                phoneNo = binding.phoneBox.getText().toString();
                postalCode = binding.postalCode.getText().toString();
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        String orderNumber = String.valueOf(getRandomNumber(100000,999999));
        Order order = new Order(orderNumber,name,phoneNo,cityName,address,String.valueOf(totalPrice),"220",null,"Tcs",String.valueOf(Calendar.getInstance().getTimeInMillis()),"Pending");

        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(orderNumber)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        for (int i = 0; i < cart_products.size(); i++) {
                            Cart cart = cart_products.get(i);
                            cart.setOrderNumber(orderNumber);

                            String id = UUID.randomUUID().toString();
                            cart.setCartId(id);

                            FirebaseFirestore.getInstance()
                                    .collection("orderProducts")
                                    .document(id)
                                    .set(cart);
                            new AlertDialog.Builder(CheckoutActivity.this)
                                    .setTitle("Order Successful")
                                    .setCancelable(false)
                                    .setMessage("Your order number is : "+orderNumber)
                                    .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(CheckoutActivity.this,PaymentActivity.class);
                                            intent.putExtra("orderCode",orderNumber);
                                            startActivity(intent);
                                        }
                                    }).show();
                        }
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        new AlertDialog.Builder(CheckoutActivity.this)
                                .setTitle("Order Failed")
                                .setCancelable(false)
                                .setMessage("Something went wrong, please try again.")
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                });

    }

    private static int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}