package android.myc.usergenerator.view

import android.content.Context

interface BaseView {

    fun onError(throwable: Throwable?)
    fun getContext(): Context
}