package com.tpb.hnk.presenters

import com.tpb.hnk.data.services.IdService
import com.tpb.hnk.util.info
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by theo on 08/07/17.
 */
@Singleton
class MainPresenter @Inject constructor(private val idService: IdService) : Presenter<MainViewContract>, MainPresenterContract {


    lateinit var view: MainViewContract

    override fun bindView(view: MainViewContract) {
        this.view = view
        idService.getTopId().subscribe { t1, t2 ->
            info("First $t1\nSecond $t2")
        }

    }

    override fun loadItems() {
    }
}