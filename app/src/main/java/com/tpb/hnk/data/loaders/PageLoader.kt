package com.tpb.hnk.data.loaders

import com.tpb.hnk.data.database.IdDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNIdList
import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.util.ConnectivityListener
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 13/07/17.
 */
class PageLoader(connectivityListener: ConnectivityListener,
                 val idService: IdService,
                 val idDao: IdDao,
                 val idPersistor: Persistor<HNIdList>): Loader(connectivityListener) {

    fun getIds(page: HNPage,
               onNext: (it: List<Long>) -> Unit,
               onError: (err: Throwable) -> Unit,
               onComplete: () -> Unit = {},
               subscribeScheduler: Scheduler = Schedulers.io(),
               observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
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
                            onError(Throwable(Loader.ERROR_NO_IDS))
                        } else {
                            onNext(it.first().ids)
                        }
                    }, onError, onComplete)
        }
    }

    fun getAllIdLists(): Flowable<List<HNIdList>> {
        return idDao.getAllIdLists()
    }

}