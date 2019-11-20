package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.CommentDOMapper;
import com.wangdao.mall.mapper.OrderDOMapper;
import com.wangdao.mall.mapper.OrderGoodsDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    CommentDOMapper commentDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    /**
     * 微信用户登录
     * @return
     */
    @Override
    public Map login() {
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put("avatarUrl", userDO.getAvatar());
        map2.put("nickName", userDO.getNickname());
        HashMap<Object, Object> map1 = new HashMap<>();
        map1.put("userInfo", map2);
        map1.put("tokenExpire", new Date(System.currentTimeMillis() + 1000*60*60*24));
        return map1;
    }

    @Override
    public Map userIndex() {
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        OrderDOExample orderDOExample = new OrderDOExample();
        orderDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
        List<OrderDO> orderDOList = orderDOMapper.selectByExample(orderDOExample);
        HashMap<Object, Object> map2 = new HashMap<>();
        int unpaid = 0;
        int unrecv = 0;
        int unship = 0;
        int comment = 0;
        for (OrderDO orderDO : orderDOList) {
            if (orderDO.getOrderStatus() == 101){
                unpaid++;
            }
            if (orderDO.getOrderStatus() == 301){
                unrecv++;
            }
            if (orderDO.getOrderStatus() == 201){
                unship++;
            }
            OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
            orderGoodsDOExample.createCriteria().andDeletedEqualTo(false).andOrderIdEqualTo(orderDO.getId());
            List<OrderGoodsDO> orderGoodsDOS = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
            CommentDOExample commentDOExample = new CommentDOExample();
            if (orderGoodsDOS.size() != 0) {
                commentDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andValueIdEqualTo(orderGoodsDOS.get(0).getGoodsId());
                List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);
                if (commentDOS.size() != 0) {
                    comment++;
                }
            }
        }
        map2.put("uncomment", orderDOList.size() - comment);
        map2.put("unpaid", unpaid);
        map2.put("unrecv", unrecv);
        map2.put("unship", unship);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("order", map2);
        return map;
    }
}
