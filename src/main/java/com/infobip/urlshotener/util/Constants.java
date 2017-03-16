package com.infobip.urlshotener.util;

/**
 * Created by apyreev on 13-Mar-17.
 */
public class Constants {

    public static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String CREATE_TABLE_ACCOUNTS =
            "CREATE TABLE IF NOT EXISTS accounts (id TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL);";
    public static final String CREATE_TABLE_URLS =
            "CREATE TABLE IF NOT EXISTS urls (url TEXT PRIMARY KEY NOT NULL, redirect_type INT DEFAULT 302, " +
            "short_url TEXT UNIQUE NOT NULL, counter INT DEFAULT 0, account_id TEXT NOT NULL, " +
            "FOREIGN KEY (account_id) REFERENCES accounts(id));";
    public static final String DELETE_ACCOUNTS = "DELETE FROM accounts;";
    public static final String DELETE_URLS = "DELETE FROM urls;";
    public static final String GET_ACCOUNTS = "SELECT * FROM accounts";
    public static final String GET_ACCOUNT = "SELECT * FROM accounts WHERE id='%s'";
    public static final String INSERT_ACCOUNT = "INSERT INTO accounts (id, password) VALUES ('%s', '%s')";
    public static final String GET_URLS = "SELECT * FROM urls";
    public static final String GET_URL = "SELECT * FROM urls WHERE url='%s'";
    public static final String GET_BY_SHORT_URL = "SELECT * FROM urls WHERE short_url='%s'";
    public static final String GET_URL_BY_ACCOUNT = "SELECT * FROM urls WHERE account_id='%s'";
    public static final String INSERT_URL = "INSERT INTO urls (url, short_url, redirect_type, account_id) VALUES ('%s', '%s', %d, '%s')";
    public static final String UPDATE_URL_COUNTER = "UPDATE urls SET counter=%d WHERE url='%s'";
    public static final String UPDATE_URL_REDIRECT_TYPE = "UPDATE urls SET redirect_type=%d WHERE url='%s'";

    public static final String ACCOUNT_OPENED = "Your account is opened.";
    public static final String WRONG_ACCOUNT_ID = "Parameter 'id' is wrong or doesn't exist.";
    public static final String ACCOUNT_EXISTS = "Account with that ID already exists.";

    public static final String TEST_DONE = "Test '%s' successful done.";

    /**
     * /help page
     */
    public static final String HELP_PAGE = "<style>\n" +
            "table,th,td\n" +
            "{\n" +
            "\tborder:1px solid black;\n" +
            "\tborder-collapse:collapse;\n" +
            "}\n" +
            "th,td\n" +
            "{\n" +
            "\tpadding:5px;\n" +
            "}\n" +
            "</style>\n" +
            "<h3>URL Shortener:</h3>\n" +
            "URL Shortener consists of configuration and user part.\n" +
            "<br/>Configuration serves for:\n" +
            "<ul>\n" +
            "\t<li>Opening of accounts</li>\n" +
            "\t<li>Registration of URLs in the 'Shortener' service</li>\n" +
            "\t<li>Displaying stats</li>\n" +
            "</ul>\n" +
            "To access Configuration part use REST call with JSON parameters.\n" +
            "<br/>User part serves for redirection.\n" +
            "<br/>\n" +
            "<b>Running</b>\n" +
            "<ol>\n" +
            "\t<li>Start a Tomcat web server with default relative url /help. Example: http://localhost:8080/help</li>\n" +
            "\t<li>Deploy the service war file to the web server.</li>\n" +
            "</ol>\n" +
            "<b>Usage</b>\n" +
            "<br/>Use a client-side application to access following functionalities. All request and response types are application/json.\n" +
            "<ol>\n" +
            "<li>\n" +
            "\t<u><i>Creating account</i></u>\n" +
            "\t<br/>HTTP Method: <b>POST</b>\n" +
            "\t<br/>URI: <b>/account</b>\n" +
            "\t<br/>Request Body: <b>id</b>\n" +
            "\t<ul>\n" +
            "\t\t<li>Example: {\"id\":\"myAccount1\"}</li>\n" +
            "\t</ul>\n" +
            "\tResponse Body: <b>success</b>, <b>description</b>, <b>password</b>\n" +
            "\t<ul>\n" +
            "\t\t<li>Example: {\"success\":\"true\",\"description\":\"Your account is opened.\",\"password\":\"xC345Fc0\"}\n" +
            "\t</ul>\n" +
            "</li>\n" +
            "<li>\n" +
            "\t<u><i>Registering URL</i></u>\n" +
            "\t<br/>HTTP Method: <b>POST</b>\n" +
            "\t<br/>URI: <b>/register</b>\n" +
            "\t<br/>Request Header: <b>Authorization</b>\n" +
            "\t<br/>Request Body: <b>url</b>, <b>redirectType</b>\n" +
            "\t<ul>\n" +
            "\t\t<li>Example: {\"url\":\"http://stackoverflow.com/questions/1567929/website-safe-data-access-architecture-question?rq=1\",\"redirectType\":\"301\"}</li>\n" +
            "\t</ul>\n" +
            "\tResponse Body: <b>shortUrl</b>\n" +
            "\t<ul>\n" +
            "\t\t<li>Example: {\"shortUrl\":\"http://localhost:8080/xYswlE\"}\n" +
            "\t</ul>\n" +
            "</li>\n" +
            "<li>\n" +
            "\t<u><i>Fetching statistics</i></u>\n" +
            "\t<br/>HTTP Method: <b>GET</b>\n" +
            "\t<br/>URI: <b>/statistic/{accountId}</b>\n" +
            "\t<br/>Request Header: <b>Authorization</b>\n" +
            "\tResponse Body: <b>key:value</b>\n" +
            "\t<ul>\n" +
            "\t\t<li>Example: {\"http://myweb.com/somelongurl/somedirectory/\":\"10\",\"http://myweb.com/somelongurl1/somedirectory2/\":\"4\",\"http://myweb.com/somelongurl3/somedirectory4/\":\"91\"}\n" +
            "\t</ul>\n" +
            "</li>\n" +
            "<li>\n" +
            "\t<u><i>Redirecting</i></u>\n" +
            "\t<ul>\n" +
            "\t\tEntering shortUrl provided in \"Registering URL\" redirection occurs to registered url and number of calls for that url is incremented by 1.\n" +
            "\t</ul>\n" +
            "</li>\n" +
            "</ol>";
}
