package com.example.android.hilt.di

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/*

MainActivity는 provideNavigator(activity: FragmentActivity) 함수를 호출하여 ServiceLocator에서 AppNavigator 인스턴스를 가져옵니다.

AppNavigator는 인터페이스이므로 생성자 삽입을 사용할 수 없습니다. 인터페이스에 사용할 구현을 Hilt에 알리려면 Hilt 모듈 내 함수에 @Binds 주석을 사용하면 됩니다.

@Binds 주석은 추상 함수에 달아야 합니다(이 함수는 추상 함수이므로 코드를 포함하지 않고 클래스도 추상화되어야 함). 추상 함수의 반환 유형은 구현을 제공하려는 인터페이스(예: AppNavigator)입니다. 구현은 인터페이스 구현 유형(예: AppNavigatorImpl)으로 고유한 매개변수를 추가하여 지정됩니다.

이전에 생성한 DatabaseModule 클래스에 정보를 추가할 수 있을까요? 아니면 새 모듈이 필요할까요? 새 모듈을 만들어야 하는 데는 다음과 같은 몇 가지 이유가 있습니다.

효율적인 구성을 위해 모듈 이름은 제공하는 정보 유형을 전달해야 합니다. 예를 들어, DatabaseModule이라는 모듈에 탐색 결함을 포함하는 것은 적절하지 않습니다.

DatabaseModule 모듈은 ApplicationComponent에 설치되므로 애플리케이션 컨테이너에서 결합을 사용할 수 있습니다. 새 탐색 정보(예: AppNavigator)에는 활동의 특정 정보가 필요합니다(AppNavigatorImpl은 Activity를 종속 항목으로 포함하기 때문). 따라서, Application 컨테이너가 아닌 Activity 정보를 사용할 수 있는 Activity 컨테이너에 설치해야 합니다.

Hilt 모듈에는 비정적 결합 메서드와 추상 결합 메서드를 모두 포함할 수 없으므로 동일한 클래스에 @Binds와 @Provides 주석을 배치하면 안 됩니다.

*/
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}