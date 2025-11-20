#!/usr/bin/env node
/**
 * 预生成 minify 测试资源文件
 * 使用 @swc/core 生成 minify 结果并保存到 resources 目录
 */

const swc = require('@swc/core');
const fs = require('fs');
const path = require('path');

// 测试用例配置
const testCases = [
    {
        name: 'simple-javascript',
        code: `function add(a, b) {
    return a + b;
}`,
        options: {
            compress: {},
            mangle: true
        }
    },
    {
        name: 'javascript-with-variables',
        code: `const x = 42;
const y = 100;
const result = x + y;`,
        options: {
            compress: {},
            mangle: true
        }
    },
    {
        name: 'typescript-code',
        code: `interface User {
    name: string;
    age: number;
}
const user: User = { name: "John", age: 30 };`,
        options: {
            compress: {},
            mangle: true
        },
        // TypeScript 需要先转换为 JavaScript
        transformFirst: true,
        transformOptions: {
            jsc: {
                parser: {
                    syntax: 'typescript',
                    tsx: false
                },
                target: 'es2020'
            }
        }
    },
    {
        name: 'compress-only',
        code: `function unused() {
    return 42;
}
function used() {
    return 100;
}
used();`,
        options: {
            compress: {},
            mangle: false
        }
    }
];

// 获取 resources 目录路径
const resourcesDir = path.join(__dirname, '..');
const minifyDir = path.join(resourcesDir, 'minify');

// 确保目录存在
if (!fs.existsSync(minifyDir)) {
    fs.mkdirSync(minifyDir, { recursive: true });
}

console.log('开始生成 minify 测试资源文件...\n');

testCases.forEach((testCase, index) => {
    try {
        console.log(`[${index + 1}/${testCases.length}] 处理: ${testCase.name}`);
        
        let codeToMinify = testCase.code;
        
        // 如果是 TypeScript，先转换为 JavaScript
        if (testCase.transformFirst) {
            const transformed = swc.transformSync(testCase.code, testCase.transformOptions);
            codeToMinify = transformed.code;
        }
        
        // 使用 @swc/core 进行 minify
        const result = swc.minifySync(codeToMinify, testCase.options);
        
        // 保存结果到文件
        const outputPath = path.join(minifyDir, `${testCase.name}.js`);
        fs.writeFileSync(outputPath, result.code, 'utf-8');
        
        console.log(`  ✓ 已生成: ${outputPath}`);
    } catch (error) {
        console.error(`  ✗ 错误: ${testCase.name}`);
        console.error(`     ${error.message}`);
        process.exit(1);
    }
});

console.log('\n所有 minify 资源文件生成完成！');

