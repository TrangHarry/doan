package com.example.ulesa.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.CommentModel;

public class EvaluateRoomActivity extends AppCompatActivity {

    EditText editText;
    private RatingBar ratingBar;
    Button send;
    String idRoom;
    String idUSer;
    float rate = 0;
    String cmt = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dánh giá phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.rate_text);
        ratingBar = findViewById(R.id.rate_id);
        send = findViewById(R.id.sent_cmt);
        Intent intent = getIntent();
        idRoom = intent.getStringExtra("room_id");
        idUSer = DataBaseHelper.getID(this);
        ratingBar.setRating(5);
        send.setOnClickListener(v -> {
            cmt = editText.getText().toString();
            if (cmt == null) cmt = "";
            rate = ratingBar.getRating();
            CommentModel commentModel = new CommentModel(cmt, rate, idUSer);
            DataBaseHelper.addComment(this, commentModel, idRoom,2);
        });
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE2");
        this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE2")) {
                Toast.makeText(EvaluateRoomActivity.this, "Bình luận thành công",Toast.LENGTH_SHORT).show();
                finish();
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