package site.yourevents.principal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections
import java.util.UUID

class AuthDetails(
    private val uuid: UUID,
    private val email: String,
    private val role: String,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return email
    }
}