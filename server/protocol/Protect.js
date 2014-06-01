/**
 * Created by Andriy on 01.06.14.
 */
var Random = require('./Random');
var Data = require('./Data');
var crypto = require('crypto');

var IV_SIZE = 16;
var KEY_SIZE = 16;
var BLOCK_SIZE = 16;

/**
 * encrypts plain text in CBC mode with AES128 and PKCS7 padding
 * @param plain_text text to encrypt
 * @param key 128bits (16 bytes)
 * @param iv 128bits (16 bytes)
 * @return {Buffer} cipher_text or nullptr if encode failed
 */
function do_encodeIV(plain_text, key, iv)
{
    iv = Data.toBinaryData(iv);
    key = Data.toBinaryData(key);
    plain_text = Data.toBinaryData(plain_text);

    var cipher = crypto.createCipheriv('AES-128-CBC', key, iv);
    cipher.setAutoPadding(true);
    var part1 = cipher.update(plain_text);
    var part2 = cipher.final();
    return Buffer.concat([part1, part2]);
}

/**
 * encrypts plain text in CBC mode with AES128 and PKCS7 padding.
 * the random IV 128bits will be generated and prepended to the cipher text
 * @param plain_text text to encrypt
 * @param key 128bits (16 bytes)
 * @return {Buffer} IV | cipher_text or nullptr if encode failed
 */
exports.encode = function(plain_text, key)
{
    var iv = Random.generateRandomData(IV_SIZE);
    return Buffer.concat([iv, do_encodeIV(plain_text, key, iv)]);
}

exports.encodeIV = do_encodeIV;
/**
 * decrypts plain text in CBC mode with AES128 and PKCS7 padding
 * Please not the IV 128 bits should prepend cypher text
 * @param cipher_text IV | cipher_text
 * @param key  128bits (16 bytes)
 * @return {Buffer} plain_text or nullptr if decode failed
 */
exports.decode = function(cipher_text, key)
{

}

/**
 * decrypts plain text in CBC mode with AES128 and PKCS7 padding
 * @param cipher_text
 * @param key  128bits (16 bytes)
 * @param iv 128bits (16 bytes) the same as during ecryption
 * @return plain_text or nullptr if decode failed
 */
exports.decodeIV = function(cipher_text, key, iv)
{

}
