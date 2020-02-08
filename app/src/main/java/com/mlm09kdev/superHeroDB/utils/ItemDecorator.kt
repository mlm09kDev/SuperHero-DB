package com.mlm09kdev.superHeroDB.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Manuel Montes de Oca on 2/8/2020.
 */

class ItemDecorator(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}