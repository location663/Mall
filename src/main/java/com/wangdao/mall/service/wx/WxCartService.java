package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CheckoutDataBean;
import com.wangdao.mall.bean.ReceiveCartDo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.exception.WxException;

import java.util.List;
import java.util.Map;

public interface WxCartService  {
    void add(Integer goodsId, Integer number, Integer productId, UserDO userDO) throws WxException;
    Map index(UserDO userDO);
    Integer fastAdd(Integer goodsId, Integer number, Integer productId, UserDO userDO) throws WxException;
    void checked(List<Integer> products, Boolean checked);
    CheckoutDataBean checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId,UserDO userDo);
    void update(ReceiveCartDo receiveCartDo, UserDO userDO);
    Map delete(List<Integer> productId, UserDO userDO);
    int goodsCount(UserDO userDO);
}
