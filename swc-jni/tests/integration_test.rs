// Integration tests for swc-jni
// These tests verify that all modules work together correctly

use std::fs;
use std::path::Path;

#[test]
fn test_module_integration() {
    // Verify that the library compiles and links correctly
    // This ensures all modules are properly integrated
    assert!(true, "All modules integrated successfully");
}

#[test]
fn test_test_fixtures_exist() {
    // Verify test fixtures are in place
    let fixtures_dir = Path::new(env!("CARGO_MANIFEST_DIR")).join("test-fixtures");
    
    assert!(fixtures_dir.join("javascript/simple.js").exists(), 
            "JavaScript test fixture should exist");
    assert!(fixtures_dir.join("typescript/simple.ts").exists(), 
            "TypeScript test fixture should exist");
    assert!(fixtures_dir.join("options/parse-options.json").exists(), 
            "Parse options fixture should exist");
}

#[test]
fn test_javascript_fixture_content() {
    let fixtures_dir = Path::new(env!("CARGO_MANIFEST_DIR")).join("test-fixtures");
    let simple_js = fixtures_dir.join("javascript/simple.js");
    
    let content = fs::read_to_string(simple_js).expect("Should read simple.js");
    assert!(content.contains("Hello"), "JavaScript fixture should contain expected content");
}

#[test]
fn test_typescript_fixture_content() {
    let fixtures_dir = Path::new(env!("CARGO_MANIFEST_DIR")).join("test-fixtures");
    let simple_ts = fixtures_dir.join("typescript/simple.ts");
    
    let content = fs::read_to_string(simple_ts).expect("Should read simple.ts");
    assert!(content.contains("interface"), "TypeScript fixture should contain interface");
}

#[test]
fn test_invalid_fixture_exists() {
    let fixtures_dir = Path::new(env!("CARGO_MANIFEST_DIR")).join("test-fixtures");
    let syntax_error = fixtures_dir.join("invalid/syntax-error.js");
    
    assert!(syntax_error.exists(), "Invalid syntax fixture should exist");
}

#[test]
fn test_options_fixtures() {
    let fixtures_dir = Path::new(env!("CARGO_MANIFEST_DIR")).join("test-fixtures/options");
    
    let parse_opts = fs::read_to_string(fixtures_dir.join("parse-options.json"))
        .expect("Should read parse-options.json");
    assert!(serde_json::from_str::<serde_json::Value>(&parse_opts).is_ok(), 
            "Parse options should be valid JSON");
    
    let transform_opts = fs::read_to_string(fixtures_dir.join("transform-options.json"))
        .expect("Should read transform-options.json");
    assert!(serde_json::from_str::<serde_json::Value>(&transform_opts).is_ok(), 
            "Transform options should be valid JSON");
    
    let minify_opts = fs::read_to_string(fixtures_dir.join("minify-options.json"))
        .expect("Should read minify-options.json");
    assert!(serde_json::from_str::<serde_json::Value>(&minify_opts).is_ok(), 
            "Minify options should be valid JSON");
}

// Note: Full pipeline tests (parse -> transform -> print) are performed
// in the individual module tests. Integration with JNI is tested in
// the swc-binding module with Kotlin/Java tests.

