package com.example.ulesa.ui.Manager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRoomActivity extends AppCompatActivity {
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trangdata-1e653-default-rtdb.firebaseio.com/");
    private EditText textroomname,textidroom, textimagelink,textprice,textreview,textlistimage,textlocation,textevaluate, textviewevaluate,
            textviewbook,textcus;
    private Button btnaddroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_add_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm phòng mới");
        getSupportActionBar().setSubtitle("Thêm phòng mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textroomname = findViewById(R.id.text_roomname);
        textidroom = findViewById(R.id.text_idroom);
        textimagelink = findViewById(R.id.text_image_link);
        textprice = findViewById(R.id.text_price);
        textreview = findViewById(R.id.text_review);
        textevaluate = findViewById(R.id.text_evaluate);
        textlocation = findViewById(R.id.text_location);
        textviewevaluate = findViewById(R.id.text_view_evaluate);
        textviewbook = findViewById(R.id.text_view_book);
        textlistimage = findViewById(R.id.text_list_image);
        textcus = findViewById(R.id.text_cus);
        btnaddroom = findViewById(R.id.btn_addroom);
        btnaddroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String IDRoom = textidroom.getText().toString();
                final String RoomName = textroomname.getText().toString();
                final String PictureMain = textimagelink.getText().toString();
                final String Review = textreview.getText().toString();
                final String AnyPic = textlistimage.getText().toString();
                final Integer Price = textprice.toString().length();
                final Integer Evaluate = textevaluate.toString().length();
                final String Location = textlocation.getText().toString();
                final Integer ViewBooking = textviewbook.toString().length();
                final Integer ViewEvaluate = textviewevaluate.toString().length();
                final Integer Cus = textcus.toString().length();
                if(IDRoom.isEmpty()|| RoomName.isEmpty()|| PictureMain.isEmpty()||Review.isEmpty()||AnyPic.isEmpty()){
                    Toast.makeText(AddRoomActivity.this,"Bạn cần điền đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(IDRoom)){
                                Toast.makeText(AddRoomActivity.this,"IDRoom đã tồn tại!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                db.child("main").child(IDRoom).child("RoomName").setValue(RoomName);
                                db.child("main").child(IDRoom).child("PictureMain").setValue(PictureMain);
                                db.child("main").child(IDRoom).child("Review").setValue(Review);
                                db.child("main").child(IDRoom).child("AnyPic").setValue(AnyPic);
                                db.child("main").child(IDRoom).child("Price").setValue(Price);
                                db.child("main").child(IDRoom).child("Evaluate").setValue(Evaluate);
                                db.child("main").child(IDRoom).child("Location").setValue(Location);
                                db.child("main").child(IDRoom).child("ViewBooking").setValue(ViewBooking);
                                db.child("main").child(IDRoom).child("ViewEvaluate").setValue(ViewEvaluate);
                                db.child("main").child(IDRoom).child("Cus").setValue(Cus);
                                Toast.makeText(AddRoomActivity.this,"Thêm phòng thành công!",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}