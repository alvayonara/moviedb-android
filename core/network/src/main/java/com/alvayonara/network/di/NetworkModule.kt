package com.alvayonara.network.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.alvayonara.moviedb_android.network.BuildConfig
import com.alvayonara.network.NetworkGenerator
import com.alvayonara.network.NetworkGeneratorImpl
import com.alvayonara.network.NetworkServiceWrapper

@Module
class NetworkModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .build()

    @Provides
    fun provideRequestHeader(
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val interceptor = Interceptor { chain ->
            var request = chain.request()
            val builder = request.newBuilder()
            builder.addHeader("Content-Type", "application/json")
            request = builder.build()
            chain.proceed(request)
        }

        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(chuckerInterceptor)
        return okHttpClient.build()
    }

    @Provides
    fun provideRxJavaConverter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)

    @Provides
    @NetworkScope
    fun provideNetworkGenerator(retrofitBuilder: Retrofit.Builder): NetworkGenerator =
        NetworkGeneratorImpl(retrofitBuilder)

    @Provides
    fun provideNetworkServiceWrapper(networkGenerator: NetworkGenerator): NetworkServiceWrapper =
        networkGenerator
}
