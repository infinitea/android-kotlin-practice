package com.xavier.newsfeed.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.xavier.newsfeed.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xavier on 2018/3/31.
 */

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
  Picasso.get().load(url).placeholder(R.drawable.ic_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp).into(imageView)
}

@BindingAdapter("date")
fun setDate(dateView: TextView, dateString: String) {
  val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
  df1.timeZone = TimeZone.getTimeZone("UTC")

  val date = df1.parse(dateString)
  dateView.text = dateView.resources.getString(R.string.updated_at_placeholder, DateFormat.getDateTimeInstance().format(date));
}