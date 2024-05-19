package com.example.ulesa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.MainActivity;
import com.example.ulesa.R;
import com.example.ulesa.model.PriceVND;

public class PaymentSuccessActivity extends AppCompatActivity {

    private Button btncomplete;
    private TextView textdeposits;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thanh toán thành công");
        btncomplete = findViewById(R.id.btn_complete);
        textdeposits = findViewById(R.id.text_deposits);
        int giaDatcoc = getIntent().getIntExtra("tiendatcoc", 0);
        textdeposits.setText(PriceVND.changeToVND(giaDatcoc));
        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaymentSuccessActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}