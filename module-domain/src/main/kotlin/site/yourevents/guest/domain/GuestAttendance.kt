package site.yourevents.guest.domain

data class GuestAttendance(
    val attending: List<Guest>,
    val nonAttending: List<Guest>
)