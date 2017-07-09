package com.tpb.hnk.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tpb.hnk.App
import com.tpb.hnk.R
import com.tpb.hnk.presenters.MainPresenter
import com.tpb.hnk.presenters.MainViewContract
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by theo on 08/07/17.
 */
class MainActivity : AppCompatActivity(), MainViewContract {


    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).netComponent.inject(this)
        recycler.layoutManager = LinearLayoutManager(this)
        presenter.attachView(this)
        refresher.setOnRefreshListener { presenter.refresh() }
    }

    override fun showLoading() {
        runOnUiThread { refresher.isRefreshing = true }
    }

    override fun hideLoading() {
       runOnUiThread { refresher.isRefreshing = false }
    }

    override fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        recycler.adapter = adapter
    }
}