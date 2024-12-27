package site.yourevents.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity(name = "member")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID,

    @Column
    private val nickname: String,

    @Column
    private val socialId: String,

    @Column
    private val email: String,
) {
}