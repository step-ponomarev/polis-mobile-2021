package ru.mail.polis.dao.users

import ru.mail.polis.dao.DaoException

interface IUserService {
    @Throws(DaoException::class)
    suspend fun updateUserByEmail(email: String, user: UserED): UserED?

    @Throws(DaoException::class)
    suspend fun findUserByEmail(email: String): UserED?

    @Throws(DaoException::class)
    suspend fun findUsersByEmails(emails: Set<String>): List<UserED>

    @Throws(DaoException::class)
    suspend fun addUser(user: UserED): UserED?

    @Throws(DaoException::class)
    suspend fun isExist(email: String): Boolean

    @Throws(DaoException::class)
    suspend fun deleteUser(email: String)
}
