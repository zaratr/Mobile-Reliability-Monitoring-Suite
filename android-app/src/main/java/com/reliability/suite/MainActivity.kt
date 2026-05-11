package com.reliability.suite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val simulator = FlakySimulator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        simulator.recordBreadcrumb("MainActivity created")

        CoroutineScope(Dispatchers.IO).launch {
            simulator.runSlowBackgroundTask()
        }
    }

    fun onCheckoutClicked() {
        simulator.recordBreadcrumb("User clicked Checkout button")
        CoroutineScope(Dispatchers.IO).launch {
            simulator.triggerNetworkTimeout()
        }
    }

    fun onProfileClicked() {
        simulator.recordBreadcrumb("User clicked Profile button")
        simulator.triggerNullPointerException()
    }
}
