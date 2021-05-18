package com.mobile.azrinurvani.cruddaggernavigationcomponent.di.main

import com.mobile.azrinurvani.cruddaggernavigationcomponent.network.MainApi
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main.adapter.HomeRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi{
        return retrofit.create(MainApi::class.java)
    }


    @Provides
    fun provideMahasiswaAdapter() : HomeRecyclerAdapter{
        return HomeRecyclerAdapter()
    }
}