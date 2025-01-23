package site.yourevents.common.domain

import java.time.LocalDateTime

abstract class BaseTime(
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
