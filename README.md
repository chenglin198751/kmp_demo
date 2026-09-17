这是一个面向 Android、iOS 的 Kotlin Multiplatform 项目。

* [/iosApp](./iosApp/iosApp) 包含一个 iOS 应用。即使你使用 Compose Multiplatform 共享 UI，也仍需要这个入口点来构建 iOS 应用。这里也是你为项目添加 SwiftUI 代码的地方。

* [/shared](./shared/src) 用于存放会在各个 Compose Multiplatform 应用之间共享的代码。它包含若干子目录：
  - [commonMain](./shared/src/commonMain/kotlin) 存放对所有目标平台通用的代码。
  - 其他目录存放仅会为目录名所指示的平台编译的 Kotlin 代码。例如，如果你想在 Kotlin 应用的 iOS 部分使用 Apple 的 CoreCrypto，那么 [iosMain](./shared/src/iosMain/kotlin) 目录就是放置这些调用的合适位置。同理，如果你要编辑 Desktop（JVM）平台特有的部分，[jvmMain](./shared/src/jvmMain/kotlin) 目录就是合适的位置。

### 运行应用

使用 IDE 工具栏中 run 控件提供的运行配置。你也可以使用以下命令和选项：

- Android 应用：`./gradlew :androidApp:assembleDebug`
- iOS 应用：在 Xcode 中打开 [/iosApp](./iosApp) 目录并从中运行。

### 运行测试

使用 IDE 编辑器侧边栏中的运行按钮，或通过 Gradle 任务运行测试：

- Android 测试：`./gradlew :shared:testAndroidHostTest`
- iOS 测试：`./gradlew :shared:iosSimulatorArm64Test`

---

了解更多关于 [Kotlin Multiplatform](https://www.jetbrains.com.cn/en-us/help/kotlin-multiplatform-dev/get-started.html) 的信息…
