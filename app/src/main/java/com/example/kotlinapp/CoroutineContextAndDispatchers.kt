package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class CoroutineContextAndDispatchers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameCoroutine()
    }


    // A child coroutine(Child one) with its own job defined will not be affected when parent job is cancelled.
    private fun methodOne() = runBlocking {
        val request = launch {

            //child one
            launch(Job()) {
                println("==>> Job1 : Child one running on independent job")
                delay(1000)
                println("==>> Job1 : Child one still running....")
            }

            //Child two
            launch {
                delay(100)
                println("==>> job2 : Child Two running on parent  job")
                delay(1000)
                println("==>> job2 : Child Two will be stopped....")
            }
        }

        println("Job is : ${coroutineContext[Job]}")
        delay(200)
        request.cancelAndJoin()
        println("==>>> Cancelling the job....")
    }

    //Naming a coroutine.
    private fun nameCoroutine() = runBlocking {

        val one = async(CoroutineName("oneCoroutine")) {
            delay(1000)
            println("First coroutine.....")
            10
        }

        val two = async(CoroutineName("twoCoroutine")) {
            delay(1000)
            println("Second coroutine.....")
            20
        }
        println("==>> ${one.await()+two.await()}")
    }

}