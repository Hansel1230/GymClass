package com.universidad.gymclass.di

import com.universidad.gymclass.data.repository.AuthRepositoryImpl
import com.universidad.gymclass.data.repository.ClassRepositoryImpl
import com.universidad.gymclass.data.repository.ReservationRepositoryImpl
import com.universidad.gymclass.domain.repository.AuthRepository
import com.universidad.gymclass.domain.repository.ClassRepository
import com.universidad.gymclass.domain.repository.ReservationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository

    @Binds
    @Singleton
    abstract fun bindReservationRepository(
        reservationRepositoryImpl: ReservationRepositoryImpl
    ): ReservationRepository
}