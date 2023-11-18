package network.onepay.sdk.listener

import network.onepay.sdk.dto.PaymentResponse

interface OnOnePaySuccessListener {
    fun onSuccess(response: PaymentResponse)
    fun onFailed(response: PaymentResponse)
}