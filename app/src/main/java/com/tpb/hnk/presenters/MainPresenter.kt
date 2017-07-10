package com.tpb.hnk.presenters

import android.app.Application
import com.tpb.hnk.data.database.Persistor
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.error
import com.tpb.hnk.util.info
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Singleton
class MainPresenter @Inject constructor(
        private val idService: IdService,
        private val itemService: ItemService,
        private val persistor: Persistor<HNItem>,
        private val application: Application) : Presenter<MainViewContract>, MainPresenterContract, ItemLoader {

    lateinit var view: MainViewContract
    val adapter = ItemAdapter(this, application.resources)
    var page = HNPage.TOP
    val idRequests = CompositeDisposable()
    val itemRequests = CompositeDisposable()

    override fun attachView(view: MainViewContract) {
        this.view = view
        view.bindRecyclerViewAdapter(adapter)
        view.showLoading()
        loadIds(page.toObservable(idService))
    }

    override fun setPageType(newPage: HNPage) {
        if (page != newPage) {
            page = newPage
            view.showLoading()
            refresh()
        }
    }

    override fun refresh() {
        info("Refreshing")
        loadIds(page.toObservable(idService))
    }


    override fun onQueryTextChange(query: String) {
    }

    override fun onQueryTextSubmitted(query: String) {
    }

    private fun loadIds(obs: Observable<List<Long>>) {
        idRequests.clear()
        itemRequests.clear()
        idRequests.add(obs.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::dispatchIds, this::handleIdLoadError))
    }

    private fun dispatchIds(ids: List<Long>) {
        info("Ids loaded")
        adapter.receiveIds(ids)
        view.hideLoading()
    }

    private fun handleIdLoadError(err: Throwable) {
        error("Id load error", err)
    }

    override fun loadItem(id: Long) {
        itemService.getItem(id).subscribe(object: Observer<HNItem> {
            override fun onNext(t: HNItem) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }
        })

        itemRequests.add(itemService.getItem(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = persistor.persist { adapter.receiveItem(it) }
                ))

    }



    override fun loadRange(ids: LongArray) {

    }
}
