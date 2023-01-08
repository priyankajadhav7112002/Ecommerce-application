package com.example.ecommerceapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.databinding.ActivityAdminPanelBinding;
import com.example.ecommerceapp.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Admin_Panel extends AppCompatActivity {

    ActivityAdminPanelBinding binding;
    private String id,title,description;
    private String price;
    private boolean show;
    private int stock;
    private Uri uri ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.AdminTitle.getText().toString();
                description = binding.AdminDescription.getText().toString();
                price = binding.AdminPrice.getText().toString();
                stock = Integer.parseInt(binding.AdminStock.getText().toString());
                addProduct();
            }
        });
        binding.AdminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
        binding.uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    private void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("products/"+id+".png");

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseFirestore.getInstance()
                                .collection("products")
                                .document(id)
                                .update("image",uri.toString());
                        Toast.makeText(Admin_Panel.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void addProduct(){
        id = UUID.randomUUID().toString();
        Product product = new Product(title,null,description,price,id,stock, true);
        FirebaseFirestore.getInstance()
                .collection("products")
                .document(id)
                .set(product);
        Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            uri = data.getData();

            binding.AdminImage.setImageURI(uri);
        }
    }
}