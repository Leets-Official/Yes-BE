package site.yourevents.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import site.yourevents.common.entity.BaseTimeEntity
import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO
import java.util.UUID

@Entity(name = "member")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID? = null,

    @Column
    private val socialId: String,

    @Column
    private val nickname: String,

    @Column
    private val email: String,
) : BaseTimeEntity() {
    fun toDomain(): Member = Member(
        id = id!!,
        socialId = socialId,
        nickname = nickname,
        email = email,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )

    companion object {
        fun from(memberVO: MemberVO): MemberEntity = MemberEntity(
            socialId = memberVO.socialId,
            nickname = memberVO.nickname,
            email = memberVO.email,
        )

        fun from(member: Member): MemberEntity = MemberEntity(
            id = member.id,
            socialId = member.socialId,
            nickname = member.nickname,
            email = member.email
        )
    }
}
