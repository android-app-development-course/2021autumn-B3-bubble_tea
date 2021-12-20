package com.my.bubbletea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CollectActivity.MyAdapter mMyAdapter;
    private List<Note1> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        mRecyclerView=findViewById(R.id.id_div);
        notes=new ArrayList<>();
        for (int i=1;i<=3;i++)
        {
            Note1 note=new Note1();
            note.setId("第"+i+"个名字");
            notes.add(note);
        }
        mMyAdapter = new CollectActivity.MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
    public class MyAdapter extends RecyclerView.Adapter<CollectActivity.MyHolder>{

        @NonNull
        @Override
        public CollectActivity.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(CollectActivity.this).inflate(R.layout.item_collect,parent,false);//加载view
            CollectActivity.MyHolder myHolder=new CollectActivity.MyHolder(view);//返回view
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CollectActivity.MyHolder holder, int position) {
            Note1 note=notes.get(position);
        }


        @Override
        public int getItemCount() {
            return notes.size();
        }
    }

    public  static class MyViewHolder{
        TextView id;
    }

    //RecyclerView  Holder写法
    public class MyHolder extends RecyclerView.ViewHolder{
        TextView id;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id1);

        }
    }
}