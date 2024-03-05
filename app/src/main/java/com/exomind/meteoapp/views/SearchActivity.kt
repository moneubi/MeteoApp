package com.exomind.meteoapp.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.exomind.meteoapp.common.Constant
import com.exomind.meteoapp.databinding.ActivitySearchBinding
import com.exomind.meteoapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!viewModel.isFinishSearch.value!!) {
            viewModel.getWeather(Constant.PARIS)
        }
        viewModel.getWeatherObservable().observe(this){
            if (it != null) {
                viewModel.addWeather(weather = it)
            }
        }
        binding.btnRestart.setOnClickListener {
            viewModel.restartAll()
            viewModel.getWeather(Constant.PARIS)
            binding.progressValue.visibility = View.VISIBLE
            binding.tChargement.visibility = View.VISIBLE
            binding.btnRestart.visibility = View.GONE
        }
        binding.imgBack.setOnClickListener {
            viewModel.reset()
            finish()
        }
        viewModel.progressValue.observe(this) {
            if (it < 100) {
                binding.progressValue.progress = it
            } else {
                binding.progressValue.visibility = View.GONE
                binding.tChargement.visibility = View.GONE
                binding.btnRestart.visibility = View.VISIBLE
            }
        }
        viewModel.isFinishSearch.observe(this){
            viewModel.setupWeather(context = this, recyclerView = binding.rWeather)
        }
    }
}