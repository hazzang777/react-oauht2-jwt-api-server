package me.hardy.oauthjwt.config


import me.hardy.oauthjwt.security.jwt.JwtAuthenticationFilter
import me.hardy.oauthjwt.security.jwt.RedirectUrlCookieFilter
import me.hardy.oauthjwt.security.oauth.OAuthService
import me.hardy.oauthjwt.security.oauth.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuthService: OAuthService,
    private val oAuthSuccessHandler: OAuthSuccessHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val redirectUrlCookieFilter: RedirectUrlCookieFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            cors {  }
            csrf { disable() }
            httpBasic { disable() }
            sessionManagement {SessionCreationPolicy.STATELESS}
            authorizeRequests {
                authorize("/", permitAll)
                authorize("/oauth2/**",permitAll)
                authorize("/auth/**",permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {
                redirectionEndpoint {
                    baseUri = "/oauth2/callback/*"
                }
                authorizationEndpoint {
                    baseUri = "/auth/authorize"
                }
                userInfoEndpoint {
                    userService = oAuthService
                    authenticationSuccessHandler = oAuthSuccessHandler
                }
            }

            exceptionHandling {
                authenticationEntryPoint = Http403ForbiddenEntryPoint()
            }

            addFilterBefore<CorsFilter>(jwtAuthenticationFilter)
            addFilterBefore<OAuth2AuthorizationRequestRedirectFilter>(redirectUrlCookieFilter)
        }


        return http.build()
    }
}