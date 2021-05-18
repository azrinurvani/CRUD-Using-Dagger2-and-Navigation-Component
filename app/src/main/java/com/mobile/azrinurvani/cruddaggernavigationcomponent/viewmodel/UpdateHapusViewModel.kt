package com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.network.UpdateHapusApi
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class UpdateHapusViewModel @Inject constructor(
        private val context : Application,
        private val api : UpdateHapusApi
) : AndroidViewModel(context) {


    companion object{
        private const val TAG = "UpdateHapusViewModel"
    }

    init {
        Log.d(TAG, "UpdateHapusViewModel is working... ")
    }


    fun updateDataFromApi(id: String?, nim:String?, nama:String?, majors:String?, jekel:String?, email:String?){
        api.updateMahasiswa(id,nim,nama,majors,jekel,email)
                .subscribeOn(Schedulers.io())
                ?.doOnError {
                    Log.e(TAG, "updateDataFromApi: onError : ${it.localizedMessage}" )
                }
                ?.doOnComplete{
                    Log.d(TAG, "updateDataFromApi: Update Success")

                }
                ?.subscribe({
//                    Toast.makeText(context,"insertDataFromApi subscribe success ",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "updateDataFromApi: RxSubscribe Success")

                },{
                    Log.e(TAG, "updateDataFromApi: subscribe error : ${it.localizedMessage}" )
                })
    }

    fun deleteDataFromApi(id:String?){
        api.deleteMahasiswa(id)
                .subscribeOn(Schedulers.io())
                ?.doOnError {
                    Log.e(TAG, "deleteDataFromApi: onError ${it.localizedMessage}" )
                }
                ?.doOnComplete{
                    Log.d(TAG, "deleteDataFromApi: Delete Success")
                }
                ?.subscribe({
                    Log.d(TAG, "deleteDataFromApi: SUCCESS Rx Subscribe")
                },{
                    Log.e(TAG, "deleteDataFromApi: Failed Rx Subscibe ${it.localizedMessage}" )
                })
    }

}