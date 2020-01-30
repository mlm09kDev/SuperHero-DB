package com.mlm09kdev.SuperHeroDB.ui.Weather.Today

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.mlm09kdev.SuperHeroDB.R
import com.mlm09kdev.SuperHeroDB.model.network.ConnectionInterceptorImpl
import com.mlm09kdev.SuperHeroDB.model.network.NetworkDataSourceImpl
import com.mlm09kdev.SuperHeroDB.model.network.SuperHeroAPIService
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodayWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            TodayWeatherFragment()
    }

    private lateinit var viewModel: TodayWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TodayWeatherViewModel::class.java)
        // TODO: Use the ViewModel

       val apiService =
           SuperHeroAPIService(
               ConnectionInterceptorImpl(
                   this.context!!
               )
           )
        val networkDataSource =
            NetworkDataSourceImpl(
                apiService
            )

        networkDataSource.downloadedSuperHero.observe(this, Observer {
            textview.text = it.toString() })

        GlobalScope.launch(Dispatchers.Main) {
           networkDataSource.fetchSuperHero("76")

        }
    }

}
