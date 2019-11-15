/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 16:34
 **/
package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminAdministerController {

    @Autowired
    AdminService adminService;

    /**
     *  系统管理的角色管理admin/admin/list
     */
    @RequestMapping("admin/list")
    public BaseReqVo selectAllAdmin(String username,Integer page,Integer limit,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = adminService.selectAllAdmin(username,page,limit,sort,order);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
