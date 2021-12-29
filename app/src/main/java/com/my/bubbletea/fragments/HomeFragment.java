package com.my.bubbletea.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.my.bubbletea.DescribeActivity;
import com.my.bubbletea.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ImageView view1,view2,view3,view4,view5,view6;
    //private GridView grid_photo;
    //private BaseAdapter mAapter=null;
    //private ArrayList<Note_recommand> mData=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        //grid_photo=(GridView) findViewById(R.id.grid_photo);
        //mData=new ArrayList<Note_recommand>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view1=(ImageView) view.findViewById(R.id.home_midrightbg1);
        view2=(ImageView) view.findViewById(R.id.home_midrightbg2);
        view3=(ImageView) view.findViewById(R.id.view3);
        view4=(ImageView) view.findViewById(R.id.view4);
        view5=(ImageView) view.findViewById(R.id.view5);
        view6=(ImageView) view.findViewById(R.id.view6);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view1);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view2);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view3);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view4);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view5);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png").into(view6);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DescribeActivity.class);
                startActivity(it);
            }
        });
        //btn1 = view.findViewById(R.id.home_midrightbg1);


        return view;
    }
    //private void turn_descrbe()
}
