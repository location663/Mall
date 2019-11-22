package com.wangdao.mall.controller.admin;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CouponDO;
import com.wangdao.mall.bean.CouponUserDO;
import com.wangdao.mall.service.admin.CouponService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-16 16:19
 **/
@RestController
@RequestMapping("admin")
public class CouponController {
    @Autowired
    CouponService couponService;

    @RequestMapping("coupon/list")
    @RequiresPermissions(value = {"admin:coupon:list","admin:coupon:update","admin:coupon:delete",
            "admin:coupon:create"},logical = Logical.OR)
    public BaseReqVo queryCoupons(Integer page, Integer limit, String name, Integer type, Integer status){
        Map<String, Object> map = couponService.queryCoupons(page, limit, name, type, status);
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("coupon/create")
    @RequiresPermissions(value = {"admin:coupon:create"})
    public BaseReqVo createCoupon(@RequestBody CouponDO couponDO){
        int id = couponService.createCoupon(couponDO);
        CouponDO couponDO1 = couponService.queryCouponByKey(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(couponDO1);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/read")
    @RequiresPermissions(value = {"admin:coupon:read"})
    public BaseReqVo queryCouponInfo(int id){
        CouponDO couponDO = couponService.queryCouponByKey(id);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(couponDO);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/listuser")
    @RequiresPermissions(value = {"admin:coupon:list","admin:coupon:update","admin:coupon:delete",
            "admin:coupon:create","admin:coupon:listuser"},logical = Logical.OR)
    public BaseReqVo queryCouponsByUserIdStatus(Integer page, Integer limit, Integer couponId, Integer userId, Integer status) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = couponService.queryCouponsByConditions(page, limit, couponId, userId, status);
        baseReqVo.setData(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("coupon/delete")
    @RequiresPermissions(value = {"admin:coupon:delete"})
    public BaseReqVo deleteCoupon(@RequestBody CouponDO couponDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int delete = couponService.deleteCoupon(couponDO.getId());
        if(delete == 1){
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }

    @RequestMapping("coupon/update")
    @RequiresPermissions(value = {"admin:coupon:update"})
    public BaseReqVo updateCoupon(@RequestBody CouponDO couponDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        CouponDO couponDO1 = couponService.updateCoupon(couponDO);
        baseReqVo.setData(couponDO1);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
