package com.example.ulesa.ui.Manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;

public class EditStatusActivity extends AppCompatActivity {
   private EditText textedit;
   private Button btnupdate;
   private LinearLayout layoutsta;
   String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cập nhật trạng thái");
        textedit = findViewById(R.id.txt_edit_order);
        btnupdate = findViewById(R.id.btn_update);
        layoutsta = findViewById(R.id.layout_edit_status);
        int status = getIntent().getIntExtra("status", 0);
        String idBill = getIntent().getStringExtra("id_bill");
        textedit.setText(status == 1? "Hoàn thành" : "Không hoàn thành");
        btnupdate.setOnClickListener(v -> {
            int newstatus = textedit.getText().toString().equals("Hoàn thành") ? 1 : 0;
            DataBaseHelper.changeStatus(idBill,newstatus,
                    this);
        });
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE1");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE1")) {
                showToast(intent.getAction());
            }
        }

    };
    private void showToast(String action) {
        if (action.equals("com.example.appulesa.DATA_DONE1")) {
            Log.d("TAG", "onReceive DATA_DONE");
            Toast.makeText(this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
        } else if(action.equals("com.example.appulesa.DATA_FALSE")){
            Log.d("TAG", "onReceive DATA_FALSE");
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