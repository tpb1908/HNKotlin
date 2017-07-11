package com.tpb.hnk.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.tpb.hnk.data.database.ItemDao
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.ItemService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by theo on 11/07/17.
 */
class Loader(context: Context, val itemService: ItemService, val dao: ItemDao, val persistor: Persistor<HNItem>): BroadcastReceiver() {

    private var isConnected = false

    init {
        context.registerReceiver(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        checkConnection(context)
    }

    fun getItem(id: Long,
                onNext: (it: HNItem) -> Unit = {},
                onError: (err: Throwable) -> Unit = {},
                onComplete: () -> Unit = {},
                subscribeScheduler: Scheduler = Schedulers.newThread(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()): Disposable {
        if (isConnected) {
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


    override fun onReceive(c: Context, p1: Intent?) {
        checkConnection(c)
    }

    private fun checkConnection(c: Context) {
        val manager = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}