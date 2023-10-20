package com.example.testorder.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorder.Interface.ItemClickListener;
import com.example.testorder.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView product_Name, product_Description, product_Price;
    public ImageView product_Image;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        product_Image = (ImageView) itemView.findViewById(R.id.product_Image);
        product_Name = (TextView) itemView.findViewById(R.id.product_Name);
        product_Description = (TextView) itemView.findViewById(R.id.product_Description);
        product_Price = (TextView) itemView.findViewById(R.id.product_Price);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
