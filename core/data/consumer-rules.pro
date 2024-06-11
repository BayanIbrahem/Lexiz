#-dontwarn java.lang.invoke.StringConcatFactory
#-keep class kotlinx.serialization.** { *; }
#-keepclasseswithmembers class * {
#    @kotlinx.serialization.* <methods>;
#}
#
#-keep class com.dev.bayan.ibrahim.core.data.model.** { *; }
#-keep class com.dev.bayan.ibrahim.core.data.model.* { *; }