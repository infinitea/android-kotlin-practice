package com.xavier.newsfeed

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.xavier.newsfeed.fragments.NewsDetailFragment
import com.xavier.newsfeed.fragments.NewsItemFragment
import com.xavier.newsfeed.model.NewsItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity(), NewsItemFragment.OnListFragmentInteractionListener {
  override fun onListFragmentInteraction(item: NewsItem) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    supportFragmentManager.beginTransaction().add(R.id.main_fragment, NewsDetailFragment.newInstance(item), "NewsDetailFragment").addToBackStack("NewsDetailFragment").commit()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction().add(R.id.main_fragment, NewsItemFragment.newInstance(1), "NewsItemFragment").commit()

    supportFragmentManager.addOnBackStackChangedListener {
      when (supportFragmentManager.backStackEntryCount) {
        0 -> toolbar.navigationIcon = null
        else -> {
          toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        }
      }
    }

    toolbar.setNavigationOnClickListener {onBackPressed()}
  }
}
