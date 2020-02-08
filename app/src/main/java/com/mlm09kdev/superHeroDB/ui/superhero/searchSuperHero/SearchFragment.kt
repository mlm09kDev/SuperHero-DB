package com.mlm09kdev.superHeroDB.ui.superhero.searchSuperHero

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.utils.ItemDecorator
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


    private lateinit var viewModel: SearchViewModel

    private lateinit var searchString: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        Log.i("SearchView", "onCreateView")
        return inflater.inflate(R.layout.search_superhero_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search Super Hero Database"
        bindUI()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("SearchString", searchString)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        searchString = savedInstanceState?.getString("SearchString").toString()
    }


    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.getSuperHeroList(searchString).await()
        superHero.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            group_search_loading.visibility = View.GONE
            initRecyclerView(it.toSearchItem())
        })
    }

    private fun List<SuperHeroEntity>.toSearchItem(): List<SearchItem> {
        return this.map {
            SearchItem(it)
        }
    }

    private fun initRecyclerView(items: List<SearchItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        //recyclerView_search_results.addItemDecoration(ItemDecorator(resources.getDimension(R.dimen.card_view_margin).toInt()))
        recyclerView_search_results.apply {
            layoutManager =
                if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation)
                    GridLayoutManager(this@SearchFragment.context, 2)
                else
                    LinearLayoutManager(this@SearchFragment.context)
            adapter = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? SearchItem)?.let {
                Snackbar.make(view, it.superHeroEntity.name, Snackbar.LENGTH_SHORT).show()
                showSuperHeroDetails(it.superHeroEntity.id, view) }
        }
    }

    private fun showSuperHeroDetails(id: String, view: View) {

        val actionDetail = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(id)
        Navigation.findNavController(view).navigate(actionDetail)
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
        searchView.maxWidth=Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Use '%' to symbolized wildcard' in the search
                searchString = "%$query%"
                bindUI()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //search as text changes
                return true
            }
        })
    }


}
