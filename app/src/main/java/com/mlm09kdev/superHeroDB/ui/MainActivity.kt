package com.mlm09kdev.superHeroDB.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CallBackInterface {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
/*        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        editor.putInt("LastId", 750)
        editor.apply()*/ //important, otherwise it wouldn't save.
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun showActionAndNavBars() {
        appBar_layout.setExpanded(true, true)
        bottom_nav.translationY = 0f
        bottom_nav.animate()
    }
}
