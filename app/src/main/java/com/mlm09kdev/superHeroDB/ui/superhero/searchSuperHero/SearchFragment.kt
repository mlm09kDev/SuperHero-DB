package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.search_superhero_layout.*
import kotlinx.coroutines.Dispatchers
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
        return inflater.inflate(R.layout.search_superhero_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search Super Hero Database"
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHero.await()
        superHero.observe(this@SearchFragment, Observer {it ->
            if (it == null )
                return@Observer
            group_loading.visibility = View.GONE
            initRecyclerView(it.toSearchItem())
        })
    }

    private fun List<SuperHeroEntity>.toSearchItem():List<SearchItem>{
        return this.map {
            SearchItem(it)
        }
    }

    private fun initRecyclerView(items : List<SearchItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        recyclerView_searchResults.apply {
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
            adapter = groupAdapter

        }
        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this.context,"added to favs", Toast.LENGTH_SHORT ).show()
        }
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

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //ic_search ass text changes
                return true
            }
        })
    }


}
