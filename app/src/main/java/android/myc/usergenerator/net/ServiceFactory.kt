package android.myc.usergenerator.net

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceFactory {
    fun <T> createRetrofitService(clazz: Class<T>, endPoint: String): T {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val restAdapter = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return restAdapter.create(clazz)
    }
}