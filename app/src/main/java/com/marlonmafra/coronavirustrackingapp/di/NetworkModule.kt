package com.marlonmafra.coronavirustrackingapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marlonmafra.coronavirustrackingapp.BuildConfig
import com.marlonmafra.coronavirustrackingapp.network.TrackingApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT_DEFAULT_TIME_IN_SECONDS = 30L
private val timeUnit: TimeUnit = TimeUnit.SECONDS

@Module
class NetworkModule(private val baseURL: String) {

    @Provides
    @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun prepareOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, timeUnit)
        okHttpClientBuilder.readTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, timeUnit)
        okHttpClientBuilder.writeTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, timeUnit)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        okHttpClientBuilder.addInterceptor(interceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRestAdapter(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideTrackingApiService(restAdapterBuilder: Retrofit.Builder): TrackingApiService {
        return restAdapterBuilder.build().create(TrackingApiService::class.java)
    }
}
