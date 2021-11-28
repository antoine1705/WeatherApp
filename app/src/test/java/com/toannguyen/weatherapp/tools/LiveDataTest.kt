package com.toannguyen.weatherapp.tools

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
inline fun <reified T> LiveData<T>.captureValues(): List<T?> {
    val mockObserver = mockk<Observer<T>>()
    val list = mutableListOf<T?>()
    every { mockObserver.onChanged(captureNullable(list)) } just runs
    this.observeForever(mockObserver)
    return list
}
