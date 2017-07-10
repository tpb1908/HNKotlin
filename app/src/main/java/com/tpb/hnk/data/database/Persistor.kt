package com.tpb.hnk.data.database

import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.util.info

/**
 * Created by theo on 10/07/17.
 */
class Persistor(val database: Database) {

    fun persist(onNext: (i: HNItem) -> Unit): (i: HNItem) -> Unit {
        return {
            info("Persisting item $it")
            onNext(it)
        }
    }


}