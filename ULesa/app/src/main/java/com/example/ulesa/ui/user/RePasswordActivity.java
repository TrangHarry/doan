package com.example.ulesa.ui.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;

public class RePasswordActivity extends AppCompatActivity {
    private EditText editOldPass, editnewPass;
    private Button changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editOldPass = findViewById(R.id.txt_password);
        editnewPass = findViewById(R.id.txt_repassword);
        changeBtn = findViewById(R.id.button_change_pass);

        changeBtn.setOnClickListener(v -> {
            String oldPass = String.valueOf(editOldPass.getText());
            String newPass = String.valueOf(editnewPass.getText());
            if(oldPass != null && newPass != null && oldPass.length() != 0 && newPass.length() != 0) {
                DataBaseHelper.changePassWord(oldPass, newPass, this);
            }
        });

        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_FALSE");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("RePasswordActivity_", "onReceive " + action);
            showToast(action);
        }

    };
    private void showToast(String action) {
        if (action.equals("com.example.appulesa.DATA_DONE")) {
            Log.d("RePasswordActivity_", "onReceive DATA_DONE");
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        } else if(action.equals("com.example.appulesa.DATA_FALSE")){
            Log.d("RePasswordActivity_", "onReceive DATA_FALSE");
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}