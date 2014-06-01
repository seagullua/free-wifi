global.config = require('config-node');
global.config({env:'config.shared'});
global.config({env:'config.local'});

var path = require('path');

function getRouter (name) {
    return require(path.join(__dirname, "/routers/", name));
}

function getMiddleware(name) {
    return require(path.join(__dirname, "/middleware/", name));
}

(function () {

    //Load Exress modules
    var http =          require('http');
    var express =       require('express');


    //Start application
    var app = express();


    //Configure middleware
    app.configure(function () {
        app.use(express.logger('dev'));
        //app.use(express.bodyParser());


    });


    //NO ROUTERS BEFORE

    //Start all modules
    getRouter('wifiapi')(app);


    //NO MORE ROUTERS AFTER

    var server = http.createServer(app);
    server.listen(global.config.server.port);

})();
