package com.min.test

import android.content.Context
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class MyCallback(context: Context, tv: TextView) : ActionMode.Callback {
    var mTextView: TextView = tv
    var mContext: Context = context

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {;
        // 선택 옵션 새로 만듬
        mode!!.menuInflater.inflate(R.menu.highlight_menu, menu);

        return true;
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // 기존 선택 옵션 제거
        menu!!
        menu.removeItem(android.R.id.selectAll);
        menu.removeItem(android.R.id.cut);
        menu.removeItem(android.R.id.copy);
        menu.removeItem(android.R.id.shareText);

        return true;
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        val start = mTextView.selectionStart
        val end = mTextView.selectionEnd
        val wordToSpan: Spannable = mTextView.text as Spannable

        // highlight 메뉴 선택시 배경색 변경
        when (item!!.itemId) {
            R.id.highlight -> {
                wordToSpan.setSpan(
                    BackgroundColorSpan(mContext.resources.getColor(R.color.purple_500)),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                mTextView.text = wordToSpan
                return true;
            }
        }
        return false;
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }
}