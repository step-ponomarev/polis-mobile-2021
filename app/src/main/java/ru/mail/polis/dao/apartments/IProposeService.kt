package ru.mail.polis.dao.apartments

interface IProposeService {
    suspend fun findOwnerEmail(email: String): List<ProposeED>
    suspend fun findRenterEmail(email: String): List<ProposeED>
    suspend fun createPropose(proposeED: ProposeED): ProposeED
}
