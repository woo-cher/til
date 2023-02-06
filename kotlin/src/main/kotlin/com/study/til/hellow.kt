package com.study.til

fun main() {
    println("Hello kotlin!")
    getParamBetweenStr(1, 2)
}

fun arrayToStr(arr: Array<String>): String {
    return arr.contentToString()
}

fun toFunctional(arr: Array<String>) = arr.contentToString();

fun getParamBetweenStr(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}