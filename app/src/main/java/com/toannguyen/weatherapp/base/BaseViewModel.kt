package com.toannguyen.weatherapp.base

import androidx.lifecycle.ViewModel
import com.toannguyen.domain.usecase.base.Error
import com.toannguyen.domain.usecase.base.Result
import com.toannguyen.weatherapp.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val errorObs = SingleLiveEvent<Error>()
    val loadingObs = SingleLiveEvent<Boolean>()

    open fun handleState(state: Result.State) {
        loadingObs.value = state == Result.State.Loading
    }

    open fun handleFailure(error: Error) {
        errorObs.value = error
    }
}