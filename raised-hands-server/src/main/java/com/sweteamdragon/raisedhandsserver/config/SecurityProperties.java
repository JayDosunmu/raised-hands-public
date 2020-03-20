package com.sweteamdragon.raisedhandsserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "com.sweteamdragon.raised-hands")
public class SecurityProperties {
    private String secretKey;
    private long expirationTime;
    private String tokenPrefix;
    private String headerString;

//    public long getExpirationTimeLong() {
//        return Long.parseLong(this.expirationTime);
//    }
}
