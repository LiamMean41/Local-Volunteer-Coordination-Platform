package com.example.community
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = spaceHeight  // Space below each item
    }
}
