package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.exception.NoSuchGoodsException;
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

    /**
     * 团购活动列表
     * @param pageDTO
     * @return
     */
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

    /**
     * 团购规则列表
     * @param pageDTO
     * @return
     */
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

    /**
     * 创建团购规则
     * @param grouponRulesDO
     * @return
     */
    @Override
    public GrouponRulesDO createGrouponRules(GrouponRulesDO grouponRulesDO) throws NoSuchGoodsException {
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
        if (goodsDO == null){
            throw new NoSuchGoodsException();
        }
        grouponRulesDO.setAddTime(new Date());
        grouponRulesDO.setUpdateTime(new Date());
        grouponRulesDO.setGoodsName(goodsDO.getName());
        grouponRulesDO.setPicUrl(goodsDO.getPicUrl());

        int i = grouponRulesDOMapper.insertSelective(grouponRulesDO);

        return grouponRulesDO;
    }

    /**
     * 更新团购规则
     * @param grouponRulesDO
     * @return
     * @throws NoSuchGoodsException
     */
    @Override
    public int updateGrouponRules(GrouponRulesDO grouponRulesDO) throws NoSuchGoodsException {
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
        if (null == goodsDO){
            throw new NoSuchGoodsException();
        }
        grouponRulesDO.setUpdateTime(new Date());
        int i = grouponRulesDOMapper.updateByPrimaryKey(grouponRulesDO);
        return i;
    }

    /**
     * 删除团购规则
     * @param grouponRulesDO
     * @return
     */
    @Override
    public int deleteGrouponRules(GrouponRulesDO grouponRulesDO) {
        grouponRulesDO.setUpdateTime(new Date());
        grouponRulesDO.setDeleted(true);
        int i = grouponRulesDOMapper.updateByPrimaryKey(grouponRulesDO);
        return i;
    }
}