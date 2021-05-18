package com.mobile.azrinurvani.cruddaggernavigationcomponent.network

import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.DataMahasiswa
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.Resource
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.ResponseGetMahasiswa
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.ResponseInsertMahasiswa
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {

    @GET("getMahasiswa")
    fun getMahasiswa(): Flowable<ResponseGetMahasiswa?>?

    @GET("getMahasiswa")
    fun getMahasiswa2(): Flowable<List<DataMahasiswa>>

    @FormUrlEncoded
    @POST("insertMahasiswa")
    fun insertMahasiswa(
            @Field("nim") nim: String?,
            @Field("name") nama: String?,
            @Field("majors") jurusan: String?,
            @Field("jekel") jekel: String?,
            @Field("email") email: String?
    ): Completable



}