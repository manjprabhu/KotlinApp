package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinapp.interoperability.AppUtils

class ExtensionFunction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testExtension(ChildClass())
    }

    private fun AppUtils.exFunction(): Int {
        return 10
    }

    private fun MutableList<Int>.swap(i: Int, j: Int): MutableList<Int> {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
        return this
    }

    private fun String.removeFirstLastChar(): String {
        return this.substring(1, this.length - 1)
    }

    fun Student.isExcellent(marks: Int): Boolean {
        return marks > 90
    }

    private fun demoExtensionFunction() {
        val app = AppUtils
        println("==>> Extension function : ${app.exFunction()}")

        val list = mutableListOf(5, 10, 15)
        println("before swapping the list :$list")

        val resultList = list.swap(0, 2)
        println("after swapping the list :$resultList")

        val student = Student()

        val result = student.isPassed(45)
        println("Student passing status is $result")

        val excellentResult = student.isExcellent(92)
        println("Student excellent  status is $excellentResult")

        val newString = "Hello test"
        println("Result string : ${newString.removeFirstLastChar()}")

        MyClass.printCompanion()

    }


    //Companion object extension
    class MyClass {
        companion object {
        }
    }

    private fun MyClass.Companion.printCompanion() {
        println("==>> Printing from companion!!!")
    }


    open class ParentClass {
    }

    class ChildClass : ParentClass() {
    }

    private fun ParentClass.getName() = "ParentClass!!!"

    private fun ChildClass.getName() = "ChildClass!!!"

    private fun testExtension(obj: ParentClass) {
        val name = obj.getName()
        println("==>> $name ")
    }

}