#!/usr/bin/env zx
import ghpages from 'gh-pages';

await $`rm -fr ./dist`
await $`mkdir -p ./dist`
await $`cp -r swc-binding/build/dokka/html dist/docs`

await $`cp docs/theme.css dist/theme.css`
await $`cp docs/prism.js dist/prism.js`

const enMd = await $`npx markdown docs/how-to-implement-swc-jvm-binding.md --stylesheet theme.css --flavor gfm --highlight`

function format(str) {
    return str
        .replaceAll('<body>', '<body><div class="markdown-body">')
        .replaceAll('</body>', '<script src="prism.js" ></script></div></body>')
        .replaceAll('<p>&lt;details&gt;', '<details>')
        .replaceAll('&lt;summary&gt;', '<summary>')
        .replaceAll('&lt;/summary&gt;', '</summary>')
        .replaceAll('&lt;/details&gt;\n\n</p>', '</details>')
        .replaceAll('&lt;code&gt;', '<code>')
        .replaceAll('&lt;/code&gt;', '</code>')
        .replaceAll('<br>', '')
}
await fs.writeFile(
    './dist/how-to-implement-swc-jvm-binding.html',
    format(enMd.stdout.toString())
)
const cnMd = await $`npx markdown docs/how-to-implement-swc-jvm-binding.zh-CN.md  --stylesheet theme.css --flavor gfm --highlight`

await fs.writeFile(
    './dist/how-to-implement-swc-jvm-binding.zh-CN.html',
    format(cnMd.stdout.toString())
)

ghpages.publish('./dist', {
    repo: "https://github.com/yidafu/swc-binding.git",
}, function(err) {
    if (err) console.log(err)
    else console.log(chalk.green("updated github pages"))
});
