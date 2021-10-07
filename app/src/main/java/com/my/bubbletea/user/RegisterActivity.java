package com.my.bubbletea.user;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
class RegisterThread implements Callable<Boolean> {
    private String input_usrname;
    private String input_pwd;

    public RegisterThread(String u,String p) {
        input_usrname = u;
        input_pwd = p;
    }

    @Override
    public Boolean call() throws Exception {
        Thread.sleep(1000);
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RegisterThread rt = new RegisterThread("Me","pwd");
                        FutureTask<Boolean> futureTask = new FutureTask<>(rt);
                        futureTask.run();
                        try {
                            Boolean isRegistered = futureTask.get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lpi.hide();

                                    // [TODO] perform differently based on status code returned

                                    // back to main activity.
                                    Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                registerButton.setEnabled(true);
            }
        });



    }
}