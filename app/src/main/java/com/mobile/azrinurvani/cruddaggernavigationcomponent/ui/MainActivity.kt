package com.mobile.azrinurvani.cruddaggernavigationcomponent.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.mobile.azrinurvani.cruddaggernavigationcomponent.BaseActivity
import com.mobile.azrinurvani.cruddaggernavigationcomponent.R
import com.mobile.azrinurvani.cruddaggernavigationcomponent.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }


    private fun initView(){
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout) // untuk menampilkan icon hamburger
        NavigationUI.setupWithNavController(binding.navView,navController) // untuk set NavigationView menggunakan NavigationUI
        binding.navView.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_list ->{
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.homeFragment)
            }
            R.id.menu_add->{
                Toast.makeText(this,"Navigate to Add",Toast.LENGTH_SHORT).show()
            }
        }
        item.isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //Called onSupportNavigateUp to fix Hamburger and Back icon can't to click
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),binding.drawerLayout)
    }


    companion object{
        const val TAG = "MainActivity"
    }
}