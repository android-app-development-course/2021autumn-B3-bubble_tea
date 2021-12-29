package com.my.bubbletea;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class CAttachment {
    public String url;
    public String name;
}

class CNote1 {
    //private String photo;
    public String id;
    public String title;
    public List<ParseFile> attachments;
    public ParseObject publisher;

    CNote1(String i, String t, List<ParseFile> l, ParseObject user) {
        id = i;
        title = t;
        //content = c;
        attachments = l;
        publisher = user;
    }
}

class CMyAdapter extends RecyclerView.Adapter<CMyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CNote1> favouriteList;

    public CMyAdapter(Context concer, ArrayList<CNote1> f) {
        this.context = concer;
        this.favouriteList = f;
    }

    @NonNull
    @Override
    public CMyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);//加载view
        return new ViewHolder(fview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CNote1 note = favouriteList.get(position);
        try {
            holder.publisherText.setText(note.publisher.fetchIfNeeded().getString("nickname"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String url =note.publisher.fetchIfNeeded().getParseFile("avatar").getUrl();
            Picasso.get().load(url).into(holder.avatar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i=0;i<note.attachments.size();i++) {
            if (i==0) Picasso.get().load(note.attachments.get(0).getUrl()).into(holder.collectionimg);
            else if(i==1) Picasso.get().load(note.attachments.get(1).getUrl()).into(holder.collectionimg);
            else if(i==2) Picasso.get().load(note.attachments.get(2).getUrl()).into(holder.collectionimg);
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

public class CollectActivity extends AppCompatActivity {
    private RecyclerView FavoriteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        FavoriteListView = findViewById(R.id.id_div);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        getMoment();
        getFavourite();
        FavoriteListView.setLayoutManager(gridLayoutManager);
        FavoriteListView.setAdapter(new CMyAdapter(this, new ArrayList(cacheFavourite)));
//        Toast.makeText(this,"111",Toast.LENGTH_SHORT).show();
//        notes = new ArrayList<>();
//        String url = "https://milk.app.moe.yt:233/files/milktea/d5476befbc7b9c92c7e0e063440e3213_CleanShot_2021-12-18_at_20.35.41.png";
//        for (int i=1;i<=3;i++)
//        {
//            Note1 note=new Note1()
//            ;
//            //note.setId();
//            notes.add(url, "第"+i+"个名字");
//
//        }
//        mMyAdapter = new FavouriteActivity.MyAdapter(this.getContext(), new ArrayList(cacheFavourite));
//        FavoriteListView.setAdapter(mMyAdapter);
//        FavoriteListView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public Vector<CNote1> cacheFavourite= new Vector<>();
    public void getFavourite() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Moment");
        query.setLimit(5);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> favouriteList, ParseException e) {
                if (e == null) {
                    cacheFavourite.clear();
                    for(int i=0;i<favouriteList.size();i++) {
                        cacheFavourite.add(new CNote1(
                                favouriteList.get(i).getObjectId(),
                                favouriteList.get(i).getString("title"),
                                //favouriteList.get(i).getString("content"),
                                favouriteList.get(i).getList("attachments"),
                                favouriteList.get(i).getParseObject("publisher")
                        ));
                        try {
                            // 估计这个是没有cache到Object里，所以要从server端fetch一次......考虑一下需不需要存下来吧。
                            cacheFavourite.get(i).publisher.fetch();
                            //Log.e("Publisher:",cacheFavourite.get(i).publisher.getString("nickname"));
                            //Log.e("Publisher's avatarUrl:",cacheFavourite.get(i).publisher.getParseFile("avatar").getUrl());

                        } catch (ParseException parseException) {
                            Log.e("ERR",parseException.getMessage());
                            parseException.printStackTrace();
                        }
                        List<ParseFile> l = favouriteList.get(i).getList("attachments");
                        for(int j=0;j<l.size();j++) {
                            // 图片附件的URL
                            // getFile() 可以返回file，参照：https://parseplatform.org/Parse-SDK-Android/api/com/parse/ParseFile.html
                            //Log.e("attachments url:",l.get(j).getUrl());
                        }
                    }
                    Log.e("Obejct retrived:", String.valueOf(cacheFavourite.size()));
                } else {
                    Log.d("Favourite", "Error: " + e.getMessage());
                    ParseUser.logOut();

                }

//                Log.e("Debug",String.valueOf(i));
//                Toast.makeText(mView.getContext(),"clicked"+String.valueOf(i),Toast.LENGTH_SHORT).show();


                FavoriteListView.setAdapter(new CMyAdapter(FavoriteListView.getContext() ,new ArrayList(cacheFavourite)));


            }
        });

    }
}



//    public  static class MyViewHolder{
//        TextView id;
//    }

