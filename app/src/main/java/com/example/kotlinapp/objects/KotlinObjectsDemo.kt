package com.example.kotlinapp.objects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class KotlinObjectsDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        objectDeclarationDemo()
        objectExpressionDemo()
    }

    //Object Declaration( Singletone)
    private fun objectDeclarationDemo() {
        println(Person.display())
    }

    //Object Expression (Similar to java's annonymous class)
    private fun objectExpressionDemo() {
        val obj1 = object : Student("XYZ") {

        }
        obj1.fullName()


        val obj2 = object : Student("ABC") {

            override fun fullName() {
                println("==>> Annonymous  : $name")
            }
        }
        obj2.fullName()


        val obj3 = object {
           val x: Int = 10

            fun method() {
                println("==>> Value of X is  : $x")
            }
        }
        obj3.method()
    }

    //Suppose we want a class to have singletone behaviour for certain properties then use companion object
}