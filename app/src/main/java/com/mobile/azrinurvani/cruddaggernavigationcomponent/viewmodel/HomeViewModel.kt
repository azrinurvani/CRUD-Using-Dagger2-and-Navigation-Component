package com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.DataMahasiswa
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.Resource
import com.mobile.azrinurvani.cruddaggernavigationcomponent.network.MainApi
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main.adapter.HomeRecyclerAdapter
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@SuppressLint("CheckResult")
class HomeViewModel @Inject constructor(
    private val context: Application,
    private val api: MainApi
) : AndroidViewModel(context) {

    companion object{
        private const val TAG = "HomeViewModel"
    }

    var dataMahasiswa : MutableLiveData<ArrayList<DataMahasiswa>> = MutableLiveData()

    var dataMahasiswa2 : MediatorLiveData<Resource<List<DataMahasiswa>>>?= null

    @Inject
    lateinit var mahasiswaAdapter: HomeRecyclerAdapter


    init {
        Log.d(TAG, "HomeViewModel is working...")
    }

    fun getDataMahasiswaObserver() : MutableLiveData<ArrayList<DataMahasiswa>>{
        return dataMahasiswa
    }



    fun getDataMahasiswaFromApi(){
        api.getMahasiswa()
            ?.subscribeOn(Schedulers.io())
            ?.doOnError {
                Log.e(TAG, "getDataMahasiswaFromApi: error ${it.localizedMessage}")
                dataMahasiswa.postValue(null)
            }
            ?.subscribe({
                val data = it.dataMahasiswa as ArrayList<DataMahasiswa>
                Log.d(TAG, "getDataMahasiswaFromApi: subscribe success, data : $data")
                dataMahasiswa.postValue(data)
            },{
                dataMahasiswa.postValue(null)
                Log.e(TAG, "getDataMahasiswaFromApi: subscribe Error : ${it.localizedMessage}")
//                Toast.makeText(context,"getDataMahasiswaFromApi.subscribeError : ${it.localizedMessage}",Toast.LENGTH_LONG).show()
            })
    }

    fun getDataMahasiswaFromApi2(): LiveData<Resource<List<DataMahasiswa>>>?{
        if (dataMahasiswa2==null){
            dataMahasiswa2 = MediatorLiveData()
            dataMahasiswa2?.value = Resource.loading(null)

            val source : LiveData<Resource<List<DataMahasiswa>>> = LiveDataReactiveStreams.fromPublisher(
                api.getMahasiswa2()
                    .onErrorReturn {
                        Log.e(TAG, "getDataMahasiswaFromApi2: ${it.localizedMessage}" )
                        val dataMahasiswa = DataMahasiswa()
                        dataMahasiswa.mahasiswaId = "-1"
                        val arrayDataMahasiswa = ArrayList<DataMahasiswa>()
                        arrayDataMahasiswa.add(dataMahasiswa)
                        arrayDataMahasiswa
                    }
                    .map(object : Function<List<DataMahasiswa>,Resource<List<DataMahasiswa>>>{
                        override fun apply(dataMahasiswaMap: List<DataMahasiswa>): Resource<List<DataMahasiswa>> {
                            if (dataMahasiswaMap.isNotEmpty()){
                                if (dataMahasiswaMap[0].mahasiswaId != "-1"){
                                    return Resource.error("Something went wrong")
                                }
                            }
                            return Resource.success(dataMahasiswaMap)
                        }
                    })
                    .subscribeOn(Schedulers.io())
            )

            dataMahasiswa2?.addSource(source){
                dataMahasiswa2?.value = it
                dataMahasiswa2?.removeSource(source)
            }
        }
        return dataMahasiswa2
    }


    fun insertDataFromApi(nim:String,nama:String,majors:String,jekel:String,email:String){
        api.insertMahasiswa(nim,nama,majors,jekel,email)
                .subscribeOn(Schedulers.io())
                ?.doOnError {
                    Log.e(TAG, "insertDataFromApi: onError : ${it.localizedMessage}" )
                }
                ?.doOnComplete{
                    Log.d(TAG, "insertDataFromApi: Insert Success")

                }
                ?.subscribe({
//                    Toast.makeText(context,"insertDataFromApi subscribe success ",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "insertDataFromApi: RxSubscribe Success")

                },{
                    Log.e(TAG, "insertDataFromApi: subscribe error : ${it.localizedMessage}" )
                })
    }



//    Adapter
    fun getRecyclerAdapter(): HomeRecyclerAdapter{
        return mahasiswaAdapter
    }
    fun setRecyclerAdapter(data : ArrayList<DataMahasiswa>){
        mahasiswaAdapter.setMahasiswaAdapter(data)
    }

}