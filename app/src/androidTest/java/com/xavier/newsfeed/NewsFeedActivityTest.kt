package com.xavier.newsfeed

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.xavier.newsfeed.fragments.NewsItemFragment
import com.xavier.newsfeed.model.EspressoIdlingResource
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    if (fragment != null && fragment.recyclerView?.visibility == View.VISIBLE) {
      var itemCount = fragment.recyclerView?.layoutManager?.itemCount
      assertThat(itemCount, equalTo(fragment.recyclerView?.layoutManager?.itemCount))
    }
  }
}
