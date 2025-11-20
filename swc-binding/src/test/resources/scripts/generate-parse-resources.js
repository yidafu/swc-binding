#!/usr/bin/env node
/**
 * 预生成 parse 测试资源文件
 * 使用 @swc/core 生成 parse 结果的 AST JSON 并保存到 resources 目录
 */

const swc = require('@swc/core');
const fs = require('fs');
const path = require('path');

// 测试用例配置
const testCases = [
    {
        name: 'simple-javascript',
        code: 'const x = 42;',
        options: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        }
    },
    {
        name: 'javascript-function',
        code: `function add(a, b) {
    return a + b;
}`,
        options: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        }
    },
    {
        name: 'typescript-interface',
        code: `interface User {
    name: string;
    age: number;
}`,
        options: {
            syntax: 'typescript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            tsx: false,
            decorators: false
        }
    },
    {
        name: 'typescript-class',
        code: `class Person {
    name: string;
    constructor(name: string) {
        this.name = name;
    }
}`,
        options: {
            syntax: 'typescript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            tsx: false,
            decorators: false
        }
    },
    {
        name: 'javascript-with-comments',
        code: `// This is a comment
const x = 42; // inline comment`,
        options: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: true,
            script: false
        }
    },
    {
        name: 'empty-module',
        code: '',
        options: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        }
    }
];

// 获取 resources 目录路径
const resourcesDir = path.join(__dirname, '..');
const parseDir = path.join(resourcesDir, 'parse');

// 确保目录存在
if (!fs.existsSync(parseDir)) {
    fs.mkdirSync(parseDir, { recursive: true });
}

console.log('开始生成 parse 测试资源文件...\n');

testCases.forEach((testCase, index) => {
    try {
        console.log(`[${index + 1}/${testCases.length}] 处理: ${testCase.name}`);
        
        // 使用 @swc/core 进行 parse
        const result = swc.parseSync(testCase.code, testCase.options);
        
        // 将 AST 对象转换为 JSON 字符串
        const astJson = JSON.stringify(result, null, 2);
        
        // 保存结果到文件
        const outputPath = path.join(parseDir, `${testCase.name}.json`);
        fs.writeFileSync(outputPath, astJson, 'utf-8');
        
        console.log(`  ✓ 已生成: ${outputPath}`);
    } catch (error) {
        console.error(`  ✗ 错误: ${testCase.name}`);
        console.error(`     ${error.message}`);
        process.exit(1);
    }
});

console.log('\n所有 parse 资源文件生成完成！');

