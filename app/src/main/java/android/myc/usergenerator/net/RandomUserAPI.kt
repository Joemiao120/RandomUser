package android.myc.usergenerator.net

import android.myc.usergenerator.net.ServiceFactory.createRetrofitService
import android.myc.usergenerator.bean.ResponseBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserAPI {
    @GET("api/")
    fun getRandomUsers(@Query("page") page: Int?, @Query("results") results: Int?): Observable<ResponseBean>

    @GET("api/")
    fun getDataByGender(@Query("gender") gender: String?, @Query("page") page: Int?, @Query("results") results: Int?): Observable<ResponseBean>

    @GET("api/")
    fun getDataByNat(@Query("nat") nat: String?, @Query("page") page: Int?, @Query("results") results: Int?): Observable<ResponseBean>

    object Factory {
        fun create(): RandomUserAPI {
            return createRetrofitService(
                RandomUserAPI::class.java,
                SERVICE_ENDPOINT
            )
        }
    }

    companion object {
        const val SERVICE_ENDPOINT = "https://randomuser.me/"
    }
}