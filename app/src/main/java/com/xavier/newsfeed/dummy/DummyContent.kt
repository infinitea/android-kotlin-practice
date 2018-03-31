package com.xavier.newsfeed.dummy

import com.xavier.newsfeed.model.NewsItem
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

  /**
   * An array of sample (dummy) items.
   */
  val ITEMS: MutableList<NewsItem> = ArrayList<NewsItem>()

  /**
   * A map of sample (dummy) items, by ID.
   */
  val ITEM_MAP: MutableMap<String, NewsItem> = HashMap<String, NewsItem>()

  private val COUNT = 25

  init {
    // Add some sample items.
    for (i in 1..COUNT) {
//      addItem(createDummyItem(i))
      addItem(NewsItem("title" + i, "description" + i, "https://imagejournal.org/wp-content/uploads/bb-plugin/cache/23466317216_b99485ba14_o-panorama.jpg", "2018-03-31T02:21:36Z"))
    }
  }

  private fun addItem(item: NewsItem) {
    ITEMS.add(item)
    ITEM_MAP.put(item.title, item)
  }

  private fun createDummyItem(position: Int): DummyItem {
    return DummyItem((position).toString(), "Item " + position, makeDetails(position))
  }

  private fun makeDetails(position: Int): String {
    val builder = StringBuilder()
    builder.append("Details about Item: ").append(position)
    for (i in 0 until position) {
      builder.append("\nMore details information here.")
    }
    return builder.toString()
  }

  /**
   * A dummy item representing a piece of content.
   */
  class DummyItem(val id: String, val content: String, val details: String) {

    public override fun toString(): String {
      return content
    }
  }
}
