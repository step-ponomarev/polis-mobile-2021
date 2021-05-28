package ru.mail.polis.exception

/**
 * Исключение для передачи сообщения пользователю.
 * Следует перехватить сообщение и вывести пользователю нотификацию.
 *
 * @property resourceStringCode ID ресурса - строки для отображения в тосте.
 */
class NotificationKeeperException : Exception {
    private val resourceStringCode: Int

    /**
     * @param message причина ошибки.
     * @param resourceStringCode ID ресурса - строки для отображения в тосте.
     */
    constructor(message: String?, resourceStringCode: Int) : super(message) {
        this.resourceStringCode = resourceStringCode
    }

    /**
     * @param message причина ошибки.
     * @param cause причина ошибки, прокидываем от вышестоящего исключения.
     * @param resourceStringCode ID ресурса - строки для отображения в тосте.
     */
    constructor(message: String?, cause: Throwable?, resourceStringCode: Int) : super(
        message,
        cause
    ) {
        this.resourceStringCode = resourceStringCode
    }

    fun getResourceStringCode(): Int {
        return this.resourceStringCode
    }
}
