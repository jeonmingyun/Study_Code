package com.example.ex_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class ListAdapter(private var mContext : Context, private var itemList : ArrayList<TestVo>) : Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View =  LayoutInflater.from(mContext).inflate(R.layout.item_test_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : TestVo = itemList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val name : TextView = itemView.findViewById(R.id.name)
        private val age : TextView = itemView.findViewById(R.id.age)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item : TestVo) {
            name.text = item.name
            age.text = item.age.toString()
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if(view == itemView) {
                Toast.makeText(mContext, "$position item click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}