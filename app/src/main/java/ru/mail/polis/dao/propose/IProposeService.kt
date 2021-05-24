package ru.mail.polis.dao.propose

interface IProposeService {
    suspend fun findOwnerEmail(email: String): List<ProposeED>
    suspend fun findRenterEmail(email: String): List<ProposeED>
    suspend fun createPropose(proposeED: ProposeED): ProposeED
    suspend fun checkProposeExist(ownerEmail: String, renterEmail: String): Boolean
}
