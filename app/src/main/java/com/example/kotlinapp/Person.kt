package com.example.kotlinapp

import android.util.Log

class Person {

    var name ="ABC"
    var age= 25
    var contactNumber= 875382589

    fun display() {
        Log.d("TAG","Name: $name  Age: $age   ContactNumber: $contactNumber")
    }

    fun displayInfo()  = print("\n ==>>> Name: $name\n " +
            "Contact Number: $contactNumber\n " +
            "age: $age")

    fun changeAge() {
        age += 5;
    }

    fun displayAge() {
        println("NewAge is : $age");
    }

    fun setPersonName(name:String) {
        this.name = name
    }

    fun setPersonAge(age:Int) {
        this.age = age
    }

}