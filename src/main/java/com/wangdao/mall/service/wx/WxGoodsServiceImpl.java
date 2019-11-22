/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/18
 * Time  下午 7:53
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.*;

@Service
@Transactional
public class WxGoodsServiceImpl implements WxGoodsService {

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Autowired
    BrandDOMapper brandDOMapper;

    @Autowired
    CommentDOMapper commentDOMapper;

    @Autowired
    GoodsAttributeDOMapper goodsAttributeDOMapper;

    @Autowired
    GoodsSpecificationDOMapper goodsSpecificationDOMapper;

    @Autowired
    GoodsProductDOMapper goodsProductDOMapper;

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    IssueDOMapper issueDOMapper;

    @Autowired
    SearchHistoryDOMapper searchHistoryDOMapper;

    @Autowired
    CollectDOMapper collectDOMapper;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    FootprintDOMapper footprintDOMapper;


    /**
     * //WX统计商品总数
     * @return
     */
    @Override
    public HashMap<String, Object> queryGoodsCount() {
        GoodsDOExample goodsDOExample = new GoodsDOExample();
        HashMap<String, Object> map = new HashMap<>();

        goodsDOExample.createCriteria().andDeletedEqualTo(false);
        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
        PageInfo<GoodsDO> userPageInfo = new PageInfo<>(goodsDOList);
        long total = userPageInfo.getTotal();

        map.put("goodsCount",total);

        return map;
    }


    /**
     * 获取显示(搜索keyword)(某类目)(某品牌)(是新品)(是热卖)的商品列表
     * response里有desc关键字
     * 把传过来的keyword关键字存入search_history数据表里
     * 需要改进:显示商品时检查商品是否在售卖
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsList(String keyword,Integer categoryId, Integer brandId,Boolean isNew,Boolean isHot,Integer page, Integer size, String sort, String order) {
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        GoodsDOExample goodsDOExample1 = new GoodsDOExample();
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        HashMap<String, Object> map = new HashMap<>();
        List<CategoryDO> filterCategoryList = new ArrayList<>();
        PageHelper.startPage(page,size);

        //封装goodsList和count
        if ( isHot==null && isNew==null && keyword!=null ) {

            //如果用户已经登录，keyword!=null，说明是搜索，先把传过来的keyword关键字存入search_history数据表里
            if (userDO!=null){
                SearchHistoryDO searchHistoryDO = new SearchHistoryDO();
                searchHistoryDO.setUserId(userDO.getId());
                searchHistoryDO.setKeyword(keyword);
                searchHistoryDO.setFrom("wx");
                searchHistoryDO.setAddTime(new Date());
                searchHistoryDO.setUpdateTime(new Date());
                searchHistoryDO.setDeleted(false);
                int insertSearchHistoryDO = searchHistoryDOMapper.insert(searchHistoryDO);
            }

            goodsDOExample1.createCriteria().andDeletedEqualTo(false).andNameLike("%" + keyword + "%");
            goodsDOExample1.setOrderByClause(sort+" "+order);
            List<GoodsDO> goodsDOList1 = goodsDOMapper.selectByExample(goodsDOExample1);

            if (categoryId==0){  //说明是搜索只 关键字 （综合）显示商品List
                PageInfo<GoodsDO> userPageInfo1 = new PageInfo<>(goodsDOList1);
                long total1 = userPageInfo1.getTotal();
                map.put("count",total1);
                map.put("goodsList",goodsDOList1);
            }else {   //categoryId!=0说明是搜索 关键字和和显示某分类下 （分类）显示商品List
                GoodsDOExample goodsDOExample2 = new GoodsDOExample();
                goodsDOExample2.createCriteria().andDeletedEqualTo(false).andCategoryIdEqualTo(categoryId).andNameLike("%" + keyword + "%");
                List<GoodsDO> goodsDOList2 = goodsDOMapper.selectByExample(goodsDOExample2);
                PageInfo<GoodsDO> userPageInfo2 = new PageInfo<>(goodsDOList1);
                long total2 = userPageInfo2.getTotal();
                map.put("count",total2);
                map.put("goodsList",goodsDOList2);
            }
            //封装filterCategoryList
            List<CategoryDO> categoryDOArrayList = new ArrayList<>();
            for (GoodsDO goodsDO : goodsDOList1) {
                CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());
                if (!categoryDO.getDeleted()){
                    categoryDOArrayList.add(categoryDO);
                }
            }
            //把 categoryDOArrayList 里的 Category 对象去重
            Set set = new HashSet();
            for (CategoryDO categoryDO : categoryDOArrayList) {
                if (set.add(categoryDO)) {
                    filterCategoryList.add(categoryDO);
                }
            }
        }else if (isHot==null && isNew==null && brandId==null && keyword==null){   //说明keyword=null,是通过categoryId显示某类下所有商品List   (待改进，返回的filterCategoryList存疑)
            GoodsDOExample goodsDOExample3 = new GoodsDOExample();
            goodsDOExample3.createCriteria().andDeletedEqualTo(false).andCategoryIdEqualTo(categoryId);
            List<GoodsDO> goodsDOList3 = goodsDOMapper.selectByExample(goodsDOExample3);
            PageInfo<GoodsDO> userPageInfo3 = new PageInfo<>(goodsDOList3);
            long total3 = userPageInfo3.getTotal();
            map.put("count",total3);
            map.put("goodsList",goodsDOList3);

            CategoryDOExample categoryDOExample3 = new CategoryDOExample();
            categoryDOExample3.createCriteria().andPidNotEqualTo(0);
            List<CategoryDO> categoryDOS = categoryDOMapper.selectByExample(categoryDOExample3);
            filterCategoryList.addAll(categoryDOS);
        }else if (isHot==null && isNew==null && brandId!=null){
            GoodsDOExample goodsDOExample4 = new GoodsDOExample();
            goodsDOExample4.createCriteria().andDeletedEqualTo(false).andBrandIdEqualTo(brandId);
            List<GoodsDO> goodsDOList4 = goodsDOMapper.selectByExample(goodsDOExample4);
            PageInfo<GoodsDO> userPageInfo4 = new PageInfo<>(goodsDOList4);
            long total4 = userPageInfo4.getTotal();
            map.put("count",total4);
            map.put("goodsList",goodsDOList4);

            //封装filterCategoryList
            List<CategoryDO> categoryDOArrayList4 = new ArrayList<>();
            for (GoodsDO goodsDO : goodsDOList4) {
                CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());
                if (!categoryDO.getDeleted()){
                    categoryDOArrayList4.add(categoryDO);
                }
            }
            //把 categoryDOArrayList 里的 Category 对象去重
            Set set = new HashSet();
            for (CategoryDO categoryDO : categoryDOArrayList4) {
                if (set.add(categoryDO)) {
                    filterCategoryList.add(categoryDO);
                }
            }
        }else {
            GoodsDOExample goodsDOExample5 = new GoodsDOExample();
            if (isNew!=null && isHot==null) {
                goodsDOExample5.createCriteria().andDeletedEqualTo(false).andIsNewEqualTo(true);
            }else {
                goodsDOExample5.createCriteria().andDeletedEqualTo(false).andIsHotEqualTo(true);
            }

            goodsDOExample5.setOrderByClause(sort + " " + order);
            List<GoodsDO> goodsDOList5 = goodsDOMapper.selectByExample(goodsDOExample5);

            if (categoryId==0) {  //说明是只显示全为新品的商品List
                PageInfo<GoodsDO> userPageInfo5 = new PageInfo<>(goodsDOList5);
                long total5 = userPageInfo5.getTotal();
                map.put("count", total5);
                map.put("goodsList", goodsDOList5);
            }else {  //categoryId!=0  //说明是只显示全为新品的某类的商品List
                GoodsDOExample goodsDOExample6 = new GoodsDOExample();
                goodsDOExample6.createCriteria().andDeletedEqualTo(false).andIsNewEqualTo(true).andCategoryIdEqualTo(categoryId);
                goodsDOExample6.setOrderByClause(sort + " " + order);
                List<GoodsDO> goodsDOList6 = goodsDOMapper.selectByExample(goodsDOExample6);
                PageInfo<GoodsDO> userPageInfo6 = new PageInfo<>(goodsDOList6);
                long total6 = userPageInfo6.getTotal();
                map.put("count", total6);
                map.put("goodsList", goodsDOList6);
            }

            //封装filterCategoryList
            List<CategoryDO> categoryDOArrayList5 = new ArrayList<>();
            for (GoodsDO goodsDO : goodsDOList5) {
                CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());
                if (!categoryDO.getDeleted()) {
                    categoryDOArrayList5.add(categoryDO);
                }
            }
            //把 categoryDOArrayList 里的 Category 对象去重
            Set set = new HashSet();
            for (CategoryDO categoryDO : categoryDOArrayList5) {
                if (set.add(categoryDO)) {
                    filterCategoryList.add(categoryDO);
                }
            }
        }
        map.put("filterCategoryList",filterCategoryList);
        return map;
    }


    /**
     * WX获得分类数据(显示某一级类目下所有二级类目)
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsCategory(Integer id) {
        HashMap<String, Object> map = new HashMap<>();

        CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(id);
        if (categoryDO.getPid()==0){//pid是0，说明它是一级父类目
            CategoryDOExample categoryDOExample = new CategoryDOExample();
            categoryDOExample.createCriteria().andDeletedEqualTo(false).andPidEqualTo(categoryDO.getId());
            List<CategoryDO> brotherCategory = categoryDOMapper.selectByExample(categoryDOExample);

            map.put("currentCategory",brotherCategory.get(0));//默认显示currentCategory是List里第一个二级目录
            map.put("brotherCategory",brotherCategory);
            map.put("parentCategory",categoryDO);
        }else {//pid不是0，说明它是二级子类目也是当前类目
            map.put("currentCategory",categoryDO);//pid不是0，说明它是二级子类目也是当前类目
            CategoryDO parentCategory = categoryDOMapper.selectByPrimaryKey(categoryDO.getPid());//找出其父类目
            map.put("parentCategory",parentCategory);
            CategoryDOExample categoryDOExample1 = new CategoryDOExample();
            categoryDOExample1.createCriteria().andDeletedEqualTo(false).andPidEqualTo(parentCategory.getId());
            List<CategoryDO> categoryDOS = categoryDOMapper.selectByExample(categoryDOExample1);
            map.put("brotherCategory",categoryDOS);
        }
        return map;
    }


    /**
     * 获取商品详情
     * "groupon": [],//团购商品列表  和 "shareImage": "",这两个属性不知道该返回啥，需改进
     * "issue": []    //所有的问答  //我是直接返回数据表的所有issue对象，不知道是否正确(老师项目也是全返回)
     * "userHasCollect": 0,    //返回的这个属性，0为没收藏关注，1为收藏关注，需要注意，先检查登录状态，如果用户没登录统一为0
     * "comment"  商品评论   //商品详情页只显示两条评论，但是不知道这两条以什么顺序显示
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsDetail(Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //封装 userHasCollect   0为没收藏关注，1为收藏关注了
        if (userDO!=null){
            //登录状态下，查询collect数据表里是否绑定此用户userid的商品id和delete为false属性
            CollectDOExample collectDOExample = new CollectDOExample();
            collectDOExample.createCriteria().andUserIdEqualTo(userDO.getId()).andValueIdEqualTo(id);
            List<CollectDO> collectDOS1 = collectDOMapper.selectByExample(collectDOExample);
            if (collectDOS1.size()>0) {
                for (CollectDO collectDO : collectDOS1) {
                    if (collectDO.getDeleted()) { //登录状态下，查到表里有收藏此商品,但是已经逻辑delete
                        map.put("userHasCollect", 0);
                    }else {
                        map.put("userHasCollect", 1);//登录状态下，查到表里有收藏此商品,delete为false
                    }
                }
            }else {
                map.put("userHasCollect",0);  //登录状态下，查到表里没有收藏此商品
            }
        }else {
            map.put("userHasCollect", 0);//无登录状态下全部为0
        }

        //封装 shareImage: ""  不知道是啥，返回空串，后续需要改进
        map.put("shareImage","");

        //封装 info
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(id);
        map.put("info",goodsDO);

        //把浏览足迹添加或者更新至足迹数据表
        if (userDO!=null){
            FootprintDOExample footprintDOExample = new FootprintDOExample();
            footprintDOExample.createCriteria().andGoodsIdEqualTo(id).andUserIdEqualTo(userDO.getId());
            List<FootprintDO> footprintDOS = footprintDOMapper.selectByExample(footprintDOExample);
            if (footprintDOS.size()>0) {   //有则修改最后浏览时间
                for (FootprintDO footprintDO : footprintDOS) {
                    footprintDO.setUpdateTime(new Date());
                    footprintDO.setDeleted(false);
                    footprintDOMapper.updateByPrimaryKeySelective(footprintDO);
                }
            }else {   //没有则新建足迹对象
                FootprintDO footprintDO = new FootprintDO();
                footprintDO.setUserId(userDO.getId());
                footprintDO.setGoodsId(id);
                footprintDO.setAddTime(new Date());
                footprintDO.setUpdateTime(new Date());
                footprintDO.setDeleted(false);
                int insertSelectiveNum2 = footprintDOMapper.insertSelective(footprintDO);
            }
        }


        //封装groupon[] 当前商品的团购规则表 注意团购过期时间 和delete
        ArrayList<GrouponRulesDO> grouponRulesDOS = new ArrayList<>();

        GrouponRulesDOExample grouponRulesDOExample = new GrouponRulesDOExample();
        grouponRulesDOExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(id);
        List<GrouponRulesDO> grouponRulesDOList = grouponRulesDOMapper.selectByExample(grouponRulesDOExample);

        for (GrouponRulesDO grouponRulesDO : grouponRulesDOList) {
            if (grouponRulesDO.getExpireTime() != null) {    //应该检查是否过期
                if (!(new Date().after(grouponRulesDO.getExpireTime()))) {  //没过期
                    grouponRulesDOS.add(grouponRulesDO);
                }
            }
        }
        map.put("groupon",grouponRulesDOS);

        //封装全部问答 issue 后续根据情况需要改进
        IssueDOExample issueDOExample = new IssueDOExample();
        issueDOExample.createCriteria().andDeletedEqualTo(false);
        List<IssueDO> issueDOS = issueDOMapper.selectByExample(issueDOExample);
        map.put("issue",issueDOS);

        //封装 attribute
        GoodsAttributeDOExample goodsAttributeDOExample = new GoodsAttributeDOExample();
        goodsAttributeDOExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsAttributeDO> goodsAttributeDOS = goodsAttributeDOMapper.selectByExample(goodsAttributeDOExample);
        map.put("attribute",goodsAttributeDOS);

        //封装 brand
//        BrandDOExample brandDOExample = new BrandDOExample();
//        brandDOExample.createCriteria().andDeletedEqualTo(false).andIdEqualTo(goodsDO.getBrandId());
//        List<BrandDO> brandDOS = brandDOMapper.selectByExample(brandDOExample);
//        map.put("brand",brandDOS.get(0));
        BrandDO brandDO = brandDOMapper.selectByPrimaryKey(goodsDO.getBrandId());
        map.put("brand",brandDO);

        //封装 productList
        GoodsProductDOExample goodsProductDOExample = new GoodsProductDOExample();
        goodsProductDOExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsDO.getId());
        List<GoodsProductDO> goodsProductDOS = goodsProductDOMapper.selectByExample(goodsProductDOExample);
        map.put("productList",goodsProductDOS);

        //封装"comment"商品评论,详情页只显示两条评论，但是不知道这两条以什么顺序显示,我这里只显示List里前两条
        HashMap<String, Object> commentMap = new HashMap<>();
        List<GoodsDetailResponseComment> data = new ArrayList<>();
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria().andDeletedEqualTo(false).andValueIdEqualTo(id);
        List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);
        int i=0;
        for (CommentDO commentDO : commentDOS) {
            if (i >= 2){
                break;
            }
            UserDO userDO1 = userDOMapper.selectByPrimaryKey(commentDO.getUserId());
            GoodsDetailResponseComment goodsDetailResponseComment = new GoodsDetailResponseComment();
            goodsDetailResponseComment.setAddTime(commentDO.getAddTime());
            goodsDetailResponseComment.setPicList(commentDO.getPicUrls());
            goodsDetailResponseComment.setNickname(userDO1.getUsername());
            goodsDetailResponseComment.setId(commentDO.getId());
            goodsDetailResponseComment.setAvatar(userDO1.getAvatar()); //用户头像
            goodsDetailResponseComment.setContent(commentDO.getContent());
            data.add(goodsDetailResponseComment);
            i++;
        }
        PageInfo<CommentDO> userPageInfo = new PageInfo<>(commentDOS);
        long count = userPageInfo.getTotal();
        commentMap.put("data",data);
        commentMap.put("count",count);
        map.put("comment",commentMap);

        //封装 specificationList
        ArrayList<Map> specificationList = new ArrayList<>();
        //先查出该商品所有的未删除的规格List
        GoodsSpecificationDOExample goodsSpecificationDOExample = new GoodsSpecificationDOExample();
        goodsSpecificationDOExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsDO.getId());
        List<GoodsSpecificationDO> goodsSpecificationDOList = goodsSpecificationDOMapper.selectByExample(goodsSpecificationDOExample);
        HashSet<String> name = new HashSet<>();
        for (GoodsSpecificationDO goodsSpecificationDO : goodsSpecificationDOList) {
            name.add(goodsSpecificationDO.getSpecification());
        }
        for (String s : name) {  //name 相同的specificationList对象封装成一个valueList
            GoodsSpecificationDOExample goodsSpecificationDOExample1 = new GoodsSpecificationDOExample();
            goodsSpecificationDOExample1.createCriteria().andDeletedEqualTo(false).andSpecificationEqualTo(s).andGoodsIdEqualTo(goodsDO.getId());
            List<GoodsSpecificationDO> valueList = goodsSpecificationDOMapper.selectByExample(goodsSpecificationDOExample1);

            HashMap<String, Object> specificationMap = new HashMap<>();
            specificationMap.put("name",s);
            specificationMap.put("valueList",valueList);
            specificationList.add(specificationMap);
        }
        map.put("specificationList",specificationList);

        return map;
    }


    /**
     * 商品详情页的关联商品(广告)
     * 该商品详情页的关联商品最多只显示该商品同类目下6个商品，顺序不清楚，可以再次显示该商品自己
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsRelated(Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(id);//获取商品详情页的商品对象
        GoodsDOExample goodsDOExample = new GoodsDOExample();
        goodsDOExample.createCriteria().andDeletedEqualTo(false).andCategoryIdEqualTo(goodsDO.getCategoryId());
        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
        ArrayList<GoodsDO> goodsList = new ArrayList<>();
        int i=0;
        for (GoodsDO aDo : goodsDOList) {    //因为顺序不清楚，所有只显示前6个同类商品
            if (i >= 6){
                break;
            }
            goodsList.add(aDo);
            i++;
        }
        map.put("goodsList",goodsList);
        return map;
    }
}
