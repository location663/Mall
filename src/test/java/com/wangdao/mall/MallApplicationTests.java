package com.wangdao.mall;


import com.wangdao.mall.service.admin.AdminService;
import com.wangdao.mall.service.wx.WxGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;


@SpringBootTest
class MallApplicationTests {





    /**
     * 测试url： http://192.168.2.100:8081/admin/admin/list?page=1&limit=20&sort=add_time&order=desc
     * 测试方法：AdminAdministerController 的 selectAllAdmin()方法
     * 测试结果：成功
     * 前端测试：未知 更新 需要和 option()方法一起测试
     */
//    @Test
//    public void mytest01(){
//        String username = "admin123";
//        Integer page = 1;
//        Integer limit = 20;
//        String sort = "add_time";
//        String order = "desc";
//        Map map = adminService.selectAllAdmin(username,page,limit,sort, order);
//        System.out.println(map);
//    }

    /**
     *  测试url： http://192.168.2.100:8081/admin/role/options
     *  查询成功：AdminAministerContriller 的 selectAllAdminRole()方法
     *  测试结果：成功查询到mapList
     *  前端测试: 未知 更新 需要和 上门的list方法一起测试
     *  现在开始测试：测试结果 成功
     */
//    @Test
//    public void mytest02(){
//        List<Map> mapList = adminService.selectAllAdminRole();
//        System.out.println("看这里： " + mapList);
//    }

    /**
     * 数据库测试：成功
     * 信息获得： 应该新增密码以及用户名的校验 如果用户名相等则不应该insert
     * 返工  完成
     * 新增 添加第一次增加时候的时间
     */
//    @Test
//    public void mytest03(){
//        Integer[] integers = {1,2};
//        AdminDO adminDO = new AdminDO();
//        adminDO.setUsername("woxiang");
//        adminDO.setPassword("123456789");
//        adminDO.setAvatar("http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg");
//        adminDO.setRoleIds(integers);
//        List<AdminDO> newAdmin = adminService.createNewAdmin(adminDO);
//        if (newAdmin != null){
//            for (AdminDO aDo : newAdmin) {
//                System.out.println(aDo);
//            }
//        }else {
//            System.out.println("重名");
//        }
//    }

    /**
     * 测试结果：如果只是单纯的使用前端的东西进行 update 会把没有的数据给赋默认值 例如把 add_time 给null了
     *         需要再改
     */
//    @Test
//    public void mytest04(){
//        AdminDO adminDO = new AdminDO();
//        Integer[] integers = {1,2};
//        adminDO.setId(10);
//        adminDO.setUsername("testtest");
//        adminDO.setPassword("123798798");
//        adminDO.setAvatar("http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg");
//        adminDO.setRoleIds(integers);
//        AdminDO adminDOAfterUpdate = adminService.updateAdmin(adminDO);
//        if (adminDOAfterUpdate == null){
//            System.out.println("重名");
//        }else {
//            System.out.println(adminDOAfterUpdate);
//        }
//    }

    /**
     * 只是逻辑删除 在delected上置为true
     */
//    @Test
//    public void mytest05(){
//        AdminDO adminDO = new AdminDO();
//        adminDO.setId(8);
//        adminDO.setUsername("woxianghuo");
//        Integer[] integers = {1};
//        adminDO.setRoleIds(integers);
//        int i = adminService.deleteAdminByAdminDORecord(adminDO);
//        System.out.println(i);
//    }


//    @Test
//    public void mytest06(){
//        HashMap<String, Object> map = wxGoodsService.queryGoodsCount();
//        System.out.println(map);
//    }

}

