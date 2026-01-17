package com.rtllabs.testing.di

import com.rtllabs.testing.data.ApiService
import com.rtllabs.testing.data.PostRepoImpl
import com.rtllabs.testing.domain.GetUseCasePost
import com.rtllabs.testing.domain.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePostRepository(apiService: ApiService): PostRepository =
        PostRepoImpl(apiService)

    @Provides
    @Singleton
    fun provideGetUseCasePost(repository: PostRepository) : GetUseCasePost =
        GetUseCasePost(repository)


}