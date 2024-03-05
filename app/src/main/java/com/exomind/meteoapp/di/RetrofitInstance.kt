package com.exomind.meteoapp.di

import com.exomind.meteoapp.common.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// My class to instanciate retrofit
class RetrofitInstance {
    companion object {

        fun getRetrofitInstance(): Retrofit {
            //add my interceptor from logging
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            //return the instance of retrofit
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_PRIYER)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}