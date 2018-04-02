package com.xavier.newsfeed

import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


/**
 * Created by xavier on 2018/4/2.
 */
fun withChildViewCount(count: Int, childMatcher: Matcher<View>): Matcher<View> {
  return object : TypeSafeMatcher<View>() {
    var visibleChildCount: Int = 0
    override fun describeTo(description: Description?) {
      description?.appendText("onlyOneChildDisplayed: "+ visibleChildCount)
    }

    override fun matchesSafely(viewToCheck: View): Boolean {
      return if (viewToCheck is ViewGroup) {
        for (index: Int in 0 until viewToCheck.childCount) {
          if (childMatcher.matches(viewToCheck.getChildAt(index))) visibleChildCount++
        }
        visibleChildCount == count
      } else {
        false
      }

    }
  }
}