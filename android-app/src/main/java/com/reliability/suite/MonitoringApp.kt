package com.reliability.suite

import android.app.Application
import io.sentry.Sentry
import io.sentry.android.core.SentryAndroid
import com.google.firebase.perf.FirebasePerformance

class MonitoringApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SentryAndroid.init(this) { options ->
            options.dsn = "YOUR_SENTRY_DSN_HERE"
            options.tracesSampleRate = 1.0
            options.isEnableAutoSessionTracking = true
            options.sessionTrackingIntervalMillis = 10000
            
            options.beforeSend = { event, hint ->
                if (event.exceptions?.any { it.type == "NetworkException" } == true) {
                    event.fingerprints = listOf("api-network-timeout", "{{ default }}")
                }
                event
            }
        }

        Sentry.configureScope { scope ->
            scope.setTag("membership_tier", "premium")
            scope.setTag("device_model", android.os.Build.MODEL)
            scope.setTag("app_version", BuildConfig.VERSION_NAME)
        }

        FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true
    }
}
