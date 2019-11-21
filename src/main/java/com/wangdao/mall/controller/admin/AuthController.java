package com.wangdao.mall.controller.admin;


import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.InfoData;
import com.wangdao.mall.service.admin.AdminService;
import com.wangdao.mall.service.admin.DashboardService;
import com.wangdao.mall.shiro.CustomToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping("admin")
public class AuthController {

    @Autowired
    AdminService adminService;

    /**
     * 统计 goods,order,product,user的数量
     */
    @Autowired
    DashboardService dashboardService;

    /**
     * 登录模块
     * @return
     */
    @RequestMapping("/auth/login")
    public BaseReqVo login(@RequestBody AdminDO adminDO){
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        CustomToken admin = new CustomToken(adminDO.getUsername(), adminDO.getPassword(), "admin");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("admin", adminDO.getUsername());
        String remoteIp = request.getRemoteAddr();
        int i =adminService.updateLoginTimeAndIp(adminDO,remoteIp);
        try {
            subject.login(admin);
        } catch (AuthenticationException e) {
            baseReqVo.setErrmsg("帐号或者密码出现错误");
            baseReqVo.setErrno(900);
            return baseReqVo;
        }
        Serializable id = subject.getSession().getId();
        baseReqVo.setData(id);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     *
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"roles": ["啊啊啊啊", "1", "超级管理员", "推广管理员", "12341"],
     * 		"name": "admin123",
     * 		"perms": ["*"],
     * 		"avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
     *        },
     * 	"errmsg": "成功"
     * }
     *
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"roles": ["商场管理员"],
     * 		"name": "mall123",
     * 		"perms": ["GET /admin/order/list", "POST /admin/ad/create", "POST /admin/topic/create", "GET /admin/topic/read", "GET /admin/index/read", "GET /admin/history/list", "POST /admin/issue/update", "GET /admin/coupon/list", "POST /admin/order/reply", "GET /admin/goods/list", "GET /admin/ad/list", "POST /admin/brand/create", "POST /admin/config/express", "GET /admin/feedback/list", "POST /admin/groupon/create", "GET /admin/stat/user", "POST /admin/coupon/update", "POST /admin/issue/delete", "POST /admin/category/delete", "GET /admin/category/read", "POST /admin/keyword/update", "POST /admin/keyword/delete", "POST /admin/config/mall", "GET /admin/ad/read", "GET /admin/category/list", "GET /admin/config/express", "GET /admin/issue/list", "GET /admin/brand/list", "POST /admin/topic/delete", "GET /admin/address/list", "POST /admin/groupon/update", "POST /admin/coupon/create", "POST /admin/admin/create", "GET /admin/brand/read", "POST /admin/brand/update", "GET /admin/collect/list", "GET /admin/coupon/listuser", "POST /admin/admin/delete", "POST /admin/goods/update", "GET /admin/config/wx", "GET /admin/footprint/list", "POST /admin/brand/delete", "GET /admin/comment/list", "POST /admin/admin/update", "POST /admin/goods/delete", "POST /admin/groupon/delete", "GET /admin/goods/detail", "GET /admin/config/order", "GET /admin/user/list", "GET /admin/admin/list", "POST /admin/ad/delete", "POST /admin/config/order", "POST /admin/index/write", "GET /admin/config/mall", "POST /admin/ad/update", "POST /admin/order/refund", "GET /admin/stat/order", "GET /admin/keyword/list", "POST /admin/coupon/delete", "POST /admin/config/wx", "GET /admin/admin/read", "POST /admin/category/update", "GET /admin/order/detail", "GET /admin/groupon/list", "POST /admin/issue/create", "POST /admin/keyword/create", "GET /admin/stat/goods", "GET /admin/keyword/read", "POST /admin/category/create", "GET /admin/groupon/listRecord", "GET /admin/topic/list", "GET /admin/coupon/read", "POST /admin/order/ship", "POST /admin/comment/delete", "POST /admin/goods/create", "POST /admin/topic/update"],
     * 		"avatar": "'"
     *        },
     * 	"errmsg": "成功"
     * }
     * //        ArrayList<String> roles = new ArrayList<>();
     * //        roles.add("超级管理员");
     * //        infoData.setRoles(roles);
     * @param token
     * @return
     */
    @RequestMapping("/auth/info")
    public BaseReqVo info(String token){
        Subject subject = SecurityUtils.getSubject();
        AdminDO principal = (AdminDO) subject.getPrincipal();
        InfoData infoData = new InfoData();
        infoData.setAvatar(principal.getAvatar());
        infoData.setName(principal.getUsername());
        Integer[] roleIds = principal.getRoleIds();
        ArrayList<String> permsList = new ArrayList<>();
        ArrayList<String> roleList = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<String> perms = adminService.selectPermsLeft(roleId);
            permsList.addAll(perms);
            String roleName = adminService.selectRoleName(roleId);
            roleList.add(roleName);
        }
        if (permsList.size() == 0){
            permsList.add("*");
        }
        infoData.setRoles(roleList);
        infoData.setPerms(permsList);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(infoData);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("/auth/logout")
    public BaseReqVo logouut(){
        SecurityUtils.getSubject().logout();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return  baseReqVo;
    }

    @RequestMapping("dashboard")
    public BaseReqVo dashboard(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(dashboardService.countAll());
        return baseReqVo;
    }
}
