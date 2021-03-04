package com.example.mvvmtests

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MyTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        appName: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApp::class.java.name, context)
}
