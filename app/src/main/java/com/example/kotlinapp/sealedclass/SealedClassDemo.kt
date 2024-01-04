package com.example.kotlinapp.sealedclass

//With sealed class we are restricting the types, i.e we are dealing with individual instances
// Use sealed class more customized behavior
sealed class HttpError(val errorCode: Int) {
    data class UnauthorizedError(var reason: String) : HttpError(401)
    object NotFoundError : HttpError(404)
    object ErrorThree : HttpError(403)
    class ErrorFour : HttpError(405)
}

//With ENUM we are restricting the constant values.
//Enum are compile time constants

enum class HttpErrorEnum(val code: Int) {
    UnauthorizedError(401),
    NotFoundError(404),
    ErrorThree(100)
}