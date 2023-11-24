package com.example.testorder.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testorder.CartActivity;
import com.example.testorder.ConfirmFinalOrderActivity;
import com.example.testorder.HomeActivity;
import com.example.testorder.Model.Cart;
import com.example.testorder.Prevalent.Prevalent;
import com.example.testorder.ProductDetailsActivity;
import com.example.testorder.R;
import com.example.testorder.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GioHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GioHangFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GioHangFragment() {

    }

    public static GioHangFragment newInstance(String param1, String param2) {
        GioHangFragment fragment = new GioHangFragment();
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

    private RecyclerView cart_list;
    private RecyclerView.LayoutManager layoutManager;
    private Button confirm_button;
    private TextView txtTotalAmount;
    private int overTotalPrice = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        cart_list = view.findViewById(R.id.cart_list);
        cart_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        cart_list.setLayoutManager(layoutManager);
        txtTotalAmount = view.findViewById(R.id.total_price);

        confirm_button = view.findViewById(R.id.confirm_button);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), ConfirmFinalOrderActivity.class);
                intent.putExtra("Tổng tiền", String.valueOf(overTotalPrice));
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser.getUsername()).child("Products1"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.product_Name_Cart.setText(model.getPname());
                holder.product_Quantity_Cart.setText(model.getQuantity());
                holder.product_Price_Cart.setText(model.getPrice() + "đ");

                int oneTypeProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTypeProductPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Chỉnh sửa",
                                "Xóa"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Chỉnh sửa hóa đơn:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0){
                                    Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                                    intent.putExtra("puid", model.getPuid());
                                    startActivity(intent);
                                }
                                if (i == 1){
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getUsername())
                                            .child("Products1")
                                            .child(model.getPuid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Đã xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        cart_list.setAdapter(adapter);
        adapter.startListening();
    }
}
