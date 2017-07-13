package com.tpb.hnk.presenters

import android.app.Application
import android.view.View
import com.tpb.hnk.R
import com.tpb.hnk.data.ItemLoader
import com.tpb.hnk.data.loaders.IdLoader
import com.tpb.hnk.data.loaders.Loader
import com.tpb.hnk.data.services.HNPage
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
        private val application: Application,
        private val idLoader: IdLoader,
        private val itemLoader: com.tpb.hnk.data.loaders.ItemLoader,
        private val connectivityListener: ConnectivityListener) : Presenter<MainViewContract>, MainPresenterContract, ItemLoader, ConnectivityAware {

    lateinit var view: MainViewContract
    val adapter = ItemAdapter(this, application.resources)
    var page = HNPage.TOP
    val idRequests = CompositeDisposable()
    val itemRequests = CompositeDisposable()
    var state = State.LOADING

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
            view.showDataState()
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
            view.showSneakerError(R.string.error_title_no_network, R.string.error_message_no_network)
        } else {
            view.showDataState()
            if (adapter.itemCount == 0) {
                refresh()
            } else {
                view.showSnackbar(
                        R.string.message_connection_established,
                        R.string.action_refresh,
                        View.OnClickListener { refresh() }
                )
            }
        }
    }

    private fun loadIds() {
        state = State.LOADING
        idRequests.clear()
        itemRequests.clear()
        idRequests.add(idLoader.getIds(page, this::dispatchIds, this::handleIdLoadError))
    }

    private fun dispatchIds(ids: List<Long>) {
        info("Ids loaded")
        state = State.DISPLAYING
        adapter.receiveIds(ids)
        view.showDataState()
        view.hideLoading()
    }

    private fun handleIdLoadError(err: Throwable) {
        error("Id load error", err)
        state = State.ERROR
        view.hideLoading()

        if (err.message == Loader.ERROR_NO_IDS) {
            view.showErrorState()
        }

    }

    override fun loadItem(id: Long) {
        itemRequests.add(
                itemLoader.getItem(
                        id,
                        onNext = adapter::receiveItem,
                        onError = { adapter.itemLoadError(id) }
                )
        )
    }


    enum class State {
        LOADING, DISPLAYING, ERROR
    }

}
