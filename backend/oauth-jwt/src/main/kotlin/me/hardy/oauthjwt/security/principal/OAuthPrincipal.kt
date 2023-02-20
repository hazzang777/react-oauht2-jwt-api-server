package me.hardy.oauthjwt.security.principal

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

interface OAuthPrincipal: OAuth2User, UserDetails{

}