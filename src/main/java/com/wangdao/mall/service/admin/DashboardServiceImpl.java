/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/15
 * Time:16:23
 **/
package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    AdminDOMapper adminDOMapper;
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

    /**
     * 修改管理员密码
     * @param map
     */
    @Override
    public BaseReqVo profilePassword(Map map) {
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andUsernameEqualTo((String) map.get("username"));
        AdminDO adminDO = adminDOMapper.selectByExample(adminDOExample).get(0);
//        String password = adminDOMapper.selectByPrimaryKey(adminDO.getId()).getPassword();
        if (!map.get("oldPassword").equals(adminDO.getPassword())){
            return new BaseReqVo(null,"账号密码不对",605);
        }
        adminDO.setPassword((String) map.get("newPassword"));
        adminDO.setUpdateTime(new Date());
        adminDOMapper.updateByPrimaryKeySelective(adminDO);
        return new BaseReqVo(null,"成功",0);
    }
}
