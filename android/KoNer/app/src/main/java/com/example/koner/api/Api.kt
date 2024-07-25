package com.example.koner.api

import com.example.koner.model.HistoryRecord
import com.example.koner.model.JoinRequest
import com.example.koner.model.JoinResponse
import com.example.koner.model.LoginRequest
import com.example.koner.model.LoginResponse
import com.example.koner.model.PredictResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @POST("login")
    fun login(@Body body: LoginRequest) : Call<LoginResponse>

    @POST("join")
    fun join(@Body body: JoinRequest) : Call<JoinResponse>

    @GET("predict")
    fun predict(
        @Query("text") text:String,
    ) : Call<PredictResponse>

    @GET("/getHistory")
    fun getHistory(@Query("user_id") userId: String): Call<List<HistoryRecord>>
}