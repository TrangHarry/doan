package com.example.ulesa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.Date;
import com.example.ulesa.model.OrderModel;
import com.example.ulesa.model.PriceVND;

public class PaymentActivity extends AppCompatActivity {

    private Button btnpay;
    private TextView textDatcoc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thanh toán tiền cọc");
        btnpay = findViewById(R.id.btn_pay);
        textDatcoc = findViewById(R.id.price_datcoc);
        OrderModel orderModel = new OrderModel();
        orderModel = (OrderModel) getIntent().getSerializableExtra("bill");

        int tienDatCoc = (int) ((orderModel.getTotal())*0.2);
        textDatcoc.setText(PriceVND.changeToVND(tienDatCoc));
        OrderModel finalOrderModel = orderModel;
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper.addBill(PaymentActivity.this, finalOrderModel.getCus(),
                        new Date(finalOrderModel.getDate()), finalOrderModel.getNameID(), DataBaseHelper.getID(PaymentActivity.this),
                        0, 1, finalOrderModel.getTimeIn(), finalOrderModel.getTimeOut(), finalOrderModel.getTotal() , finalOrderModel.getWc());
                Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                intent.putExtra("tiendatcoc", tienDatCoc);
                DataBaseHelper.addBooked(finalOrderModel.getNameID(),  new Date(finalOrderModel.getDate()), PaymentActivity.this);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}