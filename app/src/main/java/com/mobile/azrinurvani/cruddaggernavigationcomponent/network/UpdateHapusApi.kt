package com.mobile.azrinurvani.cruddaggernavigationcomponent.network

import io.reactivex.Completable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UpdateHapusApi {

    @FormUrlEncoded
    @POST("updateMahasiswa")
    fun updateMahasiswa(
            @Field("id") id: String?,
            @Field("nim") nim: String?,
            @Field("name") nama: String?,
            @Field("majors") jurusan: String?,
            @Field("jekel") jekel: String?,
            @Field("email") email: String?
    ): Completable

    @FormUrlEncoded
    @POST("deleteMahasiswa")
    fun deleteMahasiswa(@Field("id") id: String?): Completable


}