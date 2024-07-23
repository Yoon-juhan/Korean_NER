package com.example.koner.api

import com.example.koner.model.JoinRequest
import com.example.koner.model.JoinResponse
import com.example.koner.model.LoginRequest
import com.example.koner.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("login")
    fun login(@Body body: LoginRequest) : Call<LoginResponse>

    @POST("join")
    fun join(@Body body: JoinRequest) : Call<JoinResponse>
}