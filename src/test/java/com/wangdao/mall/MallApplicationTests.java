package com.wangdao.mall;

import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    AdminService adminService;

    @Test
    void contextLoads() {
    }

    /**
     * 测试url： http://192.168.2.100:8081/admin/admin/list?page=1&limit=20&sort=add_time&order=desc
     * 测试方法：AdminAdministerController 的 selectAllAdmin()方法
     * 测试结果：成功
     * 前端测试：未知
     */
    @Test
    public void mytest01(){
        String username = "admin123";
        Integer page = 1;
        Integer limit = 20;
        String sort = "add_time";
        String order = "desc";
        Map map = adminService.selectAllAdmin(username,page,limit,sort, order);
        System.out.println(map);
    }

}
