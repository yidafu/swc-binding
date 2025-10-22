# swc-jni

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-orange.svg)](https://www.rust-lang.org/)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

SWC JNI bindings for Kotlin/Java with latest dependency versions and enhanced async support.

Implements both **Sync** and **Async** methods for JavaScript/TypeScript parsing, transformation, minification, and code generation.

See more at <https://github.com/yidafu/swc-binding>

## Features

- 🚀 Fast JavaScript/TypeScript parsing powered by SWC 43.0.0
- 🔄 Code transformation (ES6 → ES5, TypeScript → JavaScript)
- 📦 Code minification with compression and mangling
- 🎨 Code generation with source maps support
- ⚡ Both synchronous and asynchronous APIs
- 🧪 Comprehensive test suite with 100% pass rate
- 🔧 Latest dependency versions for better performance and security
- 🌐 Enhanced async support with coroutines and callbacks

## Dependencies

This project uses the latest stable versions of all dependencies:

| Dependency | Version | Purpose |
|------------|---------|---------|
| **swc** | 43.0.0 | Core SWC compiler |
| **swc_common** | 15.0.0 | Common utilities |
| **swc_ecma_ast** | 16.0.0 | ECMAScript AST |
| **swc_ecma_codegen** | 18.0.0 | Code generation |
| **swc_ecma_transforms** | 36.0.0 | Code transformations |
| **swc_ecma_transforms_base** | 28.0.0 | Base transformations |
| **swc_ecma_visit** | 16.0.0 | AST visitor |
| **anyhow** | 1.0.100 | Error handling |
| **serde** | 1.0.225 | Serialization |
| **serde_json** | 1.0.115 | JSON serialization |
| **tracing** | 0.1.41 | Logging |
| **thiserror** | 2.0.17 | Error types |
| **jni** | 0.21.1 | Java Native Interface |
| **jni_fn** | 0.1.2 | JNI function macros |
| **tempfile** | 3.23.0 | Temporary files (dev) |

## Testing

This project includes a comprehensive test suite covering all core functionality.

### Running Tests

```bash
# Run all tests
cargo test

# Or use the provided script
./run-tests.sh

# Run only unit tests
cargo test --lib

# Run only integration tests
cargo test --test integration_test
```

### Test Coverage

- **Total Tests**: 41 (35 unit tests + 6 integration tests)
- **Coverage**: 70%+ (excluding JNI boundary functions)
- **Pass Rate**: 100%
- **Test Fixtures**: JavaScript, TypeScript, and configuration files
- **Error Handling**: Comprehensive error scenarios and edge cases

For detailed test documentation, see:
- `TEST_COVERAGE_REPORT.md` - Coverage analysis
- `TEST_IMPLEMENTATION_SUMMARY.md` - Implementation details

### Test Structure

- Unit tests for each module (`lib.rs`, `util.rs`, `parse.rs`, `transform.rs`, `minify.rs`, `print.rs`)
- Integration tests validating the full workflow
- Test fixtures for JavaScript and TypeScript
- Error handling and edge case tests

## Development

### Building

```bash
# Build debug version
cargo build

# Build for release
cargo build --release

# Build macOS debug version with dynamic library
./build_debug.sh
# Or
# Build all platforms dynamic libraries
./build.sh

# Run tests
cargo test

# Format code
cargo fmt

# Lint
cargo clippy
```

### Build Scripts

- `build_debug.sh` - Builds macOS debug version and copies dynamic library to swc-binding resources
- `run-tests.sh` - Runs comprehensive test suite
- `generate-coverage.sh` - Generates test coverage report

### Project Structure

```
swc-jni/
├── src/
│   ├── lib.rs           # Main library entry point
│   ├── async_utils.rs   # Async utilities and callbacks
│   ├── parse.rs         # Parsing functionality
│   ├── transform.rs     # Code transformation
│   ├── minify.rs        # Code minification
│   ├── print.rs         # Code generation
│   └── util.rs          # Utility functions
├── tests/
│   └── integration_test.rs  # Integration tests
├── test-fixtures/       # Test data files
└── Cargo.toml          # Project configuration
```

## License

Apache-2.0
