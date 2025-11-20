#!/usr/bin/env node
/**
 * 预生成 transform 测试资源文件
 * 使用 @swc/core 生成 transform 结果并保存到 resources 目录
 */

const swc = require('@swc/core');
const fs = require('fs');
const path = require('path');

// 测试用例配置
const testCases = [
    {
        name: 'es6-arrow-to-es5',
        code: 'const add = (a, b) => a + b;',
        options: {
            jsc: {
                parser: {
                    syntax: 'ecmascript',
                    target: 'es5',
                    isModule: false
                },
                target: 'es5',
                externalHelpers: false
            }
        }
    },
    {
        name: 'typescript-to-javascript',
        code: `interface User {
    name: string;
}
const user: User = { name: "John" };`,
        options: {
            jsc: {
                parser: {
                    syntax: 'typescript',
                    target: 'es2020',
                    isModule: false
                },
                target: 'es2020',
                externalHelpers: false
            }
        }
    },
    {
        name: 'async-await-es2015',
        code: 'const x = async () => await Promise.resolve(42);',
        options: {
            jsc: {
                parser: {
                    syntax: 'ecmascript',
                    target: 'es2015',
                    isModule: false
                },
                target: 'es2015',
                externalHelpers: false
            }
        }
    },
    {
        name: 'module-export-es2020',
        code: 'export const x = 42;',
        options: {
            jsc: {
                parser: {
                    syntax: 'ecmascript',
                    target: 'es2020',
                    isModule: true
                },
                target: 'es2020',
                externalHelpers: false
            }
        }
    }
];

// 获取 resources 目录路径
const resourcesDir = path.join(__dirname, '..');
const transformDir = path.join(resourcesDir, 'transform');

// 确保目录存在
if (!fs.existsSync(transformDir)) {
    fs.mkdirSync(transformDir, { recursive: true });
}

console.log('开始生成 transform 测试资源文件...\n');

testCases.forEach((testCase, index) => {
    try {
        console.log(`[${index + 1}/${testCases.length}] 处理: ${testCase.name}`);
        
        // 使用 @swc/core 进行 transform
        const result = swc.transformSync(testCase.code, testCase.options);
        
        // 保存结果到文件
        const outputPath = path.join(transformDir, `${testCase.name}.js`);
        fs.writeFileSync(outputPath, result.code, 'utf-8');
        
        console.log(`  ✓ 已生成: ${outputPath}`);
    } catch (error) {
        console.error(`  ✗ 错误: ${testCase.name}`);
        console.error(`     ${error.message}`);
        process.exit(1);
    }
});

console.log('\n所有 transform 资源文件生成完成！');

