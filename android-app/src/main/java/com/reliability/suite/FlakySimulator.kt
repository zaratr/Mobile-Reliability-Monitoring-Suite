package com.reliability.suite

import io.sentry.Sentry
import io.sentry.Breadcrumb
import io.sentry.SpanStatus
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException

class FlakySimulator {

    suspend fun triggerNetworkTimeout() {
        recordBreadcrumb("triggerNetworkTimeout initiated")
        val transaction = Sentry.startTransaction("CheckoutFlow", "http.client")
        try {
            val span = transaction.startChild("http.client", "POST /api/v1/pay")
            delay(5000) 
            span.status = SpanStatus.DEADLINE_EXCEEDED
            span.finish()
            throw SocketTimeoutException("Connection timed out reading from /api/v1/pay")
        } catch (e: Exception) {
            transaction.status = SpanStatus.INTERNAL_ERROR
            transaction.finish()
            Sentry.captureException(e)
        }
    }

    fun triggerNullPointerException() {
        recordBreadcrumb("triggerNullPointerException initiated")
        val userConfig: String? = null
        userConfig!!.length 
    }

    suspend fun runSlowBackgroundTask() {
        recordBreadcrumb("runSlowBackgroundTask initiated")
        val transaction = Sentry.startTransaction("DataSync", "task.background")
        delay(8000) 
        transaction.finish()
    }

    fun recordBreadcrumb(action: String) {
        val breadcrumb = Breadcrumb().apply {
            category = "ui.action"
            message = action
            level = io.sentry.SentryLevel.INFO
        }
        Sentry.addBreadcrumb(breadcrumb)
    }
}
