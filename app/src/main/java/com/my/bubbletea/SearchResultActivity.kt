package com.my.bubbletea

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity

import com.parse.*

internal class Moment(
    var id: String,
    var title: String,
    var content: String,
    var attachments: List<ParseFile>,
    var publisher: ParseObject,
    var isLiked: Boolean,
    var isCollected: Boolean
)

internal class MomentAdapter(
    private val context: Context,
    private val momentList: java.util.ArrayList<Moment>
) :
    RecyclerView.Adapter<com.my.bubbletea.MomentAdapter.Viewholder>(), Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("momentList")
    ) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val rowView =
            LayoutInflater.from(parent.context).inflate(R.layout.moment_item, parent, false)
        return Viewholder(rowView)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val model = momentList[position]
        try {
            holder.publisherText.text =
                model.publisher.fetchIfNeeded<ParseObject>().getString("nickname")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.contentText.text = model.content
        //        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("pos",String.valueOf(position));
//            }
//        });

        // 点赞
        holder.like_button.setOnClickListener(View.OnClickListener { view ->
            val currentUser = ParseUser.getCurrentUser()
            if (currentUser == null) {
                Toast.makeText(view.context, "没登录", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val momentId = momentList[position].id
            val query = ParseQuery.getQuery<ParseObject>("Moment")
            query.getInBackground(momentId,
                GetCallback { `object`, e ->
                    if (e == null) {
                        var l: java.util.ArrayList<ParseObject>? = null
                        try {
                            val tmpList = currentUser.fetch().getList<ParseObject>("likes")
                            l = if (tmpList == null) {
                                java.util.ArrayList()
                            } else {
                                java.util.ArrayList(tmpList)
                            }
                        } catch (parseException: ParseException) {
                            parseException.printStackTrace()
                        }
                        for (i in l!!.indices) {
                            if (l[i].objectId == `object`.objectId) {
                                Toast.makeText(view.context, "点赞过了", Toast.LENGTH_SHORT).show()
                                return@GetCallback
                            }
                        }
                        l.add(`object`)
                        currentUser.put("likes", l)
                        currentUser.saveInBackground { e ->
                            if (e == null) {
                                Toast.makeText(view.context, "点赞成功.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    view.context,
                                    "like failed....",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(view.context, "like failed....", Toast.LENGTH_SHORT).show()
                    }
                })
        })
        // 收藏
        holder.collect_button.setOnClickListener(View.OnClickListener { view ->
            val currentUser = ParseUser.getCurrentUser()
            if (currentUser == null) {
                Toast.makeText(view.context, "没登录", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val momentId = momentList[position].id
            val query = ParseQuery.getQuery<ParseObject>("Moment")
            query.getInBackground(momentId,
                GetCallback { `object`, e ->
                    if (e == null) {
                        var l: java.util.ArrayList<ParseObject>? = null
                        try {
                            val tmpList = currentUser.fetch().getList<ParseObject>("collections")
                            l = if (tmpList == null) {
                                java.util.ArrayList()
                            } else {
                                java.util.ArrayList(tmpList)
                            }
                        } catch (parseException: ParseException) {
                            parseException.printStackTrace()
                        }
                        for (i in l!!.indices) {
                            if (l!![i].objectId == `object`.objectId) {
                                Toast.makeText(view.context, "收藏过了", Toast.LENGTH_SHORT).show()
                                return@GetCallback
                            }
                        }
                        l!!.add(`object`)
                        currentUser.put("collections", l!!)
                        currentUser.saveInBackground { e ->
                            if (e == null) {
                                Toast.makeText(
                                    view.context,
                                    "collect success.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    view.context,
                                    "collect failed....",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(view.context, "collect failed....", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        })
        holder.comment_button.setOnClickListener(View.OnClickListener { view ->
            val currentUser = ParseUser.getCurrentUser()
            if (currentUser == null) {
                Toast.makeText(view.context, "没登录", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val it = Intent(view.context, CommentActivity::class.java)
            it.putExtra("objectID", momentList[position].id)
            view.context.startActivity(it)
        })



    }

    override fun getItemCount(): Int {
        return momentList.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentText: TextView
        val publisherText: TextView
        private val cardView: CardView
        val like_button: Button
        val collect_button: Button
        val comment_button: Button

        init {
            contentText = itemView.findViewById(R.id.moment_content)
            publisherText = itemView.findViewById(R.id.moment_publisher)
            cardView = itemView.findViewById(R.id.moment_card)
            like_button = itemView.findViewById(R.id.like_button)
            collect_button = itemView.findViewById(R.id.collect_button)
            comment_button = itemView.findViewById(R.id.comment_button)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MomentAdapter> {
        override fun createFromParcel(parcel: Parcel): MomentAdapter {
            return MomentAdapter(parcel)
        }

        override fun newArray(size: Int): Array<MomentAdapter?> {
            return arrayOfNulls(size)
        }
    }
}


class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val cacheMoments:ArrayList<Moment> = ArrayList()
        val momentListView: RecyclerView = findViewById(R.id.resultList)
        val linearLayoutManager: LinearLayoutManager = @SuppressLint("RestrictedApi")
        object : LinearLayoutManager(
            this,
            VERTICAL, false
        ) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        momentListView.layoutManager = linearLayoutManager
        val input = intent.getStringExtra("param")
        val queries: MutableList<ParseQuery<ParseObject>> = ArrayList()
        queries.add(ParseQuery.getQuery<ParseObject>("Moment").whereContains("title", input))
        queries.add(ParseQuery.getQuery<ParseObject>("Moment").whereContains("content", input))
        val mainQuery = ParseQuery.or(queries)
        momentListView.adapter = MomentAdapter(this, cacheMoments)
        try {
            mainQuery.findInBackground { momentList, e ->
                cacheMoments.clear()
                if(momentList != null) {
                    momentList.forEach { it ->
                        if(it != null) {
                            cacheMoments.add(
                                Moment(
                                    it.getObjectId(),
                                    it.getString("title")!!,
                                    it.getString("content")!!,
                                    it.getList<ParseFile>("attachments")!!,
                                    it.getParseObject("publisher")!!,
                                    false,
                                    false
                                )
                            )
                        }


                    }
                }

                momentListView.adapter = MomentAdapter(this, cacheMoments)

            }

        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}