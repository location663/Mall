/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/16
 * Time:11:13
 **/
package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.StatService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/stat")
public class StatController {

    @Autowired
    StatService statService;

    /**
     *
     * @return
     */
    @RequestMapping("user")
    @RequiresPermissions(value = {"admin:user:list"})
    public BaseReqVo statUser(){
        Map returnmap = statService.statUser();
        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
        return baseReqVo;
    }

    /**
     *
     * @return
     */
    @RequiresPermissions(value = {"admin:goods:list"})
    @RequestMapping("goods")
    public BaseReqVo statGoods(){
        Map returnmap = statService.statGoods();
        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
        return baseReqVo;
    }

    /**
     * 订单统计，统计项为日期，订单数，下单用户数，订单总金额，客单价
     * @return
     */
    @RequiresPermissions(value = {"admin:order:list"})
    @RequestMapping("order")
    public BaseReqVo statOrder(){
        Map returnmap = statService.statOrder();
        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
        return baseReqVo;
    }
}
