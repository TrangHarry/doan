package com.example.ulesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trangdata-1e653-default-rtdb.firebaseio.com/");
    Button btn_register;
    EditText txt_gmail,txt_password,txt_name,txt_date,txt_phone,txt_id;
    RadioButton rbtn_femal,rbtn_male;
    RadioGroup rbtn_gender;
    String selectbtn;
    String IDUser, gmail, pw,name, date, phone,gender;
    FirebaseAuth auth;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_id = findViewById(R.id.txtid);
        btn_register = findViewById(R.id.btnregister);
        txt_gmail = findViewById(R.id.txtgmail);
        txt_password = findViewById(R.id.txtpassword);
        txt_name = findViewById(R.id.txtname);
        txt_date = findViewById(R.id.txtdate);
        txt_phone = findViewById(R.id.txtphone);
        rbtn_gender = findViewById(R.id.rbtngender);
        rbtn_femal = findViewById(R.id.rbtnfemale);
        rbtn_male = findViewById(R.id.rbtnmale);
        rbtn_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if( checkedId == R.id.rbtnfemale){
                    selectbtn = rbtn_femal.getText().toString();
                } else if(checkedId == R.id.rbtnmale){
                    selectbtn = rbtn_male.getText().toString();
                }
            }
        });

//        btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String ID = txt_id.getText().toString();
//                final String Name = txt_name.getText().toString();
//                final String PassWord= txt_password.getText().toString();
//                final String Gmail = txt_gmail.getText().toString();
//                final String BirthDay = txt_date.getText().toString();
//                final String Phone = txt_phone.getText().toString();
//                final String Gender = selectbtn.toString();
//               if(Name.isEmpty()|| PassWord.isEmpty()|| Gmail.isEmpty()||BirthDay.isEmpty()){
//                   Toast.makeText(RegisterActivity.this,"Bạn cần điền đủ thông tin",Toast.LENGTH_SHORT).show();
//               }
//               else{
//                   db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
//                       @Override
//                       public void onDataChange(@NonNull DataSnapshot snapshot) {
//                           if(snapshot.hasChild(ID)){
//                               Toast.makeText(RegisterActivity.this,"ID đã tồn tại!",Toast.LENGTH_SHORT).show();
//                           }
//                           else {
//                               db.child("loginCus").child(ID).child("Name").setValue(Name);
//                               db.child("loginCus").child(ID).child("PassWord").setValue(PassWord);
//                               db.child("loginCus").child(ID).child("BirthDay").setValue(BirthDay);
//                               db.child("loginCus").child(ID).child("Gmail").setValue(Gmail);
//                               db.child("loginCus").child(ID).child("Phone").setValue(Phone);
//                               db.child("loginCus").child(ID).child("Gender").setValue(Gender);
//                               Toast.makeText(RegisterActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
//
//                               finish();
//                           }
//                       }
//
//                       @Override
//                       public void onCancelled(@NonNull DatabaseError error) {
//
//                       }
//                   });
//
//               }
//            }
//        });
        IDUser = Settings.Secure.getString(RegisterActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loadingg...");
        btn_register.setOnClickListener(v -> {
            register();
            if(txt_phone.length()>10){
                txt_phone.setError("Số điện thoại không hợp lệ");
            }
        });
    }
    @NonNull
    private Boolean validateEmail() {
        String val = txt_gmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txt_gmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txt_gmail.setError("Invalid email address");
            return false;
        } else {
            txt_gmail.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = txt_phone.getText().toString();

        if (val.isEmpty()) {
           txt_phone.setError("Field cannot be empty");
            return false;
        }
        else {
           txt_phone.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = txt_password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            txt_password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            txt_password.setError("Password is too weak");
            return false;
        } else {
            txt_password.setError(null);
            return true;
        }
    }

    private void register(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("loginCus");
        final FirebaseUser user = auth.getCurrentUser();
        if(!validateEmail()||!validatePassword()||!validatePhoneNo()){
            return;
        }
        IDUser = txt_id.getText().toString();
        gmail = txt_gmail.getText().toString();
        phone = txt_phone.getText().toString();
        pw = txt_password.getText().toString();
        date = txt_date.getText().toString();
        gender = selectbtn.toString();
        name = txt_name.getEditableText().toString();

        Query query = reference.orderByChild("IDUser").equalTo(IDUser);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(RegisterActivity.this, "IDUser da ton tai", Toast.LENGTH_SHORT).show();
                }else {
                        registerUser(user,IDUser, gmail, pw,name,date,phone, gender );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void registerUser(FirebaseUser user,String IDUser, String gmail,String pw, String name, String date, String phone, String gender){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("loginCus");
        auth.createUserWithEmailAndPassword(gmail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    String UserID = auth.getCurrentUser().getUid();
                    HashMap<String, Object> map = new HashMap<>();

                    map.put("Gmail",gmail);
                    map.put("Phone",phone);
                    map.put("BirthDay",date);
                    map.put("Name",name);
                    map.put("Gender",gender);
                    map.put("PassWord", pw);

                    reference.child(IDUser).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
    }

}