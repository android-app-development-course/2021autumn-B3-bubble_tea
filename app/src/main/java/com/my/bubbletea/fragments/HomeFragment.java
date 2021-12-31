package com.my.bubbletea.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView view1, view2, view3, view4, view5, view6;
    //private GridView grid_photo;
    //private BaseAdapter mAapter=null;
    //private ArrayList<Note_recommand> mData=null;
    private EditText mEditText;
    private ListView mListView;
    private TextView mTextView;
    Context context;
    Cursor cursor;
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
        // context=this;
        //initView();
        //grid_photo=(GridView) findViewById(R.id.grid_photo);
        //mData=new ArrayList<Note_recommand>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view1 = (ImageView) view.findViewById(R.id.home_midrightbg1);
        view2 = (ImageView) view.findViewById(R.id.home_midrightbg2);
        view3 = (ImageView) view.findViewById(R.id.view3);
        view4 = (ImageView) view.findViewById(R.id.view4);
        view5 = (ImageView) view.findViewById(R.id.view5);
        view6 = (ImageView) view.findViewById(R.id.view6);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/6c8876b8ec9ba07b6b52ea9021ce9681_1.png").into(view1);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/8ea28f06f57a604bf42c817f7f2ef366_2.png").into(view2);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/2f482e4a56ecc9c72a2ffafbe2f56ba4_3.png").into(view3);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/3ea1a3c83dd10f735856f502bab17a6d_4.png").into(view4);
        Picasso.get().load("https://milk.app.moe.yt:233/files/milktea/1156a3d20f37d6a92d654d26f8c6314c_5.png").into(view5);
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
        mTextView = (TextView) view.findViewById(R.id.searchbtn);
        mEditText = (EditText) view.findViewById(R.id.home_search);
        mListView = (ListView) view.findViewById(R.id.listview);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){

                }else{
                    //myhandler post(changed);
                    showListView(view);
                }
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //如果输入框内容为空，提示请输入搜索内容
                if (TextUtils.isEmpty(mEditText.getText().toString().trim())) {
                    //ToastUtils.showToast(context,"请输入您要搜索的内容");
                } else {
                    //判断cursor是否为空
                    if (cursor != null) {
                        int columnCount = cursor.getCount();
                        if (columnCount == 0) {
                            //ToastUtils.showToast(context, "对不起，没有你要搜索的内容");
                        }
                    }
                }

            }

        });
        return view;
    }
        public void showListView(View view){
            mListView.setVisibility(View.VISIBLE);
            //获得输入的内容
            String str = mEditText.getText().toString().trim();
            String[] data={"一点点","四季奶青","冰淇淋红茶","满杯红柚","奶茶三兄弟"};
            String[] data1={};
            ArrayAdapter<String> adapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,data);
            if (str==null)
            {
                adapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,data1);
            }

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String result=((TextView)view).getText().toString();
                }
            });

            //获取数据库对象
//            MyOpenHelper myOpenHelper = new MyOpenHelper(context.getApplicationContext());
//            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
//            //得到cursor
//            cursor = db.rawQuery("select * from lol where name like '%" + str + "%'", null);
//            MyListViewCursorAdapter adapter = new MyListViewCursorAdapter(context, cursor);

            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //把cursor移动到指定行
                    cursor.moveToPosition(position);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    //ToastUtils.showToast(context, name);
                }
            });
        }


    }

