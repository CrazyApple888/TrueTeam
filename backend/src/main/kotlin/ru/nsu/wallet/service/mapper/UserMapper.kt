package ru.nsu.wallet.service.mapper

import org.springframework.stereotype.Component
import ru.nsu.wallet.dto.user.UserDto
import ru.nsu.wallet.entity.User

@Component
class UserMapper {

    fun mapEntityToDto(user: User) = UserDto(
        id = user.id, email = user.email, firstName = user.firstName
    )
}