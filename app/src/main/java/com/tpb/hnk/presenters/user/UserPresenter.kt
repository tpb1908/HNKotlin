package com.tpb.hnk.presenters.user

import android.content.res.Resources
import android.view.View
import com.tpb.hnk.R
import com.tpb.hnk.dagger.scopes.ActivityScope
import com.tpb.hnk.data.loaders.ItemLoader
import com.tpb.hnk.data.loaders.UserLoader
import com.tpb.hnk.presenters.Presenter
import com.tpb.hnk.presenters.State
import com.tpb.hnk.presenters.shared.ItemAdapter
import com.tpb.hnk.presenters.shared.ItemAdapterHandlerContract
import com.tpb.hnk.util.ConnectivityAware
import com.tpb.hnk.util.ConnectivityListener
import com.tpb.hnk.util.error
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by theo on 21/08/17.
 */
@ActivityScope
class UserPresenter @Inject constructor(
        resources: Resources,
        private val userLoader: UserLoader,
        private val itemLoader: ItemLoader,
        private val connectivityListener: ConnectivityListener) : Presenter<UserViewContract>, UserPresenterContract, ConnectivityAware, ItemAdapterHandlerContract {


    lateinit var view: UserViewContract
    val adapter = ItemAdapter(this, resources)
    val idRequests = CompositeDisposable()
    val itemRequests = CompositeDisposable()
    var state = State.LOADING


    override fun attachView(view: UserViewContract) {
        this.view = view
        connectivityListener.addListener(this)
    }

    override fun refresh() {
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

    override fun loadItem(id: Long) {
        itemRequests.add(
                itemLoader.getItem(
                        id,
                        onNext = adapter::receiveItem,
                        onError = {
                            error("Item load error", it)
                            adapter.itemLoadError(id)
                        }
                )
        )
    }
}