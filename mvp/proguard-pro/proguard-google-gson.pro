##com.google.code.gson:gson:2.6.2

## GSON 2.6.2 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }


# Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }
#这是google官方的proguard的文档，请注意倒数第二行，class 后方到**签名的
#这一段包名应该是你所有的java bean　定义的目录（所以自己在写代码时，应该把java  bean 单独放在一个包中）
