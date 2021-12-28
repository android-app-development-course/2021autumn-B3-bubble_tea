package com.my.bubbletea

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val currentUser = ParseUser.getCurrentUser();
        if(currentUser == null) {
            finish()
        }
        val ObjectId = intent.getStringExtra("objectID")
        if(ObjectId == null) finish()
        val comment_submit = findViewById<MaterialButton>(R.id.comment_submit)
        val comment_input = findViewById<EditText>(R.id.comment_input)
        comment_submit.setOnClickListener {
            view ->
            val comment = ParseObject("CommentModel")
            comment.put("publisher",currentUser)
            comment.put("content",comment_input.text.toString())
            comment.save();
            val query = ParseQuery.getQuery<ParseObject>("Moment")

            val moment = query.get(ObjectId);
            if(moment != null) {
                var list = moment.getList<ParseObject>("comments")
                if(list == null) {
                    list = ArrayList<ParseObject>()
                }
                list.add(comment);
                moment.put("comments",list)
                moment.saveInBackground {
                    e->
                    if(e == null) {
                        Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show()
                    } else {
                        // rollback..
                        comment.delete()
                    }
                }

            }
            finish()
        }

    }
}