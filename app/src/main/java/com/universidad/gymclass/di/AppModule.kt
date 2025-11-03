package com.universidad.gymclass.di

import com.universidad.gymclass.data.mapper.AuthMapper
import com.universidad.gymclass.data.mapper.ClassMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    fun provideAuthMapper(): AuthMapper {
        return AuthMapper()
    }

    @Provides
    fun provideClassMapper(): ClassMapper {
        return ClassMapper()
    }
}