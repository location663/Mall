/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 16:34
 **/
package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminAdministerController {

    @Autowired
    AdminService adminService;

    /**
     * 系统管理的角色管理admin/admin/list
     * 只是实现了简单的查询显示 如果发现其他功能还需要再改
     * @param username
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("/admin/list")
    public BaseReqVo selectAllAdmin(String username,Integer page,Integer limit,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = adminService.selectAllAdmin(username,page,limit,sort,order);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 系统管理的角色管理admin/role/options
     * 只是实现了简单的查询显示 如果发现其他功能还需要再改
     * @return
     */
    @RequestMapping("/role/options")
    public BaseReqVo selectAllAdminRole(){
        List<Map> list = adminService.selectAllAdminRole();
        BaseReqVo<List<Map>> baseReqVo = new BaseReqVo<>(list, "成功", 0);
        return baseReqVo;
    }

    /**
     * 请求是一个adminDO()只包含4个  返回是一个adminDO()是完整的需要在Service层再进行一次新的查询
     *
     * request:
     * {
     * 	"username": "woxianghuo",
     * 	"password": "123456789",
     * 	"avatar": "http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg",
     * 	"roleIds": [1, 2]
     * }
     * response:
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 124,
     * 		"username": "woxianghuo",
     * 		"password": "$2a$10$hJRQZnnYxGMyGtBZCRzKtea9HVwwSIUxHR6KfhVcqSAFb9tKxiQ1i",
     * 		"avatar": "http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg",
     * 		"addTime": "2019-11-16 01:53:05",
     * 		"updateTime": "2019-11-16 01:53:05",
     * 		"roleIds": [1, 2]
     *        },
     * 	"errmsg": "成功"
     * }
     * @param adminDO
     * @return  这里只能返回一个 list 因为用名字和密码查询 只能查询出一个 List
     *
     * 应该有个username 重名校验  √
     * insert时候应该有个 setaddTime() √
     *
     * 11/16 16：35 初步完成
     */
    @RequestMapping("/admin/create")
    public BaseReqVo createNewAdmin(AdminDO adminDO){
        List<AdminDO> adminDOAfterCreate = adminService.createNewAdmin(adminDO);
        if (adminDOAfterCreate != null){
            BaseReqVo<List<AdminDO>> baseReqVo = new BaseReqVo<>(adminDOAfterCreate, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<List<AdminDO>> baseReqVo = new BaseReqVo<>(adminDOAfterCreate, "管理员已存在", 602);
        return baseReqVo;
    }


    /**
     * requset:
     * {
     * 	"id": 124,
     * 	"username": "woxiangwoxiang",
     * 	"avatar": "http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg",
     * 	"roleIds": [1, 2],
     * 	"password": "123789"
     * }
     * response:
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 124,
     * 		"username": "woxiangwoxiang",
     * 		"password": "$2a$10$PUNh3Ai43BTJU7FEfTgqguUDmzBgDKX1sn6CSrifLMpJam0G0DsjG",
     * 		"avatar": "http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg",
     * 		"updateTime": "2019-11-16 03:45:34",
     * 		"roleIds": [1, 2]
     *        },
     * 	"errmsg": "成功"
     * }
     * @param adminDO
     * @return
     */
    @RequestMapping("/admin/update")
    public BaseReqVo updateAdmin(AdminDO adminDO){
        AdminDO adminDOAfterUpdate = adminService.updateAdmin(adminDO);
        if (adminDOAfterUpdate != null){
            BaseReqVo baseReqVo = new BaseReqVo<>(adminDOAfterUpdate,"成功",0);
            return baseReqVo;
        }
        BaseReqVo baseReqVo = new BaseReqVo<>(adminDOAfterUpdate, "失败", 602);
        return baseReqVo;
    }

}
