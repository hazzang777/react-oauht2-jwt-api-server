package me.hardy.oauthjwt.dto

data class UserInfoDTO(
    val userId: Long,
    val provider: String,
    val oauthId: String
)