package com.dz.movietmdp.di


import com.dz.movietmdp.common.Constants.BASE_URL
import com.dz.movietmdp.data.remote.MoviedbApi
import com.dz.movietmdp.domain.repository.MoviesRepository
import com.dz.movietmdp.domain.repository.MoviesRepositoryImpl
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
    fun provideApi(): MoviedbApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviedbApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MoviedbApi): MoviesRepository = MoviesRepositoryImpl(api)

}