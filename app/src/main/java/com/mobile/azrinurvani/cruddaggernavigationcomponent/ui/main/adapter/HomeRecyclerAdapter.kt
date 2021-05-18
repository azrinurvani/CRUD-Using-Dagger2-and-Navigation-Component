package com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.azrinurvani.cruddaggernavigationcomponent.R
import com.mobile.azrinurvani.cruddaggernavigationcomponent.databinding.ListMahasiswaBinding
import com.mobile.azrinurvani.cruddaggernavigationcomponent.model.DataMahasiswa
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.main.HomeFragmentDirections
import com.mobile.azrinurvani.cruddaggernavigationcomponent.ui.update_hapus.UpdateHapusFragmentArgs


class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    private lateinit var bindingLayout : ListMahasiswaBinding
    var listMahasiswa = ArrayList<DataMahasiswa>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        bindingLayout = ListMahasiswaBinding.inflate(inflater,parent, false)
        return HomeViewHolder(bindingLayout)
    }

    override fun getItemCount(): Int {
        return if (listMahasiswa.size>0){
            listMahasiswa.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listMahasiswa[position])

        holder.itemView.setOnClickListener {view->
            //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_updateHapusFragment)
            //or

            //cara 1
//            val data = listMahasiswa[position]
//            val bundle = bundleOf("data" to data)
//            view.findNavController().navigate(R.id.action_homeFragment_to_updateHapusFragment,bundle)

            //cara 2 - mmenggunakan safeArgs
            listMahasiswa[position].let {
                val directions = HomeFragmentDirections.actionHomeFragmentToUpdateHapusFragment(it)
                view.findNavController().navigate(directions)
            }

        }

        //            holder.bookBtn.setOnClickListener({ view ->
//                val bundle = Bundle()
//                val salonJsonString: String = HelperMethods.getGsonParser().toJson(salonModel)
//                bundle.putString("DATA", salonJsonString)
//                Navigation.findNavController(view).navigate(R.id.salonDetailFragment, bundle)
//            })
    }

    fun setMahasiswaAdapter(data : ArrayList<DataMahasiswa>){
        this.listMahasiswa = data
        notifyDataSetChanged()
    }

    class HomeViewHolder(binding: ListMahasiswaBinding) : RecyclerView.ViewHolder(binding.root) {

        private val binding = binding
        fun bind(data : DataMahasiswa?){
            binding.txtNim.text = data?.mahasiswaNim
            binding.txtNama.text = data?.mahasiswaNama
            binding.txtEmail.text = data?.mahasiswaEmail
            binding.txtJekel.text = data?.mahasiswaJekel
            binding.txtJurusan.text = data?.mahasiswaJurusan
        }
    }
}