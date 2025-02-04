package site.yourevents.guest.repository

import org.springframework.stereotype.Repository
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.domain.Member
import site.yourevents.member.entity.MemberEntity
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class GuestRepository(
    private val guestJPARepository: GuestJPARepository,
) : GuestPersistencePort {
    override fun save(guestVo: GuestVO) =
        guestJPARepository.save(GuestEntity.from(guestVo))
            .toDomain()

    override fun save(guest: Guest) {
        guestJPARepository.save(GuestEntity.from(guest))
    }

    override fun findById(guestId: UUID): Guest? {
        return guestJPARepository.findById(guestId)
            .getOrNull()?.toDomain()
    }

    override fun getReceivedInvitationCount(member: Member) =
        guestJPARepository.getReceivedInvitationCount(MemberEntity.from(member))

    override fun getReceivedInvitations(member: Member) =
        guestJPARepository.getReceivedInvitations(MemberEntity.from(member)).map(InvitationEntity::toDomain)

    override fun findAttendGuestsByInvitation(invitation: Invitation): List<Guest> {
        return guestJPARepository.findAttendGuestsByInvitation(InvitationEntity.from(invitation))
            .map(GuestEntity::toDomain)
    }

    override fun findNotAttendGuestsByInvitation(invitation: Invitation): List<Guest> {
        return guestJPARepository.findNotAttendGuestsByInvitation(InvitationEntity.from(invitation))
            .map(GuestEntity::toDomain)
    }

    override fun findAttendanceByMemberIdAndInvitationId(memberId: UUID, invitationId: UUID) =
        guestJPARepository.findAttendanceByMemberIdAndInvitationId(memberId, invitationId)

    override fun findNicknameByInvitationIdAndMemberId(invitationId: UUID, memberId: UUID) =
        guestJPARepository.findNicknameByInvitationIdAndMemberId(invitationId, memberId)

    override fun findIdByMemberIdAndInvitationId(memberId: UUID, invitationId: UUID) =
        guestJPARepository.findIdByMemberIdAndInvitationId(memberId, invitationId)
}
