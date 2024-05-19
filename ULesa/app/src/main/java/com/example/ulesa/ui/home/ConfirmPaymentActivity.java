package com.example.ulesa.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.Date;
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.model.OrderModel;
import com.example.ulesa.model.PriceVND;

import java.util.Calendar;

public class ConfirmPaymentActivity extends AppCompatActivity {

    private Date date;
    private Calendar calendar;
    private String idRoom;
    private Integer cus, cusMax, tongGia, tangNgayLe;
    private String gioDen, gioDi;
    private TextView textNgayThang, textGioDen, textGioDi, textName, textCus, textGiaPhong, textTong, textPhuphi, textWC, textNgayLe;
    private Button btnXacNhan;

    private HomeModel dateRoom;
    private DataBaseHelper dataBaseHelper;
    private final String TAG = "TRANG_ConfirmPaymentActivity";


    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmpayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Xác nhận thanh toán");

        Intent intent = getIntent();
        idRoom = intent.getStringExtra("room_id");
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(intent.getLongExtra("date_calender", 0));
        date = new Date(intent.getStringExtra("date"));
        Log.d(TAG, "date - " + date.toString());
        gioDen = intent.getStringExtra("gio_den");
        gioDi = intent.getStringExtra("gio_di");
        Log.d("TRANG_______", intent.getStringExtra("cus_book"));
        try {
            cus = Integer.parseInt(intent.getStringExtra("cus_book"));
            cusMax = intent.getIntExtra("cus_max", 0);
        } catch (Exception e) {
            cus = 0;
            cusMax = 0;
        }
        textNgayThang = findViewById(R.id.text_ngay_thang);
        textGioDen = findViewById(R.id.text_timein);
        textGioDi = findViewById(R.id.text_timeout);
        textName = findViewById(R.id.text_name);
        textCus = findViewById(R.id.text_cus);
        textGiaPhong = findViewById(R.id.text_priceroom);
        textTong = findViewById(R.id.text_total);
        btnXacNhan = findViewById(R.id.btn_xac_nhan);
        textPhuphi = findViewById(R.id.text_phuphi);
        textWC = findViewById(R.id.text_pricewc);
        textNgayLe = findViewById(R.id.text_promotion);

        textNgayThang.setText(date.toString());
        textName.setText(DataBaseHelper.getName(this));
        textGioDen.setText(gioDen);
        textGioDi.setText(gioDi);
        textCus.setText(cus + "");
        dataBaseHelper = new DataBaseHelper();
        dateRoom = dataBaseHelper.getDataDetail(this, idRoom, 1);
        textWC.setText(PriceVND.changeToVND(100000));
        tangNgayLe = 0;
        if (checkNgayLe() > 100) {
            Toast.makeText(this,"Tăng giá do ngay lễ", Toast.LENGTH_SHORT).show();
            tangNgayLe = dateRoom.getPrice() * (checkNgayLe()-100) /100;
            textNgayLe.setText("" + PriceVND.changeToVND(dateRoom.getPrice()) + " x " + (checkNgayLe()-100) + "%" + " = " + (PriceVND.changeToVND(tangNgayLe)));
        }
        if (cus > cusMax) textPhuphi.setText(PriceVND.changeToVND(120000*(cus - cusMax)));
        else textPhuphi.setText("0");
        if (dateRoom != null) {
            textGiaPhong.setText(PriceVND.changeToVND(dateRoom.getPrice()));
            if (cus > cusMax) {
                tongGia = dateRoom.getPrice() + 120000 * (cus - cusMax) + 100000;
            } else tongGia = dateRoom.getPrice() + 100000;
        }
        Log.i("TRANG" , tongGia + "-");
        tongGia += tangNgayLe;
        Log.i("TRANG" , tongGia + "--");
        textTong.setText(PriceVND.changeToVND(tongGia));
        btnXacNhan.setOnClickListener(v -> {
            Intent intent1 = new Intent(ConfirmPaymentActivity.this, PaymentActivity.class);
            OrderModel orderModel = new OrderModel("",
                    idRoom, tongGia, "", "",
                    1,DataBaseHelper.getID(this), date.toString(), cus,
                    gioDen, gioDi, 100000);
            intent1.putExtra("bill", orderModel);
            startActivity(intent1);
        });

        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE1");
        filter.addAction("com.example.appulesa.DATA_DONE3");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }
    private int checkNgayLe() {
        int heSo = 100;
        int thu = calendar.get(Calendar.DAY_OF_WEEK);
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH) + 1;
        if(ngay == 30 && thang == 4) return 200;
        if(ngay == 1 && thang == 5) return 200;
        if(ngay == 2 && thang == 9) return 200;
        if(thu == 1 || thu == 7) return  150;
        return heSo;
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE1")) {
                dateRoom = (HomeModel) intent.getSerializableExtra("detail_room");
                textGiaPhong.setText(PriceVND.changeToVND(dateRoom.getPrice()));
                if (cus > cusMax) {
                    tongGia = dateRoom.getPrice() + 120000 * (cus - cusMax) + 100000;
                } else tongGia = dateRoom.getPrice() + 100000;
                tongGia += tangNgayLe;
                textTong.setText(PriceVND.changeToVND(tongGia));
            }
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE3")) {
                Toast.makeText(getApplicationContext(), "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
            }
        }

    };

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