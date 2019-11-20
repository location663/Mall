package com.wangdao.mall.component;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mall.aliyun")
public class AliyunComponent {
    @Value("access-key-id")
    String accessKeyId;
    @Value("access-secret")
    String accessSecret;
    @Autowired
    OssComponent oss;
    @Autowired
    SmsComponent sms;

    public OSSClient getOssClient(){
        return new OSSClient(oss.getEndPoint(), accessKeyId, accessSecret);
    }

    public IAcsClient getIacsClient(){
        DefaultProfile profile = DefaultProfile.getProfile(sms.getRegionId(), accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
