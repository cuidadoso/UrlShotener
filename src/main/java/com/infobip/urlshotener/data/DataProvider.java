package com.infobip.urlshotener.data;

import com.infobip.urlshotener.model.Account;
import com.infobip.urlshotener.model.Url;
import com.infobip.urlshotener.util.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.infobip.urlshotener.util.Constants;

/**
 * Created by apyreev on 13-Mar-17.
 */


public class DataProvider {

    private static final Logger LOGGER = LogManager.getLogger(DataProvider.class);

    private static class LazyHolder {
        private static final DataProvider INSTANCE = new DataProvider();
    }

    public static DataProvider getInstance() {
        return LazyHolder.INSTANCE;
    }

    public DataProvider() {
        LOGGER.trace("Create tables in database...");
        update(Constants.CREATE_TABLE_ACCOUNTS);
        LOGGER.trace("Accounts table is created.");
        update(Constants.CREATE_TABLE_URLS);
        LOGGER.trace("URLs table is created.");
    }

    public void deleteRows() {
        LOGGER.trace("Delete rows...");
        update(Constants.DELETE_ACCOUNTS);
        LOGGER.trace("Accounts are deleted.");
        update(Constants.DELETE_URLS);
        LOGGER.trace("URLs are deleted.");
    }

    public int update(final String query) {
        int rows = 0;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:core.db");
             Statement stmt = con.createStatement())
        {
            rows = stmt.executeUpdate(query);
        } catch (Exception e) {
            LOGGER.error(String.format("Something is wrong while database updating with query: %s. Error message: %s.",
                    query, e.getMessage()));
        }
        return rows;
    }

    public Map<String, Object> execute(final EntityType entity, final String query) {

        Map<String, Object> result = new HashMap<>();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:core.db");
             Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                switch (entity) {
                    case ACCOUNT:
                        Account account = new Account(rs.getString("id"), rs.getString("password"));
                        result.put(rs.getString("id"), account);
                        break;
                    case URL:
                        Url url = new Url(rs.getString("url"), rs.getString("short_url"),
                                Integer.parseInt(rs.getString("redirect_type")), rs.getString("account_id"),
                                Integer.parseInt(rs.getString("counter")));
                        result.put(rs.getString("url"), url);
                        break;
                }

            }
        } catch (Exception e) {
            LOGGER.error(String.format("Something is wrong while database query (%s) executing. Error message: %s.",
                    query, e.getMessage()));
        }
        return result;
    }
}
