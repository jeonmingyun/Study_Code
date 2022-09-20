package com.min.test

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.min.test.databinding.ActivityMainBinding


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setToolbar(this, binding.includeAppBar.topAppBar)

        // open SecondActivity
        binding.btnMain.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        binding.testText.customSelectionActionModeCallback = MyCallback(this, binding.testText)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

        when (item.itemId) {
            android.R.id.home -> {
                // 왼쪽 상단 버튼 눌렀을 때
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.setChecked(true)
        binding.drawerLayout.closeDrawers()

        val id: Int = item.getItemId()
        val title: String = item.getTitle().toString()

        if (id == R.id.favorite) {
            showToast(title + ": favorite 정보를 확인합니다.")
        } else if (id == R.id.search) {
            showToast(title + ": search 정보를 확인합니다.")
        } else if (id == R.id.more) {
            showToast(title + ": more 정보를 확인합니다.")
        }

        return true
    }

//    inner class MyCallback(tv: TextView) : ActionMode.Callback {
//        var mTextView: TextView = tv
//
//        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {;
//            // 선택 옵션 새로 만듬
//            mode!!.menuInflater.inflate(R.menu.highlight_menu, menu);
//
//            return true;
//        }
//
//        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//            // 기존 선택 옵션 제거
//            menu!!
//            menu.removeItem(android.R.id.selectAll);
//            menu.removeItem(android.R.id.cut);
//            menu.removeItem(android.R.id.copy);
//            menu.removeItem(android.R.id.shareText);
//
//            return true;
//        }
//
//        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
//            val start = mTextView.selectionStart
//            val end = mTextView.selectionEnd
//            val wordToSpan: Spannable = mTextView.text as Spannable
//
//            when (item!!.itemId) {
//                R.id.highlight -> {
//                    wordToSpan.setSpan(
//                        BackgroundColorSpan(resources.getColor(R.color.purple_500)),
//                        start,
//                        end,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                    mTextView.text = wordToSpan
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        override fun onDestroyActionMode(mode: ActionMode?) {
//
//        }
//    }
}