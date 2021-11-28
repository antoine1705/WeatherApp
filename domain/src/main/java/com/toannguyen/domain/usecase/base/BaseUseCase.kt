package com.toannguyen.domain.usecase.base

import com.toannguyen.domain.DispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

typealias SimpleResult = Result<Any, Error>

interface BaseUseCase<in Params> : CoroutineScope {

    // region Members
    val dispatcherProvider: DispatcherProvider

    private val parentJob: CompletableJob
        get() = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = parentJob + dispatcherProvider.main

    suspend fun run(params: Params): ReceiveChannel<SimpleResult>

    operator fun invoke(params: Params, onResult: (SimpleResult) -> Unit = {}) {
        launch(dispatcherProvider.io) {
            val result = run(params)
            result.consumeEach {
                withContext(dispatcherProvider.main) {
                    onResult.invoke(it)
                }
            }
        }
    }

    fun <T> startAsync(block: suspend () -> T): Deferred<T> = async(parentJob) {
        block()
    }

    /**
     * Should be called when use-case owner is destroyed
     * This will ensure coroutine is cancelled if it's still running some tasks
     */
    fun clear() {
        parentJob.cancel()
    }
}

class None : Any()