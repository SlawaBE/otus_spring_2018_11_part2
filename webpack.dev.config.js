const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    mode: 'development',
    entry: './src/ui/index.js',
    devtool: 'inline-source-map',
    output: {
        path: path.resolve(__dirname),
        filename: 'bundle.js',
        libraryTarget: 'umd'
    },

    devServer: {
        contentBase: path.resolve(__dirname) + '/src/ui',
        compress: true,
        port: 9000,
        host: 'localhost',
        before: (app) => {
            app.get('/api/books', (req, res) => res.send(
                [{ "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] }, { "id": "5c666ea32fcc6d154c531e45", "name": "Властелин Колец", "summary": "Одно из самых известных произведений жанра фэнтези", "authors": ["Джон Толкин"], "genres": ["приключения", "фэнтези"] }]
            ));
            app.get('/api/book', (req, res) => res.send(
                { "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] }
            ));
            app.post('/api/book', (req, res) => res.send(
                { "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] }
            ));
            app.get('/api/comments', (req, res) => res.send(
                [{ "id": "5c666ea32fcc6d154c531e47", "userName": "test", "sendDate": "2019-02-15T07:47:47.930+0000", "text": "Главный вопрос жизни, Вселенной и всего такого?", "book": { "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] } }, { "id": "5c666ea32fcc6d154c531e48", "userName": "Deep Thought", "sendDate": "2019-02-15T07:47:47.930+0000", "text": "42", "book": { "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] } }]
            ));
            app.post('/api/comment', (req, res) => res.send(
                { "id": "5c666ea32fcc6d154c531e47", "userName": "test", "sendDate": "2019-02-15T07:47:47.930+0000", "text": "Главный вопрос жизни, Вселенной и всего такого?", "book": { "id": "5c666ea32fcc6d154c531e44", "name": "Автостопом по галактике", "summary": "Путеводитель для путешествующих по галактике автостопом", "authors": ["Дуглас Адамс"], "genres": ["фантастика"] } }
            ));
        }
    },

    module: {
        rules: [
            {
                test: /\.(tsx?)|(js)$/,
                loader: 'babel-loader',
                exclude: /(node_modules|bower_components|build)/,
            }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'src/ui/index.html'
        })
    ]
}
