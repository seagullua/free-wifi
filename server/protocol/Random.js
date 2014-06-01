/**
 * Created by Andriy on 01.06.14.
 */
var crypto = require('crypto');
/**
 * Generates random sequence of data for cryptographic purposes
 * @param length how many bytes should be generated
 * @return
 */
exports.generateRandomData = function(length)
{
    try {
        return crypto.randomBytes(length);
    } catch (ex) {
        console.log("Crypto random fallback");
        try {
            //Return pseudo random when needed
            return crypto.pseudoRandomBytes(length);
        } catch (ex) {
            console.error("Random generation FAILED");
        }
    }
    //For error case
    return undefined;
}