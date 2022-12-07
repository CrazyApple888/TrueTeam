package ru.nsu.wallet.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.wallet.entity.Card

@Repository
interface CardRepository : CrudRepository<Card?, Int?> {

    fun deleteByNumberAndOwnerId(number: String, userId: Int)

    fun findAllByCategoryAndOwnerId(category: String, userId: Int): List<Card>

}