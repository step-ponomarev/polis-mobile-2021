package ru.mail.polis.exception

class DaoException : Exception {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
