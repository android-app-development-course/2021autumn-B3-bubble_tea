package com.my.bubbletea;

import static com.esafirm.imagepicker.features.ImagePickerLauncherKt.createImagePickerIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.Menu;
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

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
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("milktea")
                .server("https://milk.app.moe.yt:233/").enableLocalDataStore()
                .build()
        );

        // three fragment match three page accordingly.
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        momentFragment = new MomentFragment();

//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.icon_home:
//                        changeCurrentFragment(homeFragment);
//                        break;
//                    case R.id.icon_moment:
//                        changeCurrentFragment(momentFragment);
//                        break;
//                    case R.id.icon_profile:
//                        // should be `profileFragment`
//                        changeCurrentFragment(profileFragment);
//                        break;
//                }
//                return false;
//            }
//        });

        ExpandableBottomBar bottomBar= findViewById(R.id.expandable_bottom_bar);
        Menu menu = bottomBar.getMenu();

        bottomBar.setOnItemSelectedListener(new Function3<View, github.com.st235.lib_expandablebottombar.MenuItem, Boolean, Unit>() {
            @Override
            public Unit invoke(View view, github.com.st235.lib_expandablebottombar.MenuItem menuItem, Boolean aBoolean) {
                Log.e("id", String.valueOf(menuItem.getId()));
                switch (menuItem.getId()) {
                    case R.id.navi_home:
                        changeCurrentFragment(homeFragment);
                        break;
                    case R.id.navi_moment:
                        changeCurrentFragment(momentFragment);
                        break;
                    case R.id.navi_profile:
                        // should be `profileFragment`
                        changeCurrentFragment(profileFragment);
                        break;
                }
                return null;
            }
        });


//        menu.add(
//                new MenuItemDescriptor.Builder(
//                        this,
//                        R.id.icon_home,
//                        R.drawable.round_home_black_24dp,
//                        R.string.navigation_home,
//                        Color.GRAY
//                )
//                        .build()
//        );
//        menu.add(
//                new MenuItemDescriptor.Builder(
//                        this,
//                        R.id.icon_home,
//                        R.drawable.round_home_black_24dp,
//                        R.string.navigation_home,
//                        Color.GRAY
//                )
//                        .build()
//        );menu.add(
//                new MenuItemDescriptor.Builder(
//                        this,
//                        R.id.icon_home,
//                        R.drawable.round_home_black_24dp,
//                        R.string.navigation_home,
//                        Color.GRAY
//                )
//                        .build()
//        );

//        bottomBar.onItemSelectedListener = { view, menuItem ->
//                /**
//                 * handle menu item clicks here,
//                 * but clicks on already selected item will not affect this callback
//                */
//        }
//
//        bottomBar.onItemReselectedListener = { view, menuItem ->
//                /**
//                 * handle here all the click in already selected items
//                */
//        }


        changeCurrentFragment(homeFragment);
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