package com.alvayonara.common.utils

import android.annotation.SuppressLint
import android.net.Uri
import java.text.ParseException
import java.text.SimpleDateFormat

fun parseToUri(text: String): Uri = Uri.parse(text)

/**
 * @param inputFormat format dateTime that will be convert
 * @param outputFormat format dateTIme for result
 * @return return new date time format based on outputFormat
 *
 * @see DateFormat for supported DateFormat
 */
@SuppressLint("SimpleDateFormat")
fun String?.dateTimeConvert(inputFormat: String, outputFormat: String): String {
    return try {
        val sdf = SimpleDateFormat(inputFormat)
        val convertDate = sdf.parse(this.orEmpty())
        SimpleDateFormat(outputFormat).format(convertDate!!)
    } catch (e: ParseException) {
        ""
    }
}