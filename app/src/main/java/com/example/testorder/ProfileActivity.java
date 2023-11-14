package com.example.testorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testorder.Prevalent.Prevalent;

public class ProfileActivity extends AppCompatActivity {

    private ImageView user_profile_image;
    private TextView user_profile_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_profile_username = findViewById(R.id.user_profile_username);
        user_profile_image = findViewById(R.id.user_profile_image);

        user_profile_username.setText(Prevalent.currentOnlineUser.getUsername());
    }
}