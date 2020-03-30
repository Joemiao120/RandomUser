package android.myc.usergenerator.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RefreshRecyclerView : RecyclerView {
    private var mBottomRefreshListener: OnBottomRefreshListener? = null
    private var mOnScrollListener: OnScrollListener? = null
    var isBottomRefreshable = false
        private set

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        init()
    }

    private fun init() {
        mBottomRefreshListener = null
        isBottomRefreshable = false
        mOnScrollListener = object : OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (isBottomViewVisible) {
                    if (mBottomRefreshListener != null) {
                        mBottomRefreshListener?.onBottomRefresh()
                    }
                }
            }
        }
    }

    private val lastVisibleItemPosition: Int
        private get() {
            val manager = layoutManager
            return if (manager is LinearLayoutManager) {
                manager.findLastVisibleItemPosition()
            } else NO_POSITION
        }

    private val isBottomViewVisible: Boolean
        private get() {
            val lastVisibleItem = lastVisibleItemPosition
            return lastVisibleItem != NO_POSITION && lastVisibleItem == adapter!!.itemCount - 1
        }

    fun setOnBottomRefreshListener(listener: () -> Unit?) {
        mBottomRefreshListener = listener
        if (mBottomRefreshListener != null) {
            addOnScrollListener(mOnScrollListener!!)
            isBottomRefreshable = true
        } else {
            removeOnScrollListener(mOnScrollListener!!)
            isBottomRefreshable = false
        }
    }

    interface OnBottomRefreshListener {
        fun onBottomRefresh()
    }
}