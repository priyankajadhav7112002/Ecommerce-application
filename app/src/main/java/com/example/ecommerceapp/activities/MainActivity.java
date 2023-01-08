package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;


import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.GridLayoutManager;


import android.os.Bundle;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.ProductAdapter;
import com.example.ecommerceapp.databinding.ActivityMainBinding;
import com.example.ecommerceapp.model.Category;
import com.example.ecommerceapp.model.Product;
import com.example.ecommerceapp.utils.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProducts();
        initSlider();

    }


    private void initSlider() {
        getRecentOffers();
    }

    void initCategories(){
        categories = new ArrayList<>();

        getCategories();

        categoryAdapter = new CategoryAdapter(this,categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray categoriesArray = mainObj.getJSONArray("categories");
                        for(int i = 0;i < categoriesArray.length();i++){
                            JSONObject object = categoriesArray.getJSONObject(i);
                            Category category = new Category(
                                    object.getString("name"),
                                    Constant.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }else {
                        // Do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    void getRecentProducts(){
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        String url = Constant.GET_PRODUCTS_URL + "?count=8";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject object = null;
//                try {
//                    object = new JSONObject(response);
//                    if(object.getString("status").equals("success")){
//                       JSONArray productsArray = object.getJSONArray("products");
//                       for(int i = 0;i < productsArray.length();i++){
//                           JSONObject childObj = productsArray.getJSONObject(i);
//                           Product product = new Product(
//                                  childObj.getString("name"),
//                                  Constant.PRODUCTS_IMAGE_URL + childObj.getString("image"),
//                                  childObj.getString("status"),
//                                  childObj.getDouble("price"),
//                                  childObj.getDouble("price_discount"),
//                                  childObj.getInt("stock"),
//                                  childObj.getInt("id")
//                           );
//                           products.add(product);
//                       }
//                       productAdapter.notifyDataSetChanged();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(stringRequest);

        FirebaseFirestore.getInstance()
                .collection("products")
                .whereEqualTo("show",true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : dsList) {
                                Product product = ds.toObject(Product.class);
                                productAdapter.addProduct(product);
                            }


                    }
                });
    }

    void getRecentOffers(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET,Constant.GET_OFFERS_URL,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for(int i = 0;i < offerArray.length();i++){
                        JSONObject childObj = offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constant.NEWS_IMAGE_URL + childObj.getString("image"),
                                        childObj.getString("title")
                                )
                        );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        });
        queue.add(request);
    }

    void initProducts(){
        products = new ArrayList<>();

        productAdapter = new ProductAdapter(this);

        getRecentProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }
}