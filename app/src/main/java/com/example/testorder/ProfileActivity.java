package com.example.testorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testorder.Prevalent.Prevalent;

public class ProfileActivity extends AppCompatActivity {

    private ImageView user_profile_image;
    private TextView user_profile_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_profile_name = findViewById(R.id.user_profile_name);
        user_profile_image = findViewById(R.id.user_profile_image);

        user_profile_name.setText(Prevalent.currentOnlineUser.getName());
    }
}