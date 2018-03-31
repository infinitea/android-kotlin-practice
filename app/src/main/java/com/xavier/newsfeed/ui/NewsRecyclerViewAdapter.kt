package com.xavier.newsfeed.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.xavier.newsfeed.databinding.FragmentNewsItemBinding
import com.xavier.newsfeed.fragments.NewsItemFragment.OnListFragmentInteractionListener
import com.xavier.newsfeed.model.NewsItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsRecyclerViewAdapter(private val mValues: List<NewsItem>,
    private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemBinding = FragmentNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(itemBinding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.mItem = mValues[position]

    holder.mBinding.newsItem = holder.mItem
    holder.mBinding.executePendingBindings()

    holder.mView.setOnClickListener {
      mListener?.onListFragmentInteraction(mValues[position])
    }
  }

  override fun getItemCount(): Int {
    return mValues.size
  }

  inner class ViewHolder(val mBinding: FragmentNewsItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
    var mView = mBinding.root
    var mItem: NewsItem? = null
  }
}
