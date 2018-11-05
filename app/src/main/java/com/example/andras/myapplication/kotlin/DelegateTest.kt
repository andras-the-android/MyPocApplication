package com.example.andras.myapplication.kotlin

import android.util.Log
import kotlin.reflect.KProperty

class DelegateTest {

    var p: String by Del()
    var p1: String by Del()

    init {
        Log.d("xxxx", p)
        p1 = "pppp"
        Log.d("xxxx", p1)
    }

    class Del {

        var v = "aaaaa"

        operator fun getValue(a: DelegateTest, property: KProperty<*>) : String {
            return v
        }

        operator fun setValue(a: DelegateTest, property: KProperty<*>, vvvv: String) {
            v = vvvv
        }
    }
}