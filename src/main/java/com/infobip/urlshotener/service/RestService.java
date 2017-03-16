package com.infobip.urlshotener.service;

import com.infobip.urlshotener.dao.AccountDao;
import com.infobip.urlshotener.dao.UrlDao;
import com.infobip.urlshotener.model.Account;
import com.infobip.urlshotener.model.Url;
import com.infobip.urlshotener.util.Constants;
import com.infobip.urlshotener.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Created by apyreev on 13-Mar-17.
 */
@Path("/")
@ApplicationPath("/")
public class RestService extends Application {

    private static final Logger LOGGER = LogManager.getLogger(Helper.class);

    private AccountDao accountDao = new AccountDao();
    private UrlDao urlDao = new UrlDao();
    private String accountId;
    private String password;

    @Override
    public Set<Class<?>> getClasses()
    {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(RestService.class);
        return classes;
    }

    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {
        boolean success = true;
        Integer status = 201;
        String description = Constants.ACCOUNT_OPENED;
        String id = account.getId().toLowerCase().trim();

        if ("".equals(id)) {
            success = false;
            status = 415;
            description = Constants.WRONG_ACCOUNT_ID;
        } else if (accountDao.isExists(id)) {
            success = false;
            status = 409;
            description = Constants.ACCOUNT_EXISTS;
        } else {
            account.setId(id);
            account.setPassword(Helper.getRandomString(8));
            accountDao.create(account);
        }

        JSONObject response = Helper.getAccountResponse(success, description, account.getPassword());
        return Response.status(status).entity(response).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerURL(@Context UriInfo uriInfo,
                                @HeaderParam("Authorization") JSONObject header,
                                Url url) {
        //String decoded = Helper.decodeHeader(header);
        if(header.length() != 1) {
            return Response.status(415).build();
        }
        parseAuthenticationInfo(header);

        if (!accountDao.isAuthorized(accountId, password))
            return Response.status(401).build();

        String urlKey = url.getUrl().trim();

        if ("".equals(urlKey))
            return Response.status(415).build();

        url.setUrl(urlKey);
        url.setAccountId(accountId);
        Integer redurectType = url.getRedirectType() != 301 ? 302 : url.getRedirectType();
        url.setRedirectType(redurectType);

        Url existing = urlDao.get(urlKey);

        if (existing == null) {
            String shortUrl = generateShortUrl(uriInfo);
            url.setShortUrl(shortUrl);
            while (urlDao.isShortUrlExists(shortUrl)) {
                shortUrl = generateShortUrl(uriInfo);
                url.setShortUrl(shortUrl);
            }

            urlDao.create(url);
            return Response.status(200).entity(Helper.getURLResponse(shortUrl)).build();
        }

        if (!Objects.equals(existing.getRedirectType(), redurectType))
            urlDao.updateRedirectType(url);

        return Response.status(201).entity(Helper.getURLResponse(existing.getShortUrl())).build();
    }

    private String generateShortUrl(final UriInfo uri) {
        return uri.getBaseUri() + Helper.getRandomString(6);
    }

    @GET
    @Path("/statistic/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTrackInJSON(@HeaderParam("Authorization") JSONObject header,
                                      @PathParam("accountId") String id) {
        //String decoded = Helper.decodeHeader(header);
        if(header.length() != 1) {
            return Response.status(401).entity("{}").build();
        }
        parseAuthenticationInfo(header);

        if (!accountDao.isAuthorized(accountId, password) || !accountId.equals(id))
            return Response.status(401).entity("{}").build();

        JSONObject response = Helper.getStatisticResponse(urlDao.getByAccountId(id));

        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/{shortUrl}")
    public Response redirect(@Context UriInfo uriInfo,
                             @PathParam("shortUrl") String shortUrl) {
        Url url = urlDao.getByShortUrl(uriInfo.getBaseUri() + shortUrl);
        if (url != null) {
            urlDao.incrementCounter(url.getUrl());
            return Response.seeOther(URI.create(url.getUrl())).status(url.getRedirectType()).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("/help")
    @Produces(MediaType.TEXT_HTML)
    public String help() {
        return Constants.HELP_PAGE;
    }

    private void parseAuthenticationInfo(final JSONObject header) {
        try
        {
            Iterator iterator = header.keys();
            String key = iterator.next().toString();
            accountId = key;
            password = header.get(key).toString();
        } catch(JSONException e)
        {
            LOGGER.error(Helper.message("Read JSON from header. ERROR: %s",
                    e.getMessage()));
        }
    }
}
