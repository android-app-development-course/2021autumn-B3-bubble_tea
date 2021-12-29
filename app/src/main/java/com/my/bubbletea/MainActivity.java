package com.my.bubbletea;

import static com.esafirm.imagepicker.features.ImagePickerLauncherKt.createImagePickerIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerConfig;
import com.esafirm.imagepicker.features.ImagePickerMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.my.bubbletea.conversation.Conversation;
import com.my.bubbletea.fragments.HomeFragment;
import com.my.bubbletea.fragments.MomentFragment;
import com.my.bubbletea.fragments.ProfileFragment;
import com.my.bubbletea.user.LoginActivity;

import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_IMAGE_PICKER = 114;
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
//        Intent it = new Intent(this, Conversation.class);
//        startActivity(it);
   }


    private void changeCurrentFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_wrapper, targetFragment.getClass(), null);
        transaction.commit();
    }

}