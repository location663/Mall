package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
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
    SystemDOMapper systemDOMapper;

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
        PageHelper.startPage(1,6);
        Map<String, Object> map = new HashMap<>();
        GoodsDOExample goodsDOExample = new GoodsDOExample();

        // 取出热卖商品
        int hotKeyValue =Integer.valueOf(systemDOMapper.selectByPrimaryKey(9).getKeyValue());
        List<GoodsDO> hotGoodsDOList = null;
        if (hotKeyValue != 0) {
            PageHelper.startPage(1, hotKeyValue);
            goodsDOExample.createCriteria().andIsHotEqualTo(true).andDeletedEqualTo(false);
            hotGoodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
            goodsDOExample.clear();
        }

        //取出新品
        int newKeyValue = Integer.valueOf(systemDOMapper.selectByPrimaryKey(2).getKeyValue());
        List<GoodsDO> newGoodsDOList = null;
        if (newKeyValue != 0) {
            PageHelper.startPage(1, newKeyValue);
            goodsDOExample.createCriteria().andIsNewEqualTo(true).andDeletedEqualTo(false);
            newGoodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
            goodsDOExample.clear();
        }

        //所有优惠券信息
        CouponDOExample couponDOExample = new CouponDOExample();
        couponDOExample.createCriteria().andDeletedEqualTo(false).andTotalGreaterThanOrEqualTo(0);
        List<CouponDO> couponDOList = couponDOMapper.selectByExample(couponDOExample);
        couponDOExample.clear();

        //所有类目信息
        int categoryKeyValue = Integer.valueOf(systemDOMapper.selectByPrimaryKey(13).getKeyValue());
        List<CategoryDO> categoryDOList = null;
        if (categoryKeyValue != 0) {
            PageHelper.startPage(1, categoryKeyValue);
            CategoryDOExample categoryDOExample = new CategoryDOExample();
            categoryDOExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L1");
            categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
            categoryDOExample.clear();
        }

        //团购信息
        GrouponDOExample grouponDOExample = new GrouponDOExample();
        grouponDOExample.createCriteria().andDeletedEqualTo(false);
        List<GrouponDO> grouponDOList = grouponDOMapper.selectByExample(grouponDOExample);
        List<GrouponRecordVO> grouponRecordVOList = getGrouponRecordVOList(grouponDOList);
        grouponDOExample.clear();

        //广告信息
        AdDOExample adDOExample = new AdDOExample();
        adDOExample.createCriteria().andDeletedEqualTo(false);
        List<AdDO> adDOList = adDOMapper.selectByExample(adDOExample);
        adDOExample.clear();

        //品牌制造商
        int brandKeyValue = Integer.valueOf(systemDOMapper.selectByPrimaryKey(15).getKeyValue());
        List<BrandDO> brandDOList = null;
        if (brandKeyValue != 0) {
            PageHelper.startPage(1, (brandKeyValue));
            BrandDOExample brandDOExample = new BrandDOExample();
            brandDOExample.createCriteria().andDeletedEqualTo(false);
            brandDOList = brandDOMapper.selectByExample(brandDOExample);
            brandDOExample.clear();
        }

        //标题信息
        int topicKeyValue = Integer.valueOf(systemDOMapper.selectByPrimaryKey(16).getKeyValue());
        List<TopicDO> topicDOList = null;
        if (topicKeyValue != 0){
            PageHelper.startPage(1,topicKeyValue);
            TopicDOExample topicDOExample = new TopicDOExample();
            topicDOExample.createCriteria().andDeletedEqualTo(false);
            topicDOList = topicDOMapper.selectByExample(topicDOExample);
            topicDOExample.clear();
        }

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

    /**
     * 获取各类目下的商品
     * @param list
     * @return
     */
    public List<CategoryGoodsVO> getCategoryGoodsVOList(List<CategoryDO> list){
        List<CategoryGoodsVO> categoryGoodsVOList = new ArrayList<>();
        if (list != null) {
            for (CategoryDO categoryDO : list) {
                List<GoodsDO> goodsDOList = new ArrayList<>();
                CategoryGoodsVO categoryGoodsVO = new CategoryGoodsVO();
                categoryGoodsVO.setId(categoryDO.getId());
                categoryGoodsVO.setName(categoryDO.getName());
//            List<GoodsDO> goodsDOList = goodsDOMapper.selectByCategoryDOList(categoryDO.getName());
//            categoryGoodsVO.setGoodsList(goodsDOList);
//            categoryGoodsVOList.add(categoryGoodsVO);

                CategoryDOExample categoryDOExample = new CategoryDOExample();
                categoryDOExample.createCriteria().andPidEqualTo(categoryDO.getId());
                List<CategoryDO> categoryDOS = categoryDOMapper.selectByExample(categoryDOExample);
                for (CategoryDO aDo : categoryDOS) {
                    GoodsDOExample goodsDOExample = new GoodsDOExample();
                    goodsDOExample.createCriteria().andCategoryIdEqualTo(aDo.getId());
                    List<GoodsDO> goodsDOList1 = goodsDOMapper.selectByExample(goodsDOExample);
                    goodsDOList.addAll(goodsDOList1);
                }
                int i = 0;
                String goodsKeyValue = systemDOMapper.selectByPrimaryKey(11).getKeyValue();
                List<GoodsDO> resultList = new ArrayList<>();
                for (GoodsDO goodsDO : goodsDOList) {
                    if (i >= Integer.valueOf(goodsKeyValue)) {
                        break;
                    }
                    resultList.add(goodsDO);
                    i++;
                }
                categoryGoodsVO.setGoodsList(resultList);
                categoryGoodsVOList.add(categoryGoodsVO);

//            GoodsDOExample goodsDOExample = new GoodsDOExample();
//            goodsDOExample.createCriteria().andCategoryIdEqualTo(categoryDO.getId()).andDeletedEqualTo(false);
//            List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
//            categoryGoodsVO.setGoodsDOList(goodsDOList);
//            categoryGoodsVOList.add(categoryGoodsVO);
            }
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
