package network.onepay.sdk

import androidx.fragment.app.FragmentManager
import network.onepay.sdk.dialog.OnePayDialog
import network.onepay.sdk.listener.OnOnePaySuccessListener

object OnePaySDK {

    private var isInitialized: Boolean = false

    internal var recipient: String = ""
    internal var tokens: String = ""
    internal var networks: String = ""

    fun initialize(recipient: String, tokens: List<String>, networks: List<String>) {
        if (this.isInitialized) throw SDKInitializedException()
        this.recipient = recipient
        this.tokens = tokens.joinToString(",")
        this.networks = networks.joinToString(",")
        isInitialized = true
    }

    fun pay(
        fragmentManager: FragmentManager,
        amount: Float,
        token: String,
        note: String,
        callback: OnOnePaySuccessListener
    ) {
        if (recipient.isEmpty() || tokens.isEmpty() || networks.isEmpty())
            throw SDKUnInitializedException()
        OnePayDialog(amount, token, note, callback).show(fragmentManager, "onepay-dialog")
    }

    class SDKInitializedException : Exception("SDK had been initialized")
    class SDKUnInitializedException : Exception("SDK had been not initialize")
}