package com.toannguyen.weatherapp.di

import androidx.room.Room
import com.toannguyen.data.local.AppDatabase
import com.toannguyen.data.local.LocalService
import com.toannguyen.data.local.LocalServiceImpl
import com.toannguyen.data.mapper.WeatherRepositoryMapper
import com.toannguyen.data.mapper.WeatherRepositoryMapperImpl
import com.toannguyen.data.repository.WeatherRepositoryImpl
import com.toannguyen.data.service.WeatherForeCastService
import com.toannguyen.domain.DispatcherProvider
import com.toannguyen.domain.DispatcherProviderImpl
import com.toannguyen.domain.repository.WeatherRepository
import com.toannguyen.domain.usecase.GetWeatherInfoUseCase
import com.toannguyen.domain.usecase.GetWeatherInfoUseCaseImpl
import com.toannguyen.weatherapp.R
import com.toannguyen.weatherapp.fragment.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModules = module {
    factory<WeatherRepositoryMapper> { WeatherRepositoryMapperImpl() }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherForeCastService = get(),
            weatherRepositoryMapper = get(),
            localService = get()
        )
    }
}
val useCaseModules = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }

    factory<GetWeatherInfoUseCase> {
        GetWeatherInfoUseCaseImpl(weatherRepository = get(), dispatcherProvider = get())
    }
}
val networkModules = module {
    single { createOkHttpClient() }
    single {
        createWebService<WeatherForeCastService>(
            okHttpClient = get(),
            url = androidContext().resources.getString(R.string.base_url)
        )
    }
}

val localModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "weather-examples"
        ).build()
    }
    single<LocalService> {
        LocalServiceImpl(database = get())
    }
}

val viewModels = module {
    viewModel {
        WeatherViewModel(
            getWeatherInfoUseCase = get(),
            weatherMapper = get()
        )
    }
}