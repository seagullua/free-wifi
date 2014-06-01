/**
 * Created by Andriy on 01.06.14.
 */


/**
 * Converts string data to binary data
 * @param data_to_convert
 * @returns {*}
 */
exports.toBinaryData = function(data_to_convert)
{
    if(data_to_convert instanceof Buffer)
        return data_to_convert;
    else
        return new Buffer(data_to_convert, 'utf-8');
}

/**
 * Converts binary data to string data
 * @param data_to_conver
 * @returns {*}
 */
exports.toStringData = function(data_to_convert)
{
    if(data_to_convert instanceof Buffer)
        return data_to_convert.toString('utf-8');
    else
        return data_to_convert;
}