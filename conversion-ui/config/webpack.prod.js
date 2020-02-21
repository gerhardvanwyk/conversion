const merge = require('webpack-merge');
const common = require('./webpack.common.js');
const DefinePlugin = require('webpack/lib/DefinePlugin');
const MinifyPlugin = require("babel-minify-webpack-plugin");
const LoaderOptionsPlugin = require("webpack").LoaderOptionsPlugin;
var path = require('path');

module.exports = merge(common, {
    mode: 'production',
    output: {
        path: path.resolve(__dirname, '../../conversion/src/main/resources/static/'),
        libraryTarget: "umd"
    },
    plugins:  [
        new DefinePlugin( {
            NODE_ENV: '"production"'
        }),
        new MinifyPlugin({}, {}),
        new LoaderOptionsPlugin({
            minimize: true
        })
    ],
});
