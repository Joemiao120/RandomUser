package android.myc.usergenerator.presenter

import android.content.Context
import android.myc.usergenerator.bean.ResponseBean
import android.myc.usergenerator.bean.UserBean
import android.myc.usergenerator.contract.MainContract
import android.myc.usergenerator.contract.MainContract.Presenter
import android.myc.usergenerator.db.UsersDatabase
import android.myc.usergenerator.model.MainModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter : BasePresenter<MainContract.View?>(), Presenter {
    private val model: MainContract.Model
    private var database: UsersDatabase? = null
    var page = 0
    var flag: Int = RANDOM_USER
    var preRequest: String = "random_user"
    lateinit var mContext: Context

    companion object {
        const val RANDOM_USER = 0
        const val GENDER = 1
        const val NAT = 2
    }

    init {
        model = MainModel()
    }

    override fun getRandomUsers() {
        if (!isViewAttached) {
            return
        }
        mContext = mView?.getContext()!!

        database = UsersDatabase.getInstance(mContext)

        if (flag != RANDOM_USER) {
            flag = RANDOM_USER
            page = 0
            mView?.clearData()
        }

        subscribe(model.getRandomUsers(page++))
    }

    override fun getDataByGender(gender: String) {
        if (flag != GENDER || !preRequest?.equals(gender)) {
            flag = GENDER
            preRequest = gender
            page = 0
            mView?.clearData()
        }

        subscribe(model.getDataByGender(page++, gender))
    }

    override fun getDataByNat(nat: String) {

        if (flag != NAT || !preRequest?.equals(nat)) {
            flag = NAT
            page = 0
            preRequest = nat
            mView?.clearData()
        }

        subscribe(model.getDataByGender(page++, nat))
    }


    private fun subscribe(observable: Observable<ResponseBean>) {
        observable.subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .map {
                it.results
            }
            .doOnNext {
                 database?.userDao()?.insertAll(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<UserBean>?> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<UserBean>) {
                    mView?.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    mView?.onError(e)
                }
            })

    }


}