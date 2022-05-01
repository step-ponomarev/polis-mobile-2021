package ru.mail.polis.dao.propose

import ru.mail.polis.dao.DaoException

interface IProposeService {
    @Throws(DaoException::class)
    suspend fun findOwnerEmail(email: String): List<ProposeED>

    @Throws(DaoException::class)
    suspend fun findRenterEmail(email: String): List<ProposeED>

    @Throws(DaoException::class)
    suspend fun createPropose(proposeED: ProposeED): ProposeED

    @Throws(DaoException::class)
    suspend fun checkProposeExist(ownerEmail: String, renterEmail: String): Boolean

    @Throws(DaoException::class)
    suspend fun updatePropose(proposeED: ProposeED): ProposeED

    @Throws(DaoException::class)
    suspend fun deletePropose(proposeED: ProposeED)
}
