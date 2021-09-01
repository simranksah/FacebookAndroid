package com.example.myapplication.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.App;
import com.example.myapplication.activity.adapter.CartAdapter;
import com.example.myapplication.activity.interface1.Api;
import com.example.myapplication.activity.pojo.BaseResponse;
import com.example.myapplication.activity.pojo.CartItem;
import com.example.myapplication.activity.pojo.ProductsItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {

    private List<ProductsItem> cartList=new ArrayList();
    private Call<BaseResponse<CartItem>> call;
    private String address;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private Retrofit retrofit;
   private  CartItem cartItem;
    private TextView productName, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //Initialising bottom navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);


        //Set HOME selected
        bottomNavigationView.setSelectedItemId(R.id.cart);

        //perform Item SELECTION LISTENER
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                        SharedPreferences sharedPreferences=getSharedPreferences("user_details",MODE_PRIVATE);
                        Boolean value=sharedPreferences.getBoolean("login",false);
                        if(value==false) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        else
                        {
                            startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        }

                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.cart:

                        return true;
                }



                return false;
            }
        });


    // initRecyclerView();
     //   initRetrofitAndCallApi();

    //   apiCallback();




    } //onCreateEnds

    private void initRecyclerView() {


        cartAdapter = new CartAdapter(cartList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CartActivity.this, 1);

        cartRecyclerView=findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cartRecyclerView.setAdapter(cartAdapter);

    }

   public  void initRetrofitAndCallApi()
   {
       retrofit= App.getApp().getRetrofit();
       Api api=retrofit.create(Api.class);
       call=api.getCartItems();
   }

   public  void apiCallback()
   {


       call.enqueue(new Callback<BaseResponse<CartItem>>() {
           @Override
           public void onResponse(Call<BaseResponse<CartItem>> call, Response<BaseResponse<CartItem>> response) {

               if(response.isSuccessful())
               {
                   cartItem=response.body().getData();
                   cartList.clear();
                   cartList.addAll(cartItem.getProducts());
                  cartAdapter.notifyDataSetChanged();
               }


           }

           @Override
           public void onFailure(Call<BaseResponse<CartItem>> call, Throwable t) {
               Log.e("Check", t.getMessage());
           }
       });


   }

}
