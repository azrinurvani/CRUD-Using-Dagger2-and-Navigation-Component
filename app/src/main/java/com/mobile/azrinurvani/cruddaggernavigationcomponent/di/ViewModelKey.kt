package com.mobile.azrinurvani.cruddaggernavigationcomponent.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


//TODO 10 - Defining ViewModelKey annotation
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
