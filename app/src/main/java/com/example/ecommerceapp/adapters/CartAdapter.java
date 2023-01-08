package com.example.ecommerceapp.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.CartActivity;
import com.example.ecommerceapp.databinding.ActivityCartBinding;
import com.example.ecommerceapp.databinding.ItemCartBinding;
import com.example.ecommerceapp.databinding.QuantityDialogBinding;
import com.example.ecommerceapp.model.Cart;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    Context context;
    ArrayList<Cart> products;
    ViewGroup parent;

    int mainTotal = 0;

    public CartAdapter(Context context, ArrayList<Cart> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart product = products.get(position);
        Glide.with(context)
                .load(product.getProductImage())
                .into(holder.binding.cartImage);

        holder.binding.cartName.setText(product.getProductName());
        holder.binding.cartPrice.setText("PKR "+product.getProductPrice());
        holder.binding.cartQuantity.setText(product.getProductQty()+" item(s)");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                quantityDialogBinding.productName.setText(product.getProductName());
                quantityDialogBinding.productStock.setText(String.valueOf(product.getProductStock()));
                quantityDialogBinding.quantity.setText(String.valueOf(product.getProductQty()));
                int stock = product.getProductStock();

                quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = product.getProductQty();
                        quantity++;

                        if(quantity > product.getProductStock()){
                            Toast.makeText(context,"Max stock available: "+product.getProductStock(),Toast.LENGTH_SHORT).show();
                        }else{
                            product.setProductQty(quantity);
                            quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                        }

                    }
                });

                quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = product.getProductQty();
                        if(quantity > 1){
                            quantity--;
                            product.setProductQty(quantity);
                            quantityDialogBinding.quantity.setText(String.valueOf(quantity));

                        }

                    }
                });

                quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }

                });


                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProduct(Cart product){
        products.add(product);
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
