package com.mlm09kdev.superHeroDB.ui.superhero.favorites

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.ui.adapters.FavoritesAdapter
import com.mlm09kdev.superHeroDB.ui.adapters.SwipeToDeleteCallback
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import com.mlm09kdev.superHeroDB.utils.ItemDecorator
import kotlinx.android.synthetic.main.favorites_fragment_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class FavoritesFragment : ScopedFragment(), KodeinAware, FavoritesAdapter.OnSuperHeroClickListener,
    SwipeToDeleteCallback.RecyclerItemTouchHelperListener {

    override val kodein by closestKodein()
    private val viewModelFactory: FavoritesViewModelFactory by instance()

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var callBackInterface: CallBackInterface
    private var listState: Parcelable? = null

    companion object {
        private var mBundleRecyclerViewState: Bundle? = Bundle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBackInterface.showActionAndNavBars()
        setHasOptionsMenu(true)
        Log.i("FavoritesView", "onCreateView")
        return inflater.inflate(R.layout.favorites_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search Super Hero Database"
        bindUI()
        recyclerView_favorites.addItemDecoration(
            ItemDecorator(resources.getDimension(R.dimen.list_view_margin).toInt())
        )
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
        val listState: Parcelable? = recyclerView_favorites.layoutManager?.onSaveInstanceState()
        mBundleRecyclerViewState!!.putParcelable("listState", listState)
    }

    override fun onResume() {
        super.onResume()
        if (mBundleRecyclerViewState != null) {
            listState = mBundleRecyclerViewState!!.getParcelable("listState")
        }
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHeroList.await()
        superHero.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            group_favorites_loading.isVisible = false
            initRecyclerView()
            addDataToRecyclerView(it)
            if (listState != null)
                recyclerView_favorites.layoutManager?.onRestoreInstanceState(listState)
        })
    }


    private fun initRecyclerView() {
        recyclerView_favorites.apply {
            layoutManager = LinearLayoutManager(this@FavoritesFragment.context)

            // orientation change
            /* layoutManager =
                 if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation)
                     GridLayoutManager(this@FavoritesFragment.context, 2)
                 else
                     LinearLayoutManager(this@FavoritesFragment.context)*/

            favoritesAdapter = FavoritesAdapter(this@FavoritesFragment)
            adapter = favoritesAdapter
        }

        val itemTouchHelper =
            ItemTouchHelper(SwipeToDeleteCallback(this@FavoritesFragment))
        itemTouchHelper.attachToRecyclerView(recyclerView_favorites)
    }

    private fun addDataToRecyclerView(superHeroList: List<SuperHeroEntity>) {
        favoritesAdapter.submitSuperHeroList(superHeroList)
    }

    override fun onItemClick(position: Int, view: View?) {
        Log.i("favoritesFragment", "item:$position was clicked")
        showSuperHeroDetails(position, view!!)
    }

    override fun updateSuperHero(superHeroEntity: SuperHeroEntity, favorite: Int) {
        viewModel.updateFavorites(superHeroEntity, favorite)
    }

    private fun showSuperHeroDetails(id: Int, view: View) {
        val actionDetail =
            FavoritesFragmentDirections.actionFavoriteListFragmentToDetailsFragment(id)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        //searchView.isIconified = false
        searchView.queryHint = "Super Hero Name"
        // searchView.isIconifiedByDefault = false
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                favoritesAdapter.filter.filter(searchText)
                return true
            }
        })
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is FavoritesAdapter.SuperHeroViewHolder) {
            // get the removed item name to display it in snack bar
            val name: String = favoritesAdapter.itemsList[viewHolder.adapterPosition].name
            // backup of removed item for undo purpose
            val deletedSuperHeroEntity: SuperHeroEntity =
                favoritesAdapter.itemsList[viewHolder.adapterPosition]
            val deletedPosition = viewHolder.adapterPosition
            // remove the superHero from recycler view
            favoritesAdapter.removeSuperhero(position)
            // showing snack bar with Undo option
            val snackbar = Snackbar.make(
                recyclerView_favorites,
                "$name removed from Favorites",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("Undo") {
                // undo is selected, restore the deleted superHero
                favoritesAdapter.restoreSuperhero(deletedSuperHeroEntity, deletedPosition)
            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
    }
}
