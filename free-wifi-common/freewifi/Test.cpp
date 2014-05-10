#include "Test.h"
#include <jansson.h>
#include <tomcrypt.h>
#include <curl/curl.h>
#include "freewifi/platform/Log.h"
#include "freewifi/crypto/Data.h"
#include "freewifi/crypto/Base64.h"
#include "freewifi/crypto/ZLib.h"
#include "sqlite3.h"
#include "freewifi/crypto/Random.h"
#include "freewifi/crypto/Protect.h"
#include "freewifi/crypto/Hash.h"
#include "KeyStorage.h"
#include "WifiInfo.h"
//#include <iostream>
Test::Test()
{
}
void testSQL()
{
    sqlite3* db=NULL;
    int success = sqlite3_open("my.db", &db);

    if(success != SQLITE_OK)
    {
        log("DB failed2");
        return;
    }

    if(!db)
    {
        log("DB failed");
        return;
    }
    std::string key="abadgsfdg";
    sqlite3_key(db, key.c_str(), key.size());

    success = sqlite3_exec(db, "SELECT count(*) FROM sqlite_master;", NULL, NULL, NULL);
    if(success == SQLITE_OK)
    {
        log("DB success");
        success = sqlite3_exec(db, "CREATE TABLE IF NOT EXISTS mytbl(mid INTEGER PRIMARY KEY AUTOINCREMENT, val TEXT)", NULL, NULL, NULL);
        if(success == SQLITE_OK)
        {
            log("DB query");
            sqlite3_exec(db, "BEGIN IMMEDIATE TRANSACTION", NULL, NULL, NULL);
            for(int i=0; i<100; ++i)
            {

                success = sqlite3_exec(db, "INSERT INTO mytbl(val) VALUES ('aaa')", NULL, NULL, NULL);
                if(success != SQLITE_OK)
                {
                    log("insert err %d", i);
                }
//                else
//                {
//                    std::cout << i << std::endl;
//                    std::cout.flush();
//                }
            }
            sqlite3_exec(db, "COMMIT TRANSACTION", NULL, NULL, NULL);
        }
    }
    else
    {
        log("DB failed");
    }

    sqlite3_close(db);
}
void testHash()
{
    DataPtr data = Data::create("abc");

    log("SHA256: "+Base64::encode(Hash::apply(data))->toString());
}
void testKeyStorage()
{
    WifiInfo w("seagull", "10.10.10.10");
    log("WIFI id: " + w.getID());
    log("WIFI key" + Base64::encode(w.getDecodeKey())->toString());
    log("WIFI iv: "+ Base64::encode(w.getDecodeIV())->toString());
}

void testRandom()
{
    log("Rand start");
    DataPtr d = Random::generateRandomData(30);
    log(Base64::encode(d)->toString());

    log("Rand end");
}

void testEncryption()
{
    std::string plain_text = "The rooster crows at midnight!";
    std::string key = "A16ByteKey......";

    DataPtr iv = Data::create();
    Data::ByteArr& iv_raw = iv->getRawData();

    iv_raw.resize(Protect::IV_SIZE, 0);

    DataPtr enc = Protect::encodeIV(Data::create(plain_text),
                                    Data::create(key),
                                    iv);
    DataPtr enc2 = Protect::encode(Data::create(plain_text),
                                   Data::create(key));

    log("Enc: "+Base64::encode(enc)->toString());
    log("Enc2: "+Base64::encode(enc2)->toString());

    DataPtr dec = Protect::decodeIV(enc, Data::create(key), iv);
    DataPtr dec2 = Protect::decode(enc2, Data::create(key));
    log("Dec: "+dec->toString());
    log("Dec2: "+dec2->toString());


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
//    //testEncryption();
//    testRandom();
//    testSQL();
//    testKeyStorage();
//    testHash();
//    testEncryption();
//    testSQL();


//    testData();
//    log("Test: %d", 54);
//    log("Test: %d", 53);


//    json_error_t error;
//    json_t *root = json_loads("aaa", 0, &error);

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
