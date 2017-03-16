package com.infobip.urlshotener.util;

import com.infobip.urlshotener.model.Url;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alejandro on 14.03.2017.
 */
public class HelperTest
{
    private static final Logger LOGGER = LogManager.getLogger(HelperTest.class);

    @Test
    public void testGetRandomString() throws Exception
    {
        String str = Helper.getRandomString(6);
        assertEquals(6, str.length());
        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetRandomString"));
    }

    @Test
    public void decodeHeader() throws Exception
    {
        String original = "Original string.";
        String decoded = Helper.decodeHeader(original);
        assertNotEquals(original, decoded);
        assertNotEquals(original.length(), decoded.length());
        LOGGER.info(Helper.message(Constants.TEST_DONE, "decodeHeader"));
    }

    @Test
    public void testGetAccountResponse() throws Exception
    {
        JSONObject checkJson = new JSONObject();
        checkJson.put("success", true);
        checkJson.put("description", "Same description.");
        checkJson.put("password", "VerySecurePassword");
        String description = "Same description.",
               password = "VerySecurePassword";
        JSONObject accountJson = Helper.getAccountResponse(true, description, password);
        assertEquals(checkJson.toString(), accountJson.toString());
        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetAccountResponse"));
    }

    @Test
    public void testGetURLResponse() throws Exception
    {
        JSONObject checkJson = new JSONObject();
        checkJson.put("shortUrl", "VeryShorUrl");
        String shortUrl = "VeryShorUrl";
        JSONObject urlResponse = Helper.getURLResponse(shortUrl);
        assertEquals(checkJson.toString(), urlResponse.toString());
        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetURLResponse"));
    }

    @Test
    public void testGetStatisticResponse() throws Exception
    {
        JSONObject checkJson = new JSONObject();
        checkJson.put("url1.com",0);
        checkJson.put("url2.com",1);
        checkJson.put("url3.com",2);

        Url url1 = new Url("url1.com", "u1.c", 301, "acc1", 0);
        Url url2 = new Url("url2.com", "u2.c", 302, "acc1", 1);
        Url url3 = new Url("url3.com", "u3.c", 302, "acc1", 2);

        List<Url> urls = new ArrayList<>();
        urls.add(url1);
        urls.add(url2);
        urls.add(url3);

        JSONObject urlResponse = Helper.getStatisticResponse(urls);
        assertEquals(checkJson.toString(), urlResponse.toString());
        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetStatisticResponse"));
    }

    @Test
    public void testMessage() throws Exception
    {
        String checkString1 = "String1 - string 1.";
        String checkString2 = "String1 - string 1. String2 - string 2.";
        String checkString3 = "String1 - string 1. String2 - string 2. String3 - string 3.";

        String template1 = "String1 - %s.";
        String template2 = "String1 - %s. String2 - %s.";
        String template3 = "String1 - %s. String2 - %s. String3 - %s.";

        String resultString1 = Helper.message(template1, "string 1");
        String resultString2 = Helper.message(template2, "string 1", "string 2");
        String resultString3 = Helper.message(template3, "string 1", "string 2", "string 3");

        assertEquals(resultString1, checkString1);
        assertEquals(resultString2, checkString2);
        assertEquals(resultString3, checkString3);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testMessage"));
    }
}