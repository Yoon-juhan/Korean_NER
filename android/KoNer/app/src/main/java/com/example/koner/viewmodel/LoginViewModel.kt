package com.example.koner.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koner.UserSharedPreferences
import com.example.koner.api.RetrofitClient
import com.example.koner.model.LoginRequest
import com.example.koner.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    var loginResponse = MutableLiveData<LoginResponse>()

    private fun saveUserInfo(loginResponse: LoginResponse) {
        val editor = UserSharedPreferences.sharedPreferences.edit()
        editor.clear().apply()
        editor.putString("id", loginResponse.id)
        editor.putString("pw", loginResponse.pw)
        editor.putString("nickname", loginResponse.nickname)
        editor.apply()
    }

    fun login(id:String, pw:String) {
        val loginRequest = LoginRequest(id, pw)

        RetrofitClient.api.login(loginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    loginResponse.value = response.body()
                    Log.d("요청 성공", response.toString())
                    Log.d("로그인 응답", loginResponse.value.toString())

                    // sharedPreferences에 값 저장하는 함수 호출
                    if (loginResponse.value?.status == true) {
                        loginResponse.value?.let { saveUserInfo(it) }
                    }

                } else {
                    Log.d("요청 실패", response.body().toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("요청 실패", t.localizedMessage)
            }
        })
    }

    fun resetLoginResponse() {
        loginResponse.value = LoginResponse(" ", "", "", false)
    }
}