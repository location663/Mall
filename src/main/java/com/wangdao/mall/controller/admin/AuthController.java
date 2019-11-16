package com.wangdao.mall.controller.admin;


import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.InfoData;
import com.wangdao.mall.service.admin.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("admin")
public class AuthController {

    @RequestMapping("/auth/login")
    public BaseReqVo login(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData("4b7d719e-53b7-4019-9677-6309b2445b45");
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     *
     * @param token
     * @return
     */
    @RequestMapping("/auth/info")
    public BaseReqVo info(String token){
        BaseReqVo baseReqVo = new BaseReqVo();
        InfoData data = new InfoData();
        data.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.setName("songge");
        ArrayList<String> perms = new ArrayList<>();
        perms.add("*");
        data.setPerms(perms);
        ArrayList<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        data.setRoles(roles);

        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    /**
     * 统计 goods,order,product,user的数量
     */
    @Autowired
    DashboardService dashboardService;
    @RequestMapping("dashboard")
    public BaseReqVo dashboard(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(dashboardService.countAll());
        return baseReqVo;
    }
}
