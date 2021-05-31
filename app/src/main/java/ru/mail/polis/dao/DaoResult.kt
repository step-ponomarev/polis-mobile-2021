package ru.mail.polis.dao

sealed class DaoResult<T> {
    data class Success<T>(val data: T) : DaoResult<T>()
    data class Error<T>(val e: Exception) : DaoResult<T>()
}
