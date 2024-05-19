package com.example.ulesa.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;

public class UserActivity extends AppCompatActivity {

   private TextView textRules, textRepass, textHelp,textDeleteUser,textLogout;
   private LinearLayout helpLayout;
   private Button btnEit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        textRules = findViewById(R.id.text_rules);
        textRepass = findViewById(R.id.text_repass);
        textHelp = findViewById(R.id.text_help);
        textDeleteUser = findViewById(R.id.text_delete_user);
        textLogout = findViewById(R.id.text_logout);
        btnEit = findViewById(R.id.btn_edit);
        helpLayout = findViewById(R.id.layout_help);
        helpLayout.setOnClickListener(v -> {
            textHelp.performLongClick();
        });
        textRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });
        textHelp.setOnClickListener(v ->{
            Log.d("TRANG", " textHelp.setOnClickListener");
            startActivity(new Intent(UserActivity.this, HelpActivity.class));
        });
        btnEit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", "btnEit.setOnClickListener");
                startActivity(new Intent(UserActivity.this, EditActivity.class));
            }
        });
    }
}