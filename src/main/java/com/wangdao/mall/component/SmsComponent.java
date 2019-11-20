package com.wangdao.mall.component;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "mall.aliyun.sms")
@Component
public class SmsComponent {
    @Value("sign-name")
    String signName;
    @Value("template-code")
    String templateCode;
    @Value("region-id")
    String regionId;
}
