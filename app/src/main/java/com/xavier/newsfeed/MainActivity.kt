package com.xavier.newsfeed

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.xavier.newsfeed.fragments.NewsItemFragment
import com.xavier.newsfeed.model.NewsItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity(), NewsItemFragment.OnListFragmentInteractionListener {
  override fun onListFragmentInteraction(item: NewsItem) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    toolbar.setTitle(R.string.news_list_title)

  }
}
