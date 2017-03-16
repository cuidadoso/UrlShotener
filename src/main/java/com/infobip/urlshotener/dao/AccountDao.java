package com.infobip.urlshotener.dao;

import com.infobip.urlshotener.data.DataProvider;
import com.infobip.urlshotener.model.Account;
import com.infobip.urlshotener.util.Constants;
import com.infobip.urlshotener.util.EntityType;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by apyreev on 13-Mar-17.
 */
public class AccountDao {

    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);
    @Getter
    private DataProvider dataProvider = DataProvider.getInstance();

    public List<Account> getAll() {
        return dataProvider.execute(EntityType.ACCOUNT, Constants.GET_ACCOUNTS)
                .values()
                .stream()
                .map(obj -> (Account) obj)
                .collect(Collectors.toList());
    }

    public Account get(final String id) {
        return (Account) dataProvider.execute(EntityType.ACCOUNT ,String.format(Constants.GET_ACCOUNT, id)).get(id);
    }

    public boolean isExists(final String id) {
        return get(id) != null;
    }

    public void create(final Account account) {
        if (dataProvider.update(String.format(Constants.INSERT_ACCOUNT, account.getId(), account.getPassword())) > 0) {
            LOGGER.trace("Account added to database.");
        } else {
            LOGGER.trace("Account not added to database.");
        }
    }

    public boolean isAuthorized(final String id, final String password) {
        Account account = get(id);
        return account != null && password.equals(account.getPassword());
    }
}
