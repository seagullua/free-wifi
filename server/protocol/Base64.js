/**
 * Created by Andriy on 01.06.14.
 */
var Data = require('./Data');

/**
 * Performs Base64 encoding
 * @param data {Buffer}
 * @returns {string|String}
 */
exports.encode = function(data)
{
    var bin_data = Data.toBinaryData(data);
    return bin_data.toString('base64');
}

/**
 * Performs Base64 decoding
 * @param data {string|String} data to decode
 * @returns {Buffer}
 */
exports.decode = function(data)
{
    var str_data = Data.toStringData(data);
    return new Buffer(str_data, 'base64');
}