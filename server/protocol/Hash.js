/**
 * Created by Andriy on 01.06.14.
 */
var crypto = require('crypto');
var Data = require('./Data');

/**
 * Applies sha256 hash function
 * @param data
 * @returns {Buffer}
 */
exports.apply = function(data)
{
    var bin_data = Data.toBinaryData(data);
    var hash = crypto.createHash('sha256');
    hash.update(bin_data);

    return hash.digest();
}

/**
 * Applies sha1 hash function
 * @param data
 * @returns {Buffer}
 */
exports.applyShort = function(data)
{
    var bin_data = Data.toBinaryData(data);
    var hash = crypto.createHash('sha1');
    hash.update(bin_data);

    return hash.digest();
}