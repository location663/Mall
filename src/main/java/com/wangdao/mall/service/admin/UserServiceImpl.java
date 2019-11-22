/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/15
 * Time:20:00
 **/
package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDOMapper userDOMapper;

    /**
     * 根据条件展示user
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param username
     * @param mobile
     * @return
     */
    @Override
    public Map listByUserCondition(int page, int limit,String sort,String order,String username,String mobile) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        UserDOExample userDOExample = new UserDOExample();
        if(mobile!=null){
            userDOExample.createCriteria().andMobileLike("%"+mobile+"%");
        }
        else if(username!=null){
            userDOExample.createCriteria().andUsernameLike("%"+username+"%");
        }
        userDOExample.setOrderByClause(sort+" "+order);
        userDOExample.createCriteria().andDeletedEqualTo(false);
        List<UserDO> userDOS = userDOMapper.selectByExample(userDOExample);
        long l = userDOMapper.countByExample(userDOExample);
        map.put("items",userDOS);
        map.put("total",l);
        return map;
    }

    /**
     * 根据条件展示地址
     */
    @Autowired
    AddressDOMapper addressDOMapper;
    @Override
    public Map listByAddressCondition(int page, int limit, String sort, String order, String name, String userid) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        AddressDOExample addressDOExample= new AddressDOExample();
        addressDOExample.createCriteria().andDeletedEqualTo(false);
        if(userid!=null){
            addressDOExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userid));
        }
        else if(name!=null){
            addressDOExample.createCriteria().andNameLike("%"+name+"%");
        }
        addressDOExample.setOrderByClause(sort+" "+order);
        List<AddressDO> addressDOS = addressDOMapper.selectByExample(addressDOExample);
        long l = addressDOMapper.countByExample(addressDOExample);
        map.put("items",addressDOS);
        map.put("total",l);
        return map;
    }

    /**
     * 根据条件展示收藏
     */
    @Autowired
    CollectDOMapper collectDOMapper;
    @Override
    public Map listByCollectCondition(int page, int limit, String sort, String order, String valueId, String userId) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        CollectDOExample collectDOExample= new CollectDOExample();
        collectDOExample.createCriteria().andDeletedEqualTo(false);
        if(userId!=null){
            collectDOExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
        }
        else if(valueId!=null){
            collectDOExample.createCriteria().andValueIdEqualTo(Integer.parseInt(valueId));
        }
        collectDOExample.setOrderByClause(sort+" "+order);
        List<CollectDO> collectDos = collectDOMapper.selectByExample(collectDOExample);
        long l = collectDOMapper.countByExample(collectDOExample);
        map.put("items",collectDos);
        map.put("total",l);
        return map;
    }

    /**
     * 根据条件展示足迹
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param goodsId
     * @param userId
     * @return
     */
    @Autowired
    FootprintDOMapper footprintDOMapper;
    @Override
    public Map listByFootprintCondition(int page, int limit, String sort, String order, String goodsId, String userId) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        FootprintDOExample footprintDOExample= new FootprintDOExample();
        footprintDOExample.createCriteria().andDeletedEqualTo(false);
        if(userId!=null){
            footprintDOExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
        }
        else if(goodsId!=null){
            footprintDOExample.createCriteria().andGoodsIdEqualTo(Integer.parseInt(goodsId));
        }
        footprintDOExample.setOrderByClause(sort+" "+order);
        List<FootprintDO> footprintDOS = footprintDOMapper.selectByExample(footprintDOExample);
        long l = footprintDOMapper.countByExample(footprintDOExample);
        map.put("items",footprintDOS);
        map.put("total",l);
        return map;
    }

    /**
     * 展示搜索历史
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param keyword
     * @param userId
     * @return
     */
    @Autowired
    SearchHistoryDOMapper historyDOMapper;
    @Override
    public Map listByHistoryCondition(int page, int limit, String sort, String order, String keyword, String userId) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        SearchHistoryDOExample historyDOExample= new SearchHistoryDOExample();
        historyDOExample.createCriteria().andDeletedEqualTo(false);
        if(userId!=null){
            historyDOExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
        }
        else if(keyword!=null){
            historyDOExample.createCriteria().andKeywordLike(keyword);
        }
        historyDOExample.setOrderByClause(sort+" "+order);
        List<SearchHistoryDO> historyDOS = historyDOMapper.selectByExample(historyDOExample);
        long l = historyDOMapper.countByExample(historyDOExample);
        map.put("items",historyDOS);
        map.put("total",l);
        return map;
    }

    /**
     * 展示反馈
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param username
     * @param id
     * @return
     */
    @Autowired
    FeedbackDOMapper feedbackDOMapper;
    @Override
    public Map listByFeedbackCondition(int page, int limit, String sort, String order, String username, String id) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,limit);
        FeedbackDOExample feedbackDOExample= new FeedbackDOExample();
        feedbackDOExample.createCriteria().andDeletedEqualTo(false);
        if(username!=null){
            feedbackDOExample.createCriteria().andUsernameLike("%"+username+"%");
        }
        else if(id!=null){
            feedbackDOExample.createCriteria().andIdEqualTo(Integer.parseInt(id));
        }
        feedbackDOExample.setOrderByClause(sort+" "+order);
        List<FeedbackDO> feedbackDOS = feedbackDOMapper.selectByExample(feedbackDOExample);
        long l = feedbackDOMapper.countByExample(feedbackDOExample);
        map.put("items",feedbackDOS);
        map.put("total",l);
        return map;
    }
}
