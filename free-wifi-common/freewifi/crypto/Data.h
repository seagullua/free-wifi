#ifndef DATA_H
#define DATA_H
#include <memory>
#include <vector>
#include <string>

class Data;
typedef std::shared_ptr<Data> DataPtr;

/**
 * @brief Represents a piece of binary data
 */
class Data
{
public:
    typedef unsigned char Byte;
    typedef unsigned long Size;
    typedef std::vector<Byte> ByteArr;


    /**
     * @brief Returns the raw data
     * @return
     */
    ByteArr& getRawData()
    {
        return _data;
    }

    /**
     * @brief Returns the raw data
     * @return
     */
    const ByteArr& getRawData() const
    {
        return _data;
    }

    /**
     * @brief Converts underlying data to string.
     * You are responsible to check whether binary data has unprinted characters
     * @return
     */
    const std::string toString() const;

    /**
     * @brief Returns the length of the data
     * @return
     */
    const Size getSize() const
    {
        return _data.size();
    }

    /**
     * @brief creates empty data
     * Usage of create function is recomended instead
     */
    Data();
    /**
     * @brief creates empty data
     */
    static DataPtr create();

    /**
     * @brief Constucts the data from given byte array
     */
    Data(const ByteArr&);
    /**
     * @brief Constucts the data from given byte array
     */
    static DataPtr create(ByteArr&);

    /**
     * @brief Constucts the data from given byte array
     */
    Data(const Byte*ptr, const Size size);
    /**
     * @brief Constucts the data from given byte array
     */
    static DataPtr create(const Byte*ptr, const Size size);

    /**
     * @brief Constuct binary data from string
     */
    Data(const std::string& str);

    /**
     * @brief Constuct binary data from string
     */
    static DataPtr create(const std::string& );
private:
    ByteArr _data;
};


#endif // DATA_H
