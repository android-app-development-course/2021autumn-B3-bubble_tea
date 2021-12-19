package com.my.bubbletea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LikeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    private RecyclerView mRecyclerView2;
    private MyAdapter mMyAdapter;
    private List<Note> notes;
//    private List<Note> notes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        mRecyclerView=findViewById(R.id.rlv);
//        mRecyclerView2=findViewById(R.id.rlv2);
        notes=new ArrayList<>();
//        notes1=new ArrayList<>();
        //为它设置好多个button布局
        for (int i=1;i<=10;i++)
        {
            Note note=new Note();
            note.setText("配料"+i);
//            note.setText1("冰度"+i);
//            note.setText2("糖度"+i);
//            note.setText3("茶底"+i);
            notes.add(note);
        }
//        for (int i=1;i<=17;i++)
//        {
//            Note note=new Note();
//            note.setText("喜爱"+i);
////            note.setText1("冰度"+i);
////            note.setText2("糖度"+i);
////            note.setText3("茶底"+i);
//            notes1.add(note);
//        }

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));

    }
    public class MyAdapter extends RecyclerView.Adapter<MyHolder>{

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(LikeActivity.this).inflate(R.layout.item_letter,parent,false);//加载view
            MyHolder myHolder=new MyHolder(view);//返回view
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            Note note=notes.get(position);
            holder.btn1.setText(note.getText());
//            Note note1=notes1.get()
//            holder.btn2.setText(note.getText1());
//            holder.btn3.setText(note.getText2());
//            holder.btn4.setText(note.getText3());


        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }

    public  static class MyViewHolder{
        Button btn1;
       // Button btn2,btn3,btn4;
    }

    //RecyclerView  Holder写法
    public class MyHolder extends RecyclerView.ViewHolder{
        Button btn1;
//        Button btn2;
//        Button btn3;
//        Button btn4;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            btn1=itemView.findViewById(R.id.btn1);
//            btn2=itemView.findViewById(R.id.btn2);
//            btn3=itemView.findViewById(R.id.btn3);
//            btn4=itemView.findViewById(R.id.btn4);

        }
    }
}