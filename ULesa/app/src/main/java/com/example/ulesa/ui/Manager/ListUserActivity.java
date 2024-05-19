package com.example.ulesa.ui.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.R;
import com.example.ulesa.RegisterActivity;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.UserModel;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    private RecyclerView listuserrecy;
    private ArrayList<UserModel> listData;
    private UserAdapter userAdapter;
    private DataBaseHelper dataBaseHelper;
    private Button btnadduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_list_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách người dùng");
        getSupportActionBar().setSubtitle("Danh sách người dùng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listuserrecy = findViewById(R.id.list_user_recy);
        btnadduser = findViewById(R.id.btn_add_user);

        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " btnadduser.setOnClickListener");
                startActivity(new Intent(ListUserActivity.this, RegisterActivity.class));

            }
        });

        dataBaseHelper = new DataBaseHelper();
        listData = dataBaseHelper.getDataUser(this);
        userAdapter = new UserAdapter(listData, this);
        listuserrecy.setLayoutManager(new LinearLayoutManager(this));
        listuserrecy.setAdapter(userAdapter);

        IntentFilter intentFilter = new IntentFilter("com.example.appulesa.DATA_DONE");
        intentFilter.addAction("com.example.appulesa.DATA_DONE2");
        registerReceiver(receiver, intentFilter , Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TRANG_1", intent.getAction());
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
                userAdapter.notifyDataSetChanged();
            }
            if( intent.getAction().equals("com.example.appulesa.DATA_DONE2")) {
                int delete = userAdapter.getDelete();
                Log.d("TRANG_1", "------" + delete);
                if(delete != -1 && listData.size() != 0) {
                    listData.remove(delete);
                    userAdapter = new UserAdapter(listData, ListUserActivity.this);
                    listuserrecy.setLayoutManager(new LinearLayoutManager(ListUserActivity.this));
                    listuserrecy.setAdapter(userAdapter);
                }
                Toast.makeText(ListUserActivity.this, "Xóa người dùng thành công!!!", Toast.LENGTH_LONG).show();
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        listData = dataBaseHelper.getDataUser(this);
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