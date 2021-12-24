package com.my.bubbletea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.my.bubbletea.fragments.HomeFragment;
import com.my.bubbletea.fragments.MomentFragment;
import com.my.bubbletea.fragments.ProfileFragment;
import com.my.bubbletea.user.LoginActivity;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private Fragment homeFragment;
    private Fragment profileFragment;
    private Fragment momentFragment;
    private BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("milktea")
                .server("https://milk.app.moe.yt:233/")
                .build()
        );

        // three fragment match three page accordingly.
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        momentFragment = new MomentFragment();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        changeCurrentFragment(homeFragment);
                        break;
                    case R.id.icon_moment:
                        changeCurrentFragment(momentFragment);
                        break;
                    case R.id.icon_profile:
                        // should be `profileFragment`
                        changeCurrentFragment(profileFragment);
                        break;
                }
                return false;
            }
        });



        changeCurrentFragment(momentFragment);
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        if (currentUser != null) {
//            // do stuff with the user
//
//        } else {
//            // show the signup or login screen
//        }


//        startActivity(new Intent(this, MainActivity.class));
    }


    private void changeCurrentFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_wrapper, targetFragment.getClass(), null);
        transaction.commit();
    }
}