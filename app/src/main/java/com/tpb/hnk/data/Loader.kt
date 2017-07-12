package com.tpb.hnk.data

import com.tpb.hnk.data.database.IdDao
import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNIdList
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.ConnectivityAware
import com.tpb.hnk.util.ConnectivityListener
import com.tpb.hnk.util.info
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 11/07/17.
 */
class Loader(connectivityListener: ConnectivityListener, val itemService: ItemService,
             val itemDao: ItemDao,
             val idService: IdService,
             val idDao: IdDao,
             val itemPersistor: Persistor<HNItem>,
             val idPersistor: Persistor<HNIdList>): ConnectivityAware {

    private var shouldUseNetwork = false

    init {
        connectivityListener.addListener(this)
    }

    fun getIds(page: HNPage,
               onNext: (it: List<Long>) -> Unit = { info("Default onNext")},
               onError: (err: Throwable) -> Unit = { info("Default onError")},
               onComplete: () -> Unit = { info("Default onComplete")},
               subscribeScheduler: Scheduler = Schedulers.newThread(),
               observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
        info("Beginning id load")
        if (shouldUseNetwork) {
            return page.toObservable(idService)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribeBy(onError,
                            onComplete,
                            idPersistor.persist(onNext, { HNIdList(System.nanoTime(), page, it) })
                    )
        } else {
            return idDao.getLastIdList()
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribe({
                        if (it.isEmpty()) {
                            onError(Throwable("No ids in db"))
                        } else {
                            onNext(it.first().ids)
                        }
                    }, onError, onComplete)
        }
    }

    fun getAllIdLists(): Flowable<List<HNIdList>> {
        return idDao.getAllIdLists()
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
                    .subscribeBy(onNext = itemPersistor.persist(onNext), onError = onError, onComplete = onComplete)
        } else {
            return itemDao.getById(id)
                    .observeOn(observeScheduler)
                    .subscribeOn(subscribeScheduler)
                    .subscribe(onNext, onError, onComplete)
        }
    }

    override fun networkChange(isActive: Boolean) {
        shouldUseNetwork = isActive
    }
}