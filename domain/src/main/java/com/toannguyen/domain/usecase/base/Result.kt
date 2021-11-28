package com.toannguyen.domain.usecase.base

sealed class Result<out T, out R> {
    class Success<out T>(val successData: T) : Result<T, Nothing>()
    class Failure<out R : Error>(val errorData: R) : Result<Nothing, R>()

    sealed class State : Result<Nothing, Nothing>() {
        object Loading : State()
        object Loaded : State()
    }

    fun <K> handleResult(
        successBlock: (K) -> Unit = {},
        failureBlock: (R) -> Unit = {},
        stateBlock: (State) -> Unit = {}
    ) {
        when (this) {
            is Success -> (successData as? K)?.let { successBlock(it) }
            is Failure -> failureBlock(errorData)
            is State -> stateBlock(this)
        }
    }
}