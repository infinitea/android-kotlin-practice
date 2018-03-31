package com.xavier.newsfeed.ui

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.xavier.newsfeed.R
import com.xavier.newsfeed.databinding.FragmentNewsItemBinding
import com.xavier.newsfeed.fragments.NewsItemFragment.OnListFragmentInteractionListener
import com.xavier.newsfeed.model.NewsItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
  Picasso.get().load(url).placeholder(R.drawable.ic_image_black_24dp).into(imageView)
//  val builder = Picasso.Builder(imageView.context)
//  builder.listener { picasso, uri, exception ->  exception.printStackTrace()}
//
//  builder.build().load(uri)
}

@BindingAdapter("date")
fun setDate(dateView: TextView, dateString: String) {
  val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
  df1.timeZone = TimeZone.getTimeZone("UTC")

  val date = df1.parse(dateString)
  dateView.text = DateFormat.getDateTimeInstance().format(date)
}

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
