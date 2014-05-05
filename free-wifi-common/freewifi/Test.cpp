#include "Test.h"
#include <jansson.h>
#include <tomcrypt.h>
#include <curl/curl.h>
#include "freewifi/platform/Log.h"
#include "freewifi/crypto/Data.h"
#include "freewifi/crypto/Base64.h"
#include "freewifi/crypto/ZLib.h"
//#include <iostream>
Test::Test()
{
}
void testEncryption()
{
    unsigned char key[16], nonce[12], pt[32], ct[32],
            tag[16], tagcp[16];

    unsigned long taglen;
    int err;
    /* register cipher */
    register_cipher(&aes_desc);
    /* somehow fill key, nonce, pt */
    /* encrypt it */
    taglen = sizeof(tag);
    if ((err =
         ccm_memory(find_cipher("aes"),
                    key, 16, /* 128-bit key */
                    NULL, /* not prescheduled */
                    nonce, 12, /* 96-bit nonce */
                    NULL, 0, /* no header */
                    pt, 32, /* 32-byte plaintext */
                    ct, /* ciphertext */
                    tag, &taglen,
                    CCM_ENCRYPT)) != CRYPT_OK) {
        printf("ccm_memory error %s\n", error_to_string(err));
        return;
    }
    /* ct[0..31] and tag[0..15] now hold the output */
    /* decrypt it */
    taglen = sizeof(tagcp);
    if ((err =
         ccm_memory(find_cipher("aes"),
                    key, 16, /* 128-bit key */
                    NULL, /* not prescheduled */
                    nonce, 12, /* 96-bit nonce */
                    NULL, 0, /* no header */
                    ct, 32, /* 32-byte ciphertext */
                    pt, /* plaintext */
                    tagcp, &taglen,
                    CCM_DECRYPT)) != CRYPT_OK) {
        printf("ccm_memory error %s\n", error_to_string(err));
        return ;
    }
    /* now pt[0..31] should hold the original plaintext,
    tagcp[0..15] and tag[0..15] should have the same contents */
}
void testData()
{
    DataPtr dun = Data::create("hello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-worldhello-world");
    log(dun->toString());

    DataPtr d = ZLib::compress(dun);


    DataPtr base64 = Base64::encode(d);
    if(base64)
    {
        log(base64->toString());

        DataPtr dec = Base64::decode(base64);
        log(ZLib::decompress(dec)->toString());
        std::string dec_string = base64->toString();

        if(dec)
            log(dec_string);

        dec_string[2] = '*';

        DataPtr dec2 = Base64::decode(Data::create(dec_string));
        if(dec2)
            log("Dec ok");
        else
            log("Dec failed");

    }

}

void runTests()
{
    testData();
    log("Test: %d", 54);
    log("Test: %d", 53);

    testEncryption();
    json_error_t error;
    json_t *root = json_loads("aaa", 0, &error);

//    CURL *curl;
//    CURLcode res;

//    curl = curl_easy_init();
//    if(curl) {
//        curl_easy_setopt(curl, CURLOPT_URL, "http://4enjoy.com");
//        /* example.com is redirected, so we tell libcurl to follow redirection */
//        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);

//        /* Perform the request, res will get the return code */
//        res = curl_easy_perform(curl);
//        /* Check for errors */
//        if(res == CURLE_OK)
//        {
//            //std::cout << res;
//        }
//        //std::cout << res;
//        /* always cleanup */
//        curl_easy_cleanup(curl);
//    }
}
