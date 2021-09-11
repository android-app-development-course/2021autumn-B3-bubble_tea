package com.my.bubbletea.user;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.my.bubbletea.R;

class TimeConsumer implements Runnable {
    private Thread t;
    private String threadName;

    TimeConsumer(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (t== null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

public class LoginActivity extends AppCompatActivity {

    private MaterialButton submitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submitLogin = (MaterialButton) findViewById(R.id.submit_login_button);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prevent users from clicking again.
                submitLogin.setEnabled(false);

                LinearProgressIndicator lpi = findViewById(R.id.login_progress_bar);
                lpi.show();
                Toast.makeText(getApplicationContext(),"Successfully logged in.",Toast.LENGTH_SHORT).show();
                TimeConsumer tc = new TimeConsumer("Hi");
                tc.run();
//                lpi.hide();

//                Toast.makeText(getApplicationContext(),"Successfully logged in.",Toast.LENGTH_LONG).show();
            }
        });
    }
}