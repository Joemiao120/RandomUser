package android.myc.usergenerator.contract

import android.myc.usergenerator.bean.ResponseBean
import android.myc.usergenerator.bean.UserBean
import android.myc.usergenerator.view.BaseView
import io.reactivex.Observable

interface MainContract {
    interface Model {
        fun getRandomUsers(page: Int?): Observable<ResponseBean>
        fun getDataByGender(page: Int?, gender: String): Observable<ResponseBean>
        fun getDataByNat(page: Int?, nat: String): Observable<ResponseBean>
    }

    interface View : BaseView {
        override fun onError(throwable: Throwable?)
        fun onSuccess(list: List<UserBean>?)
        fun clearData()
    }

    interface Presenter {
        fun getRandomUsers()
        fun getDataByGender(gender: String)
        fun getDataByNat(nat: String)
    }
}