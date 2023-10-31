package com.example.kotlinapp.coroutineexceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExceptionHandling {

    private fun methodOne() {
        val topLevelScope = CoroutineScope(Job())

        topLevelScope.launch {
            try {
                throw RuntimeException("Throw runtime exception")
            } catch (e: Exception) {
                println("Handle $e")
            }
        }
    }

    // this will leads to crashing of application.Coroutine started with the inner launch doesn’t
    // catch the RuntimeException by itself and so it fails.
    private fun methodTwo() {
        CoroutineScope(Job()).launch {
            try {
                launch {
                    throw RuntimeException("Throw runtime exception inside nested coroutine....")
                }
            } catch (e: Exception) {
                println("Handle $e")
            }
        }
    }
}