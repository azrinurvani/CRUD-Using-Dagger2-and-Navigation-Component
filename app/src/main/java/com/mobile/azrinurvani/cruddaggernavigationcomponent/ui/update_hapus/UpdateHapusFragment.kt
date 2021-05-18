package com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.update_hapus

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mobile.azrinurvani.cruddaggernavigationcomponent.R
import com.mobile.azrinurvani.cruddaggernavigationcomponent.databinding.FragmentUpdateHapusBinding
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.UpdateHapusViewModel
import com.mobile.azrinurvani.cruddaggernavigationcomponent.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UpdateHapusFragment : DaggerFragment() {
    private var _binding: FragmentUpdateHapusBinding? =null
    private val binding get() = _binding


    @Inject
    lateinit var vmFactory: ViewModelProviderFactory

    private lateinit var updateHapusViewModel : UpdateHapusViewModel

    private val dataMahasiswaArgs : UpdateHapusFragmentArgs by navArgs()

    val arrayJurusan = arrayOf(
        "Teknologi Informasi",
        "Teknik Mesin",
        "Teknik Sipil",
        "Teknik Elektro",
        "Akuntansi",
        "Adminitrasi Niaga",
        "Bahasa Inggris"
    )

    private var majorsSelected = ""
    private var genderSelected = ""
    private var id : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateHapusBinding.inflate(inflater,container,false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHapusViewModel = ViewModelProviders.of(this,vmFactory).get(UpdateHapusViewModel::class.java)
        id = dataMahasiswaArgs.dataMahasiswa.mahasiswaId

//        Toast.makeText(activity,"Data Mahasiswa : ${dataMahasiswaArgs.dataMahasiswa.mahasiswaEmail}",Toast.LENGTH_LONG).show()

        setMajorsSelected()
        setFormUpdate()
        binding?.btnUpdate?.setOnClickListener {
            updateDataMahasiswa()
            Navigation.findNavController(it).navigate(R.id.action_updateHapusFragment_to_homeFragment)
        }
    }

    private fun updateDataMahasiswa(){
        genderSelected()
        if (dataMahasiswaArgs.dataMahasiswa !=null) {

            if (!TextUtils.isEmpty(binding?.edtEmail?.text.toString()) ||
                !TextUtils.isEmpty(binding?.edtNim?.text.toString()) ||
                !TextUtils.isEmpty(binding?.edtNama?.text.toString())){

                val nim = binding?.edtNim?.text.toString()
                val name = binding?.edtNama?.text.toString()
                val email = binding?.edtEmail?.text.toString()
                val majors = majorsSelected
                val gender = genderSelected

                updateHapusViewModel.updateDataFromApi(id,nim,name,majors,gender,email)

            }else{
                Toast.makeText(activity,"Data must be filled",Toast.LENGTH_LONG).show()
            }

        }else{
           Toast.makeText(activity,"Data is NULL",Toast.LENGTH_LONG).show()
        }
    }

    private fun setFormUpdate(){
        if (dataMahasiswaArgs.dataMahasiswa !=null) {

            val nama = dataMahasiswaArgs.dataMahasiswa.mahasiswaNama
            val email = dataMahasiswaArgs.dataMahasiswa.mahasiswaEmail
            val jekel = dataMahasiswaArgs.dataMahasiswa.mahasiswaJekel
            val nim = dataMahasiswaArgs.dataMahasiswa.mahasiswaNim

            binding?.edtEmail?.setText(email)
            binding?.edtNama?.setText(nama)
            binding?.edtNim?.setText(nim)

            if (jekel.equals("Laki-laki")){
                binding?.rbLaki?.isChecked = true
            }else if (jekel.equals("Perempuan")){
                binding?.rbPerempuan?.isChecked = true
            }

        }else{
            Toast.makeText(activity,"Data is NULL",Toast.LENGTH_LONG).show()
        }
    }

    private fun genderSelected(){
        if (binding?.rbLaki?.isChecked!!){
            genderSelected = "Laki-laki"
        }else if (binding?.rbPerempuan?.isChecked!!){
            genderSelected = "Perempuan"
        }
    }

    private fun setMajorsSelected(){
        val adapterMajors = activity?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_item,arrayJurusan) }
        adapterMajors?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        binding?.spinnerJurusan?.apply {
            adapter = adapterMajors
            onItemSelectedListener = MajorsItemSelectedListener()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.menu_hapus,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_hapus -> {

                dialogHapus()

            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun dialogHapus() {
        val alertDelete = activity?.let { AlertDialog.Builder(it) }
        alertDelete?.setTitle("Please confirm !")
        alertDelete?.setMessage("Are you sure ?")
        alertDelete?.setCancelable(false)
        alertDelete?.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                updateHapusViewModel.deleteDataFromApi(id)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_updateHapusFragment_to_homeFragment) }
            }

        })
        alertDelete?.setNegativeButton("No",object: DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                dialogInterface?.dismiss()
            }

        })

        alertDelete?.show()

    }

    inner class MajorsItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(adapterView: AdapterView<*>?) {

        }

        override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
            majorsSelected = adapterView?.getItemAtPosition(i).toString()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}