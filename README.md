# 🎮 Mini Kotlin Playground

A feature-rich desktop IDE for writing and executing Kotlin scripts in real-time. Demonstrates modern desktop application development using Kotlin multiplatform, reactive programming, and cutting-edge UI frameworks.

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple?style=flat-square&logo=kotlin)
![Java](https://img.shields.io/badge/Java-24-orange?style=flat-square&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

## ✨ Features

- **Interactive Code Editor** — Syntax-highlighted editor with line numbers and real-time editing feedback
- **Live Script Execution** — Execute Kotlin code instantly with streaming output
- **Intelligent Error Handling** — Detailed error messages with clickable stack traces for debugging
- **Process Management** — Run/stop scripts with execution state monitoring and exit code tracking
- **Responsive UI** — Built with Jetpack Compose for smooth, adaptive desktop experience
- **Tab Support** — Code editor with tab indentation for better code formatting

## 🏗️ Architecture & Tech Stack

### Core Technologies
- **Language**: Kotlin (multiplatform ready)
- **UI Framework**: Jetpack Compose for Desktop (reactive, declarative UI)
- **Build System**: Gradle with Kotlin DSL
- **Runtime**: JVM 24 (Temurin)
- **Concurrency**: Kotlin Coroutines for non-blocking script execution

### Key Design Patterns
- **MVVM Architecture** — State management with reactive `mutableState` 
- **Coroutine-based Async** — Non-blocking script execution and result streaming
- **Composition** — Modular UI components (EditorPane, OutputPane, StatusBar)
- **Script Sandboxing** — Isolated script execution with proper process management

### Project Structure
```
composeApp/
├── src/jvmMain/kotlin/com/milos/minikotlinplayground/
│   ├── App.kt                 # Main Compose UI layout
│   ├── main.kt                # Application entry point
│   ├── executor/              # Script execution engine
│   │   ├── ScriptExecutor.kt  # Process-based Kotlin script runner
│   │   └── ExecutionState.kt  # Execution state model
│   ├── ui/                    # Compose UI components
│   │   ├── EditorPane.kt      # Code editor component
│   │   ├── OutputPane.kt      # Output display component
│   │   └── StatusBar.kt       # Status monitoring component
│   ├── syntax/                # Syntax highlighting utilities
│   └── util/                  # Helper functions
└── build.gradle.kts           # Compose & Kotlin configuration
```

## 🚀 Getting Started

### Prerequisites

#### JVM (Temurin 24)
If you have IntelliJ IDEA installed, you already have a JDK. Verify:
```shell
java -version
```

Otherwise, download [Temurin 24](https://adoptium.net/temurin/releases/?version=24) from Adoptium.

#### Kotlin Compiler

**Windows:**
1. Download the latest Kotlin compiler from [GitHub Releases](https://github.com/JetBrains/kotlin/releases/latest)
2. Extract to a location (e.g., `C:\kotlinc`)
3. Add `C:\kotlinc\bin` to your PATH:
   - Open "Environment Variables" (Win+X → System)
   - Edit "Path" under System Variables
   - Add new entry: `C:\kotlinc\bin`
4. Verify: `kotlinc -version`

**macOS:**
```shell
brew install kotlin
```

**Linux:**
```shell
# Using SDKMAN (recommended)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install kotlin
```

### Build & Run

```shell
# Windows
.\gradlew.bat :composeApp:run

# macOS/Linux
./gradlew :composeApp:run
```

The application will launch with a sample script demonstrating the execution capability.

## 💡 Technical Highlights

### Real-time Script Execution
- Scripts run in isolated processes using `ProcessBuilder`
- Output streamed and displayed in real-time via coroutines
- Graceful cancellation and process termination support

### Reactive State Management
- Uses Compose `State` primitives for reactive UI updates
- Automatic recomposition on state changes
- Proper lifecycle management with `rememberCoroutineScope`

### Error Resilience
- Comprehensive try-catch blocks with meaningful error messages
- Clickable stack traces for fast debugging
- Execution state tracking (running, completed, failed)

## 📝 Example Usage

1. Write Kotlin code in the editor:
```kotlin
import java.lang.Thread.sleep
for (i in 1..5) {
    sleep(1000)
    println("Running... $i")
}
```

2. Click "Run" to execute
3. See output stream in real-time
4. Check exit code and any errors in the status bar

## 🔧 Development Notes

- **Hot Reload Support**: Uses Compose hot reload for faster development iteration
- **Testable Architecture**: Separation of concerns makes components independently testable
- **JVM Performance**: Optimized for Temurin 24's latest improvements

## 📦 Dependencies

Key libraries managed via `gradle/libs.versions.toml`:
- `compose-multiplatform` — UI framework
- `kotlinx-coroutines` — Async programming
- `kotlin-test` — Testing framework
- `androidx.lifecycle` — Lifecycle-aware components

## 🎯 Future Enhancements

- [ ] Kotlin REPL integration for immediate feedback
- [ ] Code templates and snippets library
- [ ] Script history and bookmarks
- [ ] Export execution results
- [ ] Theme customization

## 📄 License

This project is open source and available under the MIT License.

