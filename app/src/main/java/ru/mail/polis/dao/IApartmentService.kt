package ru.mail.polis.dao

interface IApartmentService {
    suspend fun findByEmail(email: String): ApartmentED?
    suspend fun findAll(): List<ApartmentED>
    suspend fun addApartment(apartment: ApartmentED): ApartmentED
    suspend fun updateApartment(apartment: ApartmentED): ApartmentED
    suspend fun deleteApartmentByEmail(email: String)
}
