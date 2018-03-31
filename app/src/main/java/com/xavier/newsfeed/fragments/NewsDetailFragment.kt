package com.xavier.newsfeed.fragments

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.xavier.newsfeed.databinding.FragmentNewsDetailBinding
import com.xavier.newsfeed.model.NewsItem


/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class NewsDetailFragment : Fragment() {
  // TODO: Customize parameters
  private var mNews: NewsItem? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (arguments != null) {
      mNews = arguments.getSerializable(ARG_NEWS_ITEM) as NewsItem?
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val itemBinding = FragmentNewsDetailBinding.inflate(LayoutInflater.from(context), container, false)
    itemBinding.newsItem = mNews
    itemBinding.executePendingBindings()

    return itemBinding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context != null && context is Activity) {
      val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.hideSoftInputFromWindow(context.currentFocus.windowToken, 0)
    }
  }

  companion object {

    // TODO: Customize parameter argument names
    private val ARG_NEWS_ITEM = "news-item"

    // TODO: Customize parameter initialization
    fun newInstance(item: NewsItem): NewsDetailFragment {
      val fragment = NewsDetailFragment()
      val args = Bundle()
      args.putSerializable(ARG_NEWS_ITEM, item)
      fragment.arguments = args
      return fragment
    }
  }
}