package com.xavier.newsfeed.model

import com.xavier.newsfeed.disposable
import com.xavier.newsfeed.newsFeedService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

/**
 * Created by xavier on 2018/3/31.
 */

data class NewsItem(var title: String = "", var description: String = "", var urlToImage: String = "", var publishedAt: String = "") : Serializable

data class NewsResponse(var nextPage: String = "", var news: MutableList<NewsItem> = ArrayList(), var displayNews: MutableList<NewsItem> = news) : Serializable {

  var filterString: String = ""

  fun filterNews(){
    displayNews = when (filterString.isEmpty()) {
      true -> news
      else -> news.filter {
        it.title.contains(filterString, true) && it.description.contains(filterString, true)
      }.toMutableList()
    }
  }

  fun filterNewsIfChanged(filter: String): Boolean{
    return if (filter != (filterString)) {
        filterString = filter
        filterNews()
        true
      }
      else false
  }

  companion object {
    fun getNews(url: String, successCallback: (NewsResponse) -> Unit, errorCallback: (Throwable) -> Unit) {
      disposable = newsFeedService.getNewsFeed(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
          { result -> successCallback(result) },
          { error -> errorCallback(error) }
      )
    }
  }
}