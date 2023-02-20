package me.hardy.oauthjwt.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RedirectUrlCookieFilter: OncePerRequestFilter()  {
    companion object{
        const val REDIRECT_URI_PARAM = "redirect_url"
        private const val MAX_AGE = 180
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI.startsWith("/auth/authorize")) {
            val redirectUrl = request.getParameter(REDIRECT_URI_PARAM)

            val cookie = Cookie(REDIRECT_URI_PARAM, redirectUrl)
            cookie.path = "/"
            cookie.isHttpOnly = true
            cookie.maxAge = MAX_AGE
            response.addCookie(cookie)

            println(response.toString())
        }

        filterChain.doFilter(request, response)
    }
}