# Glide3.7 specific rules #
# https://github.com/bumptech/glide/wiki/Configuration#keeping-a-glidemodule


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}