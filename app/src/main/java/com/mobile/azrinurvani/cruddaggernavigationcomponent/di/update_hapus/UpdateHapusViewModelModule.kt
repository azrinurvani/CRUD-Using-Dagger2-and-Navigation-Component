package com.mobile.azrinurvani.cruddaggernavigationcomponent.di.update_hapus

import androidx.lifecycle.ViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.di.ViewModelKey
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.HomeViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.UpdateHapusViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UpdateHapusViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UpdateHapusViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: UpdateHapusViewModel) : ViewModel
}