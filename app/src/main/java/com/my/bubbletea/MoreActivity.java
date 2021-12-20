package com.my.bubbletea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.my.bubbletea.user.LoginActivity;
import com.my.bubbletea.user.RegisterActivity;

public class MoreActivity extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        btn=(Button)findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MoreActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void turn_back(View view) {
    }

}