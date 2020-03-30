package android.myc.usergenerator.model

import android.myc.usergenerator.net.RandomUserAPI
import android.myc.usergenerator.bean.ResponseBean
import android.myc.usergenerator.contract.MainContract
import io.reactivex.Observable

class MainModel : MainContract.Model {
    companion object {
        const val COUNT_RESULT: Int = 20
    }

    override fun getRandomUsers(page: Int?): Observable<ResponseBean> {
        return RandomUserAPI.Factory.create().getRandomUsers(page, COUNT_RESULT)
    }

    override fun getDataByGender(page: Int?, gender: String): Observable<ResponseBean> {
        return RandomUserAPI.Factory.create().getDataByGender(gender, page, COUNT_RESULT)
    }

    override fun getDataByNat(page: Int?, nat: String): Observable<ResponseBean> {
        return RandomUserAPI.Factory.create().getDataByNat(nat, page, COUNT_RESULT)
    }
}