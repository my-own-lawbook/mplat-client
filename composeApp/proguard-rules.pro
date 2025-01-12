# Workaround for:
# 'Warning: androidx.compose.runtime.ExpectKt$ThreadLocal$1: can't find enclosing method 'androidx.compose.runtime.ThreadLocal ThreadLocal()' in program class androidx.compose.runtime.ExpectKt'
# that made :proguardRelease<Deb|Msi|Exe> fail
-dontwarn androidx.compose.runtime.ExpectKt$ThreadLocal$1