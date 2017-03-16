package com.infobip.urlshotener.util;

import com.infobip.urlshotener.model.Url;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by apyreev on 13-Mar-17.
 */
public class Helper {

    private static final Logger LOGGER = LogManager.getLogger(Helper.class);

    private static SecureRandom random = new SecureRandom();
    private static String header;

    /**
     * http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
     */
    public static String getRandomString(Integer len) {
        StringBuilder sb = new StringBuilder(len);
        IntStream.range(0, len)
                .forEach(i -> sb.append(Constants.SYMBOLS.charAt(random.nextInt(Constants.SYMBOLS.length()))));
        return sb.toString();
    }

    public static String decodeHeader(String header) {
        Helper.header = header;
        return new String(Base64.decodeBase64(header.substring(6).getBytes()));
    }

    public static JSONObject getAccountResponse(boolean success, String description, String password) {
        JSONObject json = new JSONObject();
        try
        {
            json.put("success", success);
            json.put("description", description);
            json.put("password", password);
        } catch(JSONException e)
        {
            LOGGER.error(message("Cant create JSON for account response. ERROR: %s",
                    e.getMessage()));
        }
        return json;
    }

    public static JSONObject getURLResponse(String shortUrl) {
        JSONObject json = new JSONObject();
        try
        {
            json.put("shortUrl", shortUrl);
        } catch(JSONException e)
        {
            LOGGER.error(message("Cant create JSON for URL response. ERROR: %s",
                    e.getMessage()));
        }
        return json;
    }

    public static JSONObject getStatisticResponse(List<Url> urls) {
        JSONObject json = new JSONObject();
        urls.forEach(url ->
        {
            try
            {
                json.put(url.getUrl(), url.getCounter());
            } catch(JSONException e)
            {
                LOGGER.error(message("Cant create JSON for statistic response. ERROR: %s",
                        e.getMessage()));
            }
        });
        return json;
    }

    public static String message(final String template, final String ...args) {
        return String.format(template, args);
    }
}
