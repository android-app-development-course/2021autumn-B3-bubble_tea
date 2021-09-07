package com.my.bubbletea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.my.bubbletea.fragments.HomeFragment;
import com.my.bubbletea.fragments.MomentFragment;
import com.my.bubbletea.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment profileFragment;
    Fragment momentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment= new HomeFragment();
        profileFragment= new ProfileFragment();
        momentFragment= new MomentFragment();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_wrapper,HomeFragment.class,null);
        transaction.commit();
    }
}