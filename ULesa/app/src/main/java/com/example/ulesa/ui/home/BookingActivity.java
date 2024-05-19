package com.example.ulesa.ui.home;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarDay;
import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.Date;
import com.example.ulesa.model.HomeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    private final String TAG = "TRANG_BookingActivity";

    HomeModel homeModel;
    private String idRoom;
    private TextView txttimein, txttimeout;
    private EditText textcus;
    private ImageView ImageTimeIn, ImageTimeOut;
    private LinearLayout layoutTimeIn, layoutTimeOut;
    private Button btnbook;
    private Date date;
    private String gioDen;

    private Integer Cus;
    private String gioDi;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<Date> listDateBooked;
    private ArrayList<CalendarDay> calendarDays;
    private ArrayList<Calendar> calendarListCheck;
    private Calendar calendarSelected;
    private com.applandeo.materialcalendarview.CalendarView calendarViewCus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking);
        ImageTimeIn = findViewById(R.id.image_timein);
        ImageTimeOut = findViewById(R.id.image_timeout);
        layoutTimeIn = findViewById(R.id.layout_timein);
        layoutTimeOut = findViewById(R.id.layout_timeout);
        txttimein = findViewById(R.id.edittxt_timein);
        txttimeout = findViewById(R.id.edittxt_timeout);
        textcus = findViewById(R.id.text_cus);
        btnbook = findViewById(R.id.btnbook);
        date = new Date(0, 0, 0);

        // calendarday
        calendarDays = new ArrayList<>();

        idRoom = getIntent().getStringExtra("room_id");
        dataBaseHelper = new DataBaseHelper();
        homeModel = dataBaseHelper.getDataDetail(this, idRoom, 1);
        textcus.setText("" + homeModel.getCus());
        listDateBooked = DataBaseHelper.getDateBooked(idRoom, getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đặt phòng");
        getSupportActionBar().setSubtitle("Đặt phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnbook.setOnClickListener(v -> {
            if (calendarSelected == null) {
                Toast.makeText(this, "Chọn ngày trước", Toast.LENGTH_SHORT).show();
                return;
            }
            date = new Date(calendarSelected.get(Calendar.DATE), calendarSelected.get(Calendar.MONTH) + 1, calendarSelected.get(Calendar.YEAR));
            Toast.makeText(this, "Date : " + date, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BookingActivity.this, ConfirmPaymentActivity.class);
            intent.putExtra("room_id", idRoom);
            intent.putExtra("date", date.toString());
            intent.putExtra("date_calender", calendarSelected.getTimeInMillis());
            intent.putExtra("gio_den", gioDen);
            intent.putExtra("gio_di", gioDi);
            try {
                Integer.parseInt(textcus.getText().toString());
                intent.putExtra("cus_book", textcus.getText().toString());
                intent.putExtra("cus_max", homeModel.getCus());
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Số người không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });


        layoutTimeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTimeIn();
            }
        });
        layoutTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTimeOut();
            }
        });

        calendarViewCus = findViewById(R.id.calendarView_custom);
        calendarViewCus.setOnCalendarDayClickListener(calendarDay -> {
            Calendar calendar = calendarDay.getCalendar();
            if (checkDaySelection(calendar)) {
                Toast.makeText(this, "Lịch đã được đặt bởi người khác", Toast.LENGTH_SHORT).show();
            } else {
                if (calendarSelected != null && calendarSelected.getTimeInMillis() == calendar.getTimeInMillis()) {
                    CalendarDay calendarDay1 = new CalendarDay(calendarSelected);
                    calendarDay1.setImageResource(R.drawable.null_icon);
                    calendarDays.add(calendarDay1);
                    calendarViewCus.setCalendarDays(calendarDays);
                    calendarDays.remove(calendarSelected);
                    calendarSelected = null;
                } else {
                    if (calendarSelected != null) Toast.makeText(this, "Chỉ được chọn 1 ngày", Toast.LENGTH_SHORT).show();
                    else {
                        calendarSelected = calendar;
                        CalendarDay calendarDay1 = new CalendarDay(calendarSelected);
                        calendarDay1.setImageResource(R.drawable.iconuser);
                        calendarDays.add(calendarDay1);
                        calendarViewCus.setCalendarDays(calendarDays);
                    }

                }
            }
        });
//////////////
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
                listDateBooked = dataBaseHelper.getListDate();
                if (listDateBooked == null || listDateBooked.size() == 0) return;
                calendarListCheck = new ArrayList<>();
                for (Date item : listDateBooked) {
                    Calendar cal = Calendar.getInstance();
                    Log.d(TAG, "+++++++++" + cal);
                    java.sql.Date date1 = new java.sql.Date(item.getYear() - 1900, item.getMonth() - 1, item.getDate());
                    cal.setTime(date1);
                    Log.d(TAG, item + "----" + cal);

                    CalendarDay calendarDay = new CalendarDay(cal);
                    calendarDay.setImageResource(R.drawable.iconcancle);
                    calendarDays.add(calendarDay);
                    calendarViewCus.setCalendarDays(calendarDays);

                    calendarListCheck.add(cal);
                }
            }
        }

    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private boolean checkDaySelection(Calendar calendar) {
        for (Calendar item : calendarListCheck) {
            if (item.getTimeInMillis() == calendar.getTimeInMillis()) return true;
        }
        return false;
    }

    private void ChooseTimeIn() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                txttimein.setText(simpleDateFormat.format(calendar.getTime()));
                gioDen = String.valueOf(txttimein.getText());
            }
        }, hours, minutes, true);
        timePickerDialog.show();
    }

    private void ChooseTimeOut() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                txttimeout.setText(simpleDateFormat.format(calendar.getTime()));
                gioDi = String.valueOf(txttimeout.getText());
            }
        }, hours, minutes, true);
        timePickerDialog.show();
    }

    private void getListDay() throws ParseException {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}