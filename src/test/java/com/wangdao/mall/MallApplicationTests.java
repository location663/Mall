package com.wangdao.mall;

import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.service.admin.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class MallApplicationTests {

    @Autowired
    AdminService adminService;

}

