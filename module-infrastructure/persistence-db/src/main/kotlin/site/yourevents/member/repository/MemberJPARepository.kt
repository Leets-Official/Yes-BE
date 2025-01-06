package site.yourevents.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.member.entity.MemberEntity
import java.util.Optional
import java.util.UUID

interface MemberJPARepository : JpaRepository<MemberEntity, UUID> {
    fun findByEmail(email: String): Optional<MemberEntity>
}
