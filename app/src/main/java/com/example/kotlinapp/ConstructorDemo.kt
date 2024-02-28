package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ConstructorDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conDemo()
    }

    class Person(firstName: String, age: Int) {
        private var mFirstName: String = ""
        private var mAge: Int = 10

        val children = mutableListOf<Person>()

        init {
            mFirstName = firstName.replaceFirstChar {
                it.uppercaseChar()
            }
            mAge = age
            println("==>> Person Name is :$mFirstName and his age is : $mAge")
        }

        init {
            mFirstName = firstName.replaceFirstChar {
                it.lowercaseChar()
            }
            mAge = age+10

            println("==>> 2 Person Name is :$mFirstName and his age is : $mAge")
        }

        //If class has primary constructor , each secondary constructor needs to delegate to the primary constructor.
        constructor(lastName:String,person:Person) : this("Mark",26) {
            println("==>> Last name is  : ${this.mFirstName}")
            person.children.add(this)
            println("==>> children : $children")
        }

    }

    private fun conDemo() {
        val p1 = Person("alen", 25)

        val p2 = Person("Waugh",p1)
    }
}