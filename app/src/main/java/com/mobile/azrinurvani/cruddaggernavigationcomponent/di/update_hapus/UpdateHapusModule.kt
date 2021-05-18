package com.mobile.azrinurvani.cruddaggernavigationcomponent.di.update_hapus

import com.mobile.azrinurvani.cruddaggernavigationcomponent.network.UpdateHapusApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UpdateHapusModule {

    @Provides
    fun provideUpdateHapusApi(retrofit: Retrofit) : UpdateHapusApi{
        return retrofit.create(UpdateHapusApi::class.java)
    }

}