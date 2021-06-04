package ru.mail.polis.dao.apartments

import ru.mail.polis.dao.DaoException
import kotlin.jvm.Throws

interface IApartmentService {
    @Throws(DaoException::class)
    suspend fun findByEmail(email: String): ApartmentED?

    @Throws(DaoException::class)
    suspend fun findByEmails(emailList: Set<String>): List<ApartmentED>

    @Throws(DaoException::class)
    suspend fun findAll(): List<ApartmentED>

    @Throws(DaoException::class)
    suspend fun addApartment(apartment: ApartmentED): ApartmentED

    @Throws(DaoException::class)
    suspend fun updateApartment(apartment: ApartmentED): ApartmentED

    @Throws(DaoException::class)
    suspend fun deleteApartmentByEmail(email: String)

    @Throws(DaoException::class)
    suspend fun isExist(email: String): Boolean
}
