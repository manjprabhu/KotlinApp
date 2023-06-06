package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowExceptionHandling : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        performExceptionHandlingFour()
    }


    private suspend fun stockFlow(): Flow<String> = flow<String> {
        emit("One")
        emit("two")
        throw Exception("New exception is thrown")
    }

    private suspend fun fallbackFlow(): Flow<String> = flow<String> {
        emit("Fallback1 ")
    }

    private fun performExceptionHandling() {
        lifecycleScope.launch {
            try {
                val stockFlow = stockFlow()
                stockFlow.collect {
                    println("==>> Collected $it")
                }
            } catch (e: Exception) {
                println("==>> Caught the exception")
            }

        }
    }

    private fun performExceptionHandlingTwo() {
        lifecycleScope.launch {
            stockFlow().map {
                throw Exception(" Exception thrown in map")
            }.catch {
                println("==>> Exception caught in catch operator")
            }.collect {
                println("==>> Collected :$it")
            }
        }
    }

    //Fallback flow
    private fun performExceptionHandlingFallbackFlow() {

        lifecycleScope.launch {
            stockFlow().onCompletion { cause ->
                if (cause == null)
                    println("==>> Flow collected successfully")
                else
                    println("==>> Flow ended with exception")

            }.catch {
                println("==>> Exception caught in catch operator")
                emitAll(fallbackFlow())
            }.collect {
                println("==>> Collected $it")
            }
        }
    }

    private fun performExceptionHandlingThree() {

        lifecycleScope.launch {
            stockFlow().onCompletion { cause ->
                if (cause == null)
                    println("==>> Flow collected successfully")
                else
                    println("==>> Flow ended with exception")
            }.onEach {
                throw Exception("Exception thrown in onEach/collect block")
                println("==>> Collected in onEach block")
            }.catch { exception ->
                println("==>> Exception caught in catch operator :: $exception")
            }.collect {

            }
        }
    }

    private fun performExceptionHandlingFour() {
        lifecycleScope.launch {
            stockFlow().onCompletion { cause ->
                if (cause == null)
                    println("==>> Flow collected successfully")
                else
                    println("==>> Flow ended with exception")
            }.onEach {
                throw Exception("Exception thrown in collect block")
            }.catch { exception ->
                println("==>> Exception caught in catch operator :: $exception")
            }.launchIn(this)
        }
    }

}