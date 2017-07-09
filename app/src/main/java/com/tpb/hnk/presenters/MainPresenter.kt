package com.tpb.hnk.presenters

import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.error
import com.tpb.hnk.util.info
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Singleton
class MainPresenter @Inject constructor(
        private val idService: IdService,
        private val itemService: ItemService) : Presenter<MainViewContract>, MainPresenterContract, ItemLoader {

    lateinit var view: MainViewContract
    val adapter = ItemAdapter(this)
    var page = HNPage.TOP


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

    private fun loadIds(obs: Observable<List<Long>>) {
        obs.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::dispatchIds, this::handleIdLoadError)
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
        itemService.getItem(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.receiveItem(it)
                }
    }

    override fun refresh() {
        info("Refreshing")
        loadIds(page.toObservable(idService))
    }


    override fun loadRange(ids: LongArray) {

    }
}
