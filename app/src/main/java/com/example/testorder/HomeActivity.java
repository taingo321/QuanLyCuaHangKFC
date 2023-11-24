package com.example.testorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.testorder.Fragment.GioHangFragment;
import com.example.testorder.Fragment.LichSuFragment;
import com.example.testorder.Fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rey.material.widget.FrameLayout;

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
                } else if (item.getItemId() == R.id.GioHang) {
                    loadFragment(new GioHangFragment());
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
