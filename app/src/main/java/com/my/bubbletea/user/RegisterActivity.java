package com.my.bubbletea.user;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.my.bubbletea.R;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/*
    TODO
    注册请求返回一个Model，
    如果成功：包括显示注册成功的status code，以及包括用户信息（返回显示
        失败：status code， 根据status code 搞个snackbar显示错误信息
 */

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

class RegisterThread implements Callable<Boolean> {
    private String input_usrname;
    private String input_pwd;

    public RegisterThread(String u,String p) {
        input_usrname = u;
        input_pwd = p;
    }

    @Override
    public Boolean call() throws Exception {
        ParseUser user = new ParseUser();
        user.setUsername(input_usrname);
        user.setPassword(input_usrname);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {



                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

        return true;
    }
}

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialButton registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setEnabled(false);
                LinearProgressIndicator lpi = findViewById(R.id.register_progress_bar);
                lpi.show();

                // 表单校验没做，直接一把梭。
                EditText usernameInput = findViewById(R.id.usernameInput);
                EditText passwordInput = findViewById(R.id.passwordInput);
                ParseUser user = new ParseUser();
                user.setUsername(String.valueOf(usernameInput.getText()));
                user.setPassword(String.valueOf(passwordInput.getText()));
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(v.getContext(),"Signed up",Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(v.getContext(),"Sign up failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                lpi.hide();
                registerButton.setEnabled(true);
            }
        });



    }
}