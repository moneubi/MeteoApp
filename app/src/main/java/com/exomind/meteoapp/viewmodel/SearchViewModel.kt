package com.exomind.meteoapp.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exomind.meteoapp.common.Constant
import com.exomind.meteoapp.di.RetrofitInstance
import com.exomind.meteoapp.domain.models.Weather
import com.exomind.meteoapp.domain.repository.SearchRepository
import com.exomind.meteoapp.utils.adapters.SearchWeatherAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val weathers: MutableList<Weather> = ArrayList()
    private var weather: MutableLiveData<Weather?> = MutableLiveData()
    private var _isFinishSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFinishSearch get() = _isFinishSearch
    private lateinit var timer: CountDownTimer
    private var numberSeconds = 0
    private var state = ""
    private var _progressValue: MutableLiveData<Int> = MutableLiveData(2)
    val progressValue get() = _progressValue

    fun getWeatherObservable(): MutableLiveData<Weather?> {
        return weather
    }

    fun getWeather(state: String) {
        val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(SearchRepository::class.java)
        val call = retrofitInstance.getWeather(state = state, appID = Constant.APP_ID)
        call.enqueue(object : Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                weather.postValue(response.body())
                initTimer()
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                weather.postValue(null)
            }
        })
    }

    fun addWeather(weather: Weather) {
        weathers.add(weather)
    }

    private fun initTimer() {
        timer = object : CountDownTimer(Constant.TIME_10000.toLong(), Constant.TIME_1000.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                if (numberSeconds < Constant.TIME_50) {
                    numberSeconds += 1
                    if (_progressValue.value!! < 100) {
                        _progressValue.postValue(_progressValue.value!! + 2)
                    }
                } else {
                    timer.cancel()
                    _isFinishSearch.postValue(true)
                }
            }
            override fun onFinish() {
                when (numberSeconds) {
                    Constant.SECOND_10 -> {
                        state = Constant.BORDEAUX
                    }
                    Constant.SECOND_20 -> {
                        state = Constant.RENNES
                    }
                    Constant.SECOND_30 -> {
                        state = Constant.MARSEILLE
                    }
                    Constant.SECOND_40 -> {
                        state = Constant.LYON
                    }
                    Constant.SECOND_50 -> {
                        state = Constant.TOULOUSE
                    }
                }
                getWeather(state = state)
            }
        }
        timer.start()
    }

    fun reset() {
        restartAll()
        timer.cancel()
    }

    fun restartAll() {
        _progressValue.postValue(2)
        _isFinishSearch.postValue(false)
        numberSeconds = 0
        weathers.clear()
    }

    fun setupWeather(context: Context, recyclerView: RecyclerView){
        recyclerView.apply {
            adapter = SearchWeatherAdapter(weathers = weathers)
            val layoutManagerG = GridLayoutManager(context, 1)
            layoutManagerG.orientation = LinearLayoutManager.VERTICAL
            layoutManager = layoutManagerG
            setHasFixedSize(false)
            itemAnimator = DefaultItemAnimator()
        }
    }
}