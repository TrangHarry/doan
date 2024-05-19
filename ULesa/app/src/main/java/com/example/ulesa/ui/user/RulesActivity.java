package com.example.ulesa.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulesa.R;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Điều khoản và chính sách");
        getSupportActionBar().setSubtitle("Điều khoản và chính sách");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}