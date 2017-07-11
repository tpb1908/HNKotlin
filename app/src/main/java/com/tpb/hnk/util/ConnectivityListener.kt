package com.tpb.hnk.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import java.lang.ref.WeakReference

/**
 * Created by theo on 11/07/17.
 */
class ConnectivityListener(context: Context) : BroadcastReceiver() {

    companion object {
        var isConnected = false
            private set

        private val listeners = MutableList<WeakReference<ConnectivityAware?>>(0, { WeakReference(null) })
    }

    fun addListener(listener: ConnectivityAware) {
        listeners.add(WeakReference(listener))
        listener.networkChange(isConnected)
    }

    init {
        context.registerReceiver(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        checkConnection(context)
    }


    override fun onReceive(c: Context, p1: Intent?) {
        checkConnection(c)
        val iter = listeners.listIterator()
        while (iter.hasNext()) {
            val ref = iter.next()
            if (ref.get() != null) {
                (ref.get() as ConnectivityAware).networkChange(isConnected)
            } else {
                iter.remove()
            }
        }
    }

    private fun checkConnection(c: Context) {
        val manager = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}

interface ConnectivityAware {

    fun networkChange(isActive: Boolean)

}