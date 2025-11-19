package dev.yidafu.swc.generator.result

/**
 * 错误代码枚举
 * 定义所有可能的错误类型
 */
enum class ErrorCode(val code: String, val description: String) {
    // 解析错误
    PARSE_ERROR("PARSE_ERROR", "解析错误"),
    INVALID_INPUT("INVALID_INPUT", "无效输入"),

    // 类型转换错误
    TYPE_CONVERSION_ERROR("TYPE_CONVERSION_ERROR", "类型转换错误"),
    TYPE_CONVERSION_FAILED("TYPE_CONVERSION_FAILED", "类型转换失败"),
    TYPE_RESOLUTION_ERROR("TYPE_RESOLUTION_ERROR", "类型解析错误"),
    PROPERTY_RESOLUTION_ERROR("PROPERTY_RESOLUTION_ERROR", "属性解析错误"),
    UNSUPPORTED_TYPE("UNSUPPORTED_TYPE", "不支持的类型"),

    // 代码生成错误
    CODE_GENERATION_ERROR("CODE_GENERATION_ERROR", "代码生成错误"),
    FILE_WRITE_ERROR("FILE_WRITE_ERROR", "文件写入错误"),

    // 配置错误
    CONFIG_ERROR("CONFIG_ERROR", "配置错误"),
    CONFIG_LOAD_ERROR("CONFIG_LOAD_ERROR", "配置加载错误"),
    CONFIG_YAML_PARSE_ERROR("CONFIG_YAML_PARSE_ERROR", "YAML配置解析错误"),
    CONFIG_INVALID_PATH("CONFIG_INVALID_PATH", "配置路径无效"),

    // 管道错误
    PIPELINE_ERROR("PIPELINE_ERROR", "管道执行错误"),
    PIPELINE_STAGE_ERROR("PIPELINE_STAGE_ERROR", "管道阶段错误"),
    STAGE_EXECUTION_ERROR("STAGE_EXECUTION_ERROR", "阶段执行错误"),

    // 转换错误
    TRANSFORM_ERROR("TRANSFORM_ERROR", "转换错误"),
    VALIDATION_ERROR("VALIDATION_ERROR", "验证错误"),

    // 通用错误
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),
    UNKNOWN("UNKNOWN", "未知错误"),
    INTERNAL_ERROR("INTERNAL_ERROR", "内部错误"),

    // 循环导入错误
    CIRCULAR_IMPORT_ERROR("CIRCULAR_IMPORT_ERROR", "循环导入错误"),

    // 文件IO错误
    FILE_IO_ERROR("FILE_IO_ERROR", "文件IO错误"),

    // 序列化错误
    SERIALIZATION_ERROR("SERIALIZATION_ERROR", "序列化错误"),

    // 跳过接口
    SKIPPED_INTERFACE("SKIPPED_INTERFACE", "跳过的接口")
}
