package com.example.kotlinapp

import android.app.Activity
import android.os.Bundle

class ScopeFunction : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val person = Person().apply {
            age = 10
            name = "Test Name"
            contactNumber = 8328893
            changeAge()
        }
        person.displayInfo()
    }

    fun dataClassDemo() {
        val student1 = Student("One", "1234")
        val student2 = Student("Two", "236")

        println(student1 == student2)

        val student3 = student2.copy()
        println(student3.toString())

        val student4 = student1.copy(name = "Four")
        student4.marks = 36

        println(student1.component1())
        println(student1.component2())
    }

    data class Student(val name: String, val regNumber: String) {
        var marks: Int = 86
    }


}