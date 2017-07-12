package com.tpb.hnk.data.database

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by theo on 10/07/17.
 *
 * @param persistor The function to be called to persist an instance of [T]
 */
class Persistor<T>(val persistor: (it: T) -> Any) {

    companion object {
        val executor: ExecutorService = Executors.newCachedThreadPool()
    }

    /**
     * Returns a function which takes an instance of a type, and
     * executes a call to [persistor] on an ExecutorService before
     * calling [onNext]
     * @param onNext The method to be run after persisting
     */
    inline fun persist(crossinline onNext: (it: T) -> Unit): (it: T) -> Unit {
        return {
            executor.submit { persistor(it) }
            onNext(it)
        }
    }

    /**
     * Returns a function which takes an instance of a type [U], and
     * executes a call to [persistor] on an ExecutorService with the instance of
     * type [T] returned from the [converter] function, before calling [onNext]
     */
    inline fun<U> persist(crossinline onNext: (it: U) -> Unit, crossinline converter: (obj: U) -> T): (it: U) -> Unit {
        return {
            executor.submit { persistor(converter(it)) }
            onNext(it)
        }
    }

}