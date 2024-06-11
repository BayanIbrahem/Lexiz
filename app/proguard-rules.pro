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
#-dontwarn java.lang.invoke.StringConcatFactory
#-keep class java.lang.invoke.StringConcatFactory
#
#-keep class kotlinx.serialization.** { *; }
#-keepclasseswithmembers class * {
#    @kotlinx.serialization.* <methods>;
#}
#-keepclassmembers @kotlinx.serialization.Serializable class *.Environment {
# # lookup for plugin generated serializable classes *** Companion;
#  # lookup for serializable objects *** INSTANCE;
#   kotlinx.serialization.KSerializer serializer(...);
#   }
#   # lookup for plugin generated serializable classes
#  -if @kotlinx.serialization.Serializable class com.yourcompany.yourpackage.*
#  -keepclassmembers class com.dev.bayan.ibrahim.core.data.* {
#   kotlinx.serialization.KSerializer serializer(...);
#   }
