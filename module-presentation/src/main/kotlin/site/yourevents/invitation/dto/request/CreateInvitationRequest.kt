package site.yourevents.invitation.dto.request

data class CreateInvitationRequest(
    val qrUrl: String//qr 생성 메서드 추가 필요. 우선 request로 받는 방식 사용
)
