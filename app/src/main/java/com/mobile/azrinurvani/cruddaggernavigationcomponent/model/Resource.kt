package com.mobile.azrinurvani.cruddaggernavigationcomponent.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable

//TODO - Create Resource.class to manage data when retrieve from API
class Resource<T>(
    val status: Status?,
    val data: T?,
    val message: String?)
{

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(@NonNull msg: String?): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}