package com.tpb.hnk.data.database

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by theo on 10/07/17.
 */
class Persistor<T>(val persistor: (it: T) -> Any) {

    companion object {
        val executor: ExecutorService = Executors.newCachedThreadPool()
    }

    fun persist(onNext: (param: T) -> Unit): (i: T) -> Unit {
        return {
            executor.submit { persistor(it) }
            onNext(it)
        }
    }

}