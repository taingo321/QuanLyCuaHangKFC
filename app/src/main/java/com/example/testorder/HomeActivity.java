package com.example.testorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.testorder.Fragment.GioHangFragment;
import com.example.testorder.Fragment.LichSuFragment;
import com.example.testorder.Fragment.ProfileFragment;
import com.example.testorder.Fragment.TrangChuFragment;
import com.example.testorder.Model.Products;
import com.example.testorder.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.FrameLayout;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView botView;
    FrameLayout mFrame;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFragment(new TrangChuFragment());

        botView = findViewById(R.id.bnv);
        botView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.TrangChu){
                    loadFragment(new TrangChuFragment());
                } else if (item.getItemId() == R.id.LichSu) {
                    loadFragment(new LichSuFragment());
                } else if (item.getItemId() == R.id.GioHang) {
                    loadFragment(new GioHangFragment());
                } else if (item.getItemId() == R.id.Profile) {
                    loadFragment(new ProfileFragment());
                }

                return true;
            }
        });




    }

    private void loadFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frContent, fragment);
        fragmentTransaction.commit();
    }


}