# Glide3.7 specific rules #
# https://github.com/bumptech/glide/wiki/Configuration#keeping-a-glidemodule


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}