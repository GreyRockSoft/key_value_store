package com.greyrock.keystore.store

import com.google.common.collect.ImmutableList
import org.jctools.queues.MpscArrayQueue
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
open class Store_Test : SizedBenchmark() {

    lateinit var testData: ImmutableList<Value>

    @Setup
    fun setup() {
        val listBuilder = ImmutableList.builder<Value>()

        for (n in classValues(size)) {
            listBuilder.add(n)
        }

        testData = listBuilder.build()
    }

    @Benchmark
    fun concurrentQueue(bh: Blackhole) {
        benchmarkQueue(bh, ConcurrentLinkedQueue<Value>())
    }

    @Benchmark
    fun concurrentNonBlocking(bh: Blackhole) {
        benchmarkQueue(bh, MpscArrayQueue<Value>(2048))
    }

    fun benchmarkQueue(bh: Blackhole, queue: AbstractQueue<Value>) {
        val runnable = Runnable {
            for (i in 0..size) {
                bh.consume(queue.poll())
            }
        }

        val thread = Thread(runnable)

        thread.start()
        for(i in testData) {
            queue.offer(i)
        }

        thread.join()
    }
}
