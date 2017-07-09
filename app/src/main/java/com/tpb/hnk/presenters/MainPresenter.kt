package com.tpb.hnk.presenters

import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.data.services.ItemService
import com.tpb.hnk.util.info
import io.reactivex.android.schedulers.AndroidSchedulers
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


    override fun attachView(view: MainViewContract) {
        this.view = view
        view.bindRecyclerViewAdapter(adapter)
        view.showLoading()
        idService.listTopIds().observeOn(AndroidSchedulers.mainThread()).subscribe { t1, t2 ->
            info("First $t1\nSecond $t2")
            adapter.receiveIds(t1)

            view.hideLoading()
        }
    }

    override fun loadRange(ids: LongArray) {

    }

    override fun loadItem(id: Long) {
        itemService.getItem(id).observeOn(AndroidSchedulers.mainThread()).subscribe {
            adapter.receiveItem(it)
        }
    }

    override fun loadItems() {

    }

    override fun refresh() {
    }
}