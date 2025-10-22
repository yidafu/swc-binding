# swc-jni

SWC JNI bindings for Kotlin/Java.

Implements both **Sync** and **Async** methods for JavaScript/TypeScript parsing, transformation, minification, and code generation.

See more at <https://github.com/yidafu/swc-binding>

## Features

- 🚀 Fast JavaScript/TypeScript parsing powered by SWC
- 🔄 Code transformation (ES6 → ES5, TypeScript → JavaScript)
- 📦 Code minification with compression and mangling
- 🎨 Code generation with source maps support
- ⚡ Both synchronous and asynchronous APIs
- 🧪 Comprehensive test suite with 70%+ coverage

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

For detailed test documentation, see:
- `TEST_COVERAGE_REPORT.md` - Coverage analysis
- `TEST_IMPLEMENTATION_SUMMARY.md` - Implementation details

### Test Structure

- Unit tests for each module (`lib.rs`, `util.rs`, `parse.rs`, `transform.rs`, `minify.rs`, `print.rs`)
- Integration tests validating the full workflow
- Test fixtures for JavaScript and TypeScript
- Error handling and edge case tests

## Development

```bash
# Build
cargo build

# Build for release
cargo build --release

# Run tests
cargo test

# Format code
cargo fmt

# Lint
cargo clippy
```

## License

Apache-2.0
