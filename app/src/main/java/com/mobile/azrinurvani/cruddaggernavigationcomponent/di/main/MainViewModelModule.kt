package com.mobile.azrinurvani.cruddaggernavigationcomponent.di.main

import androidx.lifecycle.ViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.ViewModelKey
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel) : ViewModel
}