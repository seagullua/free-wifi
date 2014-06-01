#ifndef HASH_H
#define HASH_H
#include "Data.h"
class Hash
{
public:
    /**
     * @brief applies sha256 hash
     * @param d
     * @return hashed value or nullptr if failed
     */
    static DataPtr apply(const DataPtr& d);

    /**
     * @brief applies sha1 hash
     * @param d
     * @return hashed value or nullptr if failed
     */
    static DataPtr applyShort(const DataPtr& d);
};

#endif // HASH_H
