/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/16
 * Time:11:13
 **/
package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/stat")
public class StatController {
    @Autowired
    StatService statService;
    @RequestMapping("user")
    public BaseReqVo statUser(){
        Map returnmap=statService.statUser();
        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
        return baseReqVo;
    }
    @RequestMapping("goods")
    public BaseReqVo statGoods(){
        Map returnmap=statService.statGoods();
        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
        return baseReqVo;
    }
//    @RequestMapping("order")
//    public BaseReqVo statOrder(){
//        Map returnmap=statService.statOrder();
//        BaseReqVo baseReqVo = new BaseReqVo(returnmap,"成功",0);
//        return baseReqVo;
//    }
}
