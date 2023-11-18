package network.onepay.sdk.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import network.onepay.sdk.OnePaySDK
import network.onepay.sdk.dto.PaymentResponse
import network.onepay.sdk.listener.OnOnePaySuccessListener
import network.onepay.sdk.listener.SimpleOnSuccessListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OnePaySDK.initialize(
            "0x8d70EC40AAd376aa6fD08e4CFD363EaC0AB2c174",
            listOf("usdt,usdc,dai"),
            listOf("ethereum,arbitrum,optimism,bsc")
        )

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            OnePaySDK.pay(supportFragmentManager,
                0.1f,
                "usdt",
                "this is demo note",
                object : OnOnePaySuccessListener {
                    override fun onSuccess(response: PaymentResponse) {
//                        Log.d("OnePaySuccess", response.toString())
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailed(response: PaymentResponse) {
//                        Log.d("OnePaySuccess", response.toString())
                        Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}