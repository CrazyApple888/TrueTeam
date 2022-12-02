package ru.nsu.wallet.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.wallet.entity.User

@Repository
interface UserRepository : CrudRepository<User?, Int?> {

    fun getByEmail(email: String): User?

    fun getById(id: Int): User?

}