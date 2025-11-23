# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.8.0] - 2025-11-23

### Added
- **Publishing**: Upgrade to Dokka v2 and fix Maven Central publishing (3b45a9d)
- **Publishing**: Add nmcp plugin for Maven Central publishing (677786f)
- **Compiler**: Add fresh compiler support and improve error handling in swc-jni (53bd2e8)
- **Binding**: Improve transformSync isModule handling and add comprehensive tests (80c4788)
- **AST**: Add @EncodeDefault annotation to span properties and fix AST class implementations (1474773)
- **Testing**: Enhance span comparison and test infrastructure (740567b)
- **swc-jni**: Add tracing and backtrace support for better debugging
- **swc-jni**: Add plugin and swc_v1/v2 feature flags

### Changed
- **swc-jni**: Migrate from individual swc crates to swc_core v48.0.4 unified package (3b1a7fd)
  - Upgraded from swc v0.270.25 to swc_core v48.0.4
  - Consolidated dependencies: swc_ecma_ast, swc_ecma_transforms, swc_ecma_codegen, etc.
  - Added allocator_node, ecma_minifier, and bundler features
  - License changed from Apache-2.0 to MIT
- **swc-jni**: Improve JNI build process and enhance minify/transform functionality (929c98d)
- **swc-jni**: Update Rust dependencies:
  - anyhow: 1.0.75 → 1.0.100
  - serde: 1.x → 1.0.225
  - serde_json: 1.x → 1.0.115
  - thiserror: 1.0.50 → 2.0.17
  - tracing: 0.1.40 → 0.1.41
- **swc-generator**: Migrate to pipeline architecture with YAML config (#6) (26cb9ba)
  - Introduced staged pipeline: Parser → Converter → CodeGen
  - Added YAML-based configuration system
  - Improved code generation with better type mapping
- **swc-generator**: Optimize code generation pipeline and file comparison logic (53d8411)
- **Tests**: Restructure test files and add test resources (2f59e7c)

### Fixed
- Automatically add missing ctxt fields in span objects (7e701af)

### Documentation
- Add comprehensive KDoc comments and fix documentation issues (#8) (faca1c0)
- Add comprehensive SwcNative usage guide and DSL documentation (#7) (7f0de90)

## [0.7.0] - 2025-11-XX

*Intermediate development release*

## [0.6.0] - 2025-10-XX

### Initial Release
- Initial stable release with full async support
- Complete SWC binding for JVM
- Support for parsing, transforming, printing, and minifying JavaScript/TypeScript
- Kotlin DSL for AST manipulation
- Cross-platform native library support (Linux, macOS, Windows)

---

[0.8.0]: https://github.com/yidafu/swc-binding/compare/v0.6.0...v0.8.0
[0.7.0]: https://github.com/yidafu/swc-binding/compare/v0.6.0...v0.7.0
[0.6.0]: https://github.com/yidafu/swc-binding/releases/tag/v0.6.0
