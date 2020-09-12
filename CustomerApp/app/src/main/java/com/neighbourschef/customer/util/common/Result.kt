package com.neighbourschef.customer.util.common

sealed class Result<out V, out E : Exception> {
    data class Value<V>(val value: V) : Result<V, Nothing>()
    data class Error<E : Exception>(val error: E) : Result<Nothing, E>()
}
