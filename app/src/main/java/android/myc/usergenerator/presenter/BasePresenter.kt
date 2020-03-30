package android.myc.usergenerator.presenter

import android.myc.usergenerator.view.BaseView

open class BasePresenter<V : BaseView?> {
    protected var mView: V? = null
    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

    val isViewAttached: Boolean
        get() = mView != null
}