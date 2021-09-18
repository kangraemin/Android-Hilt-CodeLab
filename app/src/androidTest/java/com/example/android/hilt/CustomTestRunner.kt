package com.example.android.hilt

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/*
Hilt를 사용하여 계측된 테스트는 Hilt를 지원하는 Application에서 실행되어야 합니다.

라이브러리에는 이미 UI 테스트를 실행하는 데 사용할 수 있는 HiltTestApplication이 포함되어 있습니다.

테스트에 사용할 Application을 지정하려면 프로젝트에서 새 테스트 실행기를 생성합니다.

androidTest 폴더 아래 AppTest.kt 파일과 같은 수준에 CustomTestRunner라는 이름의 새 파일을 생성합니다.

CustomTestRunner는 AndroidJUnitRunner에서 확장되며 다음과 같이 구현합니다.

그런 다음, 이 테스트 실행기를 계측 테스트에 사용하도록 프로젝트에 알려야 합니다.

app/build.gradle 파일의 testInstrumentationRunner 속성에 테스트 실행기를 명시합니다.

파일을 열고 기본 testInstrumentationRunner 콘텐츠를 아래와 같이 바꿉니다.
*/

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}