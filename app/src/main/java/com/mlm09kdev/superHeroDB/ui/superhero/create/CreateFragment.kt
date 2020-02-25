package com.mlm09kdev.superHeroDB.ui.superhero.create

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CreateFragment : Fragment(), KodeinAware {

    //get the closest kodein from our superHeroApplication.kt
    override val kodein by closestKodein()

    private val viewModelFactory: CreateViewModelFactory by instance()
    private lateinit var viewModel: CreateViewModel
    private lateinit var callBackInterface: CallBackInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBackInterface.showActionAndNavBars()
        return inflater.inflate(R.layout.create_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val id = prefs.getInt("LastId",731).plus(1)
        viewModel.createNewSuperHero(id)
        val editor = prefs.edit()
        editor.putInt("LastId", id)
        editor.apply() //important, otherwise it wouldn't save.
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CallBackInterface)
            callBackInterface = context
        else
            throw RuntimeException("$context must implement CallBackInterface")
    }

}
