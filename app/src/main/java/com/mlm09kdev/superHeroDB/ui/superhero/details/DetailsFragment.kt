package com.mlm09kdev.superHeroDB.ui.superhero.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.search_superhero_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class DetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory: ((String) -> DetailsViewModelFactory) by factory()

    private lateinit var viewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        val id = safeArgs?.id

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(id!!)).get(DetailsViewModel::class.java)

        bindUI()
    }


    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHero.await()
        superHero.observe(this@DetailsFragment, Observer {
            if (it == null)
                return@Observer

            group_loading.visibility = View.GONE
            textView_details_superHero_city.text = it.biography.publisher
            textView_details_superHero_name.text = it.name
            textView_details_superHero_id.text = it.id

            GlideApp.with(this@DetailsFragment).load(it.image.url).centerCrop().error(R.drawable.ic_broken_image)
                .into(imageView_details_superHero_image)
        })
    }

}
