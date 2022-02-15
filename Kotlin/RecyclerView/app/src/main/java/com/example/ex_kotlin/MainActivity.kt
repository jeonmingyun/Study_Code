package com.example.ex_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ex_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var adapter: ListAdapter
    private val itemList = arrayListOf<TestVo>(
        TestVo("name1", 10),
        TestVo("name2", 20),
        TestVo("name3", 30),
        TestVo("name4", 40),
        TestVo("name5", 50)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTestList(itemList)
    }

    private fun initTestList(itemList: ArrayList<TestVo>) {
        adapter = ListAdapter(this, itemList)
        // RecyclerView layout 지정
        val lm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.testList.layoutManager = lm
        // item 사이 간격
        val deco = DividerItemDecoration(this, lm.orientation)
        deco.setDrawable(getDrawable(R.drawable.divider)!!)
        binding.testList.addItemDecoration(deco)

        binding.testList.adapter = adapter
    }

}