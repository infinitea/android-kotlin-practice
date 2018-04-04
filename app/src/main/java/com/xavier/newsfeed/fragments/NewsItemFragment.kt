package com.xavier.newsfeed.fragments

import android.content.Context
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xavier.newsfeed.NewsFeedService
import com.xavier.newsfeed.R
import com.xavier.newsfeed.model.NewsItem
import com.xavier.newsfeed.model.NewsResponse
import com.xavier.newsfeed.ui.NewsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_news_item_list.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class NewsItemFragment : Fragment() {
  // TODO: Customize parameters
  private var mColumnCount = 1
  private var mListener: OnListFragmentInteractionListener? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  var newsResponse: NewsResponse = NewsResponse()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  var recyclerView: RecyclerView? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  var refreshLayout: SwipeRefreshLayout? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  var searchView: SearchView? = null

  private var inSearchMode: Boolean = false

  private var scrollListener = object : RecyclerView.OnScrollListener() {
    var lastVisibleItem: Int? = 0
    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
      super.onScrollStateChanged(recyclerView, newState)
      if ((recyclerView?.adapter as NewsRecyclerViewAdapter).displayLoading && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem!! + 1 == recyclerView.adapter?.itemCount) {
        startRequestNewsFeed(newsResponse.nextPage)
      }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
      super.onScrolled(recyclerView, dx, dy)
      val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
      lastVisibleItem = layoutManager.findLastVisibleItemPosition()
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_news_item_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mColumnCount = arguments?.getInt(ARG_COLUMN_COUNT) ?: 1
    error_view.visibility = View.INVISIBLE
    recyclerView = view.findViewById(R.id.list)
    val context = view.context
    if (mColumnCount <= 1) {
      recyclerView?.layoutManager = LinearLayoutManager(context)
    } else {
      recyclerView?.layoutManager = GridLayoutManager(context, mColumnCount)
    }

    refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refresh_layout).apply {
      setOnRefreshListener { startRequestNewsFeed(null) }
    }
//    refreshLayout?.setOnRefreshListener { startRequestNewsFeed(null) }

    if (savedInstanceState != null) {
      newsResponse = savedInstanceState.getSerializable(STATE_NEWS_DATA) as NewsResponse
      var scrolledPosition = savedInstanceState.getInt(STATE_CURRENT_SCROLL_POSITION, 0)
      recyclerView?.let {
        it.adapter = NewsRecyclerViewAdapter(newsResponse.displayNews, mListener)
        it.scrollToPosition(scrolledPosition)
      }
    } else {
      startRequestNewsFeed(null)
    }

    searchView = view.findViewById<SearchView>(R.id.search_view).apply {
      isEnabled = false
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          updateSearchText(query)
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          updateSearchText(newText)
          return false
        }
      })

    }
  }

  private fun updateSearchText(text: String?) {
    if (newsResponse.filterNewsIfChanged(text ?: "")) {
      inSearchMode = newsResponse.filterString.isNotEmpty()
      refreshLayout?.isEnabled = !inSearchMode
      (recyclerView?.adapter as NewsRecyclerViewAdapter).displayLoading = !inSearchMode && newsResponse.nextPage.isNotEmpty()
      (recyclerView?.adapter as NewsRecyclerViewAdapter).updateItemsAndNotifyChange(newsResponse.displayNews)
    }
  }

  private fun startRequestNewsFeed(url: String?) {
    searchView?.isEnabled = false
    if (url == null) {
      refreshLayout?.isRefreshing = true
      recyclerView?.removeOnScrollListener(scrollListener)
      NewsResponse.getNews(
          NewsFeedService.NEWS_FEED_URL,
          { response ->
            error_view.visibility = View.INVISIBLE
            recyclerView?.visibility = View.VISIBLE
            refreshLayout?.isRefreshing = false
            newsResponse = NewsResponse(response.nextPage, response.news)
            recyclerView?.adapter = NewsRecyclerViewAdapter(newsResponse.displayNews, mListener, !inSearchMode && newsResponse.nextPage.isNotEmpty())
            searchView?.isEnabled = true
            recyclerView?.addOnScrollListener(scrollListener)
          },
          { _ ->
            error_view.visibility = View.VISIBLE
            recyclerView?.visibility = View.INVISIBLE
            refreshLayout?.isRefreshing = false
          })
    } else {
      NewsResponse.getNews(
          url,
          { response ->
            error_view.visibility = View.INVISIBLE
            recyclerView?.visibility = View.VISIBLE
            newsResponse.news.addAll(response.news)
            newsResponse.nextPage = response.nextPage
            newsResponse.filterNews()
            (recyclerView?.adapter as NewsRecyclerViewAdapter).displayLoading = !inSearchMode && newsResponse.nextPage.isNotEmpty()
            (recyclerView?.adapter as NewsRecyclerViewAdapter).updateItemsAndNotifyChange(newsResponse.displayNews)
            searchView?.isEnabled = true
          },
          { _ ->
            error_view.visibility = View.VISIBLE
            recyclerView?.visibility = View.INVISIBLE
          })
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnListFragmentInteractionListener) {
      mListener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }


  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putSerializable(STATE_NEWS_DATA, newsResponse)
    outState.putInt(STATE_CURRENT_SCROLL_POSITION, (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   *
   *
   * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
   */
  interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onListFragmentInteraction(item: NewsItem)
  }

  companion object {
    // TODO: Customize parameter argument names
    private val ARG_COLUMN_COUNT = "column-count"
    val STATE_NEWS_DATA = "news-data"
    val STATE_CURRENT_SCROLL_POSITION = "current-position"

    // TODO: Customize parameter initialization
    fun newInstance(columnCount: Int): NewsItemFragment {
      val fragment = NewsItemFragment()
      val args = Bundle()
      args.putInt(ARG_COLUMN_COUNT, columnCount)
      fragment.arguments = args
      return fragment
    }
  }
}