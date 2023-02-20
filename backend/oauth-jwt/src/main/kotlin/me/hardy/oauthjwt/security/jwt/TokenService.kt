package me.hardy.oauthjwt.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm
import me.hardy.oauthjwt.security.principal.UserPrincipal

@Service
class TokenService(
    private val objectMapper: ObjectMapper
) {
    companion object{
        private const val SECRET_KEY = "54820f4176aaf91daf9e6995781cd91ab4f7319ca3e9d17d24aa1fedb0170bb36f9841b0adb47aa90d4dc35d1a8fd426cadb7a2324843be92ebc4fd36a1454c3"
    }

    fun generateToken(authentication: Authentication): String {
        val expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS))
        val principal = authentication.principal as UserPrincipal

        val tokenClaim = TokenClaim(principal.getUserId(), false)
        val jwtSub = objectMapper.writeValueAsString(tokenClaim)

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .setSubject(jwtSub)
            .setIssuer("authApp")
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .compact()
    }

    fun validateToken(token: String): String {
        val claim = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .body

        return claim.subject
    }
}

data class TokenClaim(
    val userId: Long,
    val isAdmin: Boolean
)