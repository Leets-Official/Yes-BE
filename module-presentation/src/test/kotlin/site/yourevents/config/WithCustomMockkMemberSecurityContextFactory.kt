/*
package site.yourevents.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory
import site.yourevents.principal.AuthDetails
import java.util.*


class WithCustomMockkMemberSecurityContextFactory
    : WithSecurityContextFactory<WithCustomMockkMember> {
    override fun createSecurityContext(annotation: WithCustomMockkMember): SecurityContext {
        val memberId = UUID.randomUUID()
        val socialId = "12345678"
        val role = "ROLE_USER"

        val authDetails = AuthDetails(
            uuid = memberId,
            socialId = socialId,
            role = role
        )

        val token = UsernamePasswordAuthenticationToken(
            authDetails, null, listOf(SimpleGrantedAuthority(role))
        )
        val context = SecurityContextHolder.getContext().apply {
            authentication = token
        }

        return context
    }
}*/
