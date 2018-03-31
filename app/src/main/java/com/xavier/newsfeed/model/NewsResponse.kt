package com.xavier.newsfeed.model

import java.io.Serializable

/**
 * Created by xavier on 2018/3/31.
 */

data class NewsItem(var title: String = "", var description: String = "", var urlToImage: String = "", var publishedAt: String = ""): Serializable

data class NewsResponse(var nextPage: String = "", var news: Array<NewsItem> = emptyArray()): Serializable