package com.example.kotlinapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        testflowBuilderTwo()
    }


    private fun testFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            (1..10).asFlow()
                .filter {
                    it % 2 == 0
                }.map {
                    it * it
                }.collect {
                    Log.d("", "Third : $it")
                }
        }
    }

    private fun testflowBuilderTwo() {
        lifecycleScope.launch {
            flowBuilderTwo().collect {
                Log.d("Third:", "$it")
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            flowBuilderTwo().collect {
//                Log.d("Third:", "$it")
//            }
//        }
    }

    private fun flowBuilderTwo(): Flow<Int> = flow {
        for (i in 1..10) {
            kotlinx.coroutines.delay(100)
            emit(i)
        }
    }

    private fun flowBuilderThree(): Flow<Int> = flowOf(1, 2, 7, 8, 9, 0, 9)

    private fun flowOfBuilder() {
        CoroutineScope(Dispatchers.IO).launch {
            flowOf(10, 4, 10, 5, 6, 8, 1, 34, 7)
                .map {
                    it * 1
                }.filter {
                    it % 2 == 0
                }.collect {
                    Log.d("Third flowOfBuilder:", "$it")
                }
        }
    }

    private fun testflowBuilderThree() {
        CoroutineScope(Dispatchers.Default).launch {
            flowBuilderThree().collect() {
                Log.d("Third Three:", "$it")
            }
        }
    }


    private fun testScopeFunctionLet() {
        Person().let {
            it.display()
            it.setPersonName("Hello")
            it.setPersonAge(30)
            it.display()
        }
    }

    private fun withoutLet() {
        val person = Person()
        person.display()
        person.setPersonName("NEW NAME")
        person.setPersonAge(78)
        person.display()
    }


    private fun testScopeFuntionapply() {
        val person = Person().apply {
            setPersonName("Name is Apply")
            setPersonAge(10)
        }
        person.display()
    }

    private fun testScopeFuntionapplyTwo() {
        val list = mutableListOf<Int>()
        list.apply {
            add(10)
            this.add(20)
            this.add(30)
            this.add(6)
            this.add(8)
        }.also {
            println("Print list $it")
        }.sort()
        println("Sorted list is $list")
    }

    private fun testScopeFuntionrun() {
        val list = mutableListOf(6, 3, 7, 11, 30)
        val result = list.run {
            this.add(10)
            this.add(40)
            this.add(4)
            count { it % 2 == 0 }
        }
        println("after run $result")
    }

    private fun testScopeFuntionrunTwo() {
        val nameList = mutableListOf<String>()
        val result = nameList.run {
            apply {
                add("One")
                add("two")
                add("three")
                add("four")
                add("five")
            }.also {
                println("Before Final result $it")
            }
            count { it.length == 3 }
        }
        println("Result List: $result")
    }





}