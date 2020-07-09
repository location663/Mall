/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 16:34
 **/
package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.service.admin.AdminService;
import com.wangdao.mall.service.util.StorageUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminAdministerController {

    @Autowired
    AdminService adminService;

    @Autowired
    StorageUtils storageUtils;


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
    @RequiresPermissions(value = {"admin:admin:list","admin:admin:update","damin:admin:read","admin:admin:delete","admin:admin:create"},logical = Logical.OR)
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
    @RequiresPermissions(value = {"admin:admin:list","admin:admin:update","damin:admin:read","admin:admin:delete","admin:admin:create"},logical = Logical.OR)
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
     * 	"avatar":   "http://192.168.2.100:8081/wx/storage/fetch/blq41nv0kdml5h1wmo91.jpg",
     * 	"roleIds":  [1, 2]
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
      *
      * 更新 11、16 21：33 新增用户名长度判断且只在controller层
     */
    @RequestMapping("/admin/create")
    @RequiresPermissions(value = {"admin:admin:create"})
    public BaseReqVo createNewAdmin(@RequestBody AdminDO adminDO){
        if (adminDO.getUsername().length() <= 3){
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "管理员名称不符合规定", 601);
            return baseReqVo;
        }
        AdminDO adminDOAfterCreate = adminService.createNewAdmin(adminDO);
        if (adminDOAfterCreate != null){
            BaseReqVo<AdminDO> baseReqVo = new BaseReqVo<>(adminDOAfterCreate, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<AdminDO> baseReqVo = new BaseReqVo<>(adminDOAfterCreate, "管理员已存在", 602);
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
    @RequiresPermissions(value = {"admin:admin:update"})
    public BaseReqVo updateAdmin(@RequestBody AdminDO adminDO){
        AdminDO adminDOAfterUpdate = adminService.updateAdmin(adminDO);
        if (adminDOAfterUpdate != null){
            BaseReqVo baseReqVo = new BaseReqVo<>(adminDOAfterUpdate,"成功",0);
            return baseReqVo;
        }
        BaseReqVo baseReqVo = new BaseReqVo<>(adminDOAfterUpdate, "失败", 505);
        return baseReqVo;
    }


    /**
     * response：
     * {"errno":0,"errmsg":"成功"}
     * request：
     * {
     * 	"id": 107,
     * 	"username": "mydbtest",
     * 	"avatar": "http://192.168.2.100:8081/wx/storage/fetch/lic469g2u8gdd7kndhuj.jpg",
     * 	"roleIds": [3]
     * }
     * @return
     */
    @RequestMapping("/admin/delete")
    @RequiresPermissions(value = {"admin:admin:delete"})
    public BaseReqVo deleteAdmin(@RequestBody AdminDO adminDO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String adminName = (String) request.getSession().getAttribute("admin");
        if (adminName.equals(adminDO.getUsername())){
            BaseReqVo<Object> baseReqVoError = new BaseReqVo<>(null, "管理员不能删除自己帐号", 604);
            return baseReqVoError;
        }
        int result = adminService.deleteAdmin(adminDO);
        if (result != 0){
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo baseReqVo = new BaseReqVo<>(null, "失败", 601);
        return baseReqVo;
    }


    /**
     * response:
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"total": 4786,
     * 		"items": [{
     * 			"id": 4786,
     * 			"admin": "admin123",
     * 			"ip": "192.168.4.7",
     * 			"type": 1,
     * 			"action": "登录",
     * 			"status": true,
     * 			"result": "",
     * 			"comment": "",
     * 			"addTime": "2019-11-16 08:56:07",
     * 			"updateTime": "2019-11-16 08:56:07",
     * 			"deleted": false
     *                }, {
     * 			"id": 4785,
     * 			"admin": "admin123",
     * 			"ip": "192.168.4.45",
     * 			"type": 1,
     * 			"action": "登录",
     * 			"status": true,
     * 			"result": "",
     * 			"comment": "",
     * 			"addTime": "2019-11-16 08:55:13",
     * 			"updateTime": "2019-11-16 08:55:13",
     * 			"deleted": false
     *        }]* 	},
     * 	"errmsg": "成功"
     * }
     *
     * page=1&limit=20&sort=add_time&order=desc
     *
     * @param page     页数
     * @param limit    分页数
     * @param sort     排序字段
     * @param order    排序方式
     * @return
     */
    @RequestMapping("/log/list")
    @RequiresPermissions(value = {"admin:log:list"})
    public BaseReqVo selectAllLogList(String name,Integer page,Integer limit,String sort,String order){
        Map<String, Object> map = adminService.selectAllLogList(name,page,limit,sort,order);
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }


    /**
     * http://192.168.2.100:8081/admin/role/list?page=1&limit=20&sort=add_time&order=desc
     * 角色管理 搜索所有的Role 简单的查询并且显示
     * @param page   页数
     * @param limit  分页数
     * @param sort   排序字段
     * @param order  排序方式
     * @return
     */
    @RequestMapping("/role/list")
    @RequiresPermissions(value = {"admin:role:list","admin:role:update","admin:role:read","admin:role:delete",
            "admin:role:permission:get","admin:role:create","admin:role:permission:update"},logical = Logical.OR)
    public BaseReqVo selectAllRoleList(String name,Integer page,Integer limit,String sort,String order){
        Map<String,Object> map = adminService.selectAllRoleList(name,page,limit,sort,order);
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }


    /**
     * 测试字体测试测试测试测试测试
     * request:
     * {
     * 	"name": "test01",
     * 	"desc": "第一次测试添加"
     * }
     * response:
     * {
     * 	"errno": 0,               重名校验：
     * 	"data": {                 没有长度校验：
     * 		"id": 102,
     * 		"name": "test01",
     * 		"desc": "第一次测试添加",
     * 		"addTime": "2019-11-16 22:43:25",
     * 		"updateTime": "2019-11-16 22:43:25"    // addtime和updatetime是一样的时间 第一层添加set * 2
     *        },
     * 	"errmsg": "成功"
     * }
     * @param roleDO
     * @return
     */
    @RequestMapping("/role/create")
    @RequiresPermissions(value = {"admin:role:create"})
    public BaseReqVo createNewRole(@RequestBody RoleDO roleDO){
        RoleDO roleDOAfterCreate = adminService.createNewRole(roleDO);
        if (roleDOAfterCreate != null){
            BaseReqVo<RoleDO> baseReqVo = new BaseReqVo<>(roleDOAfterCreate, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "角色已经存在", 640);
        return baseReqVo;
    }


    /**
     * request:
     * {
     * 	"id": 104,
     * 	"name": "无限大",
     * 	"desc": "有一说一",
     * 	"enabled": true,
     * 	"addTime": "2019-11-16 22:57:27",
     * 	"updateTime": "2019-11-16 22:57:27",
     * 	"deleted": false
     * }
     *
     * response :
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     * @param roleDO
     * @return
     *
     * 这个接口：更新失败的时候并不发送数据到后端，似乎是在前端完成了重名校验
     */
    @RequestMapping("/role/update")
    @RequiresPermissions(value = {"admin:role:update"})
    public BaseReqVo updateRole(@RequestBody RoleDO roleDO){
        RoleDO roleDOAfterUpdate = adminService.updateRole(roleDO);
        if (roleDOAfterUpdate != null){
            BaseReqVo<RoleDO> baseReqVo = new BaseReqVo<>(null, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "失败", 502);
        return baseReqVo;
    }


    /**
     * request:
     * {
     * 	"id": 104,
     * 	"name": "无限大",
     * 	"desc": "有一说一",
     * 	"enabled": true,
     * 	"addTime": "2019-11-16 22:57:27",
     * 	"updateTime": "2019-11-16 22:57:27",
     * 	"deleted": false
     * }
     * response:
     * {
     * 	"errno": 0,
     * 	"errmsg": "成功"
     * }
     * @param roleDO
     * @return
     */
    @RequestMapping("/role/delete")
    @RequiresPermissions(value = {"admin:role:delete"})
    public BaseReqVo deleteRole(@RequestBody RoleDO roleDO){
        List<AdminDO> adminList = adminService.selectAdmin();
        for (AdminDO admin : adminList) {
            Integer[] roleIds = admin.getRoleIds();
            for (Integer roleId : roleIds) {
                if (roleDO.getId().equals(roleId)){
                    BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "当前角色存在管理员，不能删除", 642);
                    return baseReqVo;
                }
            }
        }
        int result = adminService.deleteRole(roleDO);
        if (result != 0){
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "失败", 502);
        return baseReqVo;
    }


    /**
     * response :
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"total": 1666,
     * 		"items": [{
     * 			"id": 1776,
     * 			"key": "6v7rrekw9jtfqlln3qxq.jpg",
     * 			"name": "1.jpg",
     * 			"type": "image/jpeg",
     * 			"size": 23895,
     * 			"url": "http://192.168.2.100:8081/wx/storage/fetch/6v7rrekw9jtfqlln3qxq.jpg",
     * 			"addTime": "2019-11-17 01:27:08",
     * 			"updateTime": "2019-11-17 01:27:08",
     * 			"deleted": false
     *                }, {
     * 			"id": 1775,
     * 			"key": "vhtbowa6yqkqoh32iv1a.jpg",
     * 			"name": "复古外套.jpg",
     * 			"type": "image/jpeg",
     * 			"size": 73367,
     * 			"url": "http://192.168.2.100:8081/wx/storage/fetch/vhtbowa6yqkqoh32iv1a.jpg",
     * 			"addTime": "2019-11-17 01:26:39",
     * 			"updateTime": "2019-11-17 01:26:39",
     * 			"deleted": false
     *        }]* 	},
     * 	"errmsg": "成功"
     * }
     *
     * request:
     * @param name      文件名
     * @param key       文件唯一索引
     * @param page      页数
     * @param limit     页面
     * @param sort      排序字段
     * @param order     排序方式
     * @return
     *
     *  key 有问题
     */
    @RequestMapping("/storage/list")
    @RequiresPermissions(value = {"admin:storage:list","admin:storage:update","admin:storage:read","admin:storage:delete","admin:storage:create"},logical = Logical.OR)
    public BaseReqVo selectAllStorageList(String name,String key,Integer page,Integer limit,String sort,String order){
        Map<String,Object> map = adminService.selectAllStorageList(name,key,page,limit,sort,order);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }


    /**
     * response : {"errno":0,"errmsg":"成功"}
     * requset :
     * {
     * 	"id": 1798,
     * 	"key": "7v5irmjjc9taj7mq0ug1.jpg",
     * 	"name": "4.jpg",
     * 	"type": "image/jpeg",
     * 	"size": 273908,
     * 	"url": "http://192.168.2.100:8081/wx/storage/fetch/7v5irmjjc9taj7mq0ug1.jpg",
     * 	"addTime": "2019-11-17 02:38:58",
     * 	"updateTime": "2019-11-17 02:38:58"
     * }
     * @param storageDO
     * @return
     */
    @RequestMapping("/storage/delete")
    @RequiresPermissions(value = {"admin:storage:delete"})
    public BaseReqVo deleteStorage(@RequestBody StorageDO storageDO){
        int result = adminService.deleteStorage(storageDO);
        if (result != 0){
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo baseReqVo = new BaseReqVo();
        return baseReqVo;
    }


    /**
     * request :
     * {
     * 	"id": 1822,
     * 	"key": "4dk9vyj0fnpjth6eflrt.jpg",
     * 	"name": "123.jpg",
     * 	"type": "image/jpeg",
     * 	"size": 71887,
     * 	"url": "http://192.168.2.100:8081/wx/storage/fetch/4dk9vyj0fnpjth6eflrt.jpg",
     * 	"addTime": "2019-11-17 02:57:43",
     * 	"updateTime": "2019-11-17 02:57:43"
     * }
     *
     * response :
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"id": 1822,
     * 		"key": "4dk9vyj0fnpjth6eflrt.jpg",
     * 		"name": "123.jpg",
     * 		"type": "image/jpeg",
     * 		"size": 71887,
     * 		"url": "http://192.168.2.100:8081/wx/storage/fetch/4dk9vyj0fnpjth6eflrt.jpg",
     * 		"addTime": "2019-11-17 02:57:43",
     * 		"updateTime": "2019-11-17 02:58:03"   只有时间不一样 还是需要重新查询一次
     *        },
     * 	"errmsg": "成功"
     * }
     *
     * 这个不用重名校验
     *
     * @param storageDO
     * @return
     *
     */
    @RequestMapping("/storage/update")
    @RequiresPermissions(value = {"admin:storage:update"})
    public BaseReqVo updateStorage(@RequestBody StorageDO storageDO){
        StorageDO storageDOAfterUpdate = adminService.updateStorage(storageDO);
        if (storageDOAfterUpdate != null){
            BaseReqVo<StorageDO> baseReqVo = new BaseReqVo<>(storageDOAfterUpdate, "成功", 0);
            return baseReqVo;
        }
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();  // 不知道返回错误代号
        return baseReqVo;
    }


    /**
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/permissions",method = {RequestMethod.GET})
    @RequiresPermissions(value = {"admin:role:permission:get","admin:role:permission:update"},logical = Logical.OR)
    public BaseReqVo listPermissionsByRoleId(Integer roleId){
        List<String> permissionList = adminService.listPermissions(roleId);
        List<SystemPermissionDO> systemPermissionDOList = adminService.systemPermissionsList();
        Map<String, Object> map = new HashMap<>();
        map.put("systemPermissions", systemPermissionDOList);
        map.put("assignedPermissions", permissionList);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map,"成功",0);
        return baseReqVo;
    }


    @RequestMapping(value = "/role/permissions",method = {RequestMethod.POST})
    @RequiresPermissions(value = {"admin:role:permission:update"})
    public BaseReqVo listPermissions(@RequestBody PermissionsVO permissionsVO){
        if (permissionsVO.getRoleId().equals(1)){
            BaseReqVo<Object> baseReqVo = new BaseReqVo<>(null, "你不能修改超级管理员的权限", 500);
            return baseReqVo;
        }
        adminService.updateRolePermissions(permissionsVO);
        BaseReqVo baseReqVo = new BaseReqVo<>(null, "成功",0);
        return baseReqVo;
    }
}
