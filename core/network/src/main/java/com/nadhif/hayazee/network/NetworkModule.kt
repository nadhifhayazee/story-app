package com.nadhif.hayazee.network

import com.nadhif.hayazee.datastore.AppDataStore
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
import javax.inject.Singleton


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
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(headerInterceptor)
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
    fun provideHeaderInterceptor(
        appDataStore: AppDataStore
    ): Interceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(Constant.HEADER_ACCEPT, Constant.HEADER_APP_JSON)
            .addHeader(Constant.HEADER_CONTENT_TYPE, Constant.HEADER_APP_JSON)
            .addHeader(Constant.HEADER_AUTHORIZATION, appDataStore.getUser()?.token ?: "")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient.Builder,
    ): ApiService {
        return retrofitBuilder.client(okHttpClient.build()).baseUrl(BuildConfig.BASE_URL).build()
            .create(ApiService::class.java)
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