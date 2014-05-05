#ifndef BASE64_H
#define BASE64_H
#include "Data.h"

class Base64
{
public:
    /**
     * @brief Makes base64 encode
     * @param data binary data to encode
     * @return nullptr if error occured or encoded data
     */
    static DataPtr encode(const DataPtr& data);

    /**
     * @brief Makes base64 decode
     * @param data binary data to encode
     * @return nullptr if error occured or encoded data
     */
    static DataPtr decode(const DataPtr& data);
};

#endif // BASE64_H
