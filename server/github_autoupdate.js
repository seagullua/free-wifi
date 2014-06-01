var http = require('http'),
    url = require('url'),
    exec = require('child_process').exec,
    qs = require('querystring');
 
var host = "127.0.0.1",
    port = "1235",
    thisServerUrl = "http://" + host + ":" + port,
    secret_key = "GitHubHello";

/* http://127.0.0.1:1235/update?secret_key=GitHubHello 
 */
var siteMap = {
    www_refs_heads_master : { updateCmd : "git pull origin master"}
  }

process.on('uncaughtException', function (err) {
  console.log('[exception] ' + err);
});

http.createServer(function (req, res) {
  var data = "";

  req.on("data", function(chunk) {
    data += chunk;
  });

  req.on("end", function() {
    var parsedUrl = url.parse(req.url, true);
    var siteId = parsedUrl.query['site'];
    var params = {};

    if(parsedUrl.query['secret_key'] != secret_key) {
      console.log("[warning] Unauthorized request " + req.url);
      res.writeHead(401, "Not Authorized", {'Content-Type': 'text/html'});
      res.end('401 - Not Authorized');
      return;
    }

    if(data.length > 0) {

      params = JSON.parse(qs.parse(data).payload);
      console.log("[ref] " + params.ref);
    }

    switch(parsedUrl.pathname) {
      case '/':
        res.writeHead(501, "Not implemented", {'Content-Type': 'text/html'});
        res.end('501 - Not implemented');
        break;

      case '/update':
        var site = parsedUrl.query['site'];
        if(params.ref) { site = site + "_" + params.ref.replace(/\//g,"_") };
        console.log("[processing] " + req.url + " for site " + site);
        
		  exec("bash "+__dirname+"/reload.sh", {cwd: __dirname}, function (error, stdout, stderr) {
			console.log(stdout);
			console.log(stderr);
			res.writeHead(200, "OK", {'Content-Type': 'text/html'});
			res.end("OK");
		  });
        
        break;

      default:
        res.writeHead(404, "Not found", {'Content-Type': 'text/html'});
        res.end("404 - Not found");
        console.log("[404] " + req.method + " to " + req.url);
    };

  });

}).listen(port, host);

console.log('Server running at ' + thisServerUrl );
