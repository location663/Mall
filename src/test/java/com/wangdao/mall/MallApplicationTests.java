package com.wangdao.mall;

import com.wangdao.mall.component.AliyunComponent;
import com.wangdao.mall.service.util.encryptutil.Md5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    AliyunComponent aliyunComponent;

    @Test
    public void myTest(){
//        String multiMd5 = Md5Utils.getMultiMd5("123");
//        System.out.println(multiMd5);
    }
}



