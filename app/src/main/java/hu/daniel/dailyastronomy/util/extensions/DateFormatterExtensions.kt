@file:JvmName("DateFormatterExtensions")
package hu.daniel.dailyastronomy.util.extensions

import java.text.SimpleDateFormat
import java.util.*

private val yearMonthDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

fun Date?.formatToYearMonthDate(): String =
    if (this != null) yearMonthDateFormatter.format(this) else ""
