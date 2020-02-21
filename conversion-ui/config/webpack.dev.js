const merge = require('webpack-merge');
const common = require('./webpack.common.js');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const DefinePlugin = require('webpack/lib/DefinePlugin');
var path = require('path');

module.exports = merge(common, {
    mode: 'development',
    module: {
        rules:  [
            {
                test: /\.scss$/,
                use: [
                    {
                        loader: 'style-loader'
                    },
                    {
                        loader: 'css-loader'
                    },
                    {
                        loader: 'sass-loader'
                    }
                ]
            },
            {
                test: /\. (jpg|png|gif|eot|svg|ttf|woff|woff2)$/,
                loader: 'file-loader'
            }
        ],
    },
    plugins:  [
        // new HtmlWebpackPlugin({
        //     inject: true,
        //     template: path.resolve(__dirname,'../sam-service/src/resources/templates/'),
        // }),
        new DefinePlugin( {
            NODE_ENV: '"local"'
        })
    ],
    devServer: {
        port: 8080,
        host: 'localhost',
        historyApiFallback: true,
        watchOptions: {
            aggregateTimeout: 300,
            poll: 1000
        },
        contentBase: path.resolve(__dirname, './build'),
        open: true
    }
});
