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
-ignorewarnings#表示没有问题，可以忽略警告
-dontoptimize
#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}
# For retrolambda
-dontwarn java.lang.invoke.*
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in activity class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.example.chriszou.quicksample.model.** { *; }
##---------------End: proguard configuration for Gson  ----------
# litepal数据库 start
-keep class org.litepal.** {
    *;
}

-keep class * extends org.litepal.crud.DataSupport {
    *;
}
# End

#glide start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
# end
# Bugly混淆规则
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# 避免影响升级功能，需要keep住support包的类
-keep class android.support.**{*;}
#下拉刷新
-keep class com.jcodecraeer.**{*;}
#-keep class org.quick.library.b.BaseApplication{*;}
#-keep public class * extends org.quick.library.b.BaseApplication
#-keep class org.quick.library.helper.**{*;}
#-keep class org.chriszou.redpackthelper.config.**{*;}
#-keep class **.R$*{*;}
-keepattributes SourceFile,LineNumberTable
