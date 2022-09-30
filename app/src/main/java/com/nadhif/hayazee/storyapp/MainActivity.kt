package com.nadhif.hayazee.storyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nadhif.hayazee.datastore.AppDataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loggedInChecking()
    }

    private fun loggedInChecking() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment?.navController
        val graphInflater = navController?.navInflater
        val navGraph = graphInflater?.inflate(R.navigation.main_navigation)
        if (dataStore.isLoggedIn()) {
            navGraph?.setStartDestination(com.nadhif.hayazee.home.R.id.home_navigation)
        } else {
            navGraph?.setStartDestination(com.nadhif.hayazee.auth.R.id.auth_navigation)
        }
        navGraph?.let {
            navController.setGraph(it, intent?.extras)

        }

    }
}