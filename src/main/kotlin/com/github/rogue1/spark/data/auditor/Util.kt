package com.github.rogue1.spark.data.auditor

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.*

object Util {

    /**
     * convert data string to date format.
     * date string should be in format 'yyyy-MM-dd HH:mm:ss'
     */
    fun toDate(str: String): Date {
       return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str)
    }

    /**
     * create date that is older by N number of days older than current instance
     */
    fun date(days: Int=0): Date {
        val duration = Duration.ofDays(days.toLong())
        return Date(Instant.now().minusMillis(duration.toMillis()).toEpochMilli())
    }

}