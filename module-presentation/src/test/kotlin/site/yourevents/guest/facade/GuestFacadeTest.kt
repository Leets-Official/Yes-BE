package site.yourevents.guest.facade

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import site.yourevents.guest.dto.request.InvitationRespondRequest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.principal.AuthDetails
import java.util.*

class GuestFacadeTest : DescribeSpec({
    val guestUseCase: GuestUseCase = mockk()
    val guestFacade = GuestFacade(guestUseCase)

    describe("GuestFacade") {
        context("respondInvitation 메서드 호출 시") {
            it("GuestUseCase.respondInvitation()가 정확히 한 번 호출된다.") {
                every {
                    guestUseCase.respondInvitation(
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                    )
                } just runs

                val request = InvitationRespondRequest(
                    guestId = UUID.randomUUID(),
                    invitationId = UUID.randomUUID(),
                    nickname = "nickname",
                    attendance = true
                )
                val authDetails = AuthDetails(
                    uuid = UUID.randomUUID(),
                    socialId = "socialId",
                    role = "ROLE_USER"
                )

                guestFacade.respondInvitation(request, authDetails)

                verify(exactly = 1) {
                    guestUseCase.respondInvitation(
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                    )
                }
                confirmVerified(guestUseCase)
            }
        }
    }
})
