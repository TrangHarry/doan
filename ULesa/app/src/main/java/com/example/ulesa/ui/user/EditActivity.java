package com.example.ulesa.ui.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.UserModel;

public class EditActivity extends AppCompatActivity {
    private final String TAG = "TRANG_EditActivity";
    UserModel userModel;
    private DataBaseHelper dataBaseHelper;

    private EditText txtgmail, txtphone, txtdate, txtname;
    private RadioGroup rbtngender;

    private RadioButton rbtnfemale, rbtnmale;
    private Button btnupdate, btncancel;
    String id;
    LinearLayout layoutedit;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtdate = findViewById(R.id.txt_date);
        txtname = findViewById(R.id.txt_name);
        txtphone = findViewById(R.id.txt_phone);
        txtgmail = findViewById(R.id.txt_gmail);
        rbtnfemale = findViewById(R.id.rbtn_female);
        rbtnmale = findViewById(R.id.rbtn_nmale);
        btnupdate = findViewById(R.id.btn_edit);
        btncancel = findViewById(R.id.btn_cancel);
        rbtngender = findViewById(R.id.rbtn_gender);

        id = DataBaseHelper.getID(this);
        dataBaseHelper = new DataBaseHelper();
        userModel = dataBaseHelper.getDataUser(this, id);
        rbtnmale.setOnClickListener(v -> {
            if(!rbtnmale.isChecked()) {
                rbtnmale.setChecked(true);
                rbtnfemale.setChecked(false);
                gender = "Nam";
            }
        });
        rbtnfemale.setOnClickListener(v -> {
            if(!rbtnfemale.isChecked()) {
                rbtnfemale.setChecked(true);
                rbtnmale.setChecked(false);
                gender = "Nữ";
            }
        });
        btnupdate.setOnClickListener(v -> {
            String newgmail = String.valueOf(txtgmail.getText());
            String newphone = String.valueOf(txtphone.getText());
            String newname = String.valueOf(txtname.getText());
            String newdate = String.valueOf(txtdate.getText());
            String newgender = rbtnmale.isChecked()? "Nam" : "Nữ" ;
            DataBaseHelper.changeIn4(newname, newgmail, newdate, newphone,newgender,
                    this);

        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutedit = findViewById(R.id.layout_edit);
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE1");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }

    private void updateUI(){
        txtdate.setText(userModel.getBirthDay() + "");
        txtphone.setText(userModel.getPhone() +"");

        txtname.setText(userModel.getName() + "");
        txtgmail.setText(userModel.getGmail() + "");
        gender = userModel.getGender();
        if(gender == null) gender = "Nam";
        if (gender.equals("Nam")) {
            rbtnmale.setChecked(true);
            rbtnfemale.setChecked(false);
        } else {
            rbtnmale.setChecked(false);
            rbtnfemale.setChecked(true);
        }
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive()");
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
                userModel = (UserModel) intent.getSerializableExtra("order_user");
                if (userModel != null) layoutedit.setVisibility(View.VISIBLE);
                Log.d(TAG, "" + userModel);
                updateUI();
            }
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE1")) {
                showToast(intent.getAction());
            }
        }

    };
    private void showToast(String action) {
        if (action.equals("com.example.appulesa.DATA_DONE1")) {
            Log.d(TAG, "onReceive DATA_DONE");
            Toast.makeText(this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
        } else if(action.equals("com.example.appulesa.DATA_FALSE")){
            Log.d(TAG, "onReceive DATA_FALSE");
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
