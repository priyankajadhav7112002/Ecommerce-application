package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.ProductDetailActivity;
import com.example.ecommerceapp.databinding.ItemProductBinding;
import com.example.ecommerceapp.model.Product;

import java.security.AccessControlException;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context) {
        this.context = context;
        products = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Product product = products.get(position);
            holder.title.setText(product.getName());
           // holder.description.setText(product.getDescription());
            holder.price.setText("PKR " +product.getPrice());

            Glide.with(context).load(product.getImage()).into(holder.img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ProductDetailActivity.class);
                    intent.putExtra("model",product);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public void addProduct(Product product){
        products.add(product);
        notifyDataSetChanged();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ItemProductBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProductBinding.bind(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title,description,price;
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.label);
            //description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.image);
        }
    }
}
