package com.gp.oktest.kotlintest

fun main(args: Array<String>) {
    //forEach也能break和continue

//    var intArray = IntArray(100) { i -> (i + 1) }
    var intArray = intArrayOf(1, 2, 3)
    val newList = intArray.filter { i ->
        i % 2 == 1
    }

//    newList.forEach {
//        println(it)
//    }


    //跳跃语法
    //Kotlin中引入了标签的概念，可以直接控制程序应该执行的代码是什么。
    //标签后面用@标明。
    //成对的标签可以自己定义

    //====================return========================
    var list1 = listOf(1, 2, 3, 4, 5)

    list1.forEach {
        if (it == 4) return@forEach
        println(it)
    }

    list1.forEach lit@{
        if (it == 4) return@lit
        println(it)
    }

    list1.forEach(fun(i) {
        if (i == 4) return
        println(i)
    })

    //====================break========================
    list1 = listOf(1, 2, 3, 4)

    val list2 = listOf(4, 3, 2, 1)

    loop@ for (i in list1) {
        for (j in list2) {
            if (i == j) {
                break@loop
            } else {
                println("$i ::::: $j")
            }
        }
    }

    for (i in list1) {
        loop@ for (j in list2) {
            if (i == j) {
                break@loop
            } else {
                println("$i ::::: $j")
            }
        }
    }

    //====================continue========================

    loop@ for (i in list1) {
        for (j in list2) {
            if (i == j) {
                continue@loop
            } else {
                println("$i ::::: $j")
            }
        }
    }

    for (i in list1) {
        ssss@ for (j in list2) {
            if (i == j) {
                continue@ssss
            } else {
                println("$i ::::: $j")
            }
        }
    }

}