package com.tpb.hnk.presenters

/**
 * Created by theo on 08/07/17.
 */

interface Presenter<in T> {

    //Could be called setView, but that interferes with Kotlin
    fun bindView(view: T)

}