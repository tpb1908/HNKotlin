package com.tpb.hnk.views

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.irozon.sneaker.Sneaker
import com.tpb.hnk.R
import com.tpb.hnk.presenters.user.UserPresenter
import com.tpb.hnk.presenters.user.UserViewContract
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.stub_error_state.*
import javax.inject.Inject

/**
 * Created by theo on 21/08/17.
 */
class UserActivity : AppCompatActivity(), UserViewContract {

    @Inject lateinit var presenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        refresher.setOnRefreshListener { presenter.refresh() }
        presenter.attachView(this)

        handleIntent(intent)
    }

    override fun showLoading() {
        runOnUiThread { refresher.isRefreshing = true }
    }

    override fun hideLoading() {
        runOnUiThread { refresher.isRefreshing = false}
    }

    override fun showSneakerError(@StringRes titleRes: Int, @StringRes messageRes: Int) {
        Sneaker.with(this)
                .setTitle(getString(titleRes))
                .setMessage(getString(messageRes))
                .sneakError()
    }

    override fun showSnackbar(messageRes: Int, actionRes: Int, listener: View.OnClickListener) {
        Snackbar.make(coordinator, getString(messageRes), Snackbar.LENGTH_LONG)
                .setAction(getString(actionRes), listener)
                .show()
    }

    override fun showErrorState() {
        if (recycler.visibility == View.VISIBLE) {
            recycler.visibility = View.GONE
            error_state_layout.visibility = View.VISIBLE
        }
    }

    override fun showDataState() {
        if (error_state_layout.visibility == View.VISIBLE) {
            error_state_layout.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }

    override fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        recycler.adapter = adapter
    }

    private fun handleIntent(intent: Intent) {

    }

}