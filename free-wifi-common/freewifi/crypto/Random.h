#ifndef RANDOM_H
#define RANDOM_H
#include "Data.h"

class Random
{
public:
    /**
     * @brief Generates random sequence of data for cryptographic purposes
     * @param length how many bytes should be generated
     * @return
     */
    static DataPtr generateRandomData(const Data::Size length);
};

#endif // RANDOM_H
