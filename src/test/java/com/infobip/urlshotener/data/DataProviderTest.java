package com.infobip.urlshotener.data;

import com.infobip.urlshotener.model.Account;
import com.infobip.urlshotener.util.Constants;
import com.infobip.urlshotener.util.EntityType;
import com.infobip.urlshotener.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Alejandro on 14.03.2017.
 */
public class DataProviderTest
{
    private static final Logger LOGGER = LogManager.getLogger(DataProviderTest.class);
    private DataProvider dataProvider;

    @Before
    public void setUp() throws Exception
    {
        dataProvider = DataProvider.getInstance();
    }

    @After
    public void tearDown() throws Exception
    {
        dataProvider.deleteRows();
    }

    @Test
    public void testUpdateExecute() throws Exception
    {
        Account checkAccount1 = new Account("acc1", "password1");
        Account checkAccount2 = new Account("acc2", "password2");

        String sqlInsert1 = Helper.message(Constants.INSERT_ACCOUNT, "acc1", "password1");
        String sqlInsert2 = Helper.message(Constants.INSERT_ACCOUNT, "acc2", "password2");
        dataProvider.update(sqlInsert1);
        dataProvider.update(sqlInsert2);
        Map<String, Object> accounts = dataProvider.execute(EntityType.ACCOUNT, Constants.GET_ACCOUNTS);

        Account resultAccount1 = (Account) accounts.get("acc1");
        Account resultAccount2 = (Account) accounts.get("acc2");

        assertEquals(2, accounts.size());
        assertNotNull(resultAccount1);
        assertNotNull(resultAccount2);
        assertEquals(checkAccount1.getId(), resultAccount1.getId());
        assertEquals(checkAccount1.getPassword(), resultAccount1.getPassword());
        assertEquals(checkAccount2.getId(), resultAccount2.getId());
        assertEquals(checkAccount2.getPassword(), resultAccount2.getPassword());

        LOGGER.info(Helper.message(Constants.TEST_DONE, "testUpdateExecute"));
    }
}