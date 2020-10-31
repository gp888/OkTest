package com.gp.oktest.kotlintest


fun main(args: Array<String>) {
    val list = listOf(1, 4, 2, 2)
    println(list.count { it%2 == 0})

    val list1 = listOf(1,2,3,4)
    println(list1.elementAtOrElse(6,{it *3}))

    val list2 = listOf(1, 4, 2, 2)
    println(list2.filterIndexed { index, it ->  index>0 && it >2} )


    val list3 = listOf(1, 2, 3, 4)
    println(list3.fold(2) { total, next -> total * next })


    val list4 = listOf(1, 2, 2, 4)
    println(list4.groupBy { if (it % 2 == 0) "right" else "error" })

    val list5 = listOf(1, 2, 2, 4)
    println(list5.last { it % 2 == 0 })


    val list6 = listOf(1, 2, 2, 4)
    println(list6.reduce { total, next -> total -next })
}
