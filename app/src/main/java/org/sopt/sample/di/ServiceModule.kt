package org.sopt.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.api.AuthService
import org.sopt.sample.data.api.MusicService
import org.sopt.sample.data.api.RegresService
import org.sopt.sample.di.NetworkModule.AuthRetrofit
import org.sopt.sample.di.NetworkModule.MusicRetrofit
import org.sopt.sample.di.NetworkModule.RegresRetrofit
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(@AuthRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesRegresService(@RegresRetrofit retrofit: Retrofit): RegresService =
        retrofit.create(RegresService::class.java)

    @Provides
    @Singleton
    fun providesMusicService(@MusicRetrofit retrofit: Retrofit): MusicService =
        retrofit.create(MusicService::class.java)
}