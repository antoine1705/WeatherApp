package com.toannguyen.weatherapp.tools

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toannguyen.tools.test.BaseTest
import com.toannguyen.tools.test.spy
import com.toannguyen.weatherapp.utils.SingleLiveEvent
import org.junit.Rule
import kotlin.reflect.KProperty

abstract class ViewModelTest : BaseTest() {
    @get:Rule
    val executorRule = ArchExecutorTestRule()
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
inline fun <reified T : Any> ViewModel.createSpyLiveData(
    property: KProperty<LiveData<T>>
): MutableLiveData<T> {
    return spy(
        obj = this,
        objToCopy = MutableLiveData(),
        fieldName = property.name
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
inline fun <reified T : Any> ViewModel.createSpySingleLiveData(
    property: KProperty<LiveData<T>>
): SingleLiveEvent<T> {
    return spy(
        obj = this,
        objToCopy = SingleLiveEvent(),
        fieldName = property.name
    )
}
