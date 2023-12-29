package com.example.kotlinapp.objects

open class Student(var name:String) {

    open fun fullName() = println("==>> Full name of the student is : $name")
}