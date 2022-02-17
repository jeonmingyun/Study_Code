package com.example.yogiyo.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yogiyo.R
import com.example.yogiyo.adapter.UserLikeListAdapter
import com.example.yogiyo.db.DbOpenHelper
import com.example.yogiyo.db.DbTable
import com.example.yogiyo.vo.UserVo

class UserLikeFrag : BaseFrag() {
    private lateinit var dbHelper: DbOpenHelper
    private lateinit var userLikeListAdapter: UserLikeListAdapter

    private lateinit var mView: View
    private lateinit var userList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.frag_user_like, container, false)
        dbHelper = DbOpenHelper.getInstance(requireContext())!!
        userList = mView.findViewById(R.id.user_list)

        return mView
    }

    override fun onResume() {
        super.onResume()
        initUserList()
        userLikeListAdapter.updateItems(selectAllUsers())
    }

    // db 검색
    private fun selectAllUsers() : ArrayList<UserVo> {
        val cursor = dbHelper.selectAllUsers()

        val users = ArrayList<UserVo>()
        while(cursor.moveToNext()) {
            val login = cursor.getString(cursor.getColumnIndexOrThrow(DbTable.Users.COLUMN_LOGIN))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DbTable.Users.COLUMN_ID))
            val nodeId = cursor.getString(cursor.getColumnIndexOrThrow(DbTable.Users.COLUMN_NODE_ID))
            val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DbTable.Users.COLUMN_AVATAR_URL))
            val htmlUrl = cursor.getString(cursor.getColumnIndexOrThrow(DbTable.Users.COLUMN_HTML_URL))
            val user = UserVo(login, id, nodeId, avatarUrl, htmlUrl)
            users.add(user)
        }
        return users
    }

    private fun initUserList() {
        userLikeListAdapter = UserLikeListAdapter(requireContext(), ArrayList<UserVo>())
        val lm = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        userList.layoutManager = lm
        val deco = DividerItemDecoration(requireContext(), lm.orientation)
        userList.addItemDecoration(deco)
        userList.adapter = userLikeListAdapter
    }

    fun searchUserAction(searchData : String) {
        userLikeListAdapter.getFilter()?.filter(searchData)
    }
}