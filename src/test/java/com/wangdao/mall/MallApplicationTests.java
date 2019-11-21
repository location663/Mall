package com.wangdao.mall;

import com.wangdao.mall.component.AliyunComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    AliyunComponent aliyunComponent;


    @Test
    public void myTest() {
//        Calendar ca = Calendar.getInstance();
//        ca.setTime(new Date());
//        System.out.println("ca初始化：" + ca.getTime());
//        ca.add(Calendar.DATE, 1);
//        System.out.println("增加天数以后的日期：" + ca.getTime());

    }
}



