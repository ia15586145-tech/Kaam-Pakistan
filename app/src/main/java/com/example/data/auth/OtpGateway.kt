package com.example.data.auth

enum class OtpChannel {
    SMS,
    WHATSAPP
}

sealed class OtpGatewayResult {
    data class GatewayNotConfigured(
        val channel: OtpChannel,
        val normalizedPhone: String
    ) : OtpGatewayResult()

    data class ChallengeSent(
        val challengeId: String,
        val channel: OtpChannel
    ) : OtpGatewayResult()

    data class VerificationFailed(val reason: String) : OtpGatewayResult()
    data class Verified(val authToken: String) : OtpGatewayResult()
}

/**
 * Abstraction for backend 6-digit SMS & WhatsApp OTP delivery.
 * API credentials (e.g., Twilio, WhatsApp Business Cloud API, or Firebase Auth)
 * must live exclusively on a secure backend server—never hardcoded in the Android APK.
 */
interface OtpGateway {
    val isBackendConfigured: Boolean
    suspend fun requestSixDigitOtp(phoneNumber: String, channel: OtpChannel): OtpGatewayResult
    suspend fun verifySixDigitOtp(challengeId: String, code: String): OtpGatewayResult
}

/**
 * Honest unconfigured gateway implementation that prevents fake OTP passes
 * while preserving the complete SMS / WhatsApp integration contract.
 */
class UnconfiguredBackendOtpGateway : OtpGateway {
    override val isBackendConfigured: Boolean = false

    override suspend fun requestSixDigitOtp(
        phoneNumber: String,
        channel: OtpChannel
    ): OtpGatewayResult {
        return OtpGatewayResult.GatewayNotConfigured(
            channel = channel,
            normalizedPhone = phoneNumber.trim()
        )
    }

    override suspend fun verifySixDigitOtp(
        challengeId: String,
        code: String
    ): OtpGatewayResult {
        return OtpGatewayResult.VerificationFailed(
            "Backend SMS/WhatsApp OTP endpoint is not connected yet. No fake OTP accepted."
        )
    }
}
