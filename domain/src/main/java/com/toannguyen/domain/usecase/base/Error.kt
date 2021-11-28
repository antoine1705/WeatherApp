package com.toannguyen.domain.usecase.base

import com.toannguyen.domain.models.ErrorModel

sealed class Error {

    class ResponseError(val errorModel: ErrorModel) : Error()
    object NetworkError : Error()
    object GenericError : Error()
    object PersistenceError : Error()
}