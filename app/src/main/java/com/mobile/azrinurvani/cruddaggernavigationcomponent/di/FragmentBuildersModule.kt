package com.mobile.azrinurvani.cruddaggernavigationcomponent.di

import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main.HomeFragment
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.update_hapus.UpdateHapusFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFragmentHome() : HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFragmentUpdateHapus() : UpdateHapusFragment
}