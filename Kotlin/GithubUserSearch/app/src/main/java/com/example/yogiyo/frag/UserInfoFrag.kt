package com.example.yogiyo.frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yogiyo.R
import com.example.yogiyo.adapter.UserInfoListAdapter
import com.example.yogiyo.api.RetrofitApi
import com.example.yogiyo.constants.Constants
import com.example.yogiyo.constants.PrefConstants
import com.example.yogiyo.util.PrefManager
import com.example.yogiyo.vo.UserVo
import com.example.yogiyo.vo.UsersVo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserInfoFrag : BaseFrag() {
    private val retrofit: Retrofit = initRetrofit()
    private var userInfoListAdapter: UserInfoListAdapter? = null
    private lateinit var responseUsersVo: UsersVo
    private var pagingIdx = 1

    private lateinit var mView: View
    private lateinit var userList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.frag_user_info, container, false)
        userList = mView.findViewById(R.id.user_list)

        return mView
    }

    override fun onStart() {
        super.onStart()
        initUserList()
        searchUserAction(getSearchData(), Constants.SEARCH_SORT_DEFAULT)
    }

    private fun getSearchData(): String {
        var query = PrefManager().getString(requireContext(), PrefConstants.PREF_KEY_SEARCH_DATA)
        if (query == null || query == "") {
            query = " "
        }
        return query
    }

    private fun initUserList() {
        userInfoListAdapter = UserInfoListAdapter(requireContext(), ArrayList<UserVo>())
        val lm = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        userList.layoutManager = lm
        val deco = DividerItemDecoration(requireContext(), lm.orientation)
        userList.addItemDecoration(deco)
        userList.adapter = userInfoListAdapter
        userList.addOnScrollListener(userListPagingListener())
    }

    private fun userListPagingListener() : RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount

                if (lastVisibleItemPosition + 1 == itemTotalCount
                    && responseUsersVo.total_count >= itemTotalCount + Constants.SEARCH_PER_PAGE) {
                    pagingIdx++
                    searchUserAction(
                        getSearchData(),
                        Constants.SEARCH_SORT_DEFAULT,
                        Constants.SEARCH_ORDER_DEFAULT,
                        Constants.SEARCH_PER_PAGE,
                        pagingIdx,
                        false
                    )
                }
            }
        }
    }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun searchUserAction(
        query: String = " ",
        sort: String = Constants.SEARCH_SORT_DEFAULT,
        order: String = Constants.SEARCH_ORDER_DEFAULT,
        per_page: Int = Constants.SEARCH_PER_PAGE,
        page: Int = 1,
        isRefresh : Boolean = true // 초기화 유무
    ) {
        val api = retrofit.create(RetrofitApi::class.java)

        api.searchUsers(query, sort, order, per_page, page).enqueue(object : Callback<UsersVo?> {
            override fun onResponse(call: Call<UsersVo?>?, response: Response<UsersVo?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responseUsersVo = responseBody
                    }

                    if(isRefresh) {
                        userInfoListAdapter!!.updateItems(responseUsersVo.items)
                    } else {
                        userInfoListAdapter!!.addAllItems(responseUsersVo.items)
                    }
                } else {
                    Log.e(TAG, "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<UsersVo?>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }


}