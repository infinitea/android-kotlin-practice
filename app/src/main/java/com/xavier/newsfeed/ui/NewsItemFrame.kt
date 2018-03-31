package com.xavier.newsfeed.ui

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView

/**
 * Created by xavier on 2018/4/1.
 */

class NewsItemFrameDecoration(val frameStroke: Int, val frameColor: Int,
    val frameDrawable: ColorDrawable = ColorDrawable(frameColor)) : RecyclerView.ItemDecoration() {

//  override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//    super.getItemOffsets(outRect, view, parent, state)
//    val layoutManager = parent?.layoutManager
//    if (layoutManager is LinearLayoutManager && layoutManager !is GridLayoutManager)
//      outRect?.set(frameStroke, frameStroke, frameStroke, frameStroke)
//  }

  override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
    super.onDrawOver(c, parent, state)
    parent ?: return
    val itemCount: Int = parent.childCount
    val left = parent.paddingLeft + frameStroke
    val right = parent.width - parent.paddingRight + frameStroke
    for (i: Int in 0..itemCount) {
      val child = parent.getChildAt(i) ?: return
      val params = child.layoutParams as RecyclerView.LayoutParams
      val top = child.bottom + params.bottomMargin + frameStroke
      val bottom = top + frameStroke
      frameDrawable.setBounds(left, top, right, bottom)
      frameDrawable.draw(c)
    }
  }
}