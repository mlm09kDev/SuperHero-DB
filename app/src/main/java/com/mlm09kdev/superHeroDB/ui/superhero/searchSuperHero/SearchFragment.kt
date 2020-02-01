package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.superhero_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SearchFragment : ScopedFragment(), KodeinAware {

    //get the closest kodein from our superHeroApplication.kt
    override val kodein by closestKodein()

    private val viewModelFactory: SearchViewModelFactory by instance()

    companion object {
        fun newInstance() =
            SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.superhero_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val superHero = viewModel.superHero.await()
        superHero.observe(this@SearchFragment, Observer {
            if (it.isEmpty()) 
                return@Observer
            group_loading.visibility = View.GONE

            updateSuperHeroInfo(it[0].name,it[0].biography.publisher)
           // updateSuperHeroInfo(it.name,it.biography.publisher)

            GlideApp.with(this@SearchFragment).load(it[0].image.url).into(imageView_superHero_image)
            //GlideApp.with(this@SearchFragment).load(it.image.url).into(imageView_superHero_image)
        })
    }

    private fun updateSuperHeroInfo(name:String, city: String){
        textView_superHero_name.text = name
        textView_superHero_city.text = city
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        searchView.isIconified = false
        searchView.queryHint = "Super Hero Name"
        searchView.isIconifiedByDefault = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO: implement search
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //search ass text changes
                return true
            }
        })
    }


}
