package com.toannguyen.weatherapp.fragment.adapter

import android.text.SpannableStringBuilder
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toannguyen.weatherapp.R
import com.toannguyen.weatherapp.databinding.ItemWeatherInformationBinding
import com.toannguyen.weatherapp.extensions.get
import com.toannguyen.weatherapp.extensions.getString

class WeatherAdapter :
    ListAdapter<WeatherAdapter.AdapterItem, RecyclerView.ViewHolder>(
        differUtils
    ) {

    companion object {
        private const val NONE = -1
        private const val TYPE_WEATHER = 1
        private const val TYPE_EMPTY = 2
    }

    sealed class AdapterItem {
        data class WeatherUI(
            val date: String,
            val tempAvg: String,
            val pressure: String,
            val humidity: String,
            val description: String
        ) : AdapterItem()

        object Empty : AdapterItem()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_WEATHER -> WeatherInfoViewHolder(parent[ItemWeatherInformationBinding::inflate])
            TYPE_EMPTY -> EmptyViewHolder(parent[ItemWeatherInformationBinding::inflate])
            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherInfoViewHolder -> holder.bindData(getItem(position) as AdapterItem.WeatherUI)
            is EmptyViewHolder -> holder.bindData()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AdapterItem.WeatherUI -> TYPE_WEATHER
            is AdapterItem.Empty -> TYPE_EMPTY
            else -> NONE
        }
    }

    class WeatherInfoViewHolder(private val binding: ItemWeatherInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(weather: AdapterItem.WeatherUI) = with(binding.tvWeatherInfo) {
            val spannable = SpannableStringBuilder()
            spannable.append(context.getString(R.string.text_date_title) + weather.date + "\n")
            spannable.append(
                context.getString(
                    R.string.text_temperature_title,
                    weather.tempAvg
                ) + "\n"
            )
            spannable.append(context.getString(R.string.text_pressure_title) + weather.pressure + "\n")
            spannable.append(
                context.getString(
                    R.string.text_humidity_title,
                    weather.humidity
                ) + "\n"
            )
            spannable.append(context.getString(R.string.text_description_title) + weather.description)
            text = spannable
        }
    }

    class EmptyViewHolder(private val binding: ItemWeatherInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData() = with(binding.tvWeatherInfo) {
            text = context.getString(R.string.text_not_found_result)
        }
    }
}

private val differUtils = AsyncDifferConfig.Builder(
    object : DiffUtil.ItemCallback<WeatherAdapter.AdapterItem>() {
        override fun areItemsTheSame(
            oldItem: WeatherAdapter.AdapterItem,
            newItem: WeatherAdapter.AdapterItem
        ): Boolean {
            return when (oldItem) {
                is WeatherAdapter.AdapterItem.WeatherUI -> {
                    oldItem == newItem as? WeatherAdapter.AdapterItem.WeatherUI
                }
                is WeatherAdapter.AdapterItem.Empty -> {
                    oldItem == newItem as? WeatherAdapter.AdapterItem.Empty
                }
            }
        }

        override fun areContentsTheSame(
            oldItem: WeatherAdapter.AdapterItem,
            newItem: WeatherAdapter.AdapterItem
        ): Boolean {
            return when (oldItem) {
                is WeatherAdapter.AdapterItem.WeatherUI -> {
                    oldItem == newItem as? WeatherAdapter.AdapterItem.WeatherUI
                }
                is WeatherAdapter.AdapterItem.Empty -> {
                    oldItem == newItem as? WeatherAdapter.AdapterItem.Empty
                }
            }
        }
    }
).build()