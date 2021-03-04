package com.example.mvvmtests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmtests.ui.Screens.CardsList
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.root_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(CardsList())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}