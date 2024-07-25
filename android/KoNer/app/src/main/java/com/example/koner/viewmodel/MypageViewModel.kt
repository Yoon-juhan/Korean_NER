package com.example.koner.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koner.api.RetrofitClient
import com.example.koner.model.HistoryRecord
import com.example.koner.model.LoginRequest
import com.example.koner.model.LoginResponse
import com.example.koner.model.PredictResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageViewModel : ViewModel() {

    val historyResponse = MutableLiveData<List<HistoryRecord>>()

    fun getHistory(id:String) {

        RetrofitClient.api.getHistory(id).enqueue(object :
            Callback<List<HistoryRecord>> {
            override fun onResponse(call: Call<List<HistoryRecord>>, response: Response<List<HistoryRecord>>) {
                if (response.isSuccessful) {

                    historyResponse.value = response.body()
                    Log.d("요청 성공", response.toString())
                    Log.d("예측기록 응답", historyResponse.value.toString())

                } else {
                    Log.d("요청 실패", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<HistoryRecord>>, t: Throwable) {
                Log.d("요청 실패", t.localizedMessage)
            }
        })
    }
}