package com.mlm09kdev.SuperHeroDB.utils

import kotlinx.coroutines.*

/**
 * Created by Manuel Montes de Oca on 1/30/2020.
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    // only called once the view request an update
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}