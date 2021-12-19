package com.my.bubbletea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DescribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
    }

    public void turn_back(View view) {
        Intent intent =new Intent(DescribeActivity.this,MainActivity.class);
        startActivity(intent);
        DescribeActivity.this.finish();
    }

    public void turn_detail(View view) {
        Intent intent =new Intent(DescribeActivity.this,DetailActivity.class);
        startActivity(intent);
        DescribeActivity.this.finish();
    }
}