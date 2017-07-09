package com.tpb.hnk.presenters

import android.support.v7.widget.RecyclerView

/**
 * Created by theo on 08/07/17.
 */
interface MainViewContract {

    fun showLoading()

    fun hideLoading()

    fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>)

}