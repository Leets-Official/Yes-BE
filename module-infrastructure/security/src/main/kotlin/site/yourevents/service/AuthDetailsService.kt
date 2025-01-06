package site.yourevents.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.principal.AuthDetails

@Service
class AuthDetailsService(
    private val memberUseCase: MemberUseCase
) : UserDetailsService {
    override fun loadUserByUsername(email: String): AuthDetails {
        val member = memberUseCase.findByEmail(email)
            ?: throw MemberNotFountException()

        return AuthDetails(member.getId(), member.getNickname(), "ROLE_USER")
    }
}
