# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xiongweidong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include pathCir and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with activity relative pathCir so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Bugly混淆规则
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# 避免影响升级功能，需要keep住support包的类
-keep class android.support.**{*;}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
# Glide-End