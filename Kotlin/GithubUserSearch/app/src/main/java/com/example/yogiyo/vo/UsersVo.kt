package com.example.yogiyo.vo

data class UsersVo(
    var total_count: Int = 0,
    var incomplete_results: Boolean = false,
    var items: ArrayList<UserVo> = ArrayList()
)