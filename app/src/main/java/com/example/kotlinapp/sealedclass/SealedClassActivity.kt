package com.example.kotlinapp.sealedclass

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class SealedClassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val error: HttpError = HttpError.NotFoundError
        when (error) {
            HttpError.NotFoundError -> Unit
            is HttpError.ErrorThree -> Unit
            is HttpError.UnauthorizedError -> Unit
        }

        val errorEnum = HttpErrorEnum.ErrorThree
        when (errorEnum) {
            HttpErrorEnum.UnauthorizedError -> TODO()
            HttpErrorEnum.NotFoundError -> TODO()
            HttpErrorEnum.ErrorThree -> TODO()
        }
    }
}