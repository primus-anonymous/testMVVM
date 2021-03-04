package com.example.mvvmtests

import com.example.mvvmtests.di.ApplicationComponent
import com.example.mvvmtests.di.ApplicationModule
import com.example.mvvmtests.di.DaggerTestApplicaitonComponent
import com.example.mvvmtests.di.TestApplicaitonComponent

class TestApp : App() {

    override val appComponent: ApplicationComponent by lazy {
        DaggerTestApplicaitonComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    val testComponent: TestApplicaitonComponent by lazy {
        appComponent as TestApplicaitonComponent
    }
}