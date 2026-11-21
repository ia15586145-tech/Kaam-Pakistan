package com.example.data.util

object PrivacyUtils {
    /**
     * Masks a Pakistani mobile phone number for UI display while keeping only last 3-4 digits visible.
     * Example: "03001234567" -> "0300 ••••567"
     */
    fun maskPhone(rawPhone: String): String {
        val digits = rawPhone.filter { it.isDigit() }
        if (digits.length < 7) return "•••••••"
        val prefix = digits.take(4)
        val suffix = digits.takeLast(3)
        return "$prefix ••••$suffix"
    }

    /**
     * Formats phone digits into international +92 format for WhatsApp wa.me links.
     */
    fun toInternationalPkPhone(rawPhone: String): String {
        val digits = rawPhone.filter { it.isDigit() }
        return when {
            digits.startsWith("92") -> digits
            digits.startsWith("0") && digits.length >= 10 -> "92" + digits.drop(1)
            else -> digits
        }
    }

    /**
     * Masks a 13-digit CNIC so raw CNIC is never stored in plain text or exposed in UI.
     * Example: "4210112345673" -> "42101-•••••••-3"
     */
    fun maskCnic(rawCnic: String): String {
        val digits = rawCnic.filter { it.isDigit() }
        if (digits.length < 6) return ""
        val first5 = digits.take(5)
        val last1 = digits.takeLast(1)
        return "$first5-•••••••-$last1"
    }
}
