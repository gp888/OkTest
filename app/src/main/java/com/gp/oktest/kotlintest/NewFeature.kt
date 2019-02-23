package com.gp.oktest.kotlintest

class NewFeature {
    init {
        when (val i = 1) {
            3 -> "三"
            else -> i.toString()
        }
    }
}