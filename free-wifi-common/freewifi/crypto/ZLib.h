#ifndef ZLIB_HEADER_H
#define ZLIB_HEADER_H
#include "Data.h"
class ZLib
{
public:
    /**
     * @brief Compresses given data using ZLib compression
     * @param source data to compress
     * @return nullptr if failed or new data if everything was success
     */
    static DataPtr compress(const DataPtr &source);

    /**
     * @brief Decompresses given data using ZLib compression
     * @param source data to decompress
     * @return nullptr if failed or decopressed data if everything was success
     */
    static DataPtr decompress(const DataPtr& source);
};

#endif // ZLIB_HEADER_H
