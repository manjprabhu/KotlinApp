package com.example.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.kotlinapp.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    val stFlow = MutableStateFlow(67)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intent = Intent(this, StateFlowDemo::class.java)
        startActivity(intent)
//        add(5, 10)
//        addition(12, 7)
//        nullcheckMethod()
//        elvisOperator()
//        assertionOperator()

//        letWithReturn()
//        performwithOperation()
//        smartcast2()

//        createFlow()
//        CoroutineScope(Dispatchers.IO).launch{
//          createHotflow()
//      }


    }


    override fun onResume() {
        super.onResume()
        mBinding.add.setOnClickListener {
            startCollect()
            startCollectHotFlow()
        }
    }

    fun createFlow(): Flow<Int> = flow {
        for (i in 1..100) {
            delay(100)
            emit(i)
        }
    }

    fun startCollect() {
        lifecycleScope.launch {
            createFlow().collect {
                Log.d("====>>>>", "Start collecting flow $it")
            }
        }
    }

    fun startCollectHotFlow() {
        lifecycleScope.launch {
            stFlow.collect {

                Log.d("====>>>>", "Start collecting HOT flow $it")
            }
        }
    }

    suspend fun createHotflow() {
        for (i in 1..79) {
            delay(1000)
            stFlow.emit(i)
        }
    }


    private fun add(a: Int, b: Int) {
        val sum = a + b;
        Log.v("sum:", sum.toString())
    }

    //function with default argument
    private fun addition(x: Int = 100, y: Int = 20) {
        val sum = x + y;
        Log.v("Sum:", sum.toString())
    }

    private fun nullcheckMethod() {
        var a: String?
        a = "hello string"
        Log.v("Value of A:", "" + a?.length)

        var b = "this is test"
        Log.v("Value of B:", "" + b.length)

        var c: String? = "hello"
        Log.v("Value of C:", "" + c?.length)

        var d: String?
        d = null
        Log.v("Value of D:", "" + d?.length)

    }

    //Elvis operator (?:)
    //For a non null value Elvis will return left side value
    private fun elvisOperator() {
        var x: String? = "null"

        var y: String? = "world"

        var z = x?.length ?: -1

        Log.v("Value of Z:", "" + z);
    }

    // not-null assertion(!!) operator converts null reference to non null reference and throws NPE if value is null
    private fun assertionOperator() {
        var x: String? = "Hello"
        val y = x!!.length
        Log.v("Value of x:", "" + y);
    }

    private fun performLetOperation() {
        Person().let {
            it.display()
            it.changeAge()
            it.displayAge()
            it.display()
        }
    }


    private fun letWithReturn() {
        val person = Person().let {
            "Name of the Person is: ${it.name}"
        }
        println("Person  ---:$person")
    }

    private fun performrunOperation() {
        Person().run {
            name = "XYZ"
            age = 35
            return@run "Name of the Person is: ${this.display()}"
        }
    }

    fun performwithOperation() {
        val person = with(Person()) {
            name = "Testname"
            age = 20
            "Person name is: $name age is :$age"
        }

        println(person)
    }

    //    run vs with
    private fun runVswith() {
        val person: Person? = null
        with(person) {
            this?.name = "mnb"
            this?.age = 28
            this?.contactNumber = 23783257
            this?.display()
        }
    }

    //the above function (runVswith) can rewritten using run in less number of lines
    private fun runVswith1() {
        val person: Person? = null
        person?.run {
            name = "weiot"
            age = 34
            contactNumber = 23835
            " Display: ${display()}"
        }
    }

    private fun performApply() {
        Person().apply {
            name = "newname"
            age = 23
            contactNumber = 23858
            "${display()}"
        }
    }

    //apply with null safety
    private fun performapply2() {
        val person: Person? = null
        person?.apply {
            name = "sdjf"
            age = 324
            contactNumber = 23572
            "${display()}"

        }
    }

    //also and let are same, diff is also does not accept return statement
    private fun performalso() {
        val person = Person().also {
            it.name = "jsdkf"
            println("${it.display()}")
        }
    }

    //smart cast

    private fun smartCast() {
        val obj: Any = "this is test string"

        if (obj is String) {
            println("String length is: ${obj.length}")
        }
    }

    private fun smartcast2() {
        val obj: Any = "this is test strings"

        if (obj !is String) {
            println("This is not string")
        } else {
            println("String length is: ${obj.length}")
        }
    }

    fun ifelse() {
        val someValue = 0
        someValue?.also {
            println("then")
            null
        } ?: run {
            println("else")
        }
    }


    fun testAlso(): Int {
        return Random.nextInt(100).also {
            println("getRandomInt() generated value $it")
        }
    }

    fun testAlso2() {
        val numberList = mutableListOf(5, 8, 11)

        numberList.also {
            println("Populating the list")
        }.apply {
            add(2)
            add(3)
            add(10)
        }.also {
            println("Sorting the list")
        }.count()

        println("====>> Count ${numberList.size}")
    }
}