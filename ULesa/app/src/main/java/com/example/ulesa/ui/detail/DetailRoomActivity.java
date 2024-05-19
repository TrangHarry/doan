package com.example.ulesa.ui.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.CommentModel;
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.model.PriceVND;
import com.example.ulesa.ui.home.BookingActivity;

import java.util.ArrayList;

public class DetailRoomActivity extends AppCompatActivity {

    private final String TAG = "TRANG_DetailRoomActivity";
    HomeModel homeModel;
    private TextView mainNameRoom;
    private TextView txtvalue1;
    private TextView txtvalue2,txtnote,txtdetailroom,txtprice;
    private DataBaseHelper dataBaseHelper;
    private ViewPager2 myViewPager2;
    private ImageAdapter imageAdapter;
    LinearLayout detailLayout;
    Button btnDatPhong;
    String id;
    private RecyclerView recyclerView;
    ArrayList<CommentModel> listComment;
    CommentAdapter commentAdapter;
    boolean datadone  = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mô tả phòng");
        getSupportActionBar().setSubtitle("Chi tiết phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra("room_id");
        dataBaseHelper = new DataBaseHelper();
        homeModel = dataBaseHelper.getDataDetail(this, id, 1);

        myViewPager2 = findViewById(R.id.view_pager);
        mainNameRoom = findViewById(R.id.detail_room_name_txt);
        detailLayout = findViewById(R.id.detail_layout);
        txtvalue1 = findViewById(R.id.txt_value1);
        txtvalue2 = findViewById(R.id.txt_value2);
        txtnote = findViewById(R.id.txt_note);
        txtdetailroom = findViewById(R.id.txt_detail_room);
        txtprice =findViewById(R.id.txt_price);
        detailLayout.setVisibility(View.GONE);
        btnDatPhong = findViewById(R.id.detail_dat_phong_btn);
        //
        recyclerView = findViewById(R.id.recy_cmt);
        listComment = dataBaseHelper.getListComment(this, 3, id );
        commentAdapter = new CommentAdapter( listComment, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);
        btnDatPhong.setOnClickListener(v -> {
            Log.d(TAG, "btnDatPhong.setOnClickListener " +homeModel.getNameID());
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("room_id", homeModel.getNameID());
            startActivity(intent);
        });

        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE1");
        filter.addAction("com.example.appulesa.DATA_DONE3");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);

        if(homeModel != null) {
            updateUI();
        }
    }
    private void updateUI() {
        Log.d(TAG, "updateUI()");
        mainNameRoom.setText(homeModel.getRoomName());
        txtvalue1.setText(homeModel.getEvaluate() + "");
        txtnote.setText(homeModel.getViewEvaluate()+ "");
        txtvalue2.setText(homeModel.getViewBooking()+ "");

        String detail = homeModel.getReview();
        String[] listDetail = detail.split("#");
        detail = "";
        for (int i = 0; i< listDetail.length; i++) {
            detail += listDetail[i] + "\n";
        }
        txtdetailroom.setText(detail);
        txtprice.setText(PriceVND.changeToVND(homeModel.getPrice()));

        imageAdapter = new ImageAdapter(this, homeModel.getListImage());
        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(imageAdapter);
        myViewPager2.setOffscreenPageLimit(3);

        float pageMargin= 100;
        float pageOffset = 50;

        myViewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (myViewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(myViewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE1")) {
                homeModel = (HomeModel) intent.getSerializableExtra("detail_room");
                if (homeModel != null) detailLayout.setVisibility(View.VISIBLE);
                Log.d("TRANG", "" + homeModel.getEvaluate());
                datadone = true;
                updateUI();
            }
            if(intent.getAction().equals("com.example.appulesa.DATA_DONE3")){
                listComment = dataBaseHelper.getListComment(DetailRoomActivity.this, 3, id );
                Log.d(TAG, "onReceive() listComment " + listComment.size());
                for (CommentModel commentModel : listComment) {
                    Log.d(TAG, "onReceive() listComment " + commentModel.getEvaluate());
                }
                commentAdapter.notifyDataSetChanged();
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