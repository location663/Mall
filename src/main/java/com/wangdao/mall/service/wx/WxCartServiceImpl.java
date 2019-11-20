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
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Integer add(Integer goodsId, Integer number, Integer productId, UserDO userDO) {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = null;
        try {
            cartDO = new CartDO(null, userDO.getId(), (goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), (productId), productDO.getPrice(), (number.shortValue()), new ObjectMapper().writeValueAsString(productDO.getSpecifications()), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        int i = cartDOMapper.insertSelective(cartDO);
        return i;
    }

    @Override
    public Map index(UserDO userDO) {
        Map<String,Object> returnmap=new HashMap();
        Map<String,Object> map=new HashMap<>();
        cartDOExample.clear();
        CartDOExample.Criteria criteria = cartDOExample.createCriteria();
        criteria.andUserIdEqualTo(userDO.getId());
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
        map.put("checkedGoodsAmount",cAmount);
        map.put("checkedGoodsCount",ccount);
        map.put("goodsAmount",amount);
        map.put("goodsCount",count);
        returnmap.put("cartTotal",map);
        return returnmap;
    }

    @Override
    public Integer fastAdd(Integer goodsId, Integer number, Integer productId, UserDO userDO) {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey((productId));
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = null;
        try {
            cartDO = new CartDO(null, userDO.getId(), (goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), (productId), productDO.getPrice(), (number.shortValue()), new ObjectMapper().writeValueAsString(productDO.getSpecifications()), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        int i = cartDOMapper.insertSelective(cartDO);
        //如何获得最新的id
        CartDO cartDO1 = cartDOMapper.selectByPrimaryKey(i);
        return cartDO1.getId();
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
        //查询价格
        Map index = index(userDO);
        Map cartTotal = (Map)index.get("cartTotal");
        int totalPrice=(Integer)cartTotal.get("checkedGoodsCount");
        dataBean.setGoodsTotalPrice(totalPrice);
        dataBean.setActualPrice(totalPrice-dataBean.getGrouponPrice()-dataBean.getCouponPrice());
        //查询邮费
        SystemDO systemDO = systemDOMapper.selectByPrimaryKey(5);
        if(dataBean.getActualPrice()<= Integer.parseInt(systemDO.getKeyValue())){
            dataBean.setFreightPrice(8);
        }else {
            dataBean.setFreightPrice(0);
        }
        dataBean.setOrderTotalPrice(dataBean.getActualPrice()+dataBean.getFreightPrice());
        //查询checkedgoodslist
        cartDOExample.clear();
        cartDOExample.createCriteria().andCheckedEqualTo(true);
        List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
        dataBean.setCheckedGoodsList(cartDOS);
        return dataBean;
    }

    @Override
    public void update(ReceiveCartDo receiveCartDo, UserDO userDO) {

    }
}
