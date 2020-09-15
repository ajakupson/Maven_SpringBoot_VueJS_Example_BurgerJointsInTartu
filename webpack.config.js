const path = require('path');
module.exports = {
    entry: {
        main: './src/main/resources/static/front-end/vuejs-app/app.js',
    },
    output: {
        path: path.join(__dirname, './src/main/resources/static/front-end/vuejs-app/build/'),
        filename: '[name].bundle.js'
    },
    mode: 'development'
}