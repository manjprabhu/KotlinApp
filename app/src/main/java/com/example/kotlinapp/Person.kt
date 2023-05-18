package com.example.kotlinapp

class Person {

    var name ="ABC"
    var age= 25
    var contactNumber= 875382589

    fun display() {
        println("Name: $name  Age: $age   ContactNumber:$contactNumber")
    }

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