/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/19
 * Time:20:43
 **/
package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.service.wx.WxCartService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/card")
public class CartController {
    @Autowired
    WxCartService cartService;
    @RequestMapping("add")
    public BaseReqVo add(Map map){
        String goodsId = (String)map.get("goodsId");
        String number=(String)map.get("number");
        String productId=(String)map.get("productId");
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Integer i=cartService.add(goodsId,number,productId,userDO);
        return new BaseReqVo<Object>(i,"成功",0);
    }
    @RequestMapping("index")
    public BaseReqVo index(){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Map map=cartService.index(userDO);
        return new BaseReqVo<>(map,"成功",0);
    }
    @RequestMapping("fastadd")
    public BaseReqVo fastAdd(Map map){
        String goodsId = (String)map.get("goodsId");
        String number=(String)map.get("number");
        String productId=(String)map.get("productId");
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Integer i=cartService.fastAdd(goodsId,number,productId,userDO);
        return new BaseReqVo<Object>(i,"成功",0);
    }
    @RequestMapping("checked")
    public BaseReqVo checked(Map map){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Boolean checked=(Boolean) map.get("isChecked");
        List<Integer> products=(List)map.get("productIds");
        cartService.checked(products,checked);
        Map index = cartService.index(userDO);
        return new BaseReqVo<>(index,"成功",0);
    }
}
