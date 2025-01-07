package site.yourevents.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO
import java.util.UUID

@Entity(name = "member")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID? = null,

    @Column
    private val nickname: String,

    @Column
    private val email: String,
) {
    fun toDomain(): Member {
        return Member(
            id = id!!,
            nickname = nickname,
            email = email,
        )
    }

    companion object {
        fun from(memberVO: MemberVO): MemberEntity {
            return MemberEntity(
                nickname = memberVO.nickname,
                email = memberVO.email,
            )
        }
    }
}
