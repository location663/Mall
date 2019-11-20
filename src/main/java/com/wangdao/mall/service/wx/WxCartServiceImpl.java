/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/19
 * Time:21:11
 **/
package com.wangdao.mall.service.wx;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.CartDOMapper;
import com.wangdao.mall.mapper.GoodsDOMapper;
import com.wangdao.mall.mapper.GoodsProductDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxCartServiceImpl implements WxCartService {
    UserDOExample userDOExample = new UserDOExample();
    GoodsDOExample goodsDOExample=new GoodsDOExample();
    GoodsProductDOExample productDOExample=new GoodsProductDOExample();
    CartDOExample cartDOExample=new CartDOExample();
    @Autowired
    GoodsDOMapper goodsDOMapper;
    @Autowired
    GoodsProductDOMapper productDOMapper;
    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    CartDOMapper cartDOMapper;
    @Override
    public Integer add(String goodsId, String number, String productId, UserDO userDO) {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey(Integer.parseInt(productId));
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = new CartDO(null, userDO.getId(), Integer.parseInt(goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), Integer.parseInt(productId), productDO.getPrice(), (short)Integer.parseInt(number), productDO.getSpecifications().toString(), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        int i = cartDOMapper.insertSelective(cartDO);
        return i;
    }

    @Override
    public Map index(UserDO userDO) {
        Map<String,Object> returnmap=new HashMap();
        Map<String,Object> map=new HashMap<>();
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
    public Integer fastAdd(String goodsId, String number, String productId, UserDO userDO) {
        GoodsProductDO productDO = productDOMapper.selectByPrimaryKey(Integer.parseInt(productId));
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(productDO.getGoodsId());
        CartDO cartDO = new CartDO(null, userDO.getId(), Integer.parseInt(goodsId), goodsDO.getGoodsSn(), goodsDO.getName(), Integer.parseInt(productId), productDO.getPrice(), (short)Integer.parseInt(number), productDO.getSpecifications().toString(), false, goodsDO.getPicUrl(), productDO.getAddTime(), productDO.getUpdateTime(), false);
        int i = cartDOMapper.insertSelective(cartDO);
        CartDO cartDO1 = cartDOMapper.selectByPrimaryKey(i);
        return cartDO1.getId();
    }

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
}
