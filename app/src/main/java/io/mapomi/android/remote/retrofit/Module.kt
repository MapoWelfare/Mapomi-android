package io.mapomi.android.remote.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mapomi.android.constants.BASE_URL
import io.mapomi.android.constants.DEBUG_MODE
import io.mapomi.android.remote.remotesources.RemoteInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideOkHttp() : OkHttpClient
    {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            if (DEBUG_MODE) addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(URL(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CallAdapter.Factory()).build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit) : RemoteInterface {
        return retrofit.create(RemoteInterface::class.java)
    }

}

const val CONNECT_TIMEOUT = 1L
const val WRITE_TIMEOUT = 5L
const val READ_TIMEOUT = 30L
