package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ActivityProductDetailBinding;
import com.example.ecommerceapp.model.Cart;
import com.example.ecommerceapp.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        product = (Product)intent.getSerializableExtra("model");
        binding.productTitle.setText(product.getName());
        binding.productDescription.setText(product.getDescription());
        binding.productPrice.setText("PKR " +product.getPrice());

        Glide.with(binding.getRoot())
                .load(product.getImage())
                .into(binding.productImage);

        getSupportActionBar().setTitle(product.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    // for back arrow button when click this activity should finish
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart){
            startActivity(new Intent(this,CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToCart() {
//

        String id  = UUID.randomUUID().toString();
        Cart cart = new Cart(id,product.getName(),product.getImage(),product.getPrice(),1,product.getStock(), FirebaseAuth.getInstance().getUid(),null);
        FirebaseFirestore.getInstance()
                .collection("cart")
                .document(id)
                .set(cart);
        binding.addToCart.setEnabled(false);
        binding.addToCart.setText("Added to Cart");



    }

}