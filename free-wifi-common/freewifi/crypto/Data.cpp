#include "Data.h"

Data::Data()
    : _data()
{
}

DataPtr Data::create()
{
    return std::make_shared<Data>();
}

Data::Data(const ByteArr& arr)
    : _data(arr)
{
}

DataPtr Data::create(ByteArr& arr)
{
    return std::make_shared<Data>(arr);
}

Data::Data(const Byte* ptr, const Size size)
    : _data(size)
{
    if(size > 0)
    {
        std::copy(ptr, ptr+size, &_data[0]);
    }
}

DataPtr Data::create(const Byte* ptr, const Size size)
{
    return std::make_shared<Data>(ptr, size);
}

Data::Data(const std::string& str)
    : _data(str.size())
{
    if(str.size() > 0)
    {
        std::copy(&str[0], &str[0]+str.size(), &_data[0]);
    }
}

DataPtr Data::create(const std::string& str)
{
    return std::make_shared<Data>(str);
}

const std::string Data::toString() const
{
    std::string res;
    res.resize(_data.size());
    if(_data.size() > 0)
    {
        std::copy(&_data[0], &_data[0]+_data.size(), &res[0]);
    }
    return res;
}
