package me.hardy.oauthjwt.repository

import me.hardy.oauthjwt.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByOauthId(oauthId: String): User?
}
