#!/usr/bin/env node
/**
 * 预生成 print 测试资源文件
 * 使用 @swc/core 生成 AST JSON 和 print 结果的代码并保存到 resources 目录
 * 
 * 流程：
 * 1. 使用 @swc/core parseSync 解析源代码生成 AST JSON，保存为 .json 文件
 * 2. 使用 @swc/core printSync 将 AST JSON 打印成代码，保存为 .js 文件
 */

const swc = require('@swc/core');
const fs = require('fs');
const path = require('path');

// 测试用例配置
const testCases = [
    // JavaScript
    {
        name: 'javascript-function',
        code: `function add(a, b) {
    return a + b;
}
const result = add(1, 2);`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {}
    },
    {
        name: 'javascript-es6-features',
        code: `const arrow = (x, y) => x + y;
const spread = [...[1, 2, 3]];
const {a, b} = {a: 1, b: 2};`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {}
    },
    {
        name: 'javascript-with-minify',
        code: `function add(a, b) {
    return a + b;
}
const result = add(1, 2);`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {
            minify: true
        }
    },
    {
        name: 'javascript-with-target',
        code: `const arrow = (x) => x * 2;`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {
            jsc: {
                target: 'es2020'
            }
        }
    },
    // TypeScript
    {
        name: 'typescript-interface',
        code: `interface User {
    name: string;
    age: number;
}
const user: User = { name: "John", age: 30 };`,
        parseOptions: {
            syntax: 'typescript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            tsx: false,
            decorators: false
        },
        printOptions: {}
    },
    {
        name: 'typescript-generics',
        code: `interface Container<T> {
    value: T;
}
const container: Container<number> = { value: 42 };`,
        parseOptions: {
            syntax: 'typescript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            tsx: false,
            decorators: false
        },
        printOptions: {}
    },
    // TSX
    {
        name: 'tsx-component',
        code: `interface Props {
    name: string;
}
const Component: React.FC<Props> = ({ name }) => {
    return <div>Hello, {name}!</div>;
};`,
        parseOptions: {
            syntax: 'typescript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            tsx: true,
            decorators: false
        },
        printOptions: {}
    },
    // JSX
    {
        name: 'jsx-component',
        code: `function Component({ name }) {
    return <div>Hello, {name}!</div>;
}`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false,
            jsx: true
        },
        printOptions: {}
    },
    // Module with simple import
    {
        name: 'javascript-simple-import',
        code: `import { bar } from './local.js';

console.log('main.js', bar);`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {}
    },
    // Module with imports (complex scenario)
    {
        name: 'module-with-imports',
        code: `import { bar } from './local.js';

console.log('main.js', bar);`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {}
    },
    // Compiled module code with bundler output
    {
        name: 'javascript-compiled-module',
        code: `const global_Li9sb2NhbC5qcw = (function (exports = {}) {
  const inline_Li9sb2NhbC0yLmpz = global_Li9sb2NhbC0yLmpz;
  const bar = inline_Li9sb2NhbC0yLmpz.bar;
  console.log('local.js', bar);
  const foo = "foo";
  exports.bar = bar;
  exports.foo = foo;
  return exports;
})();
const global_Li9sb2NhbC0yLmpz = (function (exports = {}) {
  const bar = "bar";
  exports.bar = bar;
  return exports;
})();
const inline_Li9sb2NhbC5qcw = global_Li9sb2NhbC5qcw;
const bar = inline_Li9sb2NhbC5qcw.bar;
console.log('main.js', bar);`,
        parseOptions: {
            syntax: 'ecmascript',
            target: 'es5',
            isModule: true,
            comments: false,
            script: false
        },
        printOptions: {}
    }
];

// 获取 resources 目录路径
const resourcesDir = path.join(__dirname, '..');
const printDir = path.join(resourcesDir, 'print');

// 确保目录存在
if (!fs.existsSync(printDir)) {
    fs.mkdirSync(printDir, { recursive: true });
}

console.log('开始生成 print 测试资源文件...\n');

testCases.forEach((testCase, index) => {
    try {
        console.log(`[${index + 1}/${testCases.length}] 处理: ${testCase.name}`);
        
        // 1. 使用 @swc/core 进行 parse，生成 AST JSON
        const ast = swc.parseSync(testCase.code, testCase.parseOptions);
        
        // 2. 保存 AST JSON 到文件
        const astJson = JSON.stringify(ast, null, 2);
        const astJsonPath = path.join(printDir, `${testCase.name}.json`);
        fs.writeFileSync(astJsonPath, astJson, 'utf-8');
        console.log(`  ✓ 已生成 AST JSON: ${astJsonPath}`);
        
        // 3. 使用 @swc/core 进行 print，将 AST JSON 转换为代码
        const result = swc.printSync(ast, testCase.printOptions);
        
        // 4. 保存打印后的代码到文件
        const codePath = path.join(printDir, `${testCase.name}.js`);
        fs.writeFileSync(codePath, result.code, 'utf-8');
        
        console.log(`  ✓ 已生成代码: ${codePath}`);
    } catch (error) {
        console.error(`  ✗ 错误: ${testCase.name}`);
        console.error(`     ${error.message}`);
        if (error.stack) {
            console.error(`     ${error.stack}`);
        }
        process.exit(1);
    }
});

console.log('\n所有 print 资源文件生成完成！');

