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

DataPtr Data::create(const Data& data)
{
    return std::make_shared<Data>(data.getRawData());
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

DataPtr& operator+=(DataPtr& a, const Data b)
{
    Data::ByteArr& a_raw = a->getRawData();

    Data::Size a_size = a->getSize();
    Data::Size b_size = b.getSize();

    if(a_size == 0)
    {
        a_raw = b.getRawData();
    }
    else if(b_size != 0)
    {
        const Data::ByteArr& b_raw = b.getRawData();
        a_raw.resize(a_size + b_size);
        std::copy(&b_raw[0], &b_raw[0] + b_size, &a_raw[0]+a_size);
    }

    return a;

}
