package ru.mail.polis.dao.users

interface IUserService {
    suspend fun updateUserByEmail(email: String, user: UserED): UserED?
}