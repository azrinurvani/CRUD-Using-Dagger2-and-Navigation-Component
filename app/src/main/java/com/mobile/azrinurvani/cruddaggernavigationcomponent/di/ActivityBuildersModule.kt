package com.mobile.azrinurvani.cruddaggernavigationcomponent.di

import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.main.MainModule
import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.main.MainViewModelModule
import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.update_hapus.UpdateHapusModule
import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.update_hapus.UpdateHapusViewModelModule
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBuildersModule {

//    @Singleton
    @ContributesAndroidInjector(
            modules = [
                FragmentBuildersModule::class,
                MainModule::class,
                MainViewModelModule::class,
                UpdateHapusModule::class,
                UpdateHapusViewModelModule::class
            ]
    )
    abstract fun contributeMainActivity() : MainActivity
}