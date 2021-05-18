package com.mobile.azrinurvani.cruddaggernavigationcomponent.di

import android.util.Log
import com.mobile.azrinurvani.cruddaggernavigationcomponent.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("CRUD-APP", "AZRI API: $message")
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
       // interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return client

    }

    @Singleton
    @Provides
    fun provideRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideHttpClient())
            .build()
    }


}