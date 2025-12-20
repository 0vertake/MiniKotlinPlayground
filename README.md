## Prerequisites

### 1. JVM (Temurin 24 or compatible JDK 24)

If you have IntelliJ IDEA installed, you likely already have a JDK. Verify by running:
```shell
java -version
```

If you need to install JDK 24, download Temurin 24 from [Adoptium](https://adoptium.net/temurin/releases/?version=24).

### 2. Install Kotlin Compiler

**Windows:**
1. Download the latest Kotlin compiler from [GitHub Releases](https://github.com/JetBrains/kotlin/releases/latest)
2. Extract the ZIP to a location (e.g., `C:\kotlinc`)
3. Add `C:\kotlinc\bin` to your PATH:
   - Search for "Environment Variables" in Windows
   - Edit "Path" under System Variables
   - Add new entry: `C:\kotlinc\bin`
4. Verify installation:
   ```shell
   kotlinc -version
   ```

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

# Or download manually
wget https://github.com/JetBrains/kotlin/releases/latest/download/kotlin-compiler.zip
unzip kotlin-compiler.zip
sudo mv kotlinc /usr/local/
echo 'export PATH=$PATH:/usr/local/kotlinc/bin' >> ~/.bashrc
source ~/.bashrc
```

Verify installation:
```shell
kotlinc -version
```

## Running the Application

### Windows
```shell
.\gradlew.bat :composeApp:run
```

### macOS/Linux
```shell
./gradlew :composeApp:run
```

