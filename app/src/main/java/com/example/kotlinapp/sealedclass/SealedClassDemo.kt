package com.example.kotlinapp.sealedclass

sealed class HttpError {

    data class UnauthorizedError(var reason: String) : HttpError()
    object NotFoundError : HttpError()
    object ErrorThree : HttpError()
}

enum class HttpErrorEnum(val code:Int) {
    UnauthorizedError(401),
    NotFoundError(404),
    ErrorThree(100)
}