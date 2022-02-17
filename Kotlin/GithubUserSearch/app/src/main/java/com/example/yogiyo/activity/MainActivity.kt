package com.example.yogiyo.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.yogiyo.R
import com.example.yogiyo.constants.Constants
import com.example.yogiyo.constants.PrefConstants
import com.example.yogiyo.databinding.ActivityMainBinding
import com.example.yogiyo.frag.UserInfoFrag
import com.example.yogiyo.frag.UserLikeFrag
import com.example.yogiyo.util.PrefManager
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initTabLayout()
    }

    // toolbar 옵션 메뉴 설정
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setQueryHint("")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //검색
                searchAction(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initActionBar() {
        /*create action bar*/
        setSupportActionBar(binding.mainToolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false) //기본 제목을 없애줍니다.
            actionBar.setDisplayHomeAsUpEnabled(false)
        }
    }

    fun searchAction(searchData: String) {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.main_frame)
        // 보이는 fragment에 따라 기능 구현
        if (fragment is UserInfoFrag) { // Screen A
            fragment.searchUserAction(searchData, Constants.SEARCH_SORT_DEFAULT)
        } else if (fragment is UserLikeFrag) { // Screen B
            fragment.searchUserAction(searchData)
        }
        // 검색값 preference 저장
        PrefManager().setString(this, PrefConstants.PREF_KEY_SEARCH_DATA, searchData)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 검색 데이터 삭제
        PrefManager().removeKey(this, PrefConstants.PREF_KEY_SEARCH_DATA)
        // 바인딩 데이터 삭제
        mBinding = null
    }

    private fun initTabLayout() {
        onFragmentReplace(R.id.main_frame, UserInfoFrag(), UserInfoFrag().TAG)
        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener())
    }

    private fun onTabSelectedListener(): TabLayout.OnTabSelectedListener? {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    // Screen A
                    0 -> onFragmentReplace(R.id.main_frame, UserInfoFrag(), UserInfoFrag().TAG)
                    // Screen B
                    1 -> onFragmentReplace(R.id.main_frame, UserLikeFrag(), UserLikeFrag().TAG)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            // Tab 다시 누르면 현재 화면 초기화
            // api 검색은 빈 값 검색 안됨
            override fun onTabReselected(tab: TabLayout.Tab) {
                searchAction("")
            }
        }
    }


}