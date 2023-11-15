package com.example.kotlinapp.sealedclass

sealed class HttpError(val errorCode: Int) {

    data class unauthorizedError(var reason: String) : HttpError(401)
    object notFoundError : HttpError(404)
    class errorThree : HttpError(300)
}

enum class HttpErrorEnum(val code:Int) {
    unauthorizedError(401),
    notFoundError(404),
    errorThree(100)
}