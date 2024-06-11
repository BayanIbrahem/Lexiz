package com.dev.bayan.ibrahim.feature.library.domain.util

import android.os.Build
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

// format date:
fun Long.addDateSimpleFormat(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.fromEpochMilliseconds(
            epochMilliseconds = this
        ).toLocalDateTime(
            timeZone = TimeZone.UTC
        ).toJavaLocalDateTime(
        ).format(
            DateTimeFormatter.ofPattern(
                "yyyy-MM-dd"
            )
        )
    } else {
        SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
    }
}
