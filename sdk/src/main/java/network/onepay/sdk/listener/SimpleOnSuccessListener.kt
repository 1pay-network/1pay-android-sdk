package network.onepay.sdk.listener

import network.onepay.sdk.dto.PaymentResponse

open class SimpleOnSuccessListener : OnOnePaySuccessListener {

    override fun onSuccess(response: PaymentResponse) {
        // TODO implement function
    }

    override fun onFailed(response: PaymentResponse) {
        // TODO implement function
    }
}