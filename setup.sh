#!/bin/bash

# =============================================================================
# SWC JNI Binding - Build Setup Script
# =============================================================================
# This script prepares the environment for building SWC JNI bindings
# for multiple platforms with optimal toolchain configurations.
#
# Supported platforms:
# - macOS: darwin-x64-apple, darwin-arm64-apple
# - Linux: linux-x64-musl, linux-arm64-gnu  
# - Windows: windows-x64-gnu, windows-arm64-gnu
# =============================================================================

set -e  # Exit on any error

echo "🚀 Setting up SWC JNI Binding build environment..."
echo "=================================================="

# =============================================================================
# 1. Check Rust installation
# =============================================================================
echo "📦 Checking Rust installation..."

if ! command -v rustc &> /dev/null; then
    echo "❌ Rust is not installed. Please install Rust first:"
    echo "   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh"
    echo "   source ~/.cargo/env"
    exit 1
fi

RUST_VERSION=$(rustc --version)
echo "✅ Rust found: $RUST_VERSION"

# =============================================================================
# 2. Install required Rust targets
# =============================================================================
echo "🎯 Installing required Rust targets..."

TARGETS=(
    "x86_64-apple-darwin"      # macOS Intel
    "aarch64-apple-darwin"     # macOS ARM64
    "x86_64-unknown-linux-musl" # Linux x64 (musl static)
    "aarch64-unknown-linux-gnu" # Linux ARM64 (GNU)
    "x86_64-pc-windows-gnu"    # Windows x64 (GNU)
    "aarch64-pc-windows-gnullvm" # Windows ARM64 (GNU)
)

for target in "${TARGETS[@]}"; do
    echo "  Installing target: $target"
    rustup target add "$target" || {
        echo "⚠️  Failed to install target $target, but continuing..."
    }
done

echo "✅ Rust targets installation completed"

# =============================================================================
# 3. Install cargo-zigbuild
# =============================================================================
echo "🔧 Installing cargo-zigbuild..."

if ! command -v cargo-zigbuild &> /dev/null; then
    echo "  Installing cargo-zigbuild..."
    cargo install cargo-zigbuild || {
        echo "❌ Failed to install cargo-zigbuild"
        echo "   Please install manually: cargo install cargo-zigbuild"
        exit 1
    }
else
    echo "✅ cargo-zigbuild already installed"
fi

ZIGBUILD_VERSION=$(cargo-zigbuild --version 2>/dev/null || echo "unknown")
echo "✅ cargo-zigbuild version: $ZIGBUILD_VERSION"

# =============================================================================
# 4. Install Zig (required by cargo-zigbuild)
# =============================================================================
echo "🧱 Installing Zig..."

if ! command -v zig &> /dev/null; then
    if command -v brew &> /dev/null; then
        echo "  Using Homebrew to install Zig..."
        brew install zig || {
            echo "❌ Failed to install Zig via Homebrew"
            exit 1
        }
    elif command -v apt-get &> /dev/null; then
        echo "  Using apt-get to install Zig..."
        sudo apt-get update
        sudo apt-get install -y zig || {
            echo "❌ Failed to install Zig via apt-get"
            exit 1
        }
    elif command -v pacman &> /dev/null; then
        echo "  Using pacman to install Zig..."
        sudo pacman -S --noconfirm zig || {
            echo "❌ Failed to install Zig via pacman"
            exit 1
        }
    else
        echo "❌ Zig 未安装且无法检测到受支持的包管理器，请手动安装 Zig："
        echo "   https://ziglang.org/download/"
        exit 1
    fi
else
    echo "✅ Zig 已安装"
fi

ZIG_VERSION=$(zig version 2>/dev/null || echo "unknown")
echo "✅ Zig version: $ZIG_VERSION"

# =============================================================================
# 5. Verify build environment
# =============================================================================
echo "🔍 Verifying build environment..."

# Check if we're in the right directory
if [ ! -f "swc-jni/Cargo.toml" ]; then
    echo "❌ Please run this script from the project root directory"
    echo "   Expected to find: swc-jni/Cargo.toml"
    exit 1
fi

# Check if build script exists
if [ ! -f "swc-jni/build.sh" ]; then
    echo "❌ Build script not found: swc-jni/build.sh"
    exit 1
fi

echo "✅ Build environment verified"

# =============================================================================
# 6. Display build configuration
# =============================================================================
echo "📋 Build Configuration Summary"
echo "==============================="
echo "Platforms and toolchains:"
echo "  • macOS Intel:    darwin-x64-apple     (Apple toolchain)"
echo "  • macOS ARM64:    darwin-arm64-apple   (Apple toolchain)"
echo "  • Linux x64:      linux-x64-musl       (musl static linking)"
echo "  • Linux ARM64:    linux-arm64-gnu      (GNU toolchain)"
echo "  • Windows x64:    windows-x64-gnu      (GNU toolchain)"
echo "  • Windows ARM64:  windows-arm64-gnu    (GNU toolchain)"
echo ""
echo "Build tool: cargo-zigbuild"
echo "Output directory: swc-binding/src/main/resources/"
echo ""

# =============================================================================
# 7. Optional: Test build (if requested)
# =============================================================================
if [ "$1" = "--test-build" ]; then
    echo "🧪 Running test build..."
    cd swc-jni
    ./build.sh
    echo "✅ Test build completed successfully!"
    cd ..
fi

# =============================================================================
# 8. Final instructions
# =============================================================================
echo "🎉 Setup completed successfully!"
echo ""
echo "Next steps:"
echo "  1. Run the build script:"
echo "     cd swc-jni && ./build.sh"
echo ""
echo "  2. Or run with test build:"
echo "     ./setup.sh --test-build"
echo ""
echo "  3. The built libraries will be available in:"
echo "     swc-binding/src/main/resources/"
echo ""
echo "For more information, see:"
echo "  • Build script: swc-jni/build.sh"
echo "  • Documentation: docs/"
echo ""

# =============================================================================
# 9. Environment variables (optional)
# =============================================================================
echo "💡 Optional environment variables:"
echo "  • RUST_LOG=debug          # Enable debug logging"
echo "  • CARGO_TARGET_DIR=target # Custom target directory"
echo "  • CC_aarch64_unknown_linux_gnu=zig # Use zig as linker for Linux ARM64"
echo ""

echo "✨ Ready to build SWC JNI bindings!"
