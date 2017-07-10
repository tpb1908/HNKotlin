package com.tpb.hnk.data.database

import com.tpb.hnk.data.models.HNItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by theo on 10/07/17.
 */
class Persistor(val database: Database) {

    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    inline fun persist(crossinline onNext: (i: HNItem) -> Unit): (i: HNItem) -> Unit {
        return {
            executor.submit { database.itemDao().insertItem(it) }
            onNext(it)
        }
    }


}