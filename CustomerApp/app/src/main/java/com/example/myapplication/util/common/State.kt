package com.example.myapplication.util.common

/**
 * Used to represent states of progress for any operation (API calls, DB fetches, etc.)
 */
sealed class State {
    /**
     * Operation completed successfully. Used to carry data
     * @param T type of data carried
     * @param data data produced at the end of an operation
     */
    class Success<T>(val data: T? = null) : State()

    /**
     * Operation currently underway
     */
    object Loading : State()

    /**
     * Operation failed with [reason]
     * @param reason Reason for failure
     */
    data class Failure(val reason: String = ""): State() {
        constructor(t: Throwable): this(t.message ?: "")
    }
}