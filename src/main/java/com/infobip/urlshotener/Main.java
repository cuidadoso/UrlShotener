package com.infobip.urlshotener;

import com.infobip.urlshotener.dao.AccountDao;
import com.infobip.urlshotener.dao.UrlDao;
import com.infobip.urlshotener.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by apyreev on 13-Mar-17.
 */
public class Main {

    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private static final Logger ACCOUNT_LOGGER = LogManager.getLogger(Account.class);

    private static AccountDao accountDao = new AccountDao();
    private static UrlDao urlDao = new UrlDao();

    public static void main(String[] args) {

        /*Account account = new Account();

        account.setId("Account1");
        account.setPassword("password");

        ACCOUNT_LOGGER.info(account.getId());
        ACCOUNT_LOGGER.info(account.toString());

        ROOT_LOGGER.info("Root logger: " + account.getId());

        if (ROOT_LOGGER.isDebugEnabled()) {
            ROOT_LOGGER.debug("RootLogger: In debug message");
            ACCOUNT_LOGGER.debug("AccountLogger in debug");
        }

        try {
            Account accountNull = null;
            //noinspection ResultOfMethodCallIgnored
            accountNull.getId();
        } catch (NullPointerException ex) {
            ACCOUNT_LOGGER.error("error message: " + ex.getMessage());
            ACCOUNT_LOGGER.fatal("fatal error message: " + ex.getMessage());
        }*/

        System.out.println(accountDao.getAll());
        System.out.println(urlDao.getAll());
    }
}
