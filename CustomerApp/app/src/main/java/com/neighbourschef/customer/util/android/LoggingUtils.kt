package com.neighbourschef.customer.util.android

import timber.log.Timber

/**
 * Debug Tree for logging with clickable links
 *
 * Logs will print the (FileName:LineNumber)methodName
 */
class HyperlinkedDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        with(element) {
            return "($fileName:$lineNumber)$methodName"
        }
    }
}

class ReleaseTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {}
}

/**
 * In Java, call [log(object, prefix)]
 */
inline fun <reified T> T?.log(prefix: String = "") = Timber.d("$prefix${toString()}")
