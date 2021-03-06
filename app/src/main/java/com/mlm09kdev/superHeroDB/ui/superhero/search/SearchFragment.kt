package com.mlm09kdev.superHeroDB.ui.superhero.search

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.ui.superhero.search.SearchItem.OnItemClickListener
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.search_superhero_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SearchFragment : Fragment(), KodeinAware, OnItemClickListener {

    //get the closest kodein from our superHeroApplication.kt
    override val kodein by closestKodein()

    private val viewModelFactory: SearchViewModelFactory by instance()
    private lateinit var viewModel: SearchViewModel
    private lateinit var callBackInterface: CallBackInterface
    private var listViewState: Parcelable? = null

    companion object {
        private var searchString: String = ""
        private var mBundleRecyclerViewState: Bundle? = Bundle()
        private const val SAVE_STATE_STRING = "listState"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBackInterface.showActionAndNavBars()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_superhero_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search Super Hero Database"
        viewModel.fetchSuperHeroList(searchString)
        bindUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CallBackInterface)
            callBackInterface = context
        else
            throw RuntimeException("$context must implement CallBackInterface")
    }

    override fun onPause() {
        super.onPause()
        saveState()

    }

    override fun onResume() {
        super.onResume()
        loadState()
    }

    private fun bindUI() {
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            group_search_loading.isVisible = false
            initRecyclerView(it.toSearchItem())
            loadState()
            if (listViewState != null)
                recyclerView_search_results.layoutManager?.onRestoreInstanceState(listViewState)
        })
    }

    private fun List<SuperHeroEntity>.toSearchItem(): List<SearchItem> {
        return this.map {
            SearchItem(it, this@SearchFragment)
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
                showSuperHeroDetails(it.superHeroEntity.id, view)
            }
        }
    }

    private fun showSuperHeroDetails(id: Int, view: View) {

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
        searchView.clearFocus()
        searchView.queryHint = "Super Hero Name"
        // searchView.isIconifiedByDefault = false
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Use '%' to symbolized wildcard' in the search
                searchString = "%$query%"
                viewModel.fetchSuperHeroList(searchString)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //search as text changes
                return true
            }
        })
    }

    override fun onItemClick(superHeroEntity: SuperHeroEntity) {
        viewModel.updateFavorites(superHeroEntity)
        saveState()
    }

    private fun saveState() {
        listViewState = recyclerView_search_results.layoutManager?.onSaveInstanceState()
        mBundleRecyclerViewState!!.putParcelable(SAVE_STATE_STRING, listViewState)
    }

    private fun loadState() {
        if (mBundleRecyclerViewState != null) {
            listViewState = mBundleRecyclerViewState!!.getParcelable(SAVE_STATE_STRING)
        }
    }
}
