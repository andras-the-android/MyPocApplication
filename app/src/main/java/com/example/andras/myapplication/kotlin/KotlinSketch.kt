package com.example.andras.myapplication.kotlin

import com.example.andras.myapplication.Log

class KotlinSketch {

    val TAG = "KotlinSketch"

    fun start() {
        aaaaa()
    }

    data class jjjj(val a: Int, val b: Int)

    fun aaaaa() {

        infix fun jjjj.concatStr(str: String) = this.a.toString() + str

        val j = jjjj(10, 11)

        Log.d(TAG, j concatStr "abc")



    }
}