package com.tpb.hnk.presenters.user

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by theo on 21/08/17.
 */
interface UserViewContract {

    fun showLoading()

    fun hideLoading()

    fun showSneakerError(@StringRes titleRes: Int, @StringRes messageRes: Int)

    fun showSnackbar(@StringRes messageRes: Int, @StringRes actionRes: Int, listener: View.OnClickListener)

    fun showErrorState()

    fun showDataState()

    fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>)

}