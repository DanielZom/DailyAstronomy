@file:JvmName("Globals")
package hu.daniel.dailyastronomy.util

import hu.daniel.dailyastronomy.BuildConfig
import timber.log.Timber

fun onDebug(runInDebugMode: () -> Unit) {
    if (BuildConfig.DEBUG) runInDebugMode.invoke()
}

fun noop(message: String? = null) {
    message?.let { Timber.e(it) }
}

