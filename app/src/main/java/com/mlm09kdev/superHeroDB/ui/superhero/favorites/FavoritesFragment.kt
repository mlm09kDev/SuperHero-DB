package com.mlm09kdev.superHeroDB.ui.superhero.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.ui.adapters.FavoritesAdapter
import com.mlm09kdev.superHeroDB.utils.ItemDecorator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.favorites_fragment_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.NullPointerException

class FavoritesFragment : ScopedFragment(), KodeinAware, FavoritesAdapter.OnSuperHeroClickListener {

    override val kodein by closestKodein()
    private val viewModelFactory: FavoritesViewModelFactory by instance()

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {

            activity?.findViewById<AppBarLayout>(R.id.appBar_layout)?.setExpanded(true,true)
            activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.translationY = 0f
        } catch (e: NullPointerException) {
            Log.i("FavoritesView", "null")
        }
        setHasOptionsMenu(true)
        Log.i("FavoritesView", "onCreateView")
        return inflater.inflate(R.layout.favorites_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Search Super Hero Database"
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHeroList.await()
        superHero.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            group_favorites_loading.visibility = View.GONE
            initRecyclerView()
            addDataToRecyclerView(it)
        })
    }

    private fun initRecyclerView() {
        recyclerView_favorites.addItemDecoration(
            ItemDecorator(resources.getDimension(R.dimen.list_view_margin).toInt())
        )
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
    }

    private fun addDataToRecyclerView(superHeroList: List<SuperHeroEntity>) {
        favoritesAdapter.submitSuperHeroList(superHeroList)
    }

    override fun onItemClick(position: String, view: View?) {
        Log.i("favoritesFragment", "item:$position was clicked")
        showSuperHeroDetails(position, view!!)
    }


    private fun showSuperHeroDetails(id: String, view: View) {

        val actionDetail =
            FavoritesFragmentDirections.actionFavoriteListFragmentToDetailsFragment(id)
        Navigation.findNavController(view).navigate(actionDetail)
    }

}
