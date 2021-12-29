package com.my.bubbletea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class Attachment {
    public String url;
    public String name;
}

class Note1 {
    //private String photo;
    public String id;
    public String title;
    public List<ParseFile> attachments;
    public ParseObject publisher;

    Note1(String i, String t, List<ParseFile> l, ParseObject user) {
        id = i;
        title = t;
        //content = c;
        attachments = l;
        publisher = user;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Note1> favouriteList;

    public MyAdapter(Context concer, ArrayList<Note1> f) {
        this.context = concer;
        this.favouriteList = f;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);//加载view
        return new ViewHolder(fview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note1 note = favouriteList.get(position);
        try {
            holder.publisherText.setText(note.publisher.fetchIfNeeded().getString("nickname"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView collectionimg;
        private ImageView avatar;
        private CardView fcardview;
        private TextView publisherText;
        private ImageButton favbutton;


        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            collectionimg = itemView.findViewById(R.id.image);
            avatar = itemView.findViewById(R.id.publish_pic);
            fcardview = itemview.findViewById(R.id.moment_card);
            publisherText = itemview.findViewById(R.id.id);
            favbutton = itemview.findViewById(R.id.favbutton);
        }
    }
}

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView FavoriteListView;
    public Vector<Note1> cacheFavourite= new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        FavoriteListView = findViewById(R.id.id_div);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        getMoment();
        FavoriteListView.setLayoutManager(gridLayoutManager);
        FavoriteListView.setAdapter(new MyAdapter(this,new ArrayList(cacheFavourite)));
//        notes = new ArrayList<>();
//        String url = "https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png";
//        for (int i=1;i<=3;i++)
//        {
//            Note1 note=new Note1();
//            //note.setId();
//            notes.add(url, "第"+i+"个名字");
//
//        }
//        mMyAdapter = new FavouriteActivity.MyAdapter(this.getContext(), new ArrayList(cacheFavourite));
//        FavoriteListView.setAdapter(mMyAdapter);
//        FavoriteListView.setLayoutManager(new GridLayoutManager(this, 2));
        getFavourite();
    }

    public void getFavourite() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Favourite");
        query.setLimit(5);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> favouriteList, ParseException e) {
                if (e == null) {
                    cacheFavourite.clear();
                    for(int i=0;i<favouriteList.size();i++) {
                        cacheFavourite.add(new Note1(
                                favouriteList.get(i).getObjectId(),
                                favouriteList.get(i).getString("title"),
                                //favouriteList.get(i).getString("content"),
                                favouriteList.get(i).getList("attachments"),
                                favouriteList.get(i).getParseObject("publisher")
                        ));
                        try {
                            // 估计这个是没有cache到Object里，所以要从server端fetch一次......考虑一下需不需要存下来吧。
                            cacheFavourite.get(i).publisher.fetch();
//                            Log.e("Publisher:",cacheMoments.get(i).publisher.getString("nickname"));
//                            Log.e("Publisher's avatarUrl:",cacheMoments.get(i).publisher.getParseFile("avatar").getUrl());

                        } catch (ParseException parseException) {
                            Log.e("ERR",parseException.getMessage());
                            parseException.printStackTrace();
                        }
                        List<ParseFile> l = favouriteList.get(i).getList("attachments");
                        for(int j=0;j<l.size();j++) {
                            // 图片附件的URL
                            // getFile() 可以返回file，参照：https://parseplatform.org/Parse-SDK-Android/api/com/parse/ParseFile.html
//                            Log.e("attachments url:",l.get(j).getUrl());
                        }
                    }
                    Log.e("Obejct retrived:", String.valueOf(cacheFavourite.size()));
                } else {
                    Log.d("Favourite", "Error: " + e.getMessage());
                    ParseUser.logOut();

                }

//                Log.e("Debug",String.valueOf(i));
//                Toast.makeText(mView.getContext(),"clicked"+String.valueOf(i),Toast.LENGTH_SHORT).show();


                FavoriteListView.setAdapter(new MyAdapter(FavoriteListView.getContext() ,new ArrayList(cacheFavourite)));


            }
        });

    }
}



//    public  static class MyViewHolder{
//        TextView id;
//    }

