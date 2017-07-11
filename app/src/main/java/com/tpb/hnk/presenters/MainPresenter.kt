package com.tpb.hnk.presenters

import android.app.Application
import com.tpb.hnk.R
import com.tpb.hnk.data.ItemLoader
import com.tpb.hnk.data.Loader
import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.util.ConnectivityAware
import com.tpb.hnk.util.ConnectivityListener
import com.tpb.hnk.util.error
import com.tpb.hnk.util.info
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Singleton
class MainPresenter @Inject constructor(
        private val idService: IdService,
        private val application: Application,
        private val loader: Loader,
        private val connectivityListener: ConnectivityListener) : Presenter<MainViewContract>, MainPresenterContract, ItemLoader, ConnectivityAware {

    lateinit var view: MainViewContract
    val adapter = ItemAdapter(this, application.resources)
    var page = HNPage.TOP
    val idRequests = CompositeDisposable()
    val itemRequests = CompositeDisposable()

    override fun attachView(view: MainViewContract) {
        this.view = view
        connectivityListener.addListener(this)
        view.bindRecyclerViewAdapter(adapter)
        view.showLoading()
        loadIds()
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
        loadIds()
    }


    override fun onQueryTextChange(query: String) {
    }

    override fun onQueryTextSubmitted(query: String) {
    }

    override fun networkChange(isActive: Boolean) {
        if (!isActive) {
            view.showError(R.string.error_title_no_network, R.string.error_message_no_network)
        }
    }

    private fun loadIds() {
        idRequests.clear()
        itemRequests.clear()
        idRequests.add(loader.getIds(page, this::dispatchIds, this::handleIdLoadError))
    }

    private fun dispatchIds(ids: List<Long>) {
        info("Ids loaded")
        adapter.receiveIds(ids)
        view.hideLoading()
    }

    private fun handleIdLoadError(err: Throwable) {
        error("Id load error", err)
        view.hideLoading()
    }

    override fun loadItem(id: Long) {
        itemRequests.add(loader.getItem(id, onNext = { adapter.receiveItem(it) }))

    }

}
