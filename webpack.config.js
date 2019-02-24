var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: './src/ui/index.js',
    output: {
        path: __dirname + '/target/classes/public/',
        filename: 'bundle.js',
    },
    module: {
        rules: [
            {
                test: /\.(tsx?)|(js)$/,
                loader: 'babel-loader',
                exclude: /node_module/,
            }
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("production")
            }
        }),
        new HtmlWebpackPlugin({
            template: './src/ui/index.html',
            inject: 'body',
        }),
        new CopyWebpackPlugin([{
            from: './src/ui/style.css',
            to: './'
        }]),
    ]
};
