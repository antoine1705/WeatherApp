package com.toannguyen.weatherapp.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toannguyen.domain.models.WeatherModel
import com.toannguyen.domain.usecase.GetWeatherInfoUseCase
import com.toannguyen.weatherapp.base.BaseViewModel
import com.toannguyen.weatherapp.fragment.adapter.WeatherAdapter
import com.toannguyen.weatherapp.mapper.WeatherMapper

class WeatherViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val weatherMapper: WeatherMapper
) : BaseViewModel() {

    val itemsObs = MutableLiveData<List<WeatherAdapter.AdapterItem>>()

    fun getWeatherForecast(cityName: String) {
        getWeatherInfoUseCase(viewModelScope, cityName) {
            it.handleResult(::handleSuccess, ::handleFailure, ::handleState)
        }
    }

    private fun handleSuccess(items: List<WeatherModel>) {
        itemsObs.value = weatherMapper.toWeatherUI(items)
    }
}