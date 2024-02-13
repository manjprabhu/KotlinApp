package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ScopeFunctions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        methodFour()
    }

    private fun methodOne() {
        // Run
        val obj1 = MyObject("One", 1)
        val result = obj1.run {
            type += 1
            "$name is now of type $type"
        }
        println("==>> Result :$result")
        println("==>> Object is $obj1")


        //*********************************
        //with

        println("==>>*********************************")
        val result2 = with(obj1) {
            name = "Two"
            type = 7
            "MyObject is $name and type is  $type"
        }

        println("==>> Result :$result2")
        println("==>> Object is $obj1")
    }

    data class Car(var brand: String, var model: String, var year: Int)


    private fun methodTwo() {
        val myCar = Car("Toyota", "Camry", 2022)

        //It is recommended to use with for calling functions on the context object without providing the lambda result.
        // In the code, with can be read as “with this object, do the following.”
        with(myCar) {
            model = "Corolla"
            year += 1

        }
        println("==>> My car is  :$myCar")

        // same as below(run) usage, though this approach will work however this is not recommended because generally "with" is used to perform operation on object without
        //providing the return result
        val result = with(myCar) {
            model = "Corolla"
            year += 1
            "My car is a $brand $model from $year"
        }
        println("==>> Result :$result")
        println("==>> My car is  :$myCar")


        println("==>>*********************************")
        //run is useful when the lambda contains both the object initialization and the computation of the return value.
        val myCar1 = Car("Toyota", "Fortuner", 2022)
        val result1 = myCar1.run {
            model = "Corolla"
            year += 1
            "My car is a $brand $model from $year"
        }
        println("==>> Result :$result1")
        println("==>> My car is  :$myCar1")
    }


    //Use "let" when we  want to perform operations on a non-null object.
// You need to transform the object and return a result.You want to introduce a new variable scope.
    private fun methodThree() {
        var value: String? = null

        val result = value?.let {
            it.length
        }?: 10

        println("==>> Result :$result")
    }

    private fun methodFour() {
        class person {
            var name:String  = "Ninja"
            var age:Int = 10
        }

        var s1 = person()
        with(s1) {
            println("==>> $name")
            println("==>> $age")
        }

        println("==>>*********************************")
        var s2:person? = null
        val r1 = with(s2) {
            println("==>> ${this?.name}")
            println("==>> ${this?.age}")
            "Person  ${this?.name } is of age ${this?.age}"
        }
        println("==>>R1:  $r1")

        //instead of above null check here we can use "run"
        println("==>>*********************************")
        val r2 = s2?.run{
            println("==>> $name")
            println("==>> $age")
            "Person  $name is of age $age"
        }?: "Person is NULL...."
        println("==>>R2:  $r2")

        println("==>>*********************************")
        var r3 = s2?.let {
            it.name =  "John"
        }?: "person is null..."
        println("==>>R3:  $r3")
    }
}