@file:JvmName("LoggingUtils")
package com.example.myapplication.util.android

import timber.log.Timber

class HyperlinkedDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        with(element) {
            return "($fileName:$lineNumber)$methodName"
        }
    }
}

/**
 * In Java, call [log(object, prefix)]
 */
inline fun Any?.log(prefix: String = "") = Timber.d("$prefix${toString()}")