package com.example.ulesa.ui.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.OrderModel;
import com.example.ulesa.model.PriceVND;
import com.example.ulesa.model.UserModel;
import com.example.ulesa.ui.order.OrderAdapter;

import java.util.ArrayList;

public class ManOrderDetailActivity extends AppCompatActivity {
    private final String TAG = "TRANG_ManOderDetailActivity";

    private ImageView imageView;
    private TextView txtName,txtIdbill,txttotal,txtstatus,txtusername,txtdate;
    private ArrayList<OrderModel> listData;
    private OrderAdapter orderAdapter;
    private DataBaseHelper dataBaseHelper;
    private String id;
    OrderModel orderModel;
    UserModel userModel;
    boolean datadone  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_order_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết đơn hàng");
        getSupportActionBar().setSubtitle("Chi tiết đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.image_room);
        orderAdapter = new OrderAdapter(listData, this);
        txtstatus = findViewById(R.id.txt_status);
        txtIdbill = findViewById(R.id.txt_idbill);
        txtName = findViewById(R.id.txt_name);
        txttotal = findViewById(R.id.txt_total);
        txtusername = findViewById(R.id.txt_username);
        txtdate = findViewById(R.id.txt_date);

        id = getIntent().getStringExtra("id_bill");
        dataBaseHelper = new DataBaseHelper();
        orderModel = dataBaseHelper.getDataOrderr(this, id, 5);

        IntentFilter intentFilter = new IntentFilter("com.example.appulesa.DATA_DONE5");
        registerReceiver( receiver, intentFilter, RECEIVER_EXPORTED);
    }
    private void updateUI() {
        Log.d(TAG, "updateUI() " + orderModel.toString());
        txtName.setText(orderModel.getRoomName());
        txtusername.setText(orderModel.getIdUser());
        txtstatus.setText(orderModel.getStatus() == 1? "Hoàn thành" : "Không hoàn thành");
        txtIdbill.setText(orderModel.getbillID() + "");
        txttotal.setText(PriceVND.changeToVND(orderModel.getTotal()));
        txtdate.setText(orderModel.getDate());
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() " + intent.getAction());

            if (intent.getAction().equals("com.example.appulesa.DATA_DONE5")) {
                orderModel = (OrderModel) intent.getSerializableExtra("order_model");
                datadone = true;
                updateUI();
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        if(datadone) updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}