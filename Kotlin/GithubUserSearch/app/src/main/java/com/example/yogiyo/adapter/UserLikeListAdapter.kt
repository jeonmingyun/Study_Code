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


class UserLikeListAdapter(
    private var mContext: Context,
    private var originalItemList: ArrayList<UserVo> // 필터링 되지 않은 원본 데이터
) : RecyclerView.Adapter<UserLikeListAdapter.ViewHolder>(), Filterable {

    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private var filteredItemList = originalItemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: UserVo = filteredItemList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    fun addAllItems(items: ArrayList<UserVo>) {
        this.originalItemList.addAll(items)
        this.filteredItemList = this.originalItemList
        notifyDataSetChanged()
    }

    fun updateItems(items: ArrayList<UserVo>) {
        this.originalItemList.clear()
        this.originalItemList = items
        this.filteredItemList = this.originalItemList
        notifyDataSetChanged()
    }

    fun deleteItem(item: UserVo) {
        this.originalItemList.remove(item)
        this.filteredItemList.remove(item)
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

            item.isChecked = true
            likeBtn.setBackgroundResource(R.drawable.btn_like_on)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (view == likeBtn) {
                val item = filteredItemList[position]
                val helper = DbOpenHelper.getInstance(mContext)

                helper!!.deleteUser(item.login) //db에서 삭제
                deleteItem(item) // list에서 삭제
            }
        }
    }

    // 검색어 Filter
    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val filterString = constraint.toString().toUpperCase()

                val filterList = ArrayList<UserVo>()
                for (item in originalItemList) {
                    if (item.login.toUpperCase().contains(filterString)) {
                        filterList.add(item)
                    }
                }

                results.values = filterList
                results.count = filterList.size

                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredItemList = results.values as ArrayList<UserVo>
                notifyDataSetChanged()
            }
        }
    }
}

