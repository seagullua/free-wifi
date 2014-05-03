#include "Test.h"
#include <jansson.h>
#include <c5/aes.h>
Test::Test()
{
    CryptoPP::AESEncryption a;
    json_error_t error;
    json_t *root = json_loads("aaa", 0, &error);
}
