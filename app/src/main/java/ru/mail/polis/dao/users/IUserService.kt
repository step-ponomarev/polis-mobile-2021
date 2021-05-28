package ru.mail.polis.dao.users

interface IUserService {
    suspend fun updateUserByEmail(email: String, user: UserED): UserED?
    suspend fun findUserByEmail(email: String): UserED?
    suspend fun findUsersByEmails(emails: Set<String>): List<UserED>
    suspend fun addUser(user: UserED): UserED?
    suspend fun isExist(email: String): Boolean
}
