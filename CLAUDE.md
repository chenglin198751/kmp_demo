# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Kotlin Multiplatform (KMP) + Compose Multiplatform 项目，同时面向 Android 与 iOS，且 **UI 也是共享的**（Compose Multiplatform）。平台入口层很薄，业务与界面代码都放在 `:shared` 模块的 `commonMain` 中。

模块划分：
- `:shared` — KMP 库，包含共享的 Compose UI（`App.kt`）与业务逻辑。编译产物：Android library + iOS 静态 framework（baseName `Shared`）。
- `:androidApp` — Android 入口，`MainActivity` 仅调用 `setContent { App() }`。
- `iosApp` — Xcode 工程，SwiftUI 入口 `ContentView` 通过 `MainViewControllerKt.MainViewController()` 加载共享的 Compose UI。

包名统一为 `com.example.kmp.demo`。

## 常用命令

在项目根目录（Git Bash）执行：

**Android**
- 构建 Android App（APK）：`./gradlew :androidApp:assembleDebug`
- 构建共享库 AAR（供外部 Android 工程集成）：`./gradlew :shared:assembleAndroidMain`
  - 产物：`shared/build/outputs/aar/shared.aar`（新 AGP KMP 插件用统一的 `androidMain` variant，无 debug/release 之分）

**iOS**（需在 macOS 上执行，Windows 无法本地构建）
- 模拟器 framework：`./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`
- 真机 framework：`./gradlew :shared:linkDebugFrameworkIosArm64`
  - 产物：`shared/build/bin/<target>/debugFramework/Shared.framework`（静态 framework，baseName `Shared`）
- 导出真机+模拟器合一的 XCFramework（对外分发用）：`./gradlew :shared:assembleSharedXCFramework`
  - 产物：`shared/build/XCFrameworks/debug/Shared.xcframework`
- iOS App 运行：在 Xcode 中打开 `iosApp/iosApp.xcodeproj` 并运行（Xcode 构建时会自动调用 Gradle 链接 framework，正常开发无需手动执行上面的 framework 命令）。

**测试**
- Android（host 侧）单测：`./gradlew :shared:testAndroidHostTest`
- iOS 模拟器单测：`./gradlew :shared:iosSimulatorArm64Test`
- 运行单个测试：`./gradlew :shared:testAndroidHostTest --tests "com.example.kmp.demo.SharedLogicAndroidHostTest"`

## 关键架构

### expect/actual 平台抽象
平台相关能力通过 `expect`/`actual` 模式提供。示例：`commonMain/Platform.kt` 声明 `expect fun getPlatform(): Platform`，`androidMain/Platform.android.kt` 与 `iosMain/Platform.ios.kt` 分别提供 actual 实现。新增平台差异逻辑时沿用此模式，对应源码集为 `commonMain` / `androidMain` / `iosMain`。

### 构建与版本
- Gradle 9.5.1（wrapper 已锁定），Kotlin `2.4.20`，AGP `9.1.1`，Compose Multiplatform `1.12.0`，Material3 `1.12.0-alpha03`。
- 版本与依赖统一在 `gradle/libs.versions.toml`（version catalog），通过 `libs.xxx` 引用。
- Android：minSdk 28，compileSdk / targetSdk 37，JVM target 11。Android SDK 路径见 `local.properties`（`sdk.dir`）。
- Gradle daemon JVM 工具链为 JDK 21（`gradle/gradle-daemon-jvm.properties`）。

### 新的 AGP KMP 插件（注意点）
`shared/build.gradle.kts` 使用较新的 `com.android.kotlin.multiplatform.library` 插件（version catalog 中的 `androidMultiplatformLibrary`），而非旧式 `com.android.library`。其 `android {}` 块用 `withHostTest {}` / `withDeviceTestBuilder {}` 配置测试。因此 Android 单元测试的源码集名为 `androidHostTest`（不是旧的 `androidUnitTest`），对应测试任务名为 `testAndroidHostTest`。

### Compose 资源
共享资源位于 `shared/src/commonMain/composeResources/`。生成代码的包名为 `kmp_demo.shared.generated.resources`（由 `rootProject.name = "KMP_DEMO"` 派生），在 `App.kt` 中以 `import kmp_demo.shared.generated.resources.Res` 方式引用。

### 测试源码集
- `commonTest` — 跨平台共享测试（`./gradlew :shared:allTests` 或各平台任务）。
- `androidHostTest` — Android host 侧测试。
- `iosTest` — iOS 测试（`iosSimulatorArm64Test`）。

## 平台环境说明

- 系统为 Windows。命令行使用 Git Bash；构建命令用 `./gradlew`（对应 `gradlew.bat` 供 cmd 使用）。
- 不要按 Linux 环境处理路径（无 `/mnt/...` 等 WSL 路径）；工具参数用 `D:\...`，Git Bash 命令中用 `D:/...`。
