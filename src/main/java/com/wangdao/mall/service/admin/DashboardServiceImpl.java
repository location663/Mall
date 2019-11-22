/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/15
 * Time:16:23
 **/
package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.GoodsDOExample;
import com.wangdao.mall.bean.GoodsProductDOExample;
import com.wangdao.mall.bean.OrderDOExample;
import com.wangdao.mall.bean.UserDOExample;
import com.wangdao.mall.mapper.GoodsDOMapper;
import com.wangdao.mall.mapper.GoodsProductDOMapper;
import com.wangdao.mall.mapper.OrderDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    GoodsDOMapper goodsDOMapper;
    @Autowired
    OrderDOMapper orderDOMapper;
    @Autowired
    GoodsProductDOMapper goodsProductDOMapper;
    @Override
    public Map countAll() {
        Map<String,Long> returnmap=new HashMap();
        long user= userDOMapper.countByExample(new UserDOExample());
        long order = orderDOMapper.countByExample(new OrderDOExample());
        long goods = goodsDOMapper.countByExample(new GoodsDOExample());
        long product = goodsProductDOMapper.countByExample(new GoodsProductDOExample());
        returnmap.put("goodsTotal",goods);
        returnmap.put("orderTotal",order);
        returnmap.put("productTotal",product);
        returnmap.put("userTotal",user);
        return returnmap;
    }
}
