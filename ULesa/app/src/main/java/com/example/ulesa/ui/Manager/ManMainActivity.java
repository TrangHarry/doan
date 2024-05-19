package com.example.ulesa.ui.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.LoginActivity;
import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.ui.home.HomeAdapter;

import java.util.ArrayList;

public class ManMainActivity extends AppCompatActivity {
    private RecyclerView manHomeRecyl;
    private ArrayList<HomeModel> listData;
    private HomeAdapter homeAdapter;
    private DataBaseHelper dataBaseHelper;
    ImageView imageView;
    private Button btnhome,btnlistorder, btnmuser, btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_main);
        btnhome = findViewById(R.id.btn_mmain);
        btnlistorder = findViewById(R.id.btn_list_order);
        btnmuser = findViewById(R.id.btn_muser);
        btnadd = findViewById(R.id.btn_add);
        imageView = findViewById(R.id.image_logout);
        imageView.setOnClickListener(v -> {
            DataBaseHelper.xoasTK(this);
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " btnadd.setOnClickListener");
                startActivity(new Intent(ManMainActivity.this, AddRoomActivity.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " btnhome.setOnClickListener");
                startActivity(new Intent(ManMainActivity.this, ManMainActivity.class));
            }
        });

        btnmuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " btnmuser.setOnClickListener");
                startActivity(new Intent(ManMainActivity.this, ListUserActivity.class));
            }
        });

        btnlistorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " textHelp.setOnClickListener");
                startActivity(new Intent(ManMainActivity.this, ListOrderActivity.class));
            }
        });
        manHomeRecyl = findViewById(R.id.man_home_recy);
        dataBaseHelper = new DataBaseHelper();
        listData = dataBaseHelper.getDataHome(this);
        homeAdapter = new HomeAdapter(listData, this);
        manHomeRecyl.setLayoutManager(new LinearLayoutManager(this));
        manHomeRecyl.setAdapter(homeAdapter);

        IntentFilter intentFilter = new IntentFilter("com.example.appulesa.DATA_DONE");
        registerReceiver(receiver, intentFilter , Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TRA NG_1", intent.getAction());
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
//                listData = dataBaseHelper.getDataHome(getContext());
                homeAdapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        listData = dataBaseHelper.getDataHome(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }
}