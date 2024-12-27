package com.unbounded.realizingself.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.unbounded.realizingself.core.base.UnboundedRealizingSelfApplication
import com.unbounded.realizingself.data.local.DataStoreRepository
import com.unbounded.realizingself.data.local.DataStoreRepositoryImpl
import com.unbounded.realizingself.data.remote.retrofitapi.ApiConstant.BASE_URL_PROD
import com.unbounded.realizingself.data.remote.retrofitapi.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val CONNECT_TIMEOUT_SECONDS = 20
    private const val READ_TIMEOUT_SECONDS = 20
    @Provides
    fun provideContentLengthInterceptor(): Interceptor {
        return ContentLengthInterceptor()
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
       // val cacheSize = (5 x 1024 x 1024).toLong()

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(  READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context,
    ): DataStoreRepository = DataStoreRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideUnboundedApplication(@ApplicationContext context: Context): UnboundedRealizingSelfApplication {
        return context.applicationContext as UnboundedRealizingSelfApplication
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_PROD)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .client(okHttpClient) // Inject OkHttpClient here
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


  }

class ContentLengthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBody = originalRequest.body

        // If the request has a body, add the Content-Length header
        val modifiedRequest = if (requestBody != null) {
            val contentLength = requestBody.contentLength()
            originalRequest.newBuilder()
                .header("Content-Length", contentLength.toString())
                .build()
        } else {
            originalRequest // No request body, so no need to add Content-Length header
        }

        return chain.proceed(modifiedRequest)
    }
}