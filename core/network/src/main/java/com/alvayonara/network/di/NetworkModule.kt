package com.alvayonara.network.di

import com.alvayonara.moviedb_android.network.BuildConfig
import com.alvayonara.network.NetworkGenerator
import com.alvayonara.network.NetworkGeneratorImpl
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideRequest(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val interceptor = Interceptor { chain ->
            var request = chain.request()
            val url =
                request.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

            val builder = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url)

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
