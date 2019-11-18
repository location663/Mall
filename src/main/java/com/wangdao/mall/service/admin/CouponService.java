package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.CouponDO;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-16 16:25
 **/
public interface CouponService {

    Map<String, Object> queryCoupons(Integer page, Integer limit, String name, Integer type, Integer status);


    int createCoupon(CouponDO couponDO);

    CouponDO queryCouponByKey(int id);

    Map<String, Object> queryCouponsByConditions(Integer page, Integer limit, Integer couponId, Integer userId, Integer status);

    int deleteCoupon(Integer id);
}
