package com.tpb.hnk.presenters

/**
 * Created by theo on 08/07/17.
 */
class MainPresenter : Presenter<MainView> {

    lateinit var view: MainView

    override fun bindView(view: MainView) {
        this.view = view
    }
}