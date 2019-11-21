/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/19
 * Time:21:11
 **/
package com.wangdao.mall.service.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxCartServiceImpl implements WxCartService {
    CartDOExample cartDOExample=new CartDOExample();
    @Autowired
    GoodsDOMapper goodsDOMapper;
    @Autowired
    GoodsProductDOMapper productDOMapper;
    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    CartDOMapper cartDOMapper;
    @Autowired
    AddressDOMapper addressDOMapper;
    @Autowired
    CouponDOMapper couponDOMapper;
    @Autowired
    SystemDOMapper systemDOMapper;
    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;
    @Override
    public void add(Integer goodsId, Integer number, Integer productId, UserDO userDO) throws WxException {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        if(number>productDO.getNumber()){
            throw new WxException("库存不足");
        }
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = null;
        try {
            cartDO = new CartDO(null, userDO.getId(), (goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), (productId), productDO.getPrice(), (number.shortValue()), new ObjectMapper().writeValueAsString(productDO.getSpecifications()), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        cartDOExample.clear();
        cartDOExample.createCriteria().andUserIdEqualTo(userDO.getId()).andProductIdEqualTo(productId).andDeletedEqualTo(false);
        List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
        if(cartDOS.size()!=0){
            for (CartDO cartDO1 : cartDOS) {
                cartDO1.setNumber((short)(number.shortValue()+cartDO1.getNumber()));
                cartDOMapper.updateByExampleSelective(cartDO1, cartDOExample);
            }
        }
        else {
            cartDOMapper.insertSelective(cartDO);
        }
    }

    @Override
    public Map index(UserDO userDO) {
        Map<String,Object> returnmap=new HashMap();
        Map<String,Object> map=new HashMap<>();
        cartDOExample.clear();
        CartDOExample.Criteria criteria = cartDOExample.createCriteria();
        criteria.andUserIdEqualTo(userDO.getId()).andDeletedEqualTo(false);
        List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
        returnmap.put("cartList",cartDOS);
        int amount = cartDOS.size();
        int count=0;
        for (CartDO cartDO : cartDOS) {
            count+=(cartDO.getPrice().intValue()*cartDO.getNumber());
        }
        criteria.andCheckedEqualTo(true);
        List<CartDO> cartDOS1 = cartDOMapper.selectByExample(cartDOExample);
        int cAmount = cartDOS1.size();
        int ccount=0;
        for (CartDO cartDO : cartDOS1) {
            ccount+=(cartDO.getPrice().intValue()*cartDO.getNumber());
        }
        map.put("checkedGoodsAmount",ccount);
        map.put("checkedGoodsCount",cAmount);
        map.put("goodsAmount",count);
        map.put("goodsCount",amount);
        returnmap.put("cartTotal",map);
        return returnmap;
    }

    @Override
    public Integer fastAdd(Integer goodsId, Integer number, Integer productId, UserDO userDO) throws WxException {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey((productId));
        if(number>productDO.getNumber()){
            throw new WxException("库存不足");
        }
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = null;
        try {
            cartDO = new CartDO(null, userDO.getId(), (goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), (productId), productDO.getPrice(), (number.shortValue()), new ObjectMapper().writeValueAsString(productDO.getSpecifications()), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        cartDOMapper.insertSelective(cartDO);
        //如何获得最新的id
        int i1 = cartDOMapper.selectLastInsertId();
        return i1;
    }

    /**
     * 修改购物车里购物车的选择状态
     * @param products
     * @param checked
     */
    @Override
    public void checked(List<Integer> products, Boolean checked) {
        for (Integer product : products) {
            cartDOExample.clear();
            cartDOExample.createCriteria().andProductIdEqualTo(product);
            CartDO cartDO = new CartDO();
            cartDO.setChecked(checked);
            int i = cartDOMapper.updateByExampleSelective(cartDO, cartDOExample);
        }
    }

    @Override
    public CheckoutDataBean checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId,UserDO userDO) {
        CheckoutDataBean dataBean = new CheckoutDataBean();
        //查询地址
        AddressDO addressDO = addressDOMapper.selectByPrimaryKey(addressId);
        dataBean.setCheckedAddress(addressDO);
        dataBean.setAddressId(addressId);
        //查询优惠卷
        dataBean.setCouponId(couponId);
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);
        if(couponDO!=null){
        dataBean.setCouponPrice(couponDO.getTotal());}
        //查询团购
        GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponRulesId);
        if(grouponRulesDO!=null){
            dataBean.setGrouponPrice(grouponRulesDO.getDiscount().intValue());
            dataBean.setAvailableCouponLength(grouponRulesDO.getDiscountMember());
        }
        dataBean.setGrouponRulesId(grouponRulesId);
        int totalPrice;
        //查询价格
        if(cartId==0) {
            Map index = index(userDO);
            Map cartTotal = (Map) index.get("cartTotal");
            totalPrice = (Integer) cartTotal.get("checkedGoodsCount");
            dataBean.setGoodsTotalPrice(totalPrice);
            //查询checkedgoodslist
            cartDOExample.clear();
            cartDOExample.createCriteria().andCheckedEqualTo(true).andDeletedEqualTo(true);
            List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
            dataBean.setCheckedGoodsList(cartDOS);
        }
        else {
            CartDO cartDO = cartDOMapper.selectByPrimaryKey(cartId);
            totalPrice=cartDO.getNumber()*cartDO.getPrice().shortValue();
            dataBean.setGoodsTotalPrice(totalPrice);
            List<CartDO> list=new ArrayList<>();
            list.add(cartDO);
            dataBean.setCheckedGoodsList(list);
        }
        dataBean.setActualPrice(totalPrice - dataBean.getGrouponPrice() - dataBean.getCouponPrice());
        //查询邮费
        SystemDO systemDO = systemDOMapper.selectByPrimaryKey(5);
        if(dataBean.getActualPrice()<= Double.valueOf(systemDO.getKeyValue())){
            dataBean.setFreightPrice(8);
        }else {
            dataBean.setFreightPrice(0);
        }
        dataBean.setOrderTotalPrice(dataBean.getActualPrice()+dataBean.getFreightPrice());
        return dataBean;
    }

    @Override
    public void update(ReceiveCartDo receiveCartDo, UserDO userDO) {
        cartDOExample.clear();
        cartDOExample.createCriteria().andIdEqualTo(receiveCartDo.getId());
        CartDO cartDO = cartDOMapper.selectByPrimaryKey(receiveCartDo.getId());
        cartDO.setNumber((short)receiveCartDo.getNumber());
        cartDOMapper.updateByExampleSelective(cartDO,cartDOExample);
    }

    @Override
    public Map delete(List<Integer> productId, UserDO userDO) {
        for (Integer integer : productId) {
            cartDOExample.clear();
            cartDOExample.createCriteria().andUserIdEqualTo(userDO.getId()).andProductIdEqualTo(integer);
            List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
            for (CartDO cartDO : cartDOS) {
                cartDO.setDeleted(true);
                cartDOMapper.updateByExampleSelective(cartDO,cartDOExample);
            }
        }
        Map index = index(userDO);
        return index;
    }

    @Override
    public int goodsCount(UserDO userDO) {
        cartDOExample.clear();
        cartDOExample.createCriteria().andUserIdEqualTo(userDO.getId()).andDeletedEqualTo(false);
        long l = cartDOMapper.countByExample(cartDOExample);
        return (int)l;
    }
}
