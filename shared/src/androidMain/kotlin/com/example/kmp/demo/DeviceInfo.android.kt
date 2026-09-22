package com.example.kmp.demo

import java.util.TimeZone

// actual：Android 端用 JVM 的 java.util.TimeZone 实现
actual fun currentTimeZoneId(): String =
    TimeZone.getDefault().id
