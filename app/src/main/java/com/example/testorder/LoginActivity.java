package com.example.testorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testorder.Model.Users;
import com.example.testorder.Prevalent.Prevalent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText login_username_input, login_password_input;
    private Button login;
    private TextInputLayout check_Username_login, check_Password_login;
    private ProgressDialog loadingBar;
    private TextView admin_panel_link, not_admin_panel_link;
    private String parentDBName = "Users";
    boolean isValidUsername, isValidPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        login_username_input = findViewById(R.id.login_username_input);
        login_password_input = findViewById(R.id.login_password_input);

        check_Username_login = findViewById(R.id.check_Username_login);
        check_Password_login = findViewById(R.id.check_Password_login);

        login_username_input.addTextChangedListener(new LoginActivity.UsernameTextWatcher());
        login_username_input.addTextChangedListener(new LoginActivity.PasswordTextWatcher());

        admin_panel_link = findViewById(R.id.admin_panel_link);
        not_admin_panel_link = findViewById(R.id.not_admin_panel_link);

        loadingBar = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areAllFieldsValid()){
                    loginUser();
                }
            }
        });

        admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Đăng nhập quản lý");
                admin_panel_link.setVisibility(View.INVISIBLE);
                not_admin_panel_link.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        not_admin_panel_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Đăng nhập");
                admin_panel_link.setVisibility(View.VISIBLE);
                not_admin_panel_link.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
    }

    private class UsernameTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = s.toString();

            if (username.isEmpty()) {
                isValidUsername = false;
                check_Username_login.setErrorEnabled(true);
                check_Username_login.setError("Vui lòng nhập tên tài khoản");
            } else {
                isValidUsername = true;
                check_Username_login.setErrorEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class PasswordTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = s.toString();

            if (password.isEmpty()) {
                isValidPassword = false;
                check_Password_login.setErrorEnabled(true);
                check_Password_login.setError("Vui lòng nhập mật khẩu");
            } else {
                isValidPassword = true;
                check_Password_login.setErrorEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void loginUser() {
        String username = login_username_input.getText().toString();
        String password = login_password_input.getText().toString();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Đang xác minh");
            loadingBar.setMessage("Xin vui lòng chờ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(username, password);
        }
    }

    private void AllowAccessToAccount(String username, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDBName).child(username).exists()){
                    Users usersData = snapshot.child(parentDBName).child(username).getValue(Users.class);

                    if (usersData.getUsername().equals(username)){
                        if (usersData.getPassword().equals(password)){
                            if (parentDBName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Đăng nhập quản lý thành công", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDBName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản " + username + " không tồn tại", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean areAllFieldsValid() {
        return isValidUsername && isValidPassword;
    }
}