package com.wangdao.mall;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    ConfigService configService;

    @Test
    void contextLoads() {

    }

}
