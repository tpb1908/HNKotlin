package com.tpb.hnk.views

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.irozon.sneaker.Sneaker
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
    lateinit var filter: ImageButton
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).netComponent.inject(this)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        presenter.attachView(this)

        refresher.setOnRefreshListener { presenter.refresh() }

        menuInflater.inflate(R.menu.menu_main, action_menu_view.menu)

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        bindMenuItems()



        handleIntent(intent)
    }

    private fun bindMenuItems() {
        val optionMenu = action_menu_view.menu.findItem(R.id.spinner)

        spinner = optionMenu.actionView as Spinner
        val ids = HNPage.values()
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ids.map { getString(it.id) })


        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View, pos: Int, id: Long) {
                presenter.setPageType(ids[pos])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val searchMenu = action_menu_view.menu.findItem(R.id.search)

        searchView = searchMenu.actionView as SearchView
        searchView.setSearchableInfo((getSystemService(Context.SEARCH_SERVICE) as SearchManager).getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        filter = ImageButton(this)
        filter.setBackgroundColor(android.R.color.transparent)
        filter.setImageResource(R.drawable.ic_filter_list_black)

        searchView.setOnSearchClickListener {
            optionMenu.actionView = filter
        }

        searchView.setOnCloseListener {
            optionMenu.actionView = spinner
            false
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                presenter.onQueryTextSubmitted(p0)
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                presenter.onQueryTextChange(p0)
                return true
            }
        })
    }


    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //TODO Do something with the query
        }
    }

    override fun showLoading() {
        runOnUiThread { refresher.isRefreshing = true }
    }

    override fun hideLoading() {
       runOnUiThread { refresher.isRefreshing = false}
    }

    override fun showError(errorRes: Int) {
        Sneaker.with(this)
                .setTitle("Test")
                .setMessage(getString(errorRes))
                .sneakError()

    }

    override fun bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        recycler.adapter = adapter
    }

}