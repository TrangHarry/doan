package com.example.ulesa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.MainActivity;
import com.example.ulesa.R;

public class UnBookingSuccessActivity extends AppCompatActivity {

    private Button btncomplete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbooking_success);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hủy đặt thành công");
        btncomplete = findViewById(R.id.btn_complete);
        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UnBookingSuccessActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}