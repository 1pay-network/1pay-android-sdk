
<h1 align="center">
  <br>
  <a href="https://1pay.network" alt="1Pay.Network" width="200"><img src="https://1pay.network/assets/dist/imgs/logo.png" alt="1Pay.Network"></a>
  <br>
  1PAY.network SDK
  <br>
</h1>

<h4 align="center">Android SDK for 1PAY.network.</h4>


## Prerequisites

* `minSdk` >= **24**
* `kotlin-android` build tools version >= **1.8.0**
* ERC-20 wallet address

## Installation

You need to verify that your installed kotlin compiler version is >= 1.8.0
project ```build.gradle```

```groovy
plugins {
    id 'org.jetbrains.kotlin.android' version '1.8.0' apply false
}
```
Add the dependency below into your **app's** `build.gradle`<br/>

[![Maven Central](https://img.shields.io/maven-metadata/v.svg?label=Maven%20Central&metadataUrl=https://s01.oss.sonatype.org/service/local/repo_groups/public/content/network/1pay/sdk/maven-metadata.xml)](https://central.sonatype.com/search?q=network.1pay) <br>

```groovy
dependencies {
    implementation 'network.1pay:sdk:$version'
}
```


## Usage
1. Init SDK
```kotlin
OnePaySDK.initialize(
   RECIPIENT_ADDRESS,
   listOf("usdt,usdc,dai"), // supported token
   listOf("ethereum,arbitrum,optimism,bsc") // supported network
)
```

2. Use `OnePaySDK.pay` function to show pay dialog
```kotlin
OnePaySDK.pay(supportFragmentManager,
    0.1f, // amount
    "usdt", // token used for pay
    "this is demo note", // note
    object : OnOnePaySuccessListener { // callback
        override fun onSuccess(response: PaymentResponse) {
            // TODO handle response
        }

        override fun onFailed(response: PaymentResponse) {
            // TODO handle failed
        }
    })
```

You can access [documentation](https://1pay.network/documents) for another platform guide.


## Handle result
You can extend class `OnOnePaySuccessListener` or use our implemented class to handle result
```kotlin
val listener = object: OnOnePaySuccessListener {
    override fun onSuccess(response: PaymentResponse) {
        // Handle success
    }

    override fun onFailed(response: PaymentResponse) {
        // Handle failed
    }
}
```

## API Refences

#### OnePaySDK.initialize
```kotlin
fun initialize(recipient: String, tokens: List<String>, networks: List<String>)
```

#### OnePaySDK.pay
```kotlin
fun pay(
    fragmentManager: FragmentManager,
    amount: Float,
    token: String,
    note: String,
    callback: OnOnePaySuccessListener
)
```

#### PaymentResponse
```kotlin
data class PaymentResponse(
    val hash: String?,
    val success: Boolean,
    val amount: Float,
    val token: String,
    val network: String,
    val note: String
)
```

## LICENSE
MIT
