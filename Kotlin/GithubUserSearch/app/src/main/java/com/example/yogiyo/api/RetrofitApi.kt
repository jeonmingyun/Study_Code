package com.example.yogiyo.api

import com.example.yogiyo.vo.UsersVo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {

    /* GET 방법 */
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/users")
    fun searchUsers(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String = "desc",
        @Query("per_page") per_page: Int = 30,
        @Query("page") page: Int = 1
    ): Call<UsersVo>
}