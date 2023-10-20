package com.example.testorder.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorder.Interface.ItemClickListener;
import com.example.testorder.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_Name_Cart, product_Quantity_Cart, product_Price_Cart;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        product_Name_Cart = (TextView)itemView.findViewById(R.id.product_Name_Cart);
        product_Price_Cart = (TextView)itemView.findViewById(R.id.product_Price_Cart);
        product_Quantity_Cart = (TextView)itemView.findViewById(R.id.product_Quantity_Cart);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
