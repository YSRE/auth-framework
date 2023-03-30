package com.shy.security.config;

import lombok.Data;
import com.shy.auth.impl.LoginUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Security相关配置
 * @author shy
 * @date 2023/3/28
 */
@Configuration
@ConfigurationProperties(prefix = "app.security")
@Data
public class SecurityProperties {

    /**
     * token加密秘钥
     */
    private String tokenSecret;

    @NestedConfigurationProperty
    private TimeoutConfig timeout = new TimeoutConfig();

    @NestedConfigurationProperty
    private Mock mock = new Mock();

    public Integer getAccessTokenTimeout(){
        return timeout.getAccessTokenTimeout();
    }

    public Integer getRefreshTokenTimeout(){
        return timeout.getRefreshTokenTimeout();
    }
    @Data
    public class TimeoutConfig{
        private Integer accessTokenTimeout = 60 * 60;
        private Integer refreshTokenTimeout = 60 * 60 * 48;
        private Integer captchaTimout = 60;
        private Integer smscodeTimout = 60;
    }

    @Data
    public class Mock {
        private boolean enable = false;
        private LoginUser user = new LoginUser();
    }
}
