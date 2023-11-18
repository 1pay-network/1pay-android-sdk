package network.onepay.sdk.dto

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

data class PaymentResponse(
    @Json(serializeNull = false)
    val hash: String?,
    val success: Boolean,
    val amount: Float,
    val token: String,
    val network: String,
    val note: String
)