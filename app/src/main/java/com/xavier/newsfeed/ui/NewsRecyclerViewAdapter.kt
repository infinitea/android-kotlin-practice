package com.xavier.newsfeed.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xavier.newsfeed.R
import com.xavier.newsfeed.databinding.FragmentNewsItemBinding
import com.xavier.newsfeed.fragments.NewsItemFragment.OnListFragmentInteractionListener
import com.xavier.newsfeed.model.NewsItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsRecyclerViewAdapter(private val mValues: List<NewsItem>,
    private val mListener: OnListFragmentInteractionListener?,
    var displayLoading: Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  enum class ViewType {
    NEWS, LOADING
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    when (viewType) {
      ViewType.NEWS.ordinal -> {
        val itemBinding = FragmentNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
      }
      ViewType.LOADING.ordinal -> {
        return LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_item_loading, parent, false))
      }
      else -> {
        return LoadingViewHolder(View(parent.context))
      }
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (position != mValues.size && holder is ViewHolder) {
      holder.mItem = mValues[position]

      holder.mBinding.newsItem = holder.mItem
      holder.mBinding.executePendingBindings()

      holder.mView.setOnClickListener {
        mListener?.onListFragmentInteraction(mValues[position])
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (position != mValues.size) ViewType.NEWS.ordinal else ViewType.LOADING.ordinal
  }

  override fun getItemCount(): Int {
    return mValues.size + (if (displayLoading) 1 else 0)
  }

  inner class ViewHolder(val mBinding: FragmentNewsItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
    var mView = mBinding.root
    var mItem: NewsItem? = null
  }

  inner class LoadingViewHolder(mView: View): RecyclerView.ViewHolder(mView)
}
