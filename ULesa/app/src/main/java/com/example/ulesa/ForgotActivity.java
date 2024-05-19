package com.example.ulesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotActivity extends AppCompatActivity {
    private EditText txt_fg;
    private Button btn_xacnhan;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        txt_fg = findViewById(R.id.txt_forgot_edit_email);
        btn_xacnhan = findViewById(R.id.btnxacnhan);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(ForgotActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        btn_xacnhan.setOnClickListener(v -> {
            forgotpassword();
        });
    }
    private Boolean validateEmail() {
        String val = txt_fg.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txt_fg.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txt_fg.setError("Invalid email address");
            return false;
        } else {
            txt_fg.setError(null);
            return true;
        }
    }
    private void forgotpassword() {
        if (!validateEmail()) {
            return;
        }
        dialog.show();
        auth.sendPasswordResetEmail(txt_fg.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                    finish();
                    Toast.makeText(ForgotActivity.this, "Vui long kiem tra email cua ban", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotActivity.this, "Dien dung email cua ban", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}