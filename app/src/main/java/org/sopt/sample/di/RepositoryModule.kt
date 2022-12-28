package org.sopt.sample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.repository.AuthRepositoryImpl
import org.sopt.sample.data.repository.MusicRepositoryImpl
import org.sopt.sample.data.repository.RegresRepositoryImpl
import org.sopt.sample.domain.repository.AuthRepository
import org.sopt.sample.domain.repository.MusicRepository
import org.sopt.sample.domain.repository.RegresRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindMusicRepository(musicRepositoryImpl: MusicRepositoryImpl): MusicRepository

    @Binds
    @Singleton
    abstract fun bindRegresRepository(regresRepository: RegresRepositoryImpl): RegresRepository
}