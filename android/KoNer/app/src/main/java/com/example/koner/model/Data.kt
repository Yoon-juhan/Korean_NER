package com.example.koner.model

data class LoginRequest (
    val id:String,
    val pw:String
)

data class LoginResponse(
    val id:String,
    val pw:String,
    val nickname:String,
    val status:Boolean
)

data class JoinRequest (
    val id:String,
    val pw:String,
    val nickname:String
)

data class JoinResponse (
    val status:Boolean
)

data class PredictResponse (
    val ner: Map<String, List<String>>
)