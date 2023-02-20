package me.hardy.oauthjwt.service

import me.hardy.oauthjwt.dto.UserInfoDTO
import me.hardy.oauthjwt.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUserInfo(userId: Long): UserInfoDTO {
        val user = userRepository.findById(userId).get()
        return UserInfoDTO(userId = user.id!!, provider = user.provider, oauthId = user.oauthId)
    }
}
