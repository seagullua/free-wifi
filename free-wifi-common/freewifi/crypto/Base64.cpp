#include "Base64.h"
#include <tomcrypt.h>

DataPtr Base64::encode(const DataPtr& data)
{
    const Data::ByteArr& input_data = data->getRawData();

    Data::Size inputSize = data->getSize();
    Data::Size outlen = inputSize + (inputSize / 3) + 16;

    DataPtr res = Data::create();
    if(inputSize > 0)
    {
        Data::ByteArr& raw = res->getRawData();

        raw.resize(outlen);


        int success = base64_encode(&input_data[0], inputSize, &raw[0], &outlen);

        if(success != CRYPT_OK)
            res = nullptr;
        else
            raw.resize(outlen);
    }
    return res;
}


DataPtr Base64::decode(const DataPtr& input)
{
    Data::Size input_size = input->getSize();
    const Data::ByteArr& input_raw = input->getRawData();

    DataPtr output = Data::create();

    if(input_size > 0)
    {
        Data::ByteArr& output_raw = output->getRawData();
        Data::Size output_size = input_size;

        output_raw.resize(output_size);

        int success = base64_decode(&input_raw[0], input_size, &output_raw[0], &output_size);

        if(success != CRYPT_OK)
            output = nullptr;
        else
            output_raw.resize(output_size);

    }
    return output;
}
