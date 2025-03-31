# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 神蓍广告SDK混淆规则
-keep class com.shenshi.** { *; }
-keep class com.kc.openset.** { *; }
-keep class com.qidian.** { *; }
-dontwarn com.shenshi.**
-dontwarn com.qidian.**

# 基本混淆规则
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keep class **.R$* {*;}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# 阿里-tanx广告SDK混淆
-keep class com.alimm.** { *; }
-keep class com.taobao.** { *; }
-keep class com.ta.** { *; }
-keep class com.ut.** { *; }
-keep class com.visenze.** { *; }
-dontwarn com.alimm.**
-dontwarn com.taobao.**
-dontwarn com.ta.**
-dontwarn com.ut.**
-dontwarn com.visenze.**

# 广点通混淆
-keep class com.qq.e.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-dontwarn com.qq.e.**

# 快手混淆
-keep class org.chromium.** { *; }
-keep class com.kwad.** { *; }
-keep class com.kwai.** { *; }
-keep class com.kwaishou.** { *; }
-keep class com.yxcorp.** { *; }
-dontwarn com.kwad.**
-dontwarn com.kwai.**
-dontwarn com.kwaishou.**
-dontwarn com.yxcorp.**

# 贝子混淆
-keep class com.union.** { *; }
-keep class org.apache.commons.** { *; }
-dontwarn com.union.**
-dontwarn org.apache.commons.**

# OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase