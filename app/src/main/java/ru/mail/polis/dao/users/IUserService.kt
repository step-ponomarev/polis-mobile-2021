package ru.mail.polis.dao.users

import kotlinx.coroutines.flow.Flow
import ru.mail.polis.dao.DaoException
import ru.mail.polis.dao.DaoResult

interface IUserService {
    @Throws(DaoException::class)
    suspend fun updateUserByEmail(email: String, user: UserED): UserED?

    fun findUserByEmail(email: String): Flow<DaoResult<UserED>>

    @Throws(DaoException::class)
    suspend fun findUsersByEmails(emails: Set<String>): List<UserED>

    @Throws(DaoException::class)
    suspend fun addUser(user: UserED): UserED?

    @Throws(DaoException::class)
    suspend fun isExist(email: String): Boolean
}
