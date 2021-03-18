package com.example.domain

import java.text.SimpleDateFormat
import java.util.*


fun Date.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yy H:mm")
    return dateFormat.format(this)
}