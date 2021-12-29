package com.my.bubbletea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class LikeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    private RecyclerView mRecyclerView2;
    private MyAdapter mMyAdapter;
    private List<Note> notes;
    private MaterialButton saveButton;
    private EditText flavorInput;
//    private List<Note> notes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        mRecyclerView=findViewById(R.id.rlv);
        saveButton = findViewById(R.id.save_flavor);
        flavorInput = findViewById(R.id.input_flavor);

//        mRecyclerView2=findViewById(R.id.rlv2);
        notes=new ArrayList<>();
//        notes1=new ArrayList<>();
        //为它设置好多个button布局
//        for (int i=1;i<=10;i++)
//        {
//            Note note=new Note();
//            note.setText("配料"+i);
////            note.setText1("冰度"+i);
////            note.setText2("糖度"+i);
////            note.setText3("茶底"+i);
//            notes.add(note);
//        }
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this,"Not logged in",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.e("current",currentUser.getString("username"));
            List<String> flavors = null;
            try {
                flavors = currentUser.fetch().getList("flavor");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (flavors != null) {
                Log.e("not emptyflavors", String.valueOf(flavors.size()));
                for(String f:flavors) {
                    Note n = new Note();
                    n.setText(f);
                    notes.add(n);
                }
            } else {
                Log.e("empty flavors", "emt" );
                flavors = new ArrayList<String>();
            }
        }
        mRecyclerView.setAdapter(mMyAdapter);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setEnabled(false);
                String flavor = flavorInput.getText().toString();
                if(flavor.isEmpty()) {
                    saveButton.setEnabled(true);
                    return;
                }

                Note n =new Note();
                n.setText(flavor);
                notes.add(n);

                ArrayList<String> flavors = null;
                try {
                    List<String> l = currentUser.fetch().getList("flavor");
                    if (l == null ) {
                        flavors = new ArrayList<>();
                    } else {
                        flavors = new ArrayList<>(l);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                flavors.add(flavor);
                currentUser.put("flavor",flavors);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        saveButton.setEnabled(true);
                        if(e == null) {
                            Toast.makeText(LikeActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            flavorInput.setText("");
                            mMyAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(LikeActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
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
            holder.btn1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        ArrayList<String> flavors = null;
                        try {
                            flavors = new ArrayList<>(currentUser.fetch().getList("flavor"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        flavors.remove(position);
                        currentUser.put("flavor",flavors);
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e == null) {
                                    Toast.makeText(LikeActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                    notes.remove(position);
                                } else {
                                    Toast.makeText(LikeActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }



                    return false;
                }
            });
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