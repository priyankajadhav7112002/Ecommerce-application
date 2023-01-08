package com.example.ecommerceapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ItemCategoriesBinding;
import com.example.ecommerceapp.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context,ArrayList<Category> categories){
        this.categories = categories;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories,parent,false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.label.setText(category.getName());

        // whenever we want to load image from internet then we use glide. We can use picaso also
        Glide.with(context).load(category.getIcon()).into(holder.binding.image);

        // we want to give color in integer format , but in category we had taken
        // it as string because we get colors from sever in this format "#ff0000" .
        // So we will use Color.parseColor() which is a class which coverts string colors to integer
//        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));
//        holder.binding.image.getResources().getDrawable(category.getIcon());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ItemCategoriesBinding binding ;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoriesBinding.bind(itemView);
        }
    }
}
