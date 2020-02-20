package com.mlm09kdev.superHeroDB.ui.adapters

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


open class SwipeToDeleteCallback(
    recyclerItemTouchHelperListener: RecyclerItemTouchHelperListener
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val swipeListener: RecyclerItemTouchHelperListener = recyclerItemTouchHelperListener

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

        if (viewHolder != null) {
            val foregroundView = (viewHolder as FavoritesAdapter.SuperHeroViewHolder).viewForeground
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView = (viewHolder as FavoritesAdapter.SuperHeroViewHolder).viewForeground
        getDefaultUIUtil().onDrawOver(
            c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive
        )
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView = (viewHolder as FavoritesAdapter.SuperHeroViewHolder).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil()
            .clearView(foregroundView)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView = (viewHolder as FavoritesAdapter.SuperHeroViewHolder).viewForeground
        getDefaultUIUtil().onDraw(
            c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive
        )
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(
            viewHolder: RecyclerView.ViewHolder?,
            direction: Int,
            position: Int
        )
    }
}