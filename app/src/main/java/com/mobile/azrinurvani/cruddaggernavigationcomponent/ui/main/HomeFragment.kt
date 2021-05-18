package com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.azrinurvani.cruddaggernavigationcomponent.R
import com.mobile.azrinurvani.cruddaggernavigationcomponent.databinding.DialogInputBinding
import com.mobile.azrinurvani.cruddaggernavigationcomponent.databinding.FragmentHomeBinding
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.DataMahasiswa
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.Resource
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.HomeViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.UpdateHapusViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    private var _binding: FragmentHomeBinding? =null
    private val binding get() = _binding

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var updateHapusViewModel: UpdateHapusViewModel


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    val arrayJurusan = arrayOf(
            "Teknologi Informasi",
            "Teknik Mesin",
            "Teknik Sipil",
            "Teknik Elektro",
            "Akuntansi",
            "Adminitrasi Niaga",
            "Bahasa Inggris"
    )

    var majorsSelected : String = ""
    var jekelSelected : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        Toast.makeText(activity,"Home Fragment",Toast.LENGTH_SHORT).show()
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(this,providerFactory).get(HomeViewModel::class.java)
        updateHapusViewModel = ViewModelProviders.of(this,providerFactory).get(UpdateHapusViewModel::class.java)
        binding?.swipeRefresh?.setOnRefreshListener {
           retrievingData()
        }

        retrievingData()
        setRecyclerView()
        binding?.fabAdd?.setOnClickListener {
            dialogInput()
        }
        itemTouchListener()

    }


    private fun retrievingData(){
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.swipeRefresh?.isEnabled = true
        binding?.swipeRefresh?.isRefreshing = true

        homeViewModel.getDataMahasiswaObserver().removeObservers(viewLifecycleOwner)
        homeViewModel.getDataMahasiswaObserver().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                homeViewModel.setRecyclerAdapter(it)
                binding?.swipeRefresh?.isEnabled = false
                binding?.swipeRefresh?.isRefreshing = false
            }else{
                binding?.swipeRefresh?.isEnabled = false
                binding?.swipeRefresh?.isRefreshing = false
                Toast.makeText(activity,"Error in fetching data...", Toast.LENGTH_LONG).show()
            }
        })
        homeViewModel.getDataMahasiswaFromApi()
//        binding?.swipeRefresh?.isEnabled = false
//        binding?.swipeRefresh?.isRefreshing = false
       binding?.progressBar?.visibility = View.GONE
    }

//    private fun retrievingData2(){
//        binding?.progressBar?.visibility = View.VISIBLE
//        homeViewModel.getDataMahasiswaFromApi2()?.removeObservers(viewLifecycleOwner)
//        homeViewModel.getDataMahasiswaFromApi2()?.observe(viewLifecycleOwner, Observer {
//            if (it!=null){
//               when(it.status){
//                   Resource.Status.LOADING -> {
//                        binding?.progressBar?.visibility = View.VISIBLE
//                   }
//                   Resource.Status.ERROR -> {
//                       binding?.progressBar?.visibility = View.GONE
//                   }
//
//                   Resource.Status.SUCCESS -> {
//                       binding?.progressBar?.visibility = View.GONE
//                       homeViewModel.setRecyclerAdapter(it.data as ArrayList<DataMahasiswa>)
//                   }
//
//               }
//            }else{
//                Toast.makeText(activity,"Error in fetching data...", Toast.LENGTH_LONG).show()
//            }
//        })
//
//        binding?.progressBar?.visibility = View.GONE
//    }

    private fun setRecyclerView(){
        binding?.rvMain?.apply {
            setHasFixedSize(true)
            adapter = homeViewModel.getRecyclerAdapter()
            layoutManager = LinearLayoutManager(activity)
            val decoration =DividerItemDecoration(activity,VERTICAL)
            addItemDecoration(decoration)
        }
    }

    //Menghapus data ketika di swipe
    private fun itemTouchListener(){
        val itemTouchListener = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val data = homeViewModel.getRecyclerAdapter()

                Toast.makeText(activity,"Deleting ${data.listMahasiswa[position].mahasiswaNama}",Toast.LENGTH_LONG).show()
                updateHapusViewModel.deleteDataFromApi(data.listMahasiswa[position].mahasiswaId)
                retrievingData()
            }

        })
        itemTouchListener.attachToRecyclerView(binding?.rvMain)
    }

    @SuppressLint("NewApi")
    private fun dialogInput(){
        val bindingDialog = DialogInputBinding.inflate(layoutInflater)

        val adapterMajors = activity?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_item,arrayJurusan) }
        adapterMajors?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        bindingDialog.spinnerJurusan.apply {
            adapter = adapterMajors
            onItemSelectedListener = MajorsItemSelectedListener()
        }

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(bindingDialog.root)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle("Input Data Mahasiswa")
        dialogBuilder.setPositiveButton("Save",object : DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                // input from view model
                val nim = bindingDialog.edtNim.text.toString()
                val name = bindingDialog.edtNama.text.toString()
                val majors = majorsSelected

                if(bindingDialog.rbLaki.isChecked){
                    jekelSelected = "Laki-laki"
                }else if (bindingDialog.rbPerempuan.isChecked){
                    jekelSelected = "Perempuan"
                }
                val jekel = jekelSelected
                val email = bindingDialog.edtEmail.text.toString()

                if (!TextUtils.isEmpty(nim)||!TextUtils.isEmpty(name)||!TextUtils.isEmpty(email)){

                    inputMahasiswa(nim,name,majors,jekel,email)
                    activity?.recreate()
                }else{
                    Toast.makeText(activity,"Text must be filled",Toast.LENGTH_LONG).show()
                }

            }
        })
        dialogBuilder.setNegativeButton("Cancel",object: DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                dialogInterface?.dismiss()
            }
        })
        dialogBuilder.show().create()


//        val alertDialog: AlertDialog = AlertDialog.Builder(context).setView(bindingDialog.root)
//                .setPositiveButton(android.R.string.ok, null)
//                .setNegativeButton(android.R.string.cancel, null)
//                .show()
//
//        val b: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//        b.setOnClickListener(View.OnClickListener {
//            //Do Your thing
//            val nim = bindingDialog.edtNim.text.toString()
//            val name = bindingDialog.edtNama.text.toString()
//            val majors = majorsSelected
//
//            if(bindingDialog.rbLaki.isChecked){
//                jekelSelected = "Laki-laki"
//            }else if (bindingDialog.rbPerempuan.isChecked){
//                jekelSelected = "Perempuan"
//            }
//            val jekel = jekelSelected
//            val email = bindingDialog.edtEmail.text.toString()
//
//            if (!TextUtils.isEmpty(nim)||!TextUtils.isEmpty(name)||!TextUtils.isEmpty(email)){
//
//                inputMahasiswa(nim,name,majors,jekel,email)
//                activity?.recreate()
//            }else{
//                Toast.makeText(activity,"Text must be filled",Toast.LENGTH_LONG).show()
//            }
//        })



    }

    private fun inputMahasiswa(nim:String,name:String,majors:String,jekel:String,email:String){
        homeViewModel.insertDataFromApi(nim,name,majors,jekel,email)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MajorsItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(adapterView: AdapterView<*>?) {

        }

        override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            majorsSelected = adapterView?.getItemAtPosition(position).toString()
        }

    }
}

