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
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.ui.home.HomeAdapter;

import java.util.ArrayList;

public class EditRoomActivity extends AppCompatActivity {
    private final String TAG = "TRANG_EditRoomActivity";
    HomeModel homeModel;
    private DataBaseHelper dataBaseHelper;
    private HomeAdapter homeAdapter;
    private ArrayList<HomeModel> listData;

    private EditText textroomname, textimagelink,textprice,textreview,textlistimage,textlocation,textevaluate, textviewevaluate,
            textviewbook,textcus;
    private Button btneditroom, btndeleteroom;
    String idroom;
    private LinearLayout layoutroomedit;
    private int delete = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_edit_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý phòng");
        getSupportActionBar().setSubtitle("Quản lý phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textroomname = findViewById(R.id.text_roomname);
        textimagelink = findViewById(R.id.text_image_link);
        textprice = findViewById(R.id.text_price);
        textreview = findViewById(R.id.text_review);
        textevaluate = findViewById(R.id.text_evaluate);
        textlocation = findViewById(R.id.text_location);
        textviewevaluate = findViewById(R.id.text_view_evaluate);
        textviewbook = findViewById(R.id.text_view_book);
        textlistimage = findViewById(R.id.text_list_image);
        textcus = findViewById(R.id.text_cus);
        btneditroom = findViewById(R.id.btn_editroom);
        btndeleteroom = findViewById(R.id.btn_deleteroom);
        layoutroomedit = findViewById(R.id.layout_roomedit);

        idroom = getIntent().getStringExtra("room_id");
        dataBaseHelper = new DataBaseHelper();
        homeModel = dataBaseHelper.getDataDetail(this,idroom,1);
        btneditroom.setOnClickListener(v -> {
            Log.d(TAG, "TRANG " + Integer.parseInt(textprice.getText().toString()));
            homeModel.setRoomName(textroomname.getText().toString());
            homeModel.setReview(textreview.getText().toString());
            homeModel.setPrice(Integer.parseInt(textprice.getText().toString()));
            homeModel.setPictureMain(textimagelink.getText().toString());
            homeModel.setEvaluate(textevaluate.getText().length());
            homeModel.setViewEvaluate(textviewevaluate.getText().length());
            homeModel.setLocation(textlocation.getText().toString());
            homeModel.setViewBooking(textviewbook.getText().length());
            homeModel.setCus(textcus.getText().length());
            DataBaseHelper.changeRoom(idroom, homeModel ,this, 2);
        });
        btndeleteroom.setOnClickListener(v -> {
           DataBaseHelper.deleteRoom(this,idroom,3);
        });
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE1");
        filter.addAction("com.example.appulesa.DATA_DONE2");
        filter.addAction("com.example.appulesa.DATA_DONE3");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE1")) {
                homeModel = (HomeModel) intent.getSerializableExtra("detail_room");
                updateRoom();
            }
            if( intent.getAction().equals("com.example.appulesa.DATA_DONE2")) {
                Toast.makeText(EditRoomActivity.this, "Đổi thông tin thành công!!!", Toast.LENGTH_LONG).show();
            }
            if( intent.getAction().equals("com.example.appulesa.DATA_DONE3")) {
                Toast.makeText(EditRoomActivity.this, "Xóa phòng thành công!!!", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    };
    private void updateRoom(){
        textroomname.setText(homeModel.getRoomName() + "");
        textreview.setText(homeModel.getReview() +"");
        textprice.setText(homeModel.getPrice() + "");
        textcus.setText(homeModel.getCus()+ "");
        textprice.setText(homeModel.getPrice() + "");
        textlistimage.setText(homeModel.getListImage()[0] + "");
        textevaluate.setText(homeModel.getEvaluate() + "");
        textimagelink.setText(homeModel.getPictureMain() + "");
        textlocation.setText(homeModel.getLocation() + "");
        textviewbook.setText(homeModel.getViewBooking() + "");
        textviewevaluate.setText(homeModel.getViewEvaluate()+ "");
    }
    private void showToast(String action) {
        if (action.equals("com.example.appulesa.DATA_DONE1")) {
            Log.d(TAG, "onReceive DATA_DONE");
            Toast.makeText(this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
        } else if(action.equals("com.example.appulesa.DATA_FALSE")){
            Log.d(TAG, "onReceive DATA_FALSE");
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        } else if (action.equals("com.example.appulesa.DATA_DONE2")) {
            Log.d(TAG, "onReceive DATA_DONE");
            Toast.makeText(this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}