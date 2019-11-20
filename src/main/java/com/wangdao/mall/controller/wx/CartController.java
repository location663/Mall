/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/19
 * Time:20:43
 **/
package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CheckoutDataBean;
import com.wangdao.mall.bean.ReceiveCartDo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.service.wx.WxCartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/cart")
public class CartController {
    @Autowired
    WxCartService cartService;
    @RequestMapping("add")
    public BaseReqVo add(@RequestBody Map map){
        Integer goodsId = (Integer)map.get("goodsId");
        Integer number=(Integer)map.get("number");
        Integer productId=(Integer)map.get("productId");
        UserDO userDO = (UserDO)SecurityUtils.getSubject().getPrincipal();
        cartService.add(goodsId,number,productId,userDO);
        int i=cartService.goodsCount(userDO);
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
    public BaseReqVo fastAdd(@RequestBody Map map){
        Integer goodsId = (Integer)map.get("goodsId");
        Integer number=(Integer)map.get("number");
        Integer productId=(Integer)map.get("productId");
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Integer i=cartService.fastAdd(goodsId,number,productId,userDO);
        return new BaseReqVo<Object>(i,"成功",0);
    }
    @RequestMapping("checked")
    public BaseReqVo checked(@RequestBody Map map){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Integer checked=(Integer) map.get("isChecked");
        List<Integer> products=(List)map.get("productIds");
        Boolean truechecked=false;
        if(checked==1){
           truechecked=true;
        }
        cartService.checked(products,truechecked);
        Map index = cartService.index(userDO);
        return new BaseReqVo<>(index,"成功",0);
    }
    @RequestMapping("checkout")
    public BaseReqVo checkout(Integer cartId,Integer addressId,Integer couponId,Integer grouponRulesId){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        CheckoutDataBean dataBean=cartService.checkout(cartId,addressId,couponId,grouponRulesId,userDO);
        return  new BaseReqVo<>(dataBean,"成功",0);
    }
    @RequestMapping("update")
    public BaseReqVo update(@RequestBody ReceiveCartDo receiveCartDo){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        cartService.update(receiveCartDo,userDO);
        return new BaseReqVo<>(null,"成功",0);
    }
    @RequestMapping("delete")
    public BaseReqVo delete(@RequestBody Map map){
        List<Integer> productIds =(List<Integer>)map.get("productIds");
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        Map returnmap=cartService.delete(productIds,userDO);
        return new BaseReqVo<>(returnmap,"成功",0);
    }
}
