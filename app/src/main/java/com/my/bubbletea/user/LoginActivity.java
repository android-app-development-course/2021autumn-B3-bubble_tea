package com.my.bubbletea.user;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.my.bubbletea.R;

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
                submitLogin.setEnabled(false);
            }
        });
    }
}