package ru.mail.polis.dao

class DaoException : Exception {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
