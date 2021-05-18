package com.mobile.azrinurvani.cruddaggernavigationcomponent.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseGetMahasiswa(

        @field:SerializedName("pesan")
        val pesan: String? = null,

        @field:SerializedName("datanya")
        val dataMahasiswa: List<DataMahasiswa?>? = null,

        @field:SerializedName("status")
        val status: Int? = null
)

@Parcelize
data class DataMahasiswa(

        @field:SerializedName("mahasiswa_jekel")
        var mahasiswaJekel: String? = null,

        @field:SerializedName("mahasiswa_email")
        var mahasiswaEmail: String? = null,

        @field:SerializedName("mahasiswa_jurusan")
        var mahasiswaJurusan: String? = null,

        @field:SerializedName("mahasiswa_nama")
        var mahasiswaNama: String? = null,

        @field:SerializedName("mahasiswa_id")
        var mahasiswaId: String? = null,

        @field:SerializedName("mahasiswa_nim")
        var mahasiswaNim: String? = null
) : Parcelable