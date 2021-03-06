package com.xavier.newsfeed

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xavier.newsfeed.fragments.NewsItemFragment
import com.xavier.newsfeed.model.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NewsFeedActivityTest {
  /**
   * A JUnit [@Rule][Rule] to launch your activity under test. This is a replacement
   * for [ActivityInstrumentationTestCase2].
   *
   *
   * Rules are interceptors which are executed for each test method and will run before
   * any of your setup code in the [@Before][Before] method.
   *
   *
   * [ActivityTestRule] will create and launch of the activity for you and also expose
   * the activity under test. To get a reference to the activity you can use
   * the [ActivityTestRule.getActivity] method.
   */
  @Rule
  @JvmField
  var mActivityRule = ActivityTestRule<MainActivity>(
      MainActivity::class.java)

  @Before
  fun registerIdlingResource() {
    IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
  }

  @After
  fun unregisterIdlingResource() {
    IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
  }

  @Test
  fun launchActivity() {
    onView(withId(R.id.main_fragment)).check(matches(isDisplayed()))

    onView(withId(R.id.list_content_root)).check(matches(isDisplayed())).check(matches(withChildViewCount(1, isDisplayed())))
  }

  @Test
  fun displayList() {
    var fragment: NewsItemFragment? = mActivityRule.activity.supportFragmentManager.findFragmentByTag("NewsItemFragment") as NewsItemFragment
    if (fragment == null || fragment.recyclerView?.visibility != View.VISIBLE) return

    for (i in 0..2) {
      var itemCount = fragment.recyclerView?.layoutManager?.itemCount
      assertThat(itemCount, equalTo(fragment.newsResponse.displayNews.size + if (fragment.newsResponse.nextPage.isNotEmpty()) 1 else 0))
      if (itemCount != null)
        onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 4))
      Thread.sleep(300)
      onView(withId(R.id.list)).perform(ViewActions.swipeUp())
      Thread.sleep(300)
      onView(withId(R.id.list)).perform(ViewActions.swipeUp())
      Thread.sleep(300)
    }
  }

  @Test
  fun checkDisplayDetail() {
    var fragment: NewsItemFragment? = mActivityRule.activity.supportFragmentManager.findFragmentByTag("NewsItemFragment") as NewsItemFragment
    if (fragment == null || fragment.recyclerView?.visibility != View.VISIBLE) return

    Thread.sleep(500)
    val random = Random()
    var itemCount = fragment.recyclerView?.layoutManager?.itemCount
    if (itemCount == null || itemCount == 0) return
    for (i in 0..2) {
      Thread.sleep(300)
      var itemIndex = random.nextInt(itemCount - 1)
      var newsItem = fragment.newsResponse.displayNews[itemIndex]

      onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(itemIndex, ViewActions.click()))
      Thread.sleep(300)
      onView(allOf(withParent(withId(R.id.detail_root)), withId(R.id.title), isDisplayed())).check(matches(withText(newsItem.title)))
      onView(allOf(withParent(withId(R.id.detail_root)), withId(R.id.description), isDisplayed())).check(matches(withText(newsItem.description)))
      pressBack()
    }
  }

  @Test
  fun checkSearch() {
    var fragment: NewsItemFragment? = mActivityRule.activity.supportFragmentManager.findFragmentByTag("NewsItemFragment") as NewsItemFragment
    if (fragment == null || fragment.recyclerView?.visibility != View.VISIBLE) return

    Thread.sleep(500)

    onView(withId(android.support.design.R.id.search_src_text)).perform(click()).perform(typeText("facebook"))
    assertThat(fragment.recyclerView?.layoutManager?.itemCount, equalTo(fragment.newsResponse.displayNews.size))
    Thread.sleep(300)

    onView(withId(android.support.design.R.id.search_src_text)).perform(click()).perform(clearText())
    assertThat(fragment.recyclerView?.layoutManager?.itemCount, equalTo(fragment.newsResponse.displayNews.size + if (fragment.newsResponse.nextPage.isNotEmpty()) 1 else 0))
    Thread.sleep(300)

    onView(withId(android.support.design.R.id.search_src_text)).perform(click()).perform(typeText("lllaewqqwrwe")) // empty case
    onView(withId(R.id.list)).check(matches(withChildViewCount(0, null)))
    Thread.sleep(500)
  }
}
