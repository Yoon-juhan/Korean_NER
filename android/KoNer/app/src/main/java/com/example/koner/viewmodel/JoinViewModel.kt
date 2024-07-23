package com.example.koner.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koner.api.RetrofitClient
import com.example.koner.model.JoinRequest
import com.example.koner.model.JoinResponse
import com.example.koner.model.LoginRequest
import com.example.koner.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinViewModel : ViewModel() {
    var joinResponse = MutableLiveData<JoinResponse>()

    fun join(id:String, pw:String, nickname:String) {
        val joinRequest = JoinRequest(id, pw, nickname)

        RetrofitClient.api.join(joinRequest).enqueue(object :
            Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if (response.isSuccessful) {

                    joinResponse.value = response.body()
                    Log.d("요청 성공", response.toString())
                    Log.d("회원가입 응답", joinResponse.value.toString())

                } else {
                    Log.d("요청 실패", response.body().toString())
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.d("요청 실패", t.localizedMessage)
            }
        })
    }
}