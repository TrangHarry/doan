package com.example.ulesa.ui.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.OrderModel;

import java.util.ArrayList;

public class ListOrderActivity extends AppCompatActivity {
    private RecyclerView listorderrecy;
    private ArrayList<OrderModel> listData;
    private ListOrderAdapter listorderAdapter;
    private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_list_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách đơn đặt");
        getSupportActionBar().setSubtitle("Danh sách đơn đặt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listorderrecy = findViewById(R.id.list_order_recy);
        dataBaseHelper = new DataBaseHelper();
        listData = dataBaseHelper.getDataOrderMan(this);
        listorderAdapter = new ListOrderAdapter(listData, this);
        listorderrecy.setLayoutManager(new LinearLayoutManager(this));
        listorderrecy.setAdapter(listorderAdapter);

        IntentFilter intentFilter = new IntentFilter("com.example.appulesa.DATA_DONE");
        registerReceiver(receiver, intentFilter , Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TRANG_ListOrderActivity", intent.getAction());
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
                listorderAdapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        listData = dataBaseHelper.getDataOrderMan(this);
        listorderAdapter.notifyDataSetChanged();
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}