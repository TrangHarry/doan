package com.example.ulesa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.ui.Manager.ManMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference db;
    FirebaseAuth auth;
    ProgressDialog dialog;
    String gmail, pw;
    private TextView tv_register, tv_forgot;
    private EditText txt_gmail, txt_password;
    private Button btn_login;
    private final String TAG = "TRANG_LoginActivity";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trangdata-1e653-default-rtdb.firebaseio.com/");
        tv_register = findViewById(R.id.register);
        tv_forgot = findViewById(R.id.forgot);
        txt_gmail = findViewById(R.id.txtgmail);
        txt_password = findViewById(R.id.txtpassword);
        btn_login = findViewById(R.id.btnlogin);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loadingg...");

//        if(auth.getCurrentUser() != null){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            finish();
//        }
        String userName = DataBaseHelper.getUserName(this);
        String passWord = DataBaseHelper.getPassWord(this);
        if (userName != null && passWord != null) {
            txt_gmail.setText(userName);
            txt_password.setText(passWord);
            logins(userName, passWord);
        }
        btn_login.setOnClickListener(v -> {
            login();

        });

        //
        //        txt_gmail.setText(userName);
        //        txt_password.setText(passWord);
        //        if (userName != null && passWord != null) {
        //            if (userName.equals("trang@gmail.com") && userName.equals("080902")) {
        //                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        //                DataBaseHelper.saveTK(userName, passWord, "man", getApplicationContext(), "man");
        //                startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //                finish();
        //                return;
        //            }
        //            db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
        //                @Override
        //                public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                    for (DataSnapshot item : snapshot.getChildren()) {
        //                        Log.d(TAG, "onDataChange " + userName + "-" + userName + "/" + item.getKey());
        //                        String auser = item.child("Gmail").getValue(String.class);
        //                        String apass = item.child("PassWord").getValue(String.class);
        //                        if (userName.equals(auser) && passWord.equals(apass)) {
        //                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        //                            DataBaseHelper.saveTK(userName, passWord, item.child("Name").getValue(String.class), getApplicationContext(), item.getKey());
        //                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //                            finish();
        //                            return;
        //                        }
        //                    }
        //                    Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
        //                }
        //
        //                @Override
        //                public void onCancelled(@NonNull DatabaseError error) {
        //                }
        //            });
        //        } else if (userName != null && passWord != null) {
        //            db.child("loginMan").addListenerForSingleValueEvent(new ValueEventListener() {
        //                @Override
        //                public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                    for (DataSnapshot item : snapshot.getChildren()) {
        //                        Log.d(TAG, "onDataChange " + userName + "-" + userName + "/" + item.getKey());
        //                        String auser = item.child("Gmail").getValue(String.class);
        //                        String apass = item.child("PassWord").getValue(String.class);
        //                        if (userName.equals(auser) && passWord.equals(apass)) {
        //                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        //                            DataBaseHelper.saveTK(userName, passWord, item.child("Name").getValue(String.class), getApplicationContext(), item.getKey());
        //                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //                            finish();
        //                            return;
        //                        }
        //                    }
        //                    Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
        //                }
        //
        //                @Override
        //                public void onCancelled(@NonNull DatabaseError error) {
        //                }
        //            });
        //        }
        //        btn_login.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                final String Gmail = txt_gmail.getText().toString();
        //                final String PassWord = txt_password.getText().toString();
        //                if (Gmail.isEmpty() || PassWord.isEmpty()) {
        //                    Toast.makeText(LoginActivity.this, "Điền đủ gmail và mật khẩu", Toast.LENGTH_SHORT).show();
        //                } else if (Gmail.equals("trang@gmail.com") && PassWord.equals("080902")) {
        //                    db.child("loginMan").addListenerForSingleValueEvent(new ValueEventListener() {
        //                        @Override
        //                        public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                            Toast.makeText(LoginActivity.this, "Quản lý đăng nhập thành công! ", Toast.LENGTH_SHORT).show();
        //                            DataBaseHelper.saveTK("trang@gmail.com", "080902", "", getApplicationContext(),
        //                                    "");
        //                            startActivity(new Intent(LoginActivity.this, ManMainActivity.class));
        //                            finish();
        //                        }
        //
        //                        @Override
        //                        public void onCancelled(@NonNull DatabaseError error) {
        //
        //                        }
        //                    });
        //                } else {
        //                    db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
        //                        @Override
        //                        public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                            for (DataSnapshot item : snapshot.getChildren()) {
        //                                Log.d(TAG, "onDataChange " + Gmail + "-" + PassWord + "/" + item.getKey());
        //                                String auser = item.child("Gmail").getValue(String.class);
        //                                String apass = item.child("PassWord").getValue(String.class);
        //                                if (Gmail.equals(auser) && PassWord.equals(apass)) {
        //                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        //                                    DataBaseHelper.saveTK(Gmail, PassWord, item.child("Name").getValue(String.class), getApplicationContext(),
        //                                            item.getKey());
        //                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //                                    finish();
        //                                    return;
        //                                }
        //                            }
        //                            Toast.makeText(LoginActivity.this, "Gmail không đúng!", Toast.LENGTH_SHORT).show();
        //                        }
        //
        //                        @Override
        //                        public void onCancelled(@NonNull DatabaseError error) {
        //                            Toast.makeText(LoginActivity.this, "không đúng!", Toast.LENGTH_SHORT).show();
        //                        }
        //                    });
        //                }
        //            }
        //        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });
    }

    private Boolean validateEmail() {
        String val = txt_gmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txt_gmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txt_gmail.setError("Invalid email address");
            return false;
        } else {
            txt_gmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = txt_password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            txt_password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            txt_password.setError("Password is too weak");
            return false;
        } else {
            txt_password.setError(null);
            return true;
        }
    }

    private void login() {
        if (txt_gmail.getText().toString().equals("trang@gmail.com") && txt_password.getText().toString().equals("080902")) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            DataBaseHelper.saveTK(txt_gmail.getText().toString(), txt_password.getText().toString(), "man", getApplicationContext(), "man");
            startActivity(new Intent(LoginActivity.this, ManMainActivity.class));
            finish();
            return;
        }
        if (!validateEmail() || !validatePassword()) {
            dialog.dismiss();
            return;
        } else {

            gmail = txt_gmail.getText().toString();
            pw = txt_password.getText().toString();
            dialog.show();
            logins(gmail, pw);
        }
    }

    private void logins(String gmail, String pw) {
        auth.signInWithEmailAndPassword(gmail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot item : snapshot.getChildren()) {
                                Log.d(TAG, "onDataChange " + gmail + "-" + "/" + item.child("Gmail").getValue());
                                if (item.child("Gmail").getValue().equals(gmail)) {
                                    DataBaseHelper.saveTK(gmail, pw, item.child("Name").getValue(String.class), getApplicationContext(), item.getKey());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}