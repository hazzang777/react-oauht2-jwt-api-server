package me.hardy.oauthjwt.security.oauth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.hardy.oauthjwt.security.jwt.TokenService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthSuccessHandler(
    private val tokenService: TokenService
): SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val token = tokenService.generateToken(authentication!!)


        response!!.sendRedirect("http://localhost:3000/social-login-proc?token=$token")
    }
}