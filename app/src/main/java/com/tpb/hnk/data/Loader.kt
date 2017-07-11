package com.tpb.hnk.data

import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.ConnectivityAware
import com.tpb.hnk.util.ConnectivityListener
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 11/07/17.
 */
class Loader(connectivityListener: ConnectivityListener, val itemService: ItemService, val dao: ItemDao, val persistor: Persistor<HNItem>): ConnectivityAware {

    private var shouldUseNetwork = false

    init {
        connectivityListener.addListener(this)
    }

    fun getItem(id: Long,
                onNext: (it: HNItem) -> Unit = {},
                onError: (err: Throwable) -> Unit = {},
                onComplete: () -> Unit = {},
                subscribeScheduler: Scheduler = Schedulers.newThread(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
        if (shouldUseNetwork) {
            return itemService.getItem(id)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribeBy(onNext = persistor.persist(onNext), onError = onError, onComplete = onComplete)
        } else {
            return dao.getById(id)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribe(onNext, onError, onComplete)
        }
    }

    override fun networkChange(isActive: Boolean) {
        shouldUseNetwork = isActive
    }
}