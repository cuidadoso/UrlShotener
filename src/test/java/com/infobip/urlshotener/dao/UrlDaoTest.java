package com.infobip.urlshotener.dao;

import com.infobip.urlshotener.model.Url;
import com.infobip.urlshotener.util.Constants;
import com.infobip.urlshotener.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by apyreev on 15-Mar-17.
 */
public class UrlDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(AccountDaoTest.class);
    private UrlDao urlDao;
    private Url url1;
    private Url url2;
    private Url url3;

    @Before
    public void setUp() throws Exception {
        urlDao = new UrlDao();
        url1 = new Url("url1.com", "u1.c", 301, "acc1", 0);
        url2 = new Url("url2.com", "u2.c", 302, "acc1", 1);
        url3 = new Url("url3.com", "u3.c", 300, "acc2", 2);
    }

    @After
    public void tearDown() throws Exception {
        urlDao.getDataProvider().deleteRows();
    }

    @Test
    public void testCreateGetAll() throws Exception {

        List<Url> urls = urlDao.getAll();
        assertEquals(0, urls.size());

        urlDao.create(url1);
        urlDao.create(url2);
        urlDao.create(url3);

        urls = urlDao.getAll();

        assertEquals(3, urls.size());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testCreateGetAll"));
    }

    @Test
    public void testGet() throws Exception {
        urlDao.create(url1);

        Url url = urlDao.get("url1.com");
        Url urlNull = urlDao.get("url2.com");

        assertNotNull(url);
        assertNull(urlNull);
        assertEquals(url1.getUrl(), url.getUrl());
        assertEquals(url1.getShortUrl(), url.getShortUrl());
        assertEquals(url1.getRedirectType(), url.getRedirectType());
        assertEquals(url1.getCounter(), url.getCounter());
        assertEquals(url1.getAccountId(), url.getAccountId());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGet"));
    }

    @Test
    public void testGetByShortUrl() throws Exception {
        urlDao.create(url1);

        Url url = urlDao.getByShortUrl("u1.c");
        Url urlNull = urlDao.getByShortUrl("u2.c");

        assertNotNull(url);
        assertNull(urlNull);
        assertEquals(url1.getUrl(), url.getUrl());
        assertEquals(url1.getShortUrl(), url.getShortUrl());
        assertEquals(url1.getRedirectType(), url.getRedirectType());
        assertEquals(url1.getCounter(), url.getCounter());
        assertEquals(url1.getAccountId(), url.getAccountId());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetByShortUrl"));
    }

    @Test
    public void testGetByAccountId() throws Exception {
        urlDao.create(url1);
        urlDao.create(url2);
        urlDao.create(url3);

        List<Url> urlsAcc1 = urlDao.getByAccountId("acc1");
        List<Url> urlsAcc2 = urlDao.getByAccountId("acc2");
        List<Url> urlsNull = urlDao.getByAccountId("acc3");

        assertFalse(urlsAcc1.isEmpty());
        assertEquals(2, urlsAcc1.size());
        assertFalse(urlsAcc2.isEmpty());
        assertEquals(1, urlsAcc2.size());
        assertTrue(urlsNull.isEmpty());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGetByAccountId"));
    }

    @Test
    public void testIncrementCounter() throws Exception {
        urlDao.create(url1);
        int count = urlDao.get("url1.com").getCounter();
        assertEquals(0, count);
        urlDao.incrementCounter("url1.com");
        count = urlDao.get("url1.com").getCounter();
        assertEquals(1, count);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testIncrementCounter"));
    }

    @Test
    public void testUpdateRedirectType() throws Exception {
        urlDao.create(url1);
        int redirectType = urlDao.get("url1.com").getRedirectType();
        assertEquals(301, redirectType);

        url1.setRedirectType(302);
        urlDao.updateRedirectType(url1);
        redirectType = urlDao.get("url1.com").getRedirectType();
        assertEquals(302, redirectType);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testUpdateRedirectType"));
    }

    @Test
    public void testIsExists() throws Exception {
        urlDao.create(url1);

        boolean isExists = urlDao.isExists("url1.com");
        boolean isNotExists = urlDao.isExists("url2.com");

        assertTrue(isExists);
        assertFalse(isNotExists);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testIsExists"));
    }

    @Test
    public void testIsShortUrlExists() throws Exception {

        urlDao.create(url1);

        boolean isExists = urlDao.isShortUrlExists("u1.c");
        boolean isNotExists = urlDao.isShortUrlExists("u2.c");

        assertTrue(isExists);
        assertFalse(isNotExists);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testIsShortUrlExists"));
    }
}