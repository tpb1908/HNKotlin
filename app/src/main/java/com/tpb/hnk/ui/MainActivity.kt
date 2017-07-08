package com.tpb.hnk.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tpb.hnk.App
import com.tpb.hnk.R
import com.tpb.hnk.data.services.IdService
import javax.inject.Inject

/**
 * Created by theo on 08/07/17.
 */
class MainActivity : AppCompatActivity() {

    @Inject lateinit var idService: IdService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).netComponent.inject(this)



    }
}