package com.neighbourschef.customer.util.common

/**
 * Used to represent states of progress for any operation (API calls, DB fetches, etc.)
 */
sealed class UiState {
    /**
     * Operation completed successfully. Used to carry data
     * @param T type of data carried
     * @param data data produced at the end of an operation
     */
    class Success<T>(val data: T? = null) : UiState()

    /**
     * Operation currently underway
     */
    object Loading : UiState()

    /**
     * Operation failed with [reason]
     * @param reason Reason for failure
     */
    data class Failure(val reason: String = ""): UiState() {
        constructor(t: Throwable): this(t.message ?: "")
    }
}
