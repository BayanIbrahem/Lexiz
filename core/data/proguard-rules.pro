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
#-keep class kotlinx.serialization.* { *; }
#-keepclasseswithmembers class * {
#    @kotlinx.serialization.* <methods>;
#}
#-keep class com.dev.bayan.ibrahim.core.data.model.* { *; }
#-keep class com.dev.bayan.ibrahim.core.data.model.** { *; }
#-keepattributes InnerClasses
#-if @kotlinx.serialization.Serializable class com.dev.bayan.ibrahim.core.data.model.LibraryLanguagesPackage,
#com.dev.bayan.ibrahim.core.data.model.LibraryPackageLanguage,
#com.dev.bayan.ibrahim.core.data.model.LibraryPackageType
#{
#    static **$* *;
#}
#
#-keepnames class <1>$$serializer {
#    static <1>$$serializer INSTANCE;
#}