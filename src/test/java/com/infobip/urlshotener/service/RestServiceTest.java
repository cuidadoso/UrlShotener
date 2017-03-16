package com.infobip.urlshotener.service;

import com.infobip.urlshotener.util.Constants;
import com.infobip.urlshotener.util.Helper;
import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.*;


import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by apyreev on 15-Mar-17.
 *
 * Tests not working cos this project use JAX-RS 1 and not compatible with JAX-RS 2.
 */
@Ignore
public class RestServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(RestServiceTest.class);
    private static final int SERVER_PORT = 8080;
    private static final String SERVER_HOST = "localhost";
    private static UndertowJaxrsServer server;
    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private RestService restService = new RestService();

    @Before
    public void setUp() throws Exception {
        Builder builder = Undertow.builder().addHttpListener(SERVER_PORT, SERVER_HOST);
        server = new UndertowJaxrsServer().start(builder);
        server.deploy(restService);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testCreateAccount() throws Exception {

        JSONObject json = new JSONObject();
        json.put("id", "acc1");
        String response = post("http://localhost:8080/account", json.toString(), "");

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testCreateAccount"));
    }

    @Test
    public void testRegisterURL() throws Exception {

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testRegisterURL"));
    }

    @Test
    public void testCreateTrackInJSON() throws Exception {

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testCreateTrackInJSON"));
    }

    @Test
    public void testRedirect() throws Exception {

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testRedirect"));
    }

    @Test
    public void testHelp() throws Exception {

        String response = get("http://localhost:8080/help");

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testHelp"));
    }

    String get(final String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String post(final String url, final String json, final String header) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", JSON.toString())
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}