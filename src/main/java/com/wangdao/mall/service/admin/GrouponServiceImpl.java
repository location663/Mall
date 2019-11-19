package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.exception.NoSuchGoodsException;
import com.wangdao.mall.mapper.*;
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




    @Autowired
    OrderDOMapper orderDOMapper;

//    /**
//     * 团购活动列表
//     * @param pageDTO
//     * @return
//     */
//    @Override
//    public Map listGrouponRecord(RequestPageDTO pageDTO) {
//        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());
//
//        List<GrouponRecordVO> list = new ArrayList<>();
//
//        List<GrouponDO> grouponDOS;
//        if (null != pageDTO.getGoodsId()) {
//            grouponDOS = grouponDOMapper.listByGoodsId(pageDTO.getGoodsId(), pageDTO.getSort(), pageDTO.getOrder());
//        } else {
//            GrouponDOExample grouponDOExample = new GrouponDOExample();
//            grouponDOExample.createCriteria().andDeletedEqualTo(false);
//            grouponDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
//            grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
//        }
//
//        for (GrouponDO grouponDO : grouponDOS) {
//            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
//            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
//            GrouponRecordVO grouponRecordVO = new GrouponRecordVO();
//            grouponRecordVO.setGoods(goodsDO);
//            grouponRecordVO.setGroupon(grouponDO);
//            grouponRecordVO.setRules(grouponRulesDO);
//            List<SubGrouponDTO> subGrouponDTOS = grouponDOMapper.listSubGroupon(grouponDO.getId());
//            grouponRecordVO.setSubGroupons(subGrouponDTOS);
//            list.add(grouponRecordVO);
//        }
//        PageInfo<GrouponDO> grouponDOPageInfo = new PageInfo<>(grouponDOS);
//        long total = grouponDOPageInfo.getTotal();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("items", list);
//        map.put("total", total);
//        return map;
//    }

    /**根据是否有goodsId分发查询出不同的团购活动的相关信息
     * @param page
     * @param limit
     * @param goodsId
     * @return
     */
    @Override
    public Map<String, Object> queryGrouponList(Integer page, Integer limit, Integer goodsId) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, limit);
        //封装所有数据的最外层的一个map,key为items和total
        //items是一个list,list里面每个元素是一个map,key分别为goods, groupon, rules, subGroupon(这是一个[])
        HashMap<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<GrouponDO> grouponDOList = new ArrayList<>();
        if (goodsId == null) {
            GrouponDOExample grouponDOExample = new GrouponDOExample();
            grouponDOExample.setOrderByClause("add_time desc");
            GrouponDOExample.Criteria criteria = grouponDOExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            grouponDOList = grouponDOMapper.selectByExample(grouponDOExample);
            for (GrouponDO grouponDO : grouponDOList) {
                //获取rules
                Integer rulesId = grouponDO.getRulesId();
                GrouponRulesDO rule = grouponRulesDOMapper.selectByPrimaryKey(rulesId);
                //根据grouponRulesDO查询出goodsId,再构造出goods
                Integer goodsId1 = rule.getGoodsId();
                GoodsDO goods = goodsDOMapper.selectByPrimaryKey(goodsId1);
                //构造subGroupons
                Integer orderId = grouponDO.getOrderId();
                Integer userId = grouponDO.getUserId();
                SubGrouponDTO subGrouponDTO = new SubGrouponDTO();
                subGrouponDTO.setOrderId(orderId);
                subGrouponDTO.setUserId(userId);
                List<SubGrouponDTO> subGrouponDTOList = new ArrayList<>();
                subGrouponDTOList.add(subGrouponDTO);
                //构造出items里的每个元素，用对象应该不能再回传给浏览器的数据里显示出goods:, groupon:, rules:, subGroupons: ，先用map来封装
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("groupon", grouponDO);
                map1.put("rules", rule);
                map1.put("goods", goods);
                map1.put("subGroupons", subGrouponDTOList);
                mapList.add(map1);
            }
        } else if (goodsId != null && !"".equals(goodsId)) {
            //goodsId不为null时
            //goods
            GoodsDO goods = goodsDOMapper.selectByPrimaryKey(goodsId);
            //根据goodsId,联立rules表和groupon表，查询出合要求的groupon
            grouponDOList = grouponDOMapper.selectByGoodsId(goodsId);
            for (GrouponDO grouponDO : grouponDOList) {
                //rules
                Integer rulesId = grouponDO.getRulesId();
                GrouponRulesDO rule = grouponRulesDOMapper.selectByPrimaryKey(rulesId);
                //subGroupons
                Integer userId = grouponDO.getUserId();
                Integer orderId = grouponDO.getOrderId();
                SubGrouponDTO subGrouponDTO = new SubGrouponDTO();
                subGrouponDTO.setOrderId(orderId);
                subGrouponDTO.setUserId(userId);
                List<SubGrouponDTO> subGrouponDTOList = new ArrayList<>();
                subGrouponDTOList.add(subGrouponDTO);

                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("groupon", grouponDO);
                map1.put("rules", rule);
                map1.put("goods", goods);
                map1.put("subGroupons", subGrouponDTOList);
                mapList.add(map1);
            }
        }
        PageInfo<GrouponDO> grouponDOPageInfo = new PageInfo<>(grouponDOList);
        long total = grouponDOPageInfo.getTotal();
        map.put("items", mapList);
        map.put("total", total);
        return map;
    }


//    public Map listGrouponRecord(RequestPageDTO pageDTO) {
//        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());
//
//        List<GrouponRecordVO> list = new ArrayList<>();
//
//        List<GrouponDO> grouponDOS;
//        if (null != pageDTO.getGoodsId()) {
//            grouponDOS = grouponDOMapper.listByGoodsId(pageDTO.getGoodsId(), pageDTO.getSort(), pageDTO.getOrder());
//        } else {
////            GrouponDOExample grouponDOExample = new GrouponDOExample();
////            grouponDOExample.createCriteria().andDeletedEqualTo(false);
////            grouponDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
////            grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
//             grouponDOS = grouponDOMapper.listByGoodsId(pageDTO.getGoodsId(), pageDTO.getSort(), pageDTO.getOrder());
//        }
//
//        for (GrouponDO grouponDO : grouponDOS) {
//            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
//            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
//            GrouponRecordVO grouponRecordVO = new GrouponRecordVO();
//            grouponRecordVO.setGoods(goodsDO);
//            grouponRecordVO.setGroupon(grouponDO);
//            grouponRecordVO.setRules(grouponRulesDO);
//            List<SubGrouponDTO> subGrouponDTOS = grouponDOMapper.listSubGroupon(grouponDO.getGrouponId());
//            grouponRecordVO.setSubGroupons(subGrouponDTOS);
//            list.add(grouponRecordVO);
//
//        }
//
//    }

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
