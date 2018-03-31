package com.xavier.newsfeed.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xavier.newsfeed.R
import com.xavier.newsfeed.dummy.DummyContent
import com.xavier.newsfeed.model.NewsItem
import com.xavier.newsfeed.ui.NewsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_news_item_list.*
import kotlinx.android.synthetic.main.fragment_news_item_list.view.*

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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (arguments != null) {
      mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.fragment_news_item_list, container, false)
    val recyclerView = view.findViewById<RecyclerView>(R.id.list)
    // Set the adapter
    val context = view.context
    if (mColumnCount <= 1) {
      recyclerView.layoutManager = LinearLayoutManager(context)
    } else {
      recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
    }
    recyclerView.adapter = NewsRecyclerViewAdapter(DummyContent.ITEMS, mListener)

    return view
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
