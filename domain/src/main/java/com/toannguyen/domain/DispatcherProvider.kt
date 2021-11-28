package com.toannguyen.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

class DispatcherProviderImpl : DispatcherProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
}
