package com.xavier.newsfeed.model

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

/**
 * Created by xavier on 2018/4/2.
 */
class EspressoIdlingResource {
  companion object {
    private val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
      get() = mCountingIdlingResource

    fun increment() {
      mCountingIdlingResource.increment()
    }

    fun decrement() {
      mCountingIdlingResource.decrement()
    }
  }
}