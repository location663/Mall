package com.wangdao.mall.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "mall.aliyun.oss")
@Component
public class OssComponent {
    @Value("bucket")
    String bucket;
    @Value("endPoint")
    String endPoint;
}
