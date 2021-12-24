package com.my.bubbletea.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.my.bubbletea.CollectActivity;
import com.my.bubbletea.ContactActivity;
import com.my.bubbletea.FavouriteActivity;
import com.my.bubbletea.LikeActivity;
import com.my.bubbletea.MessageActivity;
import com.my.bubbletea.MoreActivity;
import com.my.bubbletea.R;
import com.my.bubbletea.user.LoginActivity;
import com.my.bubbletea.user.RegisterActivity;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private MaterialButton loginButton;
    private MaterialButton intolikeButton;
    private MaterialButton intofavourite;
    private MaterialButton intomore;
    private MaterialButton intocollect;
    private MaterialButton intocontact;
    private MaterialButton intomessage;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        loginButton = (MaterialButton) view.findViewById(R.id.login_button);
        intolikeButton = (MaterialButton) view.findViewById(R.id.into_like);
        intofavourite=(MaterialButton) view.findViewById(R.id.into_favourite);
        intocollect=(MaterialButton) view.findViewById(R.id.into_collect);
        intocontact=(MaterialButton) view.findViewById(R.id.into_contact);
        intomessage=(MaterialButton) view.findViewById(R.id.into_message);
        intomore=(MaterialButton) view.findViewById(R.id.into_more);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(view.getContext(),"你都已经登录了还搁这登呢？",Toast.LENGTH_SHORT).show();
                } else {
                    Intent it = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(it);
                    // show the signup or login screen
                }


            }
        });
        loginButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ParseUser.logOut();
                Toast.makeText(view.getContext(),"Logged out",Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        intolikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), LikeActivity.class);
                startActivity(it);
            }
        });

        intofavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), FavouriteActivity.class);
                startActivity(it);
            }
        });
        intocollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), CollectActivity.class);
                startActivity(it);
            }
        });
        intocontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), ContactActivity.class);
                startActivity(it);
            }
        });
        intomore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), MoreActivity.class);
                startActivity(it);
            }
        });
        intomessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), MessageActivity.class);
                startActivity(it);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}