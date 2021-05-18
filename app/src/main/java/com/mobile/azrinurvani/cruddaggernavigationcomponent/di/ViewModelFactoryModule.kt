package com.mobile.azrinurvani.cruddaggernavigationcomponent.di

import androidx.lifecycle.ViewModelProvider
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelProviderFactory(modelProviderFactory: ViewModelProviderFactory) : ViewModelProvider.Factory
}