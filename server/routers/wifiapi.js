/**
 * Created by Andriy on 27.05.14.
 */
var Data = require('../protocol/Data');
var Base64 = require('../protocol/Base64');
var Hash = require('../protocol/Hash');
var Protect = require('../protocol/Protect');

function index(req, res)
{
    var bin = Base64.encode(Data.toBinaryData('aaa'));
    console.log(bin);
    console.log("SHA 256: " + Base64.encode(Hash.apply(Data.toBinaryData("abc"))));
    console.log(Data.toStringData(Base64.decode('||'+bin+"aaa")));


    var plain_text = "The rooster crows at midnight!";
    var key = "A16ByteKey......";

    var iv = "IV6ByteKey......";

    var tt = Protect.encodeIV(plain_text, key, iv);

    console.log(tt);
    console.log("Encryption: " + Base64.encode(tt));
    res.send('OK');
}

/**
 * Configures routes for this module
 * @param app
 */
function setup(app)
{
    app.get('/', index);
}

module.exports = setup;