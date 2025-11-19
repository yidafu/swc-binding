# SWC JNI Binding - Build Setup Guide

This guide explains how to set up the build environment for SWC JNI bindings across multiple platforms.

## 🚀 Quick Start

### 1. Run the setup script

```bash
./setup.sh
```

### 2. Build all platforms

```bash
cd swc-jni && ./build.sh
```

## 📋 Prerequisites

### Required Software

- **Rust** (latest stable version)
- **cargo-zigbuild** (for cross-compilation)
- **Git** (for version control)

### Platform-specific Requirements

- **macOS**: Xcode Command Line Tools
- **Linux**: Standard build tools (gcc, make)
- **Windows**: No additional requirements (cross-compiled from any platform)

## 🔧 Setup Script Features

The `setup.sh` script automatically:

1. **Verifies Rust installation**
2. **Installs required Rust targets**:
   - `x86_64-apple-darwin` (macOS Intel)
   - `aarch64-apple-darwin` (macOS ARM64)
   - `x86_64-unknown-linux-musl` (Linux x64 static)
   - `aarch64-unknown-linux-gnu` (Linux ARM64)
   - `x86_64-pc-windows-gnu` (Windows x64)
   - `aarch64-pc-windows-gnullvm` (Windows ARM64)

3. **Installs cargo-zigbuild** for cross-compilation
4. **Verifies build environment**
5. **Provides build configuration summary**

## 🏗️ Build Configuration

### Platform Support Matrix

| Platform | Architecture | Toolchain | Directory | Static Linking |
|----------|-------------|-----------|-----------|----------------|
| **macOS** | Intel | Apple | `darwin-x64-apple` | No |
| **macOS** | ARM64 | Apple | `darwin-arm64-apple` | No |
| **Linux** | x64 | musl | `linux-x64-musl` | ✅ Yes |
| **Linux** | ARM64 | GNU | `linux-arm64-gnu` | No |
| **Windows** | x64 | GNU | `windows-x64-gnu` | No |
| **Windows** | ARM64 | GNU | `windows-arm64-gnu` | No |

### Toolchain Selection Rationale

- **macOS**: Uses Apple native toolchain for optimal performance
- **Linux x64**: Uses musl for static linking and better compatibility
- **Linux ARM64**: Uses GNU toolchain (musl doesn't support cdylib)
- **Windows**: Uses GNU toolchain for better cross-platform compatibility

## 🛠️ Manual Setup (Alternative)

If you prefer manual setup:

### 1. Install Rust

```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
source ~/.cargo/env
```

### 2. Install Rust targets

```bash
rustup target add x86_64-apple-darwin
rustup target add aarch64-apple-darwin
rustup target add x86_64-unknown-linux-musl
rustup target add aarch64-unknown-linux-gnu
rustup target add x86_64-pc-windows-gnu
rustup target add aarch64-pc-windows-gnullvm
```

### 3. Install cargo-zigbuild

```bash
cargo install cargo-zigbuild
```

## 🧪 Testing the Setup

### Test build (recommended)

```bash
./setup.sh --test-build
```

### Manual verification

```bash
cd swc-jni
cargo check --target x86_64-apple-darwin
cargo check --target aarch64-apple-darwin
cargo check --target x86_64-unknown-linux-musl
cargo check --target aarch64-unknown-linux-gnu
cargo check --target x86_64-pc-windows-gnu
cargo check --target aarch64-pc-windows-gnullvm
```

## 📁 Output Structure

After successful build, libraries will be available in:

```
swc-binding/src/main/resources/
├── darwin-x64-apple/
│   └── libswc_jni.dylib
├── darwin-arm64-apple/
│   └── libswc_jni.dylib
├── linux-x64-musl/
│   └── libswc_jni.so
├── linux-arm64-gnu/
│   └── libswc_jni.so
├── windows-x64-gnu/
│   └── swc_jni.dll
└── windows-arm64-gnu/
    └── swc_jni.dll
```

## 🔍 Troubleshooting

### Common Issues

1. **Rust not found**

   ```bash
   # Install Rust
   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
   source ~/.cargo/env
   ```

2. **cargo-zigbuild installation failed**

   ```bash
   # Try with specific version
   cargo install cargo-zigbuild --version 0.18.0
   ```

3. **Target installation failed**

   ```bash
   # Update Rust first
   rustup update
   # Then retry target installation
   rustup target add <target-name>
   ```

4. **Build fails with "cdylib not supported"**
   - This is expected for `aarch64-unknown-linux-musl`
   - The build script uses `aarch64-unknown-linux-gnu` instead

### Environment Variables

Optional environment variables for debugging:

```bash
export RUST_LOG=debug                    # Enable debug logging
export CARGO_TARGET_DIR=custom_target    # Custom target directory
export CC_aarch64_unknown_linux_gnu=zig  # Use zig as linker
```

## 📚 Additional Resources

- [Rust Cross-compilation Guide](https://rust-lang.github.io/rustup/cross-compilation.html)
- [cargo-zigbuild Documentation](https://github.com/rust-cross/cargo-zigbuild)
- [SWC Documentation](https://swc.rs/)

## 🤝 Contributing

If you encounter issues or have suggestions:

1. Check the troubleshooting section
2. Review the build logs
3. Open an issue with detailed information
4. Include your platform and Rust version

---

**Happy building! 🎉**