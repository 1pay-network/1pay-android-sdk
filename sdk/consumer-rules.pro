# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

#-keep public class network.onepay.sdk.** {
#    *;
#}

-keepclassmembers class network.onepay.sdk.dialog.OnePayDialog {
   public *;
}

-keep public class network.onepay.sdk.dto.PaymentResponse {
    *;
}

-keep class network.onepay.sdk.OnePaySDK {
    public *;
}

# Klaxon
-keep public class kotlin.reflect.jvm.internal.impl.** { public *; }
-keep class com.beust.klaxon.** { *; }
-keep interface com.beust.klaxon.** { *; }
-keep class kotlin.Metadata { *; }


# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile