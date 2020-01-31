package com.mlm09kdev.SuperHeroDB.ui.superHero.superHero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.mlm09kdev.SuperHeroDB.R
import com.mlm09kdev.SuperHeroDB.ui.ScopedFragment
import kotlinx.android.synthetic.main.superhero_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SuperHeroFragment : ScopedFragment(), KodeinAware {

    //get the closest kodein from our superHeroApplication.kt
    override val kodein by closestKodein()

    private val viewModelFactory: SuperHeroViewModelFactory by instance()

    companion object {
        fun newInstance() =
            SuperHeroFragment()
    }

    private lateinit var viewModel: SuperHeroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.superhero_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SuperHeroViewModel::class.java)
        bindUI()

    }

    private fun bindUI() = launch {
        val superHero = viewModel.superHero.await()
        superHero.observe(this@SuperHeroFragment, Observer {
            if (it == null) return@Observer

            textview.text = it.toString()
        })
    }

}
