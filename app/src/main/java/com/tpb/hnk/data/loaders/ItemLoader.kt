package com.tpb.hnk.data.loaders

import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.ConnectivityListener
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 13/07/17.
 */
class ItemLoader(connectivityListener: ConnectivityListener,
                 val itemService: ItemService,
                 val itemDao: ItemDao,
                 val itemPersistor: Persistor<HNItem>): Loader(connectivityListener) {


    fun getItem(id: Long,
                onNext: (it: HNItem) -> Unit,
                onError: (err: Throwable) -> Unit,
                onComplete: () -> Unit = {},
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
        if (shouldUseNetwork) {
            return itemService.getItem(id)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribeBy(onNext = itemPersistor.persist(onNext), onError = onError, onComplete = onComplete)
        } else {

            return itemDao.getById(id)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribe({
                        if (it.isEmpty()) {
                            onError(Throwable(Loader.ERROR_ITEM_NOT_FOND))
                        } else {
                            onNext(it.first())
                        }
                    }, onError, onComplete)
        }
    }

}