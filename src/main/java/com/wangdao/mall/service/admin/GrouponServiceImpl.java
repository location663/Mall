package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.GoodsDOMapper;
import com.wangdao.mall.mapper.GrouponDOMapper;
import com.wangdao.mall.mapper.GrouponRulesDOMapper;
import com.wangdao.mall.mapper.OrderGoodsDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GrouponServiceImpl implements GrouponService {

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    @Override
    public Map listGrouponRecord(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        List<GrouponRecordVO> list = new ArrayList<>();

        List<GrouponDO> grouponDOS;
        if (null != pageDTO.getGoodsId()) {
            grouponDOS = grouponDOMapper.listByGoodsId(pageDTO.getGoodsId(), pageDTO.getSort(), pageDTO.getOrder());
        } else {
            GrouponDOExample grouponDOExample = new GrouponDOExample();
            grouponDOExample.createCriteria().andDeletedEqualTo(false);
            grouponDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
            grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
        }

        for (GrouponDO grouponDO : grouponDOS) {
            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
            GrouponRecordVO grouponRecordVO = new GrouponRecordVO();
            grouponRecordVO.setGoods(goodsDO);
            grouponRecordVO.setGroupon(grouponDO);
            grouponRecordVO.setRules(grouponRulesDO);
            List<SubGrouponDTO> subGrouponDTOS = grouponDOMapper.listSubGroupon(grouponDO.getId());
            grouponRecordVO.setSubGroupons(subGrouponDTOS);
            list.add(grouponRecordVO);
        }
        PageInfo<GrouponDO> grouponDOPageInfo = new PageInfo<>(grouponDOS);
        long total = grouponDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("items", list);
        map.put("total", total);
        return map;
    }

    @Override
    public Map listGrouponRules(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        GrouponRulesDOExample grouponRulesDOExample = new GrouponRulesDOExample();
        GrouponRulesDOExample.Criteria criteria = grouponRulesDOExample.createCriteria().andDeletedEqualTo(false);
        if (null != pageDTO.getGoodsId()){
            criteria.andGoodsIdEqualTo(pageDTO.getGoodsId());
        }
        List<GrouponRulesDO> grouponRulesDOS = grouponRulesDOMapper.selectByExample(grouponRulesDOExample);
        PageInfo<GrouponRulesDO> grouponRulesDOPageInfo = new PageInfo<>(grouponRulesDOS);
        long total = grouponRulesDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("items", grouponRulesDOS);
        map.put("total", total);
        return map;
    }

    @Override
    public GrouponRulesDO createGrouponRules(GrouponRulesDO grouponRulesDO) {
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
        grouponRulesDO.setAddTime(new Date());
        grouponRulesDO.setUpdateTime(new Date());
        grouponRulesDO.setGoodsName(goodsDO.getName());
        grouponRulesDO.setPicUrl(goodsDO.getPicUrl());

        int i = grouponRulesDOMapper.insertSelective(grouponRulesDO);

        return grouponRulesDO;
    }
}
