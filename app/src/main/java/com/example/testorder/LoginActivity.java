package com.example.testorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testorder.Model.Users;
import com.example.testorder.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;
import kotlin.internal.IntrinsicConstEvaluation;

public class LoginActivity extends AppCompatActivity {

    private EditText login_phone_number_input, login_password_input;
    private Button login;
    private ProgressDialog loadingBar;
    private TextView admin_panel_link, not_admin_panel_link;
    private String parentDBName = "Users";
    private CheckBox remember_me_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        login_phone_number_input = findViewById(R.id.login_phone_number_input);
        login_password_input = findViewById(R.id.login_password_input);

        admin_panel_link = findViewById(R.id.admin_panel_link);
        not_admin_panel_link = findViewById(R.id.not_admin_panel_link);

        loadingBar = new ProgressDialog(this);

        remember_me_checkbox = findViewById(R.id.remember_me_checkbox);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login Admin");
                admin_panel_link.setVisibility(View.INVISIBLE);
                not_admin_panel_link.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        not_admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
                admin_panel_link.setVisibility(View.VISIBLE);
                not_admin_panel_link.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
    }

    private void loginUser() {
        String phone = login_phone_number_input.getText().toString();
        String password = login_password_input.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(String phone, String password) {
        if (remember_me_checkbox.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDBName).child(phone).exists()){
                    Users usersData = snapshot.child(parentDBName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            if (parentDBName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Logged in Successfull", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDBName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Logged in Successfull", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}