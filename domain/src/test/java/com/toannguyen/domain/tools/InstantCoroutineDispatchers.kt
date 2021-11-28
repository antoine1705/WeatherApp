package com.toannguyen.domain.tools

import androidx.annotation.VisibleForTesting
import com.toannguyen.domain.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class InstantCoroutineDispatchers : DispatcherProvider {
    override val io: CoroutineDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineDispatcher = TestCoroutineDispatcher()
}
