package com.tpb.hnk.util

/**
 * Created by theo on 08/07/17.
 */
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import java.lang.Exception

/**
 * Created by theo on 01/07/17.
 */

class Extensions {

    companion object {

        fun getResId(resName: String, c: Class<out Any>): Int {
            try {
                val field = c.getDeclaredField(resName)
                return field.getInt(field)
            } catch (e: Exception) {
                error("Error getting resource", e)
                return -1
            }
        }

    }

}

/**
 * Adds an [RecyclerView.OnScrollListener] to show or hide the FloatingActionButton when the RecyclerView scrolls up
 * or down respectively
 */
fun RecyclerView.bindFloatingActionButton(fab: FloatingActionButton) = this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && fab.isShown) {
            fab.hide()
        } else if (dy < 0 && !fab.isShown) {
            fab.show()
        }
    }
})

/**
 * Calls the given function on [TextWatcher.afterTextChanged]
 */
fun TextView.addSimpleTextChangedListener(listener: (e : Editable) -> Unit) = this.addTextChangedListener(object: TextWatcher {
    override fun afterTextChanged(p0: Editable) {
        listener(p0)
    }

    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
    }
})



fun String.occurrencesOf(sub: String): Int {
    var count = 0
    var last = 0
    while (last != -1) {
        last = this.indexOf(sub, last)
        if (last != -1) {
            count++
            last += sub.length
        }
    }
    return count
}

fun String.occurrencesOf(ch: Char): Int = this.count { it == ch }

//Utilities for logging functions. Using class simple name as tag


fun Any.debug(message: String?) {
    Log.d(this::class.java.simpleName, message)
}

fun Any.debug(message: String?, tr: Throwable?) {
    Log.d(this::class.java.simpleName, message, tr)
}


fun Any.error(message: String?) {
    Log.e(this::class.java.simpleName, message)
}

fun Any.error(message: String?, tr: Throwable?) {
    Log.e(this::class.java.simpleName, message, tr)
}

fun Any.info(message: String?) {
    Log.i(this::class.java.simpleName, message)
}

fun Any.info(message: String?, tr: Throwable?) {
    Log.i(this::class.java.simpleName, message, tr)
}

fun Any.verbose(message: String?) {
    Log.v(this::class.java.simpleName, message)
}


fun Any.verbose(message: String?, tr: Throwable?) {
    Log.v(this::class.java.simpleName, message, tr)
}

fun Any.warn(message: String?) {
    Log.w(this::class.java.simpleName, message)
}


fun Any.warn(message: String?, tr: Throwable?) {
    Log.w(this::class.java.simpleName, message, tr)
}

fun Any.warn(tr: Throwable?) {
    Log.w(this::class.java.simpleName, tr)
}

fun Any.wtf(message: String?) {
    Log.wtf(this::class.java.simpleName, message)
}


fun Any.wtf(message: String?, tr: Throwable?) {
    Log.wtf(this::class.java.simpleName, message, tr)
}

fun Any.wtf(tr: Throwable?) {
    Log.wtf(this::class.java.simpleName, tr)
}
