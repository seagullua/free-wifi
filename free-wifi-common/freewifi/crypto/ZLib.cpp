#include "ZLib.h"
#include <zlib/zlib.h>

DataPtr ZLib::compress(const DataPtr& source)
{
    Data::Size input_size = source->getSize();
    const Data::ByteArr& input_raw = source->getRawData();

    Data::Size compressed_size = compressBound(input_size);

    DataPtr output = Data::create();
    Data::ByteArr& output_raw = output->getRawData();

    if(input_size > 0)
    {
        output_raw.resize(compressed_size);
        int success = ::compress(&output_raw[0], &compressed_size,
                &input_raw[0], input_size);

        if(success != Z_OK)
        {
            output = nullptr;
        }
        else
        {
            output_raw.resize(compressed_size);
        }
    }

    return output;
}

DataPtr ZLib::decompress(const DataPtr& source)
{
    //We are not storing the size of data and first try do decompress it
    //to the buffer which size if COMPRESS_DATA_SIZE*BASE_COMPRESSION_RATIO,
    //if this buffer will be small we will try COMPRESS_DATA_SIZE*BASE_COMPRESSION_RATIO*2, COMPRESS_DATA_SIZE*BASE_COMPRESSION_RATIO*4,...
    static const int BASE_COMPRESSION_RATIO = 2;

    DataPtr output = Data::create();
    Data::ByteArr& output_raw = output->getRawData();

    Data::Size input_size = source->getSize();
    const Data::ByteArr& input_raw = source->getRawData();

    if(input_size > 0)
    {
        Data::Size output_size = 0;
        Data::Size output_buffer_size = input_size*BASE_COMPRESSION_RATIO;
        bool finished = false;

        while(!finished)
        {
            output_size = output_buffer_size;
            output_raw.resize(output_size);

            int success = uncompress (&output_raw[0], &output_size,
                    &input_raw[0], input_size);

            if(success == Z_OK)
            {
                output_raw.resize(output_size);
                finished = true;
            }
            else if(success == Z_BUF_ERROR)
            {
                //Enlarge buffer
                output_buffer_size *= 2;
            }
            else
            {
                //Error happened
                output = nullptr;
                finished = true;
            }
        }

    }


    return output;
}

