package com.gp.oktest.kotlintest

class TopLevelClass {

    companion object {
        fun doSomeStuff() {

        }
    }

    object FakeCompanion {
        fun doOtherStuff() {

        }
    }
}

fun testCompanion() {
    TopLevelClass.doSomeStuff()
    TopLevelClass.Companion.doSomeStuff()
    TopLevelClass.FakeCompanion.doOtherStuff()
}