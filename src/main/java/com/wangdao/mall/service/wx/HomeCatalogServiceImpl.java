package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeCatalogServiceImpl implements HomeCatalogService {

    @Autowired
    TopicDOMapper topicDOMapper;

    @Autowired
    BrandDOMapper brandDOMapper;

    @Autowired
    AdDOMapper adDOMapper;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Autowired
    CouponDOMapper couponDOMapper;

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Override
    public Map<String, Object> getHomeIndex() {
        Map<String, Object> map = new HashMap<>();
        GoodsDOExample goodsDOExample = new GoodsDOExample();

        // 取出热卖商品
        goodsDOExample.createCriteria().andIsHotEqualTo(true).andDeletedEqualTo(false);
        List<GoodsDO> hotGoodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
        goodsDOExample.clear();

        //取出新品
        goodsDOExample.createCriteria().andIsNewEqualTo(true).andDeletedEqualTo(false);
        List<GoodsDO> newGoodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
        goodsDOExample.clear();

        //所有优惠券信息
        CouponDOExample couponDOExample = new CouponDOExample();
        couponDOExample.createCriteria().andDeletedEqualTo(false);
        List<CouponDO> couponDOList = couponDOMapper.selectByExample(couponDOExample);
        couponDOExample.clear();

        //所有类目信息
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L1");
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
        categoryDOExample.clear();

        //团购信息
        GrouponDOExample grouponDOExample = new GrouponDOExample();
        grouponDOExample.createCriteria().andDeletedEqualTo(false);
        List<GrouponDO> grouponDOList = grouponDOMapper.selectByExample(grouponDOExample);
        List<GrouponRecordVO> grouponRecordVOList = getGrouponRecordVOList(grouponDOList);

        //广告信息
        AdDOExample adDOExample = new AdDOExample();
        adDOExample.createCriteria().andDeletedEqualTo(false);
        List<AdDO> adDOList = adDOMapper.selectByExample(adDOExample);

        //品牌制造商
        BrandDOExample brandDOExample = new BrandDOExample();
        brandDOExample.createCriteria().andDeletedEqualTo(false);
        List<BrandDO> brandDOList = brandDOMapper.selectByExample(brandDOExample);

        //标题信息
        TopicDOExample topicDOExample = new TopicDOExample();
        topicDOExample.createCriteria().andDeletedEqualTo(false);
        List<TopicDO> topicDOList = topicDOMapper.selectByExample(topicDOExample);

        //各类目下的商品
        List<CategoryGoodsVO> categoryGoodsVOList = getCategoryGoodsVOList(categoryDOList);

        map.put("hotGoodsList",hotGoodsDOList);
        map.put("newGoodsList",newGoodsDOList);
        map.put("couponList",couponDOList);
        map.put("channel",categoryDOList);
        map.put("grouponList",grouponRecordVOList);
        map.put("banner",adDOList);
        map.put("brandList",brandDOList);
        map.put("topicList",topicDOList);
        map.put("floorGoodsList",categoryGoodsVOList);
        return map;
    }

    public List<CategoryGoodsVO> getCategoryGoodsVOList(List<CategoryDO> list){
        List<CategoryGoodsVO> categoryGoodsVOList = new ArrayList<>();
        for (CategoryDO categoryDO : list) {
            CategoryGoodsVO categoryGoodsVO = new CategoryGoodsVO();
            categoryGoodsVO.setId(categoryDO.getId());
            categoryGoodsVO.setName(categoryDO.getName());
            GoodsDOExample goodsDOExample = new GoodsDOExample();
            goodsDOExample.createCriteria().andCategoryIdEqualTo(categoryDO.getId()).andDeletedEqualTo(false);
            List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
            categoryGoodsVO.setGoodsDOList(goodsDOList);
            categoryGoodsVOList.add(categoryGoodsVO);
        }
        return categoryGoodsVOList;
    }

    /**
     * 获取grouponList
     * @param list
     * @return
     */
    public List<GrouponRecordVO> getGrouponRecordVOList(List<GrouponDO> list){
        List<GrouponRecordVO> grouponRecordVOList = new ArrayList<>();
        for (GrouponDO grouponDO : list) {
            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponDO.getRulesId());
            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(grouponRulesDO.getGoodsId());
            GrouponRecordVO grouponRecordVO = new GrouponRecordVO();
            grouponRecordVO.setGoods(goodsDO);
            grouponRecordVO.setGroupon_member(grouponRulesDO.getDiscountMember());
            grouponRecordVO.setGroupon_price(goodsDO.getRetailPrice().doubleValue()-grouponRulesDO.getDiscount().doubleValue());
            grouponRecordVOList.add(grouponRecordVO);
        }
        return grouponRecordVOList;
    }
}
