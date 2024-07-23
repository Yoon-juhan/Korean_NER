package com.example.koner.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koner.api.RetrofitClient
import com.example.koner.model.JoinRequest
import com.example.koner.model.JoinResponse
import com.example.koner.model.PredictResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var predictResponse = MutableLiveData<PredictResponse>()

    fun predict(text:String) {

        RetrofitClient.api.predict(text).enqueue(object :
            Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                if (response.isSuccessful) {

                    predictResponse.value = response.body()
                    Log.d("요청 성공", response.toString())
                    Log.d("예측 응답", predictResponse.value.toString())

                } else {
                    Log.d("요청 실패", response.body().toString())
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                Log.d("요청 실패", t.localizedMessage)
            }
        })
    }
}