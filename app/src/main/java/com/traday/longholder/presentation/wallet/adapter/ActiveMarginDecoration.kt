package com.traday.longholder.presentation.wallet.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.traday.longholder.R

class ActiveMarginDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return

        val spacing = view.resources.getDimension(R.dimen.spacing_2).toInt()
        if (itemPosition == FIRST_POSITION) {
            outRect.top = spacing
        }
        outRect.bottom = spacing
    }

    companion object {

        private const val FIRST_POSITION = 0
    }
}