package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.handler.String2ArrayTypeHandler;
import com.wangdao.mall.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WxGrouponServiceImpl implements WxGrouponService {

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    HomeCatalogServiceImpl homeCatalogServiceImpl;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    @Autowired
    WxOrderServiceImpl orderService;

    /**
     * 团购列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> listGroupon(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getSize());

        GrouponDOExample grouponDOExample = new GrouponDOExample();
        grouponDOExample.createCriteria().andDeletedEqualTo(false);
        List<GrouponDO> grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
        List<GrouponRecordVO> grouponRecordVOList = homeCatalogServiceImpl.getGrouponRecordVOList(grouponDOS);
//        PageInfo<GrouponDO> grouponDOPageInfo = new PageInfo<>(grouponDOS);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", grouponRecordVOList.size());
        map.put("data", grouponRecordVOList);
        return map;
    }

    /**
     * 团购详情
     * @param grouponId
     * @return
     */
    @Override
    public GrouponDetailVO selectById(Integer grouponId) {
        GrouponDetailVO grouponDetailVO = new GrouponDetailVO();
        GrouponDO grouponDO = grouponDOMapper.selectByPrimaryKey(grouponId);
        UserDO userDO = userDOMapper.selectByPrimaryKey(grouponDO.getCreatorUserId());
        List<UserDO> userDOS = grouponDOMapper.listJoiners(grouponId);
        GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
        BaseReqVo orderDetail = orderService.getOrderDetail(grouponDO.getOrderId());
        Map data = (Map) orderDetail.getData();
        Object orderInfo = data.get("orderInfo");
        List<OrderGoodsDO> orderGoods = (List<OrderGoodsDO>)data.get("orderGoods");
        for (OrderGoodsDO orderGood : orderGoods) {
            orderGood.setGoodsSpecificationValues(orderGood.getSpecifications());
            orderGood.setRetailPrice(orderGood.getPrice().doubleValue());
        }

        grouponDetailVO.setOrderGoods( orderGoods);
        grouponDetailVO.setOrderInfo((OrderInfoVO) orderInfo);
        grouponDetailVO.setCreator(userDO);
        grouponDetailVO.setGroupon(grouponDO);
        grouponDetailVO.setJoiners(userDOS);
        grouponDetailVO.setLinkGrouponId(grouponId);
        grouponDetailVO.setRules(grouponRulesDO);
        return grouponDetailVO;
    }

    /**
     * 展示用户的团购
     * @param showType 0表示该用户发起的团购，1表示该用户参加的团购
     * @return
     */
    @Override
    public Map listByUseridAndShowtype(Integer showType) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        GrouponDOExample grouponDOExample = new GrouponDOExample();
        GrouponDOExample.Criteria criteria = grouponDOExample.createCriteria()
                .andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
        if (showType == 0){
            criteria.andCreatorUserIdEqualTo(userDO.getId());
        } else {
            criteria.andCreatorUserIdNotEqualTo(userDO.getId());
        }
        List<GrouponDO> grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
        ArrayList<GrouponDetailVO> list = new ArrayList<>();
        for (GrouponDO grouponDO : grouponDOS) {
            GrouponDetailVO grouponDetailVO = new GrouponDetailVO();
            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
            grouponDetailVO.setActualPrice(goodsDO.getRetailPrice().doubleValue());
            UserDO userDO1 = userDOMapper.selectByPrimaryKey(grouponDO.getCreatorUserId());
            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(grouponDO.getOrderId());
            grouponDetailVO.setCreator(userDO1.getUsername());
            List<GoodsDO> goodsDOS = new ArrayList<>();
            goodsDOS.add(goodsDO);
            grouponDetailVO.setGoodsList(goodsDOS);
            grouponDetailVO.setGroupon(grouponDO);
            grouponDetailVO.setId(grouponDO.getId());
            grouponDetailVO.setIsCreator(userDO.getId() == grouponDO.getCreatorUserId());
            grouponDetailVO.setJoinerCount(grouponDOMapper.countJoinersByGrouponID(grouponDO.getGrouponId()));
            grouponDetailVO.setOrderId(grouponDO.getOrderId());
            grouponDetailVO.setOrderSn(orderDO.getOrderSn());
            grouponDetailVO.setOrderStatusText(orderDO.getStatusMap().get((int)orderDO.getOrderStatus()));
            grouponDetailVO.setRules(grouponRulesDO);
            list.add(grouponDetailVO);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("count", list.size());
        return map;
    }

}
