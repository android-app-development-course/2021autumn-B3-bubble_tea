package com.my.bubbletea.user;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.my.bubbletea.MainActivity;
import com.my.bubbletea.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/*
    submit user input to the server and get returned info.
    this should return object that include user info.
 */
class LoginThread implements Callable<Boolean> {
    private String input_usrname;
    private String input_pwd;

    public LoginThread(String u,String p) {
        input_usrname = u;
        input_pwd = p;
    }

    @Override
    public Boolean call() throws Exception {
        Thread.sleep(1000);
        return true;
    }
}

public class LoginActivity extends AppCompatActivity {

    private MaterialButton submitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearProgressIndicator lpi = findViewById(R.id.login_progress_bar);
//        lpi.show();

        submitLogin = (MaterialButton) findViewById(R.id.submit_login_button);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prevent users from clicking again.
                submitLogin.setEnabled(false);

                LinearProgressIndicator lpi = findViewById(R.id.login_progress_bar);
                lpi.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoginThread lt = new LoginThread("Me","pwd");
                        FutureTask<Boolean> futureTask = new FutureTask<>(lt);
                        futureTask.run();
                        try {
                            //  get query result from server and display in App
                            Boolean isLoggedIn = futureTask.get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lpi.hide();
                                    if(isLoggedIn) {
                                        Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
//                                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this,"Failed to log in",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                submitLogin.setEnabled(true);
           }
        });

        TextView t = findViewById(R.id.login_tosignup);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}