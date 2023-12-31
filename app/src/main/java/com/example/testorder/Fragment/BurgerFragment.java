package com.example.testorder.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testorder.Model.Products;
import com.example.testorder.ProductDetailsActivity;
import com.example.testorder.R;
import com.example.testorder.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BurgerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BurgerFragment() {

    }

    public static BurgerFragment newInstance(String param1, String param2) {
        BurgerFragment fragment = new BurgerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String CategoryName = "burgers_rices_spaghetties";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_burger, container, false);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products1");
        recyclerView = v.findViewById(R.id.recycler_menu_burgers_rices_spaghetties);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("category").equalTo(CategoryName), Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.product_Name.setText(model.getPname());
                holder.product_Description.setText(model.getDescription());
                holder.product_Price.setText("Giá = " + model.getPrice() + "đ");
                Picasso.get().load(model.getImage()).into(holder.product_Image);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                        intent.putExtra("puid", model.getPuid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
