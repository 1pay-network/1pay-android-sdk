package network.onepay.sdk.dialog

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.beust.klaxon.Klaxon
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import network.onepay.sdk.OnePaySDK
import network.onepay.sdk.R
import network.onepay.sdk.dto.PaymentResponse
import network.onepay.sdk.listener.OnOnePaySuccessListener
import java.net.URLEncoder

class OnePayDialog(
    private val amount: Float,
    private val token: String,
    private val note: String,
    private val callback: OnOnePaySuccessListener
) : BottomSheetDialogFragment() {

    private val INJECTED_SCRIPT =
        "onsuccess=onepaySuccess;onepaySuccess=function(response){onsuccess(response);android.onSuccess(JSON.stringify(response))};" +
                "onfailed=onepayFailed;onepayFailed=function(response){onfailed(response);android.onFailed(JSON.stringify(response))};"

    private val klaxon by lazy { Klaxon() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_onepay, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {}.apply {
            setOnShowListener { dialog ->
                (dialog as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.also {
                        it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                        with(BottomSheetBehavior.from(it)) {
                            peekHeight = Resources.getSystem().displayMetrics.heightPixels
                            isHideable = false
                        }
                        it.requestLayout()
                    }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val closeBtn = view.findViewById<ImageView>(R.id.btn_close)
        closeBtn.setOnClickListener {
            dismissAllowingStateLoss()
        }
        val webView = view.findViewById<WebView>(R.id.webview_payment)
        with(webView) {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    // Override url loading for handle connect wallet
                    return handleOutgoingRequest(request.url.toString())
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(INJECTED_SCRIPT, null)
                }
            }

            addJavascriptInterface(this@OnePayDialog, "android")
        }
        webView.loadUrl(buildPaymentURL())
    }

    private fun handleOutgoingRequest(url: String): Boolean {
        // Check url is from 1pay.network
        if (!url.contains("1pay.network")) {
            try {
                // Handle wallet connect, open wallet client app or open playstore to install it
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (_: Exception) {

            }
            return true
        }
        return false
    }

    @JavascriptInterface
    fun onSuccess(response: String) {
        klaxon.parse<PaymentResponse>(response)?.also(callback::onSuccess)
    }

    @JavascriptInterface
    fun onFailed(response: String) {
        klaxon.parse<PaymentResponse>(response)?.also(callback::onFailed)
    }

    private fun buildPaymentURL(): String {
        return "https://1pay.network/app" +
                "?recipient=${OnePaySDK.recipient}" +
                "&token=${OnePaySDK.tokens}" +
                "&network=${OnePaySDK.networks}" +
                "&paymentAmount=${amount}" +
                "&paymentToken=${token}" +
                "&paymentNote=${URLEncoder.encode(note, Charsets.UTF_8.toString())}"
    }
}