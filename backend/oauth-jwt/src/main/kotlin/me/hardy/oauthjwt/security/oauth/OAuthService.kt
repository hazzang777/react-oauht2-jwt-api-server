package me.hardy.oauthjwt.security.oauth

import me.hardy.oauthjwt.domain.User
import me.hardy.oauthjwt.repository.UserRepository
import me.hardy.oauthjwt.security.principal.UserPrincipal
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val userRepository: UserRepository
): DefaultOAuth2UserService(){


    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        println(userRequest!!.clientRegistration)

        val oAuth2User = super.loadUser(userRequest)

        println(oAuth2User.attributes)

        val attribute = oAuth2User.attributes!!

        val provider = userRequest!!.clientRegistration.registrationId
        val providerId = attribute["sub"]
        val oauthId = "$provider#$providerId"
        var user = userRepository.findUserByOauthId(oauthId)

        if (user == null){
            val newUser = User(
                name = oAuth2User.name,
                provider = provider,
                oauthId = oauthId,
                isAdmin = false
            )
            user = userRepository.save(newUser)
        }

        return UserPrincipal(userId = user.id!!, isAdmin = user.isAdmin, attributes = attribute)
    }
}