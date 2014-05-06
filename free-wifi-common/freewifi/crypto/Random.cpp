#include "Random.h"
#include <tomcrypt.h>
#include <mutex>
#include <random>
#include <freewifi/platform/Log.h>

namespace RandomImpl
{
    static prng_state _random_state;
    static bool _inited = false;
    static std::mutex _random_mutex;

    DataPtr fallbackRandomGenerator(const Data::Size length)
    {
        std::random_device rd;
        DataPtr data = Data::create();
        Data::ByteArr& raw = data->getRawData();
        raw.resize(length);

        uint32_t number = 0;
        const Data::Size block_length = sizeof(uint32_t);

        for(Data::Size i=0; i<length; ++i)
        {
            number = rd();
            Data::Size copy_size = block_length;
            Data::Size till_end = length - i;
            if(till_end < copy_size)
                copy_size = till_end;

            const Data::Byte* dnum = reinterpret_cast<Data::Byte*>(&number);
            std::copy(dnum, dnum+copy_size, &raw[0] + i);
        }

        //log("Random data: " + data->toString());

        return data;
    }

    void initRandomState()
    {
        if(!_inited)
        {
            std::lock_guard<std::mutex> lock(_random_mutex);
            if(!_inited)
            {
                int err = CRYPT_OK;
                err = fortuna_start(&_random_state);
                if(err != CRYPT_OK)
                    return;

                DataPtr entropy = fallbackRandomGenerator(32);
                Data::ByteArr& raw = entropy->getRawData();

                err = fortuna_add_entropy(&raw[0],
                        entropy->getSize(), &_random_state);
                if(err != CRYPT_OK)
                    return;

                err = fortuna_ready(&_random_state);
                if(err != CRYPT_OK)
                    return;

                _inited = true;
            }
        }
    }
}
using namespace RandomImpl;


DataPtr Random::generateRandomData(const Data::Size length)
{
    if(length == 0)
        return Data::create();

    initRandomState();

    std::lock_guard<std::mutex> lock(_random_mutex);

    if(_inited)
    {
        //Generate using fortuna
        DataPtr result = Data::create();
        Data::ByteArr& raw = result->getRawData();

        raw.resize(length);
        fortuna_read(&raw[0], length, &_random_state);

        return result;
    }
    else
    {
        log("Fallback generation");
        return fallbackRandomGenerator(length);
    }
}
