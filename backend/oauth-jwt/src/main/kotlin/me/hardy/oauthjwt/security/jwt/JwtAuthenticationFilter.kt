package me.hardy.oauthjwt.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.hardy.oauthjwt.security.principal.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = getToken(request)
            if (token != null) {
                val jwtSubStr = tokenService.validateToken(token)
                val tokenClaim = objectMapper.readValue<TokenClaim>(jwtSubStr)
                val principal =
                    UserPrincipal(userId = tokenClaim.userId, isAdmin = tokenClaim.isAdmin, attributes = null)
                val authentication =
                    UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                val securityContext = SecurityContextHolder.createEmptyContext()
                securityContext.authentication = authentication
                SecurityContextHolder.setContext(securityContext)
            }
        } catch (e: Exception) {
            logger.error("Could not set user authentication in security Context", e)
        }

        filterChain.doFilter(request, response)
    }


    private fun getToken(request: HttpServletRequest): String? {
        val token: String? = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7)
        }
        return null
    }
}