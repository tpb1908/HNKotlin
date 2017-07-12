package com.tpb.hnk.presenters

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView

/**
 * Created by theo on 08/07/17.
 */
interface MainViewContract {

    fun showLoading()

    fun hideLoading()

    fun showSneakerError(@StringRes titleRes: Int, @StringRes messageRes: Int)

    fun showErrorState()

    fun showDataState()

    fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>)

}