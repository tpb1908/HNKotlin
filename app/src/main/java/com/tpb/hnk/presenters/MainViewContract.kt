package com.tpb.hnk.presenters

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView

/**
 * Created by theo on 08/07/17.
 */
interface MainViewContract {

    fun showLoading()

    fun hideLoading()

    fun showError(@StringRes titleRes: Int, @StringRes messageRes: Int)

    fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>)

}