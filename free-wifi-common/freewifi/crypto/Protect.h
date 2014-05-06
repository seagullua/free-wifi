#ifndef PROTECT_H
#define PROTECT_H
#include "Data.h"

/**
 * @brief Class which stores basic protection functions
 * for enctyption:
 * Uses algorythm:
 * in CBC mode with AES128 and PKCS7 padding
 *
 * in IV mode you should specify random IV 128 bits
 * in normal move IV will be generated automatically and prepended to the cipher text
 *
 */
class Protect
{
public:

    /**
     * @brief encrypts plain text in CBC mode with AES128 and PKCS7 padding.
     * the random IV 128bits will be generated and prepended to the cipher text
     * @param plain_text text to encrypt
     * @param key 128bits (16 bytes)
     * @return IV | cipher_text or nullptr if encode failed
     */
    static DataPtr encode(const DataPtr& plain_text, const DataPtr& key);

    /**
     * @brief encrypts plain text in CBC mode with AES128 and PKCS7 padding
     * @param plain_text text to encrypt
     * @param key 128bits (16 bytes)
     * @param iv 128bits (16 bytes)
     * @return cipher_text or nullptr if encode failed
     */
    static DataPtr encodeIV(const DataPtr& plain_text,
                            const DataPtr& key,
                            const DataPtr& iv);

    /**
     * @brief decrypts plain text in CBC mode with AES128 and PKCS7 padding
     * Please not the IV 128 bits should prepend cypher text
     * @param cipher_text IV | cipher_text
     * @param key  128bits (16 bytes)
     * @return plain_text or nullptr if decode failed
     */
    static DataPtr decode(const DataPtr& cipher_text, const DataPtr& key);

    /**
     * @brief decrypts plain text in CBC mode with AES128 and PKCS7 padding
     * @param cipher_text
     * @param key  128bits (16 bytes)
     * @param iv 128bits (16 bytes) the same as during ecryption
     * @return plain_text or nullptr if decode failed
     */
    static DataPtr decodeIV(const DataPtr& cipher_text,
                            const DataPtr& key,
                            const DataPtr& iv);


    static const Data::Size IV_SIZE = 16;
    static const Data::Size KEY_SIZE = 16;
    static const Data::Size BLOCK_SIZE = 16;
};

#endif // PROTECT_H
