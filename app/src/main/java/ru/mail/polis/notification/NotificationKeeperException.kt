package ru.mail.polis.notification

import ru.mail.polis.R

/**
 * Исключение для передачи сообщения пользователю.
 * Следует перехватить сообщение и вывести пользователю нотификацию.
 *
 * @property notificationType тип нотификации с внутренним ID ресурса - строки
 */
class NotificationKeeperException : Exception {
    private val notificationType: NotificationType

    enum class NotificationType(
        private val resourceStringCode: Int
    ) {
        DAO_ERROR(R.string.error_dao);

        fun getResourceStringCode(): Int {
            return resourceStringCode
        }
    }

    /**
     * @param message причина ошибки.
     * @param notificationType тип нотификации с внутренним ID ресурса - строки
     */
    constructor(message: String?, notificationType: NotificationType) : super(message) {
        this.notificationType = notificationType
    }

    /**
     * @param message причина ошибки.
     * @param cause причина ошибки, прокидываем от вышестоящего исключения.
     * @param notificationType тип нотификации с внутренним ID ресурса - строки
     */
    constructor(message: String?, cause: Throwable?, notificationType: NotificationType) : super(
        message,
        cause
    ) {
        this.notificationType = notificationType
    }

    fun getResourceStringCode(): Int {
        return notificationType.getResourceStringCode()
    }
}
