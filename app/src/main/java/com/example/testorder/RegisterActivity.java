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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText register_username_input, register_phone_number_input, register_password_input;
    private TextInputLayout check_Username, check_Phone, check_Password;
    private ProgressDialog loadingBar;
    boolean isValidUsername, isValidPhone, isValidPassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);

        register_username_input = findViewById(R.id.register_username_input);
        register_phone_number_input = findViewById(R.id.register_phone_number_input);
        register_password_input = findViewById(R.id.register_password_input);

        register_username_input.addTextChangedListener(new RegisterActivity.UsernameTextWatcher());
        register_phone_number_input.addTextChangedListener(new RegisterActivity.PhoneTextWatcher());
        register_password_input.addTextChangedListener(new RegisterActivity.PasswordTextWatcher());

        check_Username = findViewById(R.id.check_Username);
        check_Phone = findViewById(R.id.check_Phone);
        check_Password = findViewById(R.id.check_Password);

        loadingBar = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areAllFieldsValid()){
                    CreateAccount();
                }
            }
        });
    }

    private class UsernameTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = s.toString();

            if (username.isEmpty()) {
                isValidUsername = false;
                check_Username.setErrorEnabled(true);
                check_Username.setError("Vui lòng nhập tên tài khoản");

            } else if (username.length() != 10) {
                isValidUsername = false;
                check_Username.setErrorEnabled(true);
                check_Username.setError("Tên tài khoản phải đủ 10 ký tự");

            } else {
                isValidUsername = true;
                check_Username.setErrorEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class PhoneTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = s.toString();

            if (phone.isEmpty()) {
                isValidPhone = false;
                check_Phone.setErrorEnabled(true);
                check_Phone.setError("Vui lòng nhập số điện thoại");

            } else if (phone.length() != 10) {
                isValidPhone = false;
                check_Phone.setErrorEnabled(true);
                check_Phone.setError("Số điện thoại không hợp lệ");

            } else {
                isValidPhone=true;
                check_Phone.setErrorEnabled(false);
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
                check_Password.setErrorEnabled(true);
                check_Password.setError("Vui lòng nhập mật khẩu");

            } else if (password.length() < 7) {
                isValidPassword = false;
                check_Password.setErrorEnabled(true);
                check_Password.setError("Mật khẩu phải trên 7 ký tự");

            } else {
                isValidPassword = true;
                check_Password.setErrorEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void CreateAccount() {
        String username = register_username_input.getText().toString();
        String phone = register_phone_number_input.getText().toString();
        String password = register_password_input.getText().toString();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Tạo mật khẩu");
            loadingBar.setMessage("Vui lòng chờ xác nhận");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUsername(username, phone, password);
        }
    }

    private void ValidateUsername(String username, String phone, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(username).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("username", username);

                    RootRef.child("Users").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản mới thành công.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Đang xảy ra lỗi, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Tài khoản " + username + " đã tồn tại.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();


                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean areAllFieldsValid() {
        return isValidUsername && isValidPassword && isValidPhone;
    }
}