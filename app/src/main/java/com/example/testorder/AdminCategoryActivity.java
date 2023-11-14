package com.example.testorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView chickens, burgers_rices_spaghetties, snacks, drinks_deserts,
            speacial_offers, brand_new, combos;

    private Button check_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        chickens = findViewById(R.id.chickens);
        burgers_rices_spaghetties = findViewById(R.id.burgers_rices_spaghetties);
        snacks = findViewById(R.id.snacks);
        drinks_deserts = findViewById(R.id.drinks_deserts);
        speacial_offers = findViewById(R.id.speacial_offers);
        brand_new = findViewById(R.id.brand_new);
        combos = findViewById(R.id.combos);

        check_orders = findViewById(R.id.check_orders);

        check_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        chickens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "chickens");
                startActivity(intent);
            }
        });

        burgers_rices_spaghetties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "burgers_rices_spaghetties");
                startActivity(intent);
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "snacks");
                startActivity(intent);
            }
        });

        drinks_deserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "drinks_deserts");
                startActivity(intent);
            }
        });

        speacial_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "speacial_offers");
                startActivity(intent);
            }
        });

        brand_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "brand_new");
                startActivity(intent);
            }
        });

        combos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "combos");
                startActivity(intent);
            }
        });
    }
}