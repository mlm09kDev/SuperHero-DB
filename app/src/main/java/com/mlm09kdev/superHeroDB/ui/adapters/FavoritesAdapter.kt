package com.mlm09kdev.superHeroDB.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.favorite_item_superhero_card.view.*


class FavoritesAdapter(onSuperHeroClickListener: OnSuperHeroClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: MutableList<SuperHeroEntity> = ArrayList()
    private var itemsListFull: List<SuperHeroEntity> = ArrayList()
    private var onSuperHeroClickListener: OnSuperHeroClickListener

    init {
        this.onSuperHeroClickListener = onSuperHeroClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.favorite_item_superhero_card,
                parent,
                false
            ), onSuperHeroClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SuperHeroViewHolder -> holder.bind(itemsList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun removeSuperhero(position: Int) {
        onSuperHeroClickListener.updateSuperHero(itemsList[position], 0)
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreSuperhero(superHeroEntity: SuperHeroEntity, position: Int) {
        onSuperHeroClickListener.updateSuperHero(superHeroEntity, 1)
        itemsList.add(position, superHeroEntity)
        notifyItemInserted(position)
    }

    fun submitSuperHeroList(superHeroList: List<SuperHeroEntity>) {
        val oldSuperHeroList = itemsList
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(SuperHeroItemDiffCallBack(oldSuperHeroList, superHeroList))
        itemsList.addAll(superHeroList)
        diffResult.dispatchUpdatesTo(this)
        itemsListFull = ArrayList<SuperHeroEntity>(superHeroList)

    }

    internal inner class SuperHeroItemDiffCallBack(
        var newSuperHeroList: List<SuperHeroEntity>,
        var oldSuperHeroList: List<SuperHeroEntity>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldSuperHeroList[oldItemPosition].id == newSuperHeroList[newItemPosition].id)
        }

        override fun getOldListSize(): Int {
            return oldSuperHeroList.size
        }

        override fun getNewListSize(): Int {
            return newSuperHeroList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldSuperHeroList[oldItemPosition] == newSuperHeroList[newItemPosition]
        }

    }

    internal inner class SuperHeroViewHolder constructor(
        ItemView: View,
        onSuperHeroClickListener: OnSuperHeroClickListener
    ) :
        RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        private var onSuperHeroClickListener: OnSuperHeroClickListener

        init {
            itemView.setOnClickListener(this)
            this.onSuperHeroClickListener = onSuperHeroClickListener
        }

        var viewForeground: ConstraintLayout = itemView.view_foreground
        private val favoritesName = itemView.textView_favorites_name
        private val favoritesPublisher = itemView.textView_favorites_publisher
        private val favoritesImage = itemView.imageView_favorites_image
        private val favoritesFirstAppeared = itemView.textView_favorites_first_appeared
        private val favoritesAlignment = itemView.textView_favorites_alignment

        fun bind(superHeroEntity: SuperHeroEntity) {
            favoritesName.text = superHeroEntity.name
            favoritesFirstAppeared.text = superHeroEntity.biography.firstAppearance
            favoritesPublisher.text = superHeroEntity.biography.publisher
            favoritesAlignment.text = superHeroEntity.biography.alignment.capitalize()
            GlideApp.with(itemView.context).load(superHeroEntity.image.url).centerCrop()
                .error(R.drawable.ic_broken_image)
                .into(favoritesImage)
        }

        override fun onClick(view: View?) {
            onSuperHeroClickListener.onItemClick(itemsList[adapterPosition].id, view)
        }
    }

    interface OnSuperHeroClickListener {
        fun onItemClick(position: String, view: View?)
        fun updateSuperHero(superHeroEntity: SuperHeroEntity, favorite: Int)
    }

    override fun getFilter(): Filter {
        return favoritesFilter
    }

    private val favoritesFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filteredList: ArrayList<SuperHeroEntity> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(itemsListFull)
            } else {
                var filterPattern = constraint.toString().toLowerCase().trim()
                for (superHeroEntity in itemsListFull) {
                    if (superHeroEntity.name.toLowerCase().contains(filterPattern) ||
                        superHeroEntity.biography.publisher.toLowerCase().contains(filterPattern) ||
                        superHeroEntity.biography.firstAppearance.toLowerCase().contains(
                            filterPattern
                        )
                    )
                        filteredList.add(superHeroEntity)
                }
            }
            var filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            itemsList.clear()
            itemsList.addAll(results.values as Collection<SuperHeroEntity>)
            notifyDataSetChanged()
        }
    }

}
