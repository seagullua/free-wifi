#include "Test.h"
#include <jansson.h>
#include <c5/aes.h>
#include <curl/curl.h>
#include <iostream>
Test::Test()
{
}
void runTests()
{
    CryptoPP::AESEncryption a;
    json_error_t error;
    json_t *root = json_loads("aaa", 0, &error);

    CURL *curl;
    CURLcode res;

    curl = curl_easy_init();
    if(curl) {
        curl_easy_setopt(curl, CURLOPT_URL, "http://4enjoy.com");
        /* example.com is redirected, so we tell libcurl to follow redirection */
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);

        /* Perform the request, res will get the return code */
        res = curl_easy_perform(curl);
        /* Check for errors */
        if(res == CURLE_OK)
        {
            std::cout << res;
        }
        std::cout << res;
        /* always cleanup */
        curl_easy_cleanup(curl);
    }
}
