package com.min.project_map.data.api;

import com.min.project_map.BaseApp;
import com.min.project_map.data.constants.NetworkConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(BaseApp.class)
public class RetrofitModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit()  {
        return new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public RetrofitApi provideApiService()  {
        return provideRetrofit().create(RetrofitApi.class);
    }
}
