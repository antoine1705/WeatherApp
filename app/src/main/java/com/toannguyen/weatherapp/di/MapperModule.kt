package com.toannguyen.weatherapp.di

import com.toannguyen.weatherapp.mapper.WeatherMapper
import com.toannguyen.weatherapp.mapper.WeatherMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory<WeatherMapper> { WeatherMapperImpl() }
}