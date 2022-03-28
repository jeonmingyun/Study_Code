package com.min.project_map.data.api;

import com.min.project_map.data.vo.BooksVo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi {
    String city = " ";
    String latlong = "";

    @POST("/books")
    Call<BooksVo> getBooks(@Body BooksVo booksVo);

    @GET("/books")
    Call<BooksVo> searchUsers(
            @Query("year") String year,
            @Query("month") String month
    );

    @GET("/feed/"+ city +"/")
    Call<BooksVo> getCityAqi(
            @Query("token") String token
    );
}
