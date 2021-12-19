package com.my.bubbletea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.my.bubbletea.fragments.MomentFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void turn_back(View view) {
        Intent intent =new Intent(DetailActivity.this,MainActivity.class);
        startActivity(intent);
        DetailActivity.this.finish();
    }

    public void turn_describe(View view) {
        Intent intent =new Intent(DetailActivity.this,DescribeActivity.class);
        startActivity(intent);
        DetailActivity.this.finish();
    }
}