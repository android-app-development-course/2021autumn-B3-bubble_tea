package com.my.bubbletea.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.my.bubbletea.CommentActivity;
import com.my.bubbletea.DetailActivity;
import com.my.bubbletea.R;
import com.my.bubbletea.SearchResultActivity;
import com.my.bubbletea.UpgradeActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class Attachment {
    public String url;
    public String name;
}

class Moment {
    public String id;
    public String title;
    public String content;
    public List<ParseFile> attachments;
    public ParseObject publisher;
    public boolean isLiked;
    public boolean isCollected;
    Moment(String i,String t,String c,List<ParseFile> l,ParseObject user,boolean liked,boolean coll) {
        id = i;
        title = t;
        content = c;
        attachments = l;
        publisher = user;
        isLiked = liked;
        isCollected = coll;
    }
}

class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.Viewholder> {
    private Context context;
    private ArrayList<Moment> momentList;

    public MomentAdapter(Context c, ArrayList<Moment> m) {
        this.context = c;
        this.momentList = m;
    }

    @NonNull
    @Override
    public MomentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.moment_item,parent,false);

        return new Viewholder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Moment model = momentList.get(position);
        try {
            holder.publisherText.setText(model.publisher.fetchIfNeeded().getString("nickname"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.contentText.setText(model.content);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("pos",String.valueOf(position));
//            }
//        });

        // 点赞
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(currentUser == null) {
                    Toast.makeText(view.getContext(),"没登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                String momentId = momentList.get(position).id;
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Moment");

                query.getInBackground(momentId, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            ArrayList<ParseObject> l = null;
                            try {
                                List<ParseObject> tmpList = currentUser.fetch().getList("likes");
                                if(tmpList == null) {
                                    l = new ArrayList<>();
                                } else {
                                    l = new ArrayList<>(tmpList);
                                }
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                            for(int i=0;i<l.size();i++) {
                                if (l.get(i).getObjectId().equals(object.getObjectId())) {
                                    Toast.makeText(view.getContext(),"点赞过了",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            l.add(object);
                            currentUser.put("likes", l);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(view.getContext(),"点赞成功.",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(),"like failed....",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(view.getContext(),"like failed....",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        // 收藏
        holder.collect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(currentUser == null) {
                    Toast.makeText(view.getContext(),"没登录",Toast.LENGTH_SHORT).show();
                    return;
                }


                String momentId = momentList.get(position).id;
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Moment");

                query.getInBackground(momentId, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            ArrayList<ParseObject> l = null;
                            try {
                                List<ParseObject> tmpList = currentUser.fetch().getList("collections");
                                if(tmpList == null) {
                                    l = new ArrayList<>();
                                } else {
                                    l = new ArrayList<>(tmpList);
                                }
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }

                            for(int i=0;i<l.size();i++) {
                                if (l.get(i).getObjectId().equals(object.getObjectId())) {
                                    Toast.makeText(view.getContext(),"收藏过了",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            l.add(object);
                            currentUser.put("collections", l);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(view.getContext(),"collect success.",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(),"collect failed....",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(view.getContext(),"collect failed....",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(currentUser == null) {
                    Toast.makeText(view.getContext(),"没登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent it = new Intent(view.getContext(), CommentActivity.class);
                it.putExtra("objectID", momentList.get(position).id);
                view.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  {
        private TextView contentText;
        private TextView publisherText;
        private CardView cardView;

        private Button like_button;
        private Button collect_button;
        private Button comment_button;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            contentText = itemView.findViewById(R.id.moment_content);
            publisherText = itemView.findViewById(R.id.moment_publisher);
            cardView = itemView.findViewById(R.id.moment_card);

            like_button = itemView.findViewById(R.id.like_button);
            collect_button = itemView.findViewById(R.id.collect_button);
            comment_button = itemView.findViewById(R.id.comment_button);

        }

    }
}


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MomentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MomentFragment extends Fragment {
    private EditText mEditText;
    private ListView mListView;
    private ImageButton msearchbtn;
    Context context;
    Cursor cursor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isGetData=false;
    private View mView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private ImageButton turndetail;
    private ImageButton toupgrade;

    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.lunbopic1,
            R.drawable.lunbopic2,
            R.drawable.lunbopic3
    };
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    public MomentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MomentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MomentFragment newInstance(String param1, String param2) {
        MomentFragment fragment = new MomentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode != UpgradeActivity.RESULT_OK) {
//                    getMoment();
//                    return;
//                }
//                break;
//            default:
//                break;
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    RecyclerView momentListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_moment, container, false);
        setView();

        // 帖子的listview

        momentListView = mView.findViewById(R.id.momentList);
        EditText searchInput = mView.findViewById(R.id.searchInput);
        LinearLayoutManager linearLayoutManager =  new  LinearLayoutManager( getActivity(),
                LinearLayoutManager.VERTICAL,  false ) {

            @Override
            public boolean canScrollVertically() {
                return false;

            }
        };

        searchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    String input = searchInput.getText().toString();
                    Intent it = new Intent(view.getContext(), SearchResultActivity.class);
                    it.putExtra("param",input);
                    startActivity(it);




                    return true;
                }

                return false;
            }
        });

        momentListView.setLayoutManager(linearLayoutManager);
        momentListView.setAdapter(new MomentAdapter(mView.getContext(),new ArrayList(cacheMoments)));
        getMoment();
//        mEditText = (EditText) mView.findViewById(R.id.searchInput);
//        mListView = (ListView) mView.findViewById(R.id.listview);
//        msearchbtn= (ImageButton) mView.findViewById(R.id.mid_search);
//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.length()==0){
//                    //收起
//                }else{
//                    //myhandler post(changed);
//                    showListView(mView);
//                }
//            }
//        });
//        msearchbtn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //如果输入框内容为空，提示请输入搜索内容
//                if (TextUtils.isEmpty(mEditText.getText().toString().trim())) {
//                    //ToastUtils.showToast(context,"请输入您要搜索的内容");
//                } else {
//                    //判断cursor是否为空
//                    if (cursor != null) {
//                        int columnCount = cursor.getCount();
//                        if (columnCount == 0) {
//                            //ToastUtils.showToast(context, "对不起，没有你要搜索的内容");
//                        }
//                    }
//                }
//
//            }
//
//        });



        return mView;
    }

//    public void showListView(View view){
//        mListView.setVisibility(View.VISIBLE);
//        //获得输入的内容
//        String str = mEditText.getText().toString().trim();
//        String[] data={"一点点","四季奶青","冰淇淋红茶","满杯红柚","奶茶三兄弟"};
//        String[] data1={};
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,data);
//        if (str==null)
//        {
//            adapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,data1);
//        }
//
//        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String result=((TextView)view).getText().toString();
//            }
//        });
//
//        //获取数据库对象
////            MyOpenHelper myOpenHelper = new MyOpenHelper(context.getApplicationContext());
////            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
////            //得到cursor
////            cursor = db.rawQuery("select * from lol where name like '%" + str + "%'", null);
////            MyListViewCursorAdapter adapter = new MyListViewCursorAdapter(context, cursor);
//
//        mListView.setAdapter(adapter);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //把cursor移动到指定行
//                cursor.moveToPosition(position);
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                //ToastUtils.showToast(context, name);
//            }
//        });
//    }




    public Vector<Moment> cacheMoments = new Vector<>();

    // 获取Moment 的列表
    public void getMoment() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Moment");
       // int size=ParseQuery.getQuery("Moment").count();
        query.setLimit(150); // 只获取5个先，防止太卡
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> momentList, ParseException e) {
                if (e == null) {
                    cacheMoments.clear();
                    for(int i=0;i<6;i++) {
                        cacheMoments.add(new Moment(
                                momentList.get(momentList.size()-i-1).getObjectId(),
                                momentList.get(momentList.size()-i-1).getString("title"),
                                momentList.get(momentList.size()-i-1).getString("content"),
                                momentList.get(momentList.size()-i-1).getList("attachments"),
                                momentList.get(momentList.size()-i-1).getParseObject("publisher"),
                                false,
                                false
                        ));
                        try {
                            // 估计这个是没有cache到Object里，所以要从server端fetch一次......考虑一下需不需要存下来吧。
                            cacheMoments.get(i).publisher.fetch();
//                            Log.e("Publisher:",cacheMoments.get(i).publisher.getString("nickname"));
//                            Log.e("Publisher's avatarUrl:",cacheMoments.get(i).publisher.getParseFile("avatar").getUrl());

                        } catch (ParseException parseException) {
                            Log.e("ERR",parseException.getMessage());
                            parseException.printStackTrace();
                        }
                        List<ParseFile> l = momentList.get(i).getList("attachments");
                        for(int j=0;j<l.size();j++) {
                            // 图片附件的URL
                            // getFile() 可以返回file，参照：https://parseplatform.org/Parse-SDK-Android/api/com/parse/ParseFile.html
//                            Log.e("attachments url:",l.get(j).getUrl());
                        }
                    }
                    Log.e("Obejct retrived:", String.valueOf(cacheMoments.size()));
                } else {
                    Log.d("Moment", "Error: " + e.getMessage());
                    ParseUser.logOut();

                }

//                Log.e("Debug",String.valueOf(i));
//                Toast.makeText(mView.getContext(),"clicked"+String.valueOf(i),Toast.LENGTH_SHORT).show();


                momentListView.setAdapter(new MomentAdapter(getContext(),new ArrayList(cacheMoments)));

            }
        });

    }


    private void setView(){
//        SearchView searchView =  mView.findViewById(R.id.searchView);
///*        int magId = getResources().getIdentifier("android:id/search_close_btn",null, null);
//
//        ImageView magImage = (ImageView)mView.findViewById(magId);
//        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));*/
//        //mSearchView 你的searchview对象
//        ImageView searchButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
//        searchButton.setImageResource(R.drawable.find);
        mViewPaper = (ViewPager) mView.findViewById(R.id.vp);

        //turndetail= (ImageButton) mView.findViewById(R.id.turn_detail);
        toupgrade=(ImageButton)mView.findViewById(R.id.upgrade);
        this.onStop();
        this.onDetach();
        /*turndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DetailActivity.class);
                startActivity(it);
            }
        });*/
        toupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser == null) {
                    // 未登录
                    Toast.makeText(getContext(),"Not logged in",Toast.LENGTH_SHORT).show();
                } else {
                    // 登录了
                    Intent it = new Intent(view.getContext(), UpgradeActivity.class);
                    startActivity(it);
                }
            }
        });

        getMoment();

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));


        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.ic_dot_yes);
                dots.get(oldPosition).setBackgroundResource(R.drawable.ic_dot_no);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                3,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    @Override
    public Animation onCreateAnimation(int transit,boolean enter,int nextAnim){
        if(enter&&!isGetData){
            isGetData=true;
        }else{
            isGetData=false;
        }
        return super.onCreateAnimation(transit,enter,nextAnim);
    }
    @Override
    public void onPause(){
        super.onPause();
        isGetData=false;
    }

    /**
     * 接收子线程传递过来的数据
     */
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }
    @Override
    public void onResume(){
        if (!isGetData){
            getMoment();
            isGetData=true;
        }
        super.onResume();
    }

}