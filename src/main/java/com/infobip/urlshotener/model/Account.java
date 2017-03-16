package com.infobip.urlshotener.model;

import lombok.*;

/**
 * Created by apyreev on 13-Mar-17.
 */

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class Account {
    @NonNull private String id;
    @NonNull private String password;
}
