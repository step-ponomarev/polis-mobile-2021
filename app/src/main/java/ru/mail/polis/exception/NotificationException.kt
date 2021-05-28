package ru.mail.polis.exception

class NotificationException(message: String?, cause: Throwable?, toastMessage: String) :
    Exception(message, cause) {
    private val toastMessage: String = toastMessage

    fun getToastMessage(): String {
        return this.toastMessage
    }
}
