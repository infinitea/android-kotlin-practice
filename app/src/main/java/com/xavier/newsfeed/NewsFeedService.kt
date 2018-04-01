package com.xavier.newsfeed

import com.xavier.newsfeed.model.NewsResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



/**
 * Created by xavier on 2018/3/31.
 */

interface NewsFeedService {
  @GET
  fun getNewsFeed(@Url url: String): Observable<NewsResponse>

  companion object EndPoint {
    val BASE_URL = "https://api.myjson.com/"
    val NEWS_FEED_URL = "https://api.myjson.com/bins/lbo9b"

    fun create(): NewsFeedService {
      val interceptor = HttpLoggingInterceptor()
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

      val retrofit = Retrofit.Builder()
          .client(client)
          .addCallAdapterFactory(
              RxJava2CallAdapterFactory.create())
          .addConverterFactory(
              GsonConverterFactory.create())
          .baseUrl(BASE_URL)
          .build()

      return retrofit.create(NewsFeedService::class.java)
    }
  }
}