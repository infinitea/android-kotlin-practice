package com.xavier.newsfeed

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.xavier.newsfeed.fragments.NewsDetailFragment
import com.xavier.newsfeed.fragments.NewsItemFragment
import com.xavier.newsfeed.model.NewsItem
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

val newsFeedService by lazy {
  NewsFeedService.create()
}
var disposable: Disposable? = null

class MainActivity : FragmentActivity(), NewsItemFragment.OnListFragmentInteractionListener {

  companion object {
    val STATE_CURRENT_PAGE = "current-page"

    enum class Page { LIST, DETAIL }
  }

  var currentPage: Page? = Page.LIST

  override fun onListFragmentInteraction(item: NewsItem) {
    supportFragmentManager.beginTransaction().add(R.id.main_fragment, NewsDetailFragment.newInstance(item), "NewsDetailFragment").addToBackStack(
        "NewsDetailFragment").commit()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    savedInstanceState?.getSerializable(STATE_CURRENT_PAGE)?.let { currentPage = it as Page }

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction().replace(R.id.main_fragment, NewsItemFragment.newInstance(1), "NewsItemFragment").commit()
    }
    if (currentPage == Page.LIST) toolbar.navigationIcon = null else toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

    supportFragmentManager.addOnBackStackChangedListener {
      when (supportFragmentManager.backStackEntryCount) {
        0 -> {
          toolbar.navigationIcon = null
          currentPage = Page.LIST
        }
        else -> {
          toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
          currentPage = Page.DETAIL
        }
      }
    }
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putSerializable(STATE_CURRENT_PAGE, currentPage)
  }
}
