package com.example.kmp.demo

import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

// actual：iOS 端用 Foundation 的 NSTimeZone 实现
actual fun currentTimeZoneId(): String =
    NSTimeZone.localTimeZone.name
