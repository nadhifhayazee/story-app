package com.nadhif.hayazee.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun getExample() = "get Example"

    @Provides
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named(Constant.HiltNamed.HEADER_INTERCEPTOR) headerInterceptor: Interceptor,
        @Named(Constant.HiltNamed.NETWORK_INTERCEPTOR) networkInterceptor: Interceptor,
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(networkInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .connectTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .callTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Named(Constant.HiltNamed.HEADER_INTERCEPTOR)
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(Constant.HEADER_ACCEPT, Constant.HEADER_APP_JSON)
            .addHeader(Constant.HEADER_CONTENT_TYPE, Constant.HEADER_APP_JSON)
            .addHeader(Constant.HEADER_AUTHORIZATION, "")
            .build()
        chain.proceed(request)
    }


//    @Provides
//    @Named(Constant.HiltNamed.NETWORK_INTERCEPTOR)
//    fun provideNetworkInterceptor(
//        @ApplicationContext context: Context
//    ): Interceptor = Interceptor { chain ->
//        val request: Request = chain.request()
//        if (!NetworkUtils.isConnected(context)) {
//            throw NetworkConnectionException(context)
//        }
//        chain.proceed(request)
//    }

}