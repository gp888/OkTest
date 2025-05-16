package com.gp.oktest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runInterruptible


class Test  {

    suspend fun test() {
        runInterruptible { // 内部会调用 interrupt 方法
            while (true){
                Thread.sleep(100)
                println("do something")
            }
        }
    }

}

fun main(): Unit = runBlocking {
    val t = Test()
    val job = launch(Dispatchers.IO) {
        t.test()
    }
    delay(500)
    job.cancel() // 这里只需要 cancel 就可以了
    delay(1000)
    println("end")

    testStateFlow()
}


val stateFlow = MutableStateFlow(0)

suspend fun testStateFlow() {
    collectStateFlow()
    delay(100)
    stateFlow.emit(1)
    // 第二次 collect 也会获取到之前发送过的 1
    collectStateFlow()
}

private fun collectStateFlow() {
    CoroutineScope(Dispatchers.IO).launch {
        stateFlow.collect {
            println("collect $it")
        }
    }
}