package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.LogApplication
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

/*
Hilt 모듈은 @Module과 @InstallIn 주석이 달린 클래스입니다.
@Module은 Hilt에 모듈임을 알려 주고 @InstallIn은 어느 컨테이너에서 Hilt 구성요소를 지정하여 결합을 사용할 수 있는지 Hilt에 알려 줍니다.
Hilt 구성요소는 컨테이너로 간주할 수 있으며 구성요소 전체 목록은 여기에서 확인할 수 있습니다.

Hilt에서 삽입할 수 있는 Android 클래스마다 연결된 Hilt 구성요소가 있습니다.
예를 들어, Application 컨테이너는 ApplicationComponent와 연결되며 Fragment 컨테이너는 FragmentComponent와 연결됩니다.


모듈을 사용하여 Hilt에 결합을 추가합니다.
즉, 모듈을 사용하여 Hilt에 다양한 유형의 인스턴스 제공 방법을 알려 줍니다.
인터페이스나 프로젝트에 포함되지 않은 클래스와 같이 생성자가 삽입될 수 없는 유형의 결합을 Hilt 모듈에 포함합니다.
빌더를 사용하여 인스턴스를 생성해야 하는 OkHttpClient를 한 예로 들 수 있습니다.

Kotlin에서 @Provides 함수만 포함하는 모듈은 object 클래스가 될 수 있습니다.
이렇게 하면 제공자가 최적화되고 생성된 코드에 대부분 인라인됩니다.

*/
// @DisableInstallInCheck

// ApplicationComponent -> SingletonComponent ( ref : https://stackoverflow.com/questions/65266636/is-applicationcomponent-deprecated )
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    /*
    @Provides로 인스턴스 제공

    Hilt 모듈에 있는 함수에 @Provides 주석을 달아 Hilt에 생성자가 삽입될 수 없는 유형의 제공 방법을 알려 줄 수 있습니다.

    @Provides 주석이 있는 함수 본문은 Hilt에서 이 유형의 인스턴스를 제공해야 할 때마다 실행됩니다.

    @Provides 주석이 있는 함수의 반환 유형은 Hilt에 결합 유형 또는 유형의 인스턴스 제공 방법을 알려 줍니다.

    함수 매개변수는 유형의 종속 항목입니다.

    이 경우 DatabaseModule 클래스에 이 함수가 포함됩니다.
    */

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }

    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        return database.logDao()
    }
}