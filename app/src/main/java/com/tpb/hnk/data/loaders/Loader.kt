package com.tpb.hnk.data.loaders

import com.tpb.hnk.util.ConnectivityAware
import com.tpb.hnk.util.ConnectivityListener

/**
 * Created by theo on 11/07/17.
 */
abstract class Loader(connectivityListener: ConnectivityListener): ConnectivityAware {

    protected var shouldUseNetwork = false

    init {
        connectivityListener.addListener(this)
    }


    override fun networkChange(isActive: Boolean) {
        shouldUseNetwork = isActive
    }

    companion object {
        val ERROR_NO_IDS = "NO_IDS_IN_DB"
        val ERROR_ITEM_NOT_FOND = "ITEM_NOT_FOUND"
    }
}