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

data class PredictJson(
    val person: List<String>,
    val organization: List<String>,
    val artifacts: List<String>,
    val date: List<String>,
    val time: List<String>,
    val location: List<String>,
    val animal: List<String>,
    val plant: List<String>,
    val event: List<String>,
    val study_field: List<String>,
    val theory: List<String>,
    val civilization: List<String>,
    val quantity: List<String>,
    val material: List<String>,
    val term: List<String>
)

data class HistoryRecord(
    val HISTORY_ID: String,
    val USER_ID: String,
    val TEXT: String,
    val PREDICT_JSON: String
)