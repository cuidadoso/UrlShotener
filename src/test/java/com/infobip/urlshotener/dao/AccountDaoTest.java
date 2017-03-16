package com.infobip.urlshotener.dao;

import com.infobip.urlshotener.model.Account;
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
public class AccountDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(AccountDaoTest.class);
    private AccountDao accountDao;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        accountDao = new AccountDao();
        account1 = new Account("acc1", "password1");
        account2 = new Account("acc2", "password2");
    }

    @After
    public void tearDown() throws Exception {
        accountDao.getDataProvider().deleteRows();
    }

    @Test
    public void testCreateGetAll() throws Exception {

        List<Account> accounts = accountDao.getAll();
        assertEquals(0, accounts.size());

        accountDao.create(account1);
        accountDao.create(account2);
        accounts = accountDao.getAll();

        assertEquals(2, accounts.size());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testCreateGetAll"));
    }

    @Test
    public void testGet() throws Exception {
        accountDao.create(account1);
        Account account = accountDao.get("acc1");
        Account accountNull = accountDao.get("acc2");

        assertNotNull(account);
        assertEquals(account1.getId(), account.getId());
        assertEquals(account1.getPassword(), account.getPassword());
        assertNull(accountNull);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testGet"));
    }

    @Test
    public void testIsExists() throws Exception {
        accountDao.create(account1);
        boolean isExists = accountDao.isExists("acc1");
        boolean isNotExists = accountDao.isExists("acc2");

        assertTrue(isExists);
        assertFalse(isNotExists);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testIsExists"));
    }

    @Test
    public void testIsAuthorized() throws Exception {
        accountDao.create(account1);

        boolean isAuthorized = accountDao.isAuthorized("acc1", "password1");
        boolean isPasswordWrong = accountDao.isAuthorized("acc1", "password1111");
        boolean isNotAuthorized = accountDao.isAuthorized("acc2", "password2");

        assertTrue(isAuthorized);
        assertFalse(isPasswordWrong);
        assertFalse(isNotAuthorized);

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testIsAuthorized"));
    }

}