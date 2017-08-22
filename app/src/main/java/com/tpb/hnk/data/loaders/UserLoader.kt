package com.tpb.hnk.data.loaders

import com.tpb.hnk.data.models.HNUser
import com.tpb.hnk.data.services.UserService
import com.tpb.hnk.util.ConnectivityListener
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 22/08/17.
 */
class UserLoader(connectivityListener: ConnectivityListener,
                 val userService: UserService): Loader(connectivityListener) {

    fun getUser(id: String,
                onNext: (it: HNUser) -> Unit,
                onError: (err: Throwable) -> Unit,
                onComplete: () -> Unit = {},
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
        return userService.getUser(id)
                .observeOn(observeScheduler)
                .subscribeOn(subscribeScheduler)
                .subscribeBy(onNext = onNext, onError = onError, onComplete = onComplete)
    }

}