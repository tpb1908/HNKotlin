package com.tpb.hnk.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tpb.hnk.App
import com.tpb.hnk.R
import com.tpb.hnk.presenters.MainPresenter
import com.tpb.hnk.presenters.MainViewContract
import com.tpb.hnk.util.info
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

        presenter.bindView(this)

        info("Presenter $presenter")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}