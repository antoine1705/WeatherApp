package com.toannguyen.domain.usecase.base

import com.toannguyen.domain.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

typealias SimpleResult = Result<Any, Error>

interface BaseUseCase<in Params> : CoroutineScope {

    val dispatcherProvider: DispatcherProvider

    override val coroutineContext: CoroutineContext
        get() = dispatcherProvider.io

    suspend fun run(params: Params): ReceiveChannel<SimpleResult>

    operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (SimpleResult) -> Unit = {}
    ) {
        scope.launch {
            val result = async(coroutineContext) {
                run(params)
            }
            result.await().consumeEach {
                onResult.invoke(it)
            }
        }
    }
}