package site.yourevents.member.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest(
    @Autowired private val memberJPARepository: MemberJPARepository
) : DescribeSpec({
    val memberRepository = MemberRepository(memberJPARepository)
    val socialId = "12345678"
    val nickname = "yes!"
    val email = "yes@yes.com"

    beforeSpec {
        memberJPARepository.save(
            MemberEntity.from(
                MemberVO(
                    socialId,
                    nickname,
                    email,
                )
            )
        )
    }

    afterSpec {
        memberJPARepository.deleteAllInBatch()
    }

    describe("MemberRepository") {
        context("findBySocialId()에서 socialId가 주어지면") {
            it("존재할 경우 Member를 반환해야 한다") {
                val member = memberRepository.findBySocialId(socialId)

                member!!.apply {
                    getSocialId() shouldBe socialId
                    getNickname() shouldBe nickname
                    getEmail() shouldBe email
                }
            }

            it("존재하지 않는 경우 null을 반환해야 한다") {
                val member = memberRepository.findBySocialId("11111111")

                member shouldBe null
            }
        }
    }
})
