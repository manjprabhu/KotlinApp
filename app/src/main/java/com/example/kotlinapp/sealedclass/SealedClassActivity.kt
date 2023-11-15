package com.example.kotlinapp.sealedclass

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class SealedClassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val error: HttpError = HttpError.notFoundError
        when (error) {
            HttpError.notFoundError -> Unit
            is HttpError.errorThree -> Unit
            is HttpError.unauthorizedError -> Unit
        }

        val errorEnum = HttpErrorEnum.errorThree
        when (errorEnum) {
            HttpErrorEnum.unauthorizedError -> TODO()
            HttpErrorEnum.notFoundError -> TODO()
            HttpErrorEnum.errorThree -> TODO()
        }
    }
}