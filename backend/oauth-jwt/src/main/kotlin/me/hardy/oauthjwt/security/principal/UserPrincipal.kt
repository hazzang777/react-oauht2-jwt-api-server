package me.hardy.oauthjwt.security.principal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrincipal(
    private val userId: Long,
    private val isAdmin: Boolean,
    private val attributes: Map<String, Any>? = emptyMap()
): UserDetails, OAuth2User {

    fun getUserId() = userId

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        if (isAdmin){
            authorities.add((GrantedAuthority {"ROLE_ADMIN"}))
            authorities.add((GrantedAuthority {"ROLE_USER"}))
        } else {
            authorities.add((GrantedAuthority {"ROLE_USER"}))
        }
        return authorities
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = userId.toString()

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    override fun getName(): String? = null

    override fun getAttributes() = this.attributes
}