/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/16
 * Time:11:27
 **/
package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.OrderDOMapper;
import com.wangdao.mall.mapper.OrderGoodsDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import com.wangdao.mall.service.util.DateTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    UserDOMapper userDOMapper;
    @Override
    public Map statUser() {
        String[] strings={"day","users"};
        UserDOExample userDOExample = new UserDOExample();
        List<UserDO> userDOS = userDOMapper.selectByExample(userDOExample);
        /**
         * 按登录时间去重，并依次按登录时间搜索并统计
         */
        Set<Date> dateSet=new HashSet();
        for (UserDO userDO : userDOS) {
            dateSet.add(userDO.getLastLoginTime());
        }
        List<StateDo> stateDos=new ArrayList();
        for (Date date : dateSet) {
            userDOExample.createCriteria().andLastLoginTimeEqualTo(date);
            List<UserDO> userDOS1 = userDOMapper.selectByExample(userDOExample);
            StateDo stateDo = new StateDo();
            stateDo.setDay(date);
            stateDo.setUsers(userDOS1.size());
            stateDos.add(stateDo);
        }
        Map<String, Object> returnmap = new HashMap<>();
        returnmap.put("columns",strings);
        returnmap.put("rows",stateDos);
        return returnmap;
    }

    /**
     *
     */
    @Autowired
    OrderDOMapper orderDOMapper;
    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;
    @Override
    public Map statGoods() {
        String[] strings={"day","orders","products","amount"};
        List<StateDo> stateDos = orderDOMapper.selectGoods();
        Map<String, Object> returnmap = new HashMap<>();
        returnmap.put("columns",strings);
        returnmap.put("rows",stateDos);
        return returnmap;
    }
    /**
     *
     */
//    @Autowired
//
//    @Override
//    public Map statOrder() {
//        String[] strings={"day","users","customers","amount","pcr"};
//        UserDOExample userDOExample = new UserDOExample();
//        List<UserDO> userDOS = userDOMapper.selectByExample(userDOExample);
//        /**
//         * 按登录时间去重，并依次按登录时间搜索并统计
//         */
//        Set<Date> dateSet=new HashSet();
//        for (UserDO userDO : userDOS) {
//            dateSet.add(userDO.getLastLoginTime());
//        }
//        List<StateDo> stateDos=new ArrayList();
//        for (Date date : dateSet) {
//            userDOExample.createCriteria().andLastLoginTimeEqualTo(date);
//            List<UserDO> userDOS1 = userDOMapper.selectByExample(userDOExample);
//            StateDo stateDo = new StateDo();
//            stateDo.setDay(date);
//            stateDo.setUsers(userDOS1.size());
//            stateDos.add(stateDo);
//        }
//        Map<String, Object> returnmap = new HashMap<>();
//        returnmap.put("columns",strings);
//        returnmap.put("rows",stateDos);
//        return returnmap;
//    }
}
