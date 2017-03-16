package com.infobip.urlshotener.dao;

import com.infobip.urlshotener.data.DataProvider;
import com.infobip.urlshotener.model.Url;
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
public class UrlDao {

    private static final Logger LOGGER = LogManager.getLogger(UrlDao.class);
    @Getter
    private DataProvider dataProvider = DataProvider.getInstance();

    public List<Url> getAll() {
        return dataProvider.execute(EntityType.URL, Constants.GET_URLS)
                .values()
                .stream()
                .map(obj -> (Url) obj)
                .collect(Collectors.toList());
    }

    public Url get(final String url) {
        return (Url) dataProvider.execute(EntityType.URL, String.format(Constants.GET_URL, url)).get(url);
    }

    public Url getByShortUrl(final String shortUrl) {
        List<Url> urls = dataProvider.execute(EntityType.URL, String.format(Constants.GET_BY_SHORT_URL, shortUrl))
                .values()
                .stream()
                .map(obj -> (Url) obj)
                .collect(Collectors.toList());
        for (Url url : urls) {
            if (shortUrl.equals(url.getShortUrl())) {
                return url;
            }
        }
        return null;
    }

    public List<Url> getByAccountId(final String accountId) {
        return dataProvider.execute(EntityType.URL, String.format(Constants.GET_URL_BY_ACCOUNT, accountId))
                .values()
                .stream()
                .map(obj -> (Url) obj)
                .collect(Collectors.toList());
    }

    public void create(final Url url) {
        if (dataProvider.update(String.format(Constants.INSERT_URL, url.getUrl(), url.getShortUrl(), url.getRedirectType(), url.getAccountId())) > 0) {
            LOGGER.trace("URL added to database.");
        } else {
            LOGGER.trace("URL not added to database.");
        }
    }

    public void incrementCounter(final String url) {
        int count = get(url).getCounter() + 1;
        if (dataProvider.update(String.format(Constants.UPDATE_URL_COUNTER, count, url)) > 0) {
            LOGGER.trace("URL counter updated.");
        } else {
            LOGGER.trace("URL counter not updated.");
        }
    }

    public void updateRedirectType(Url url) {
        if (dataProvider.update(String.format(Constants.UPDATE_URL_REDIRECT_TYPE, url.getRedirectType(), url.getUrl())) > 0) {
            LOGGER.trace("URL counter updated.");
        } else {
            LOGGER.trace("URL counter not updated.");
        }
    }

    public boolean isExists(final String url) {
        return get(url) != null;
    }

    public boolean isShortUrlExists(final String shortUrl) {
        return getByShortUrl(shortUrl) != null;
    }
}
