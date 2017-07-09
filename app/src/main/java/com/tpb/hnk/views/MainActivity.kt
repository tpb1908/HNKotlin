package com.tpb.hnk.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.tpb.hnk.App
import com.tpb.hnk.R
import com.tpb.hnk.data.services.HNPage
import com.tpb.hnk.presenters.MainPresenter
import com.tpb.hnk.presenters.MainViewContract
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by theo on 08/07/17.
 */
class MainActivity : AppCompatActivity(), MainViewContract {


    @Inject lateinit var presenter: MainPresenter

    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).netComponent.inject(this)

        recycler.layoutManager = LinearLayoutManager(this)
        presenter.attachView(this)

        refresher.setOnRefreshListener { presenter.refresh() }

        menuInflater.inflate(R.menu.menu_main, action_menu_view.menu)


        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        spinner = action_menu_view.menu.findItem(R.id.spinner).actionView as Spinner
        val ids = HNPage.values()
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ids.map { getString(it.id) })

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View, pos: Int, id: Long) {
                presenter.setPageType(ids[pos])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

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