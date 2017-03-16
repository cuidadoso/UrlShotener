package com.infobip.urlshotener.model;

import lombok.*;

/**
 * Created by apyreev on 13-Mar-17.
 */

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class Url {
    @NonNull private String url;
    @NonNull private String shortUrl;
    @NonNull private Integer redirectType = 302;
    @NonNull private String accountId;
    private int counter = 0;
}
