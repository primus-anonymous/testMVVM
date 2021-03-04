package com.example.mvvmtests

import android.app.Application
import com.example.mvvmtests.di.ApplicationComponent
import com.example.mvvmtests.di.ApplicationModule
import com.example.mvvmtests.di.DaggerApplicationComponent

open class App : Application() {

    open val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}