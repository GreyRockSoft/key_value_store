package com.greyrock.keystore.store;


import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Thread)
open class SizedBenchmark {
    @Param("10", "1000", "100000") var size: Int = 0
}

open class Value(var value: Int) {
    val text = value.toString().reversed()
}

fun intValues(size: Int): Iterable<Int> {
    return 1..size
}

fun classValues(size: Int): Iterable<Value> {
    return intValues(size).map(::Value)
}
