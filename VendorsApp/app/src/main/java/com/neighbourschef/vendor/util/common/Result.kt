package com.neighbourschef.vendor.util.common

sealed class Result<out V, out E : Exception> {
    data class Value<V>(val value: V) : Result<V, Nothing>()
    data class Error<E : Exception>(val error: E) : Result<Nothing, E>()

    companion object {
        inline fun <reified V> of(operation: () -> V): Result<V, Exception> =
            try {
                Value(operation())
            } catch (e: Exception) {
                Error(e)
            }
    }
}

inline fun <reified I, reified O> Result<I, Exception>.map(mapper: (I) -> O): Result<O, Exception> =
    try {
        when (this) {
            is Result.Value -> Result.Value(mapper(value))
            is Result.Error -> this
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
