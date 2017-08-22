package com.tpb.hnk.presenters.main

import com.tpb.hnk.data.services.HNPage

/**
 * Created by theo on 08/07/17.
 */
interface MainPresenterContract {

    fun refresh()

    fun setPageType(newPage: HNPage)

    fun onQueryTextChange(query: String)

    fun onQueryTextSubmitted(query: String)


}