package com.my.bubbletea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class UpgradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        Button publishButton = findViewById(R.id.publish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                publishButton.setEnabled(false);
                if (currentUser == null) {
                    // 未登录
                    Toast.makeText(view.getContext(),"Not logged in",Toast.LENGTH_SHORT).show();
                } else {
                    // 登录了
                    EditText titleInput = findViewById(R.id.titleInput);
                    EditText contentInput = findViewById(R.id.contentInput);
                    ParseObject moment = new ParseObject("Moment");
                    moment.put("title",String.valueOf(titleInput.getText()));
                    moment.put("content",String.valueOf(contentInput.getText()));
                    moment.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                //saved successfully
                                Toast.makeText(view.getContext(),"Published.",Toast.LENGTH_SHORT).show();
                            } else {
                                // error.
                                Log.e("Error When Saving",e.toString());
                            }
                        }
                    });

                }
                publishButton.setEnabled(true);
                finish();
            }
        });


    }

    public void add(View view) {//添加动态

    }
}