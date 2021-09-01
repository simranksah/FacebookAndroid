package com.example.myapplication.activity.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.R;
import com.example.myapplication.activity.pojo.ProductsItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    List<ProductsItem> cartList;

    public CartAdapter(List<ProductsItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, int position) {


        final ProductsItem productsItem=cartList.get(position);
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions( new RequestOptions().placeholder(R.drawable.ic_launcher_background)).load(productsItem.getImage()).into(holder.imageView);
        holder.name.setText(productsItem.getName());
        holder.price.setText(productsItem.getPrice());
        holder.quantity.setNumber(String.valueOf(productsItem.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name;
        public  TextView price;
        public ElegantNumberButton quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cart_image_view);

            name=itemView.findViewById(R.id.cart_product_name);
            price=itemView.findViewById(R.id.cart_product_price);
            quantity=itemView.findViewById(R.id.cart_product_quantity);
        }
    }
}
