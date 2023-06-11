package com.example.kotlinapp.usecase10

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class ScopingFunctions : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
    }


    // This will run all the 3 coroutine in parallel
    private fun performOperation() {
        val scope = CoroutineScope(Job())

        scope.launch {

            val job1 = launch {
                println("==>> Job1 started..")
                delay(100)
                println("==>> Job1 completed..")
            }

            val job2 = launch {
                println("==>> Job2 started..")
                delay(200)
                println("==>> Job2 completed..")
            }

//          To run job1 and job2 run paralley and after these 2 jobs completed, start job3
            job1.join()
            job2.join()

            val job3 = launch {
                println("==>> Job3 started..")
                delay(300)
                println("==>> Job completed..")
            }
        }
    }

    //  To run job1 and job2 run paralley and after these 2 jobs completed, start job3 , solution 2
    private fun performOperationTwo() {
        val scope = CoroutineScope(Job())

        scope.launch {

            launch {
                launch {
                    println("==>> Job1 started..")
                    delay(100)
                    println("==>> Job1 completed..")
                }

                launch {
                    println("==>> Job2 started..")
                    delay(200)
                    println("==>> Job2 completed..")
                }
            }.join()

            val job3 = launch {
                println("==>> Job3 started..")
                delay(300)
                println("==>> Job completed..")
            }
        }
    }

    //  To run job1 and job2 run paralley and after these 2 jobs completed, start job3 , solution 3
    private fun performOperationThree() {
        val scope = CoroutineScope(Job())

        scope.launch {

            coroutineScope {
                launch {
                    println("==>> Job1 started..")
                    delay(100)
                    println("==>> Job1 completed..")
                }

                launch {
                    println("==>> Job2 started..")
                    delay(200)
                    println("==>> Job2 completed..")
                }
            }

            val job3 = launch {
                println("==>> Job3 started..")
                delay(300)
                println("==>> Job completed..")
            }
        }
    }

    //  To run job1 and job2 run paralley and after these 2 jobs completed, start job3 , solution 3
    private fun performOperationFour() {
        val scope = CoroutineScope(Job())

        scope.launch {

            supervisorScope {
                launch {
                    println("==>> Job1 started..")
                    delay(100)
                    println("==>> Job1 completed..")
                }

                launch {
                    println("==>> Job2 started..")
                    delay(200)
                    println("==>> Job2 completed..")
                }
            }

            val job3 = launch {
                println("==>> Job3 started..")
                delay(300)
                println("==>> Job completed..")
            }
        }
    }

    private fun performOperationFive() {
        val scope = CoroutineScope(Job())

        scope.launch {

            doSomeTask()
            val job3 = launch {
                println("==>> Job3 started..")
                delay(300)
                println("==>> Job completed..")
            }
        }
    }

    //Execute doSomeTask as extension function of "CoroutineScope"
    // some all the 3 coroutines will run concurrently
    fun CoroutineScope.doSomeTask() {
        launch {
            println("==>> Job1 started..")
            delay(100)
            println("==>> Job1 completed..")
        }

        launch {
            println("==>> Job2 started..")
            delay(200)
            println("==>> Job2 completed..")
        }
    }


    // Calling this function will make job3 will be executed once job1 and job2 are completed
    suspend fun doSomeTaskTwo() = coroutineScope {
        launch {
            println("==>> Job1 started..")
            delay(100)
            println("==>> Job1 completed..")
        }

        launch {
            println("==>> Job2 started..")
            delay(200)
            println("==>> Job2 completed..")
        }
    }
}