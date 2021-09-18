package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


/*
이는 LoggerDataSource 유형이 Fragment에 삽입되어 있지만, Hilt는 같은 유형에 두 개의 결합이 있어 어느 구현을 사용해야 하는지 모르기 때문에 DuplicateBindings 오류가 표시됩니다.

Hilt에 동일한 유형의 다른 구현(여러 개의 결합)을 제공하는 방법을 알리려면 한정자를 사용하면 됩니다.

한정자는 결합을 식별하는 데 사용되는 주석입니다.

*/

@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingModule {

    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource): LoggerDataSource
}

@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {

    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
}