package site.yourevents.guest.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.guest.entity.GuestEntity
import java.util.UUID

interface OwnerNicknameJPARepository : JpaRepository<GuestEntity, UUID> {
}
