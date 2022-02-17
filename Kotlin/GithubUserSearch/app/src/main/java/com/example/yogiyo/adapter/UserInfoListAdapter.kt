package com.example.yogiyo.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yogiyo.R
import com.example.yogiyo.db.DbOpenHelper
import com.example.yogiyo.vo.UserVo


class UserInfoListAdapter(
    private var mContext: Context,
    private var itemList: ArrayList<UserVo>
) :
    RecyclerView.Adapter<UserInfoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: UserVo = itemList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addAllItems(items: ArrayList<UserVo>) {
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItems(items: ArrayList<UserVo>) {
        this.itemList.clear()
        this.itemList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val userImg: ImageView = itemView.findViewById(R.id.user_img)
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val likeBtn: CheckBox = itemView.findViewById(R.id.like_btn)

        init {
            likeBtn.setOnClickListener(this)
        }

        fun bind(item: UserVo) {
            userName.text = item.login

            if (TextUtils.isEmpty(item.avatar_url)) {
                userImg.setImageResource(R.drawable.no_img)
            } else {
                Glide.with(mContext).load(item.avatar_url).into(userImg);
            }

            if (item.isChecked) {
                likeBtn.setBackgroundResource(R.drawable.btn_like_on)
            } else {
                likeBtn.setBackgroundResource(R.drawable.btn_like_off)
            }
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (view == likeBtn) {
                val item = itemList[position]
                val helper = DbOpenHelper.getInstance(mContext)

                itemList[position].isChecked = !itemList[position].isChecked
                if (itemList[position].isChecked) {
                    helper!!.insertOrReplaceUser(
                        item.login,
                        item.id,
                        item.node_id,
                        item.avatar_url,
                        item.html_url
                    )
                    likeBtn.setBackgroundResource(R.drawable.btn_like_on)
                } else {
                    helper!!.deleteUser(item.login)
                    likeBtn.setBackgroundResource(R.drawable.btn_like_off)
                }
            }
        }

    }
}

