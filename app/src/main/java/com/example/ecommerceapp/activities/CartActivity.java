package com.example.ecommerceapp.activities;

import static com.example.ecommerceapp.model.Cart.totalPrice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.databinding.ActivityCartBinding;
import com.example.ecommerceapp.model.Cart;
import com.example.ecommerceapp.model.Product;import java.io.Serializable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hishd.tinycart.util.TinyCartHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<Cart> cart_products;
    int mainTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getCartItems();
        cart_products = new ArrayList<>();

        adapter = new CartAdapter(this, cart_products);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);


        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(new Intent(CartActivity.this,CheckoutActivity.class));
                intent.putExtra("List",cart_products);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getCartItems() {
        FirebaseFirestore.getInstance()
                .collection("cart")
                .whereEqualTo("sellerUid", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dsList) {
                            Cart cart = ds.toObject(Cart.class);
                            int price = Integer.parseInt(cart.getProductPrice());
                            int qty = cart.getProductQty();
                            int total = price* qty;
                            mainTotal += total;
                            totalPrice = mainTotal;
                            adapter.addProduct(cart);
                        }
                        updateText(totalPrice);

                    }
                });
    }

    public void updateText(double totalPrice){
        binding.textView2.setText(String.format("PkR %.2f",totalPrice));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}