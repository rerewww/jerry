const path = require('path');

module.exports = {
    entry: [
        './src/entry.ts'
    ],
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'dist')
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                exclude: /node_modules/,
                use: {
                    loader: 'ts-loader',
                }
            }
        ]
    },
    resolve: {
        // require('./example')과 같이 빈 확장자를 아래 확장자로 import 해줌.
        extensions: ['.ts', '.js']
    },
    node: { fs: 'empty' }
};