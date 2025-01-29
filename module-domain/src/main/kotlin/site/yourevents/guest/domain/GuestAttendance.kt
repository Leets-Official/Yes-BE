package site.yourevents.guest.domain

data class GuestAttendance(
    val attending: List<GuestVO>,
    val nonAttending: List<GuestVO>
)