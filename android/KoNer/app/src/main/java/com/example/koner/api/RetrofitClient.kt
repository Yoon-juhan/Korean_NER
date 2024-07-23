package com.example.koner.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{

        private val client  = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create()) // String을 객체로 변환 (서버가 json을 리턴할 때 GsonConverterFactory)
            .build()

        val api:Api = client.create(Api::class.java)

    }


}