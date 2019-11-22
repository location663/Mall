/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/15
 * Time  下午 5:24
 */

package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

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



    /**
     * 商品列表
     * @param page
     * @param limit
     * @param goodsSnInt
     * @param nameStr
     * @param sort
     * @param order
     * @return
     */
    @Override
    public HashMap<String, Object> queryGoodsList(Integer page, Integer limit,Integer goodsSnInt,String nameStr, String sort, String order) {
        GoodsDOExample goodsDOExample = new GoodsDOExample();
        HashMap<String, Object> map = new HashMap<>();
        Integer goodsSn=null;
        goodsSn=goodsSnInt;
        String name=null;
        name=nameStr;
        PageHelper.startPage(page,limit);

        if (goodsSn!=null && name!=null){
            goodsDOExample.createCriteria().andDeletedEqualTo(false).andGoodsSnLike("%"+ goodsSn +"%").andNameLike("%"+ name +"%");
        }else if (goodsSn==null && name!=null){
            goodsDOExample.createCriteria().andDeletedEqualTo(false).andNameLike("%"+ name +"%");
        }else if (goodsSn!=null ){
            goodsDOExample.createCriteria().andDeletedEqualTo(false).andGoodsSnLike("%"+ goodsSn +"%");
        }else {
            goodsDOExample.createCriteria().andDeletedEqualTo(false);
        }

        goodsDOExample.setOrderByClause(sort+" "+order);

        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExampleWithBLOBs(goodsDOExample);

        PageInfo<GoodsDO> userPageInfo = new PageInfo<>(goodsDOList);
        long total = userPageInfo.getTotal();

        map.put("total",total);
        map.put("items",goodsDOList);

        return map;
    }



    /**
     * 商品编辑页的商品介绍显示   (查看商品详情待改进)
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> queryGoodsDetail(Integer id) {
        HashMap<String, Object> map = new HashMap<>();

        //获取"categoryIds": [1013001, 1013002]

        GoodsDOExample goodsDOExample = new GoodsDOExample();
        goodsDOExample.createCriteria().andIdEqualTo(id);
        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExampleWithBLOBs(goodsDOExample);
        GoodsDO goodsDO =goodsDOList.get(0);
        //GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(id);  //通过goods_id获取一个GoodsDO对象

        CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());// 通过Category_id获取CategoryDO对象

        List<Object> categoryIds = new ArrayList<>();
        categoryIds.add(categoryDO.getPid());
        categoryIds.add(categoryDO.getId());
        map.put("categoryIds",categoryIds);
        map.put("goods",goodsDO);

        //获取attributes
        GoodsAttributeDOExample goodsAttributeDOExample = new GoodsAttributeDOExample();
        goodsAttributeDOExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);//只获取该商品的attributes表里delete属性为fales的attribute
        List<GoodsAttributeDO> attributes = goodsAttributeDOMapper.selectByExample(goodsAttributeDOExample);
        map.put("attributes",attributes);

        //获取specifications
        GoodsSpecificationDOExample goodsSpecificationDOExample = new GoodsSpecificationDOExample();
        goodsSpecificationDOExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);//只获取该商品的specifications表里delete属性为fales的specification
        List<GoodsSpecificationDO> specifications = goodsSpecificationDOMapper.selectByExample(goodsSpecificationDOExample);
        map.put("specifications",specifications);

        //获取products
        GoodsProductDOExample goodsProductDOExample = new GoodsProductDOExample();
        goodsProductDOExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);//只获取该商品的products表里delete属性为fales的product
        List<GoodsProductDO> products = goodsProductDOMapper.selectByExample(goodsProductDOExample);
        map.put("products",products);

        return map;
    }



    /**
     * 商品介绍页获取全部分类和品牌商categoryList
     * @return
     */
    @Override
    public HashMap<String, Object> queryGoodsCatAndBrandList() {
        HashMap<String, Object> map1 = new HashMap<>();

        //封装categoryList
        CategoryDOExample categoryDOExample1 = new CategoryDOExample();
        List<Object> categoryList = new ArrayList<>();

        //查出所有父类list
        categoryDOExample1.createCriteria().andPidEqualTo(0).andDeletedEqualTo(false);//只获取分类category数据表中delete属性为false的分类
        List<CategoryDO> categoryDOList1 = categoryDOMapper.selectByExample(categoryDOExample1);
        for (CategoryDO categoryDO : categoryDOList1) {
            HashMap<String, Object> map2 = new HashMap<>();

            //查询某父类目下所有子类list
            CategoryDOExample categoryDOExample =new CategoryDOExample();
            categoryDOExample.createCriteria().andPidEqualTo(categoryDO.getId()).andDeletedEqualTo(false);//只获取分类category数据表中delete属性为false的分类
            List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);

            //子类封装进children的List
            List<Object> children = new ArrayList<>();
            for (CategoryDO aDo : categoryDOList) {
                HashMap<String, Object> map3 = new HashMap<>();
                map3.put("value",aDo.getId());
                map3.put("label",aDo.getName());
                children.add(map3);
            }

            map2.put("value",categoryDO.getId());
            map2.put("label",categoryDO.getName());
            map2.put("children",children);

            categoryList.add(map2);
        }
        map1.put("categoryList",categoryList);



        //封装brandList
        BrandDOExample brandDOExample = new BrandDOExample();
        brandDOExample.createCriteria().andDeletedEqualTo(false);//只获取品牌brandList数据表中delete属性为false的品牌
        List<BrandDO> brandDOList = brandDOMapper.selectByExample(brandDOExample);
        List<Object> brandList = new ArrayList<>();
        for (BrandDO brandDO : brandDOList) {
            HashMap<String, Object> map4 = new HashMap<>();
            map4.put("value",brandDO.getId());
            map4.put("label",brandDO.getName());
            brandList.add(map4);
        }
        map1.put("brandList",brandList);

        return map1;
    }



    /**
     * 删除商品  待改进(需不需要连带删除绑定了goods_id的商品规格，参数，库存这三个表对应的数据)
     * @return
     */
    @Override
    public int goodsDelete(GoodsDO goodsDO) {
        goodsDO.setDeleted(true);
        return goodsDOMapper.updateByPrimaryKey(goodsDO);
    }


    /**
     * 获取所有商品评论
     * @param page
     * @param limit
     * @param userIdInt
     * @param valueIdInt
     * @param sort
     * @param order
     * @return
     */
    @Override
    public HashMap<String, Object> queryCommentList(Integer page, Integer limit, Integer userIdInt, Integer valueIdInt, String sort, String order) {
        Integer userId=null;
        userId=userIdInt;
        Integer valueId =null;
        valueId=valueIdInt;

        CommentDOExample commentDOExample = new CommentDOExample();
        PageHelper.startPage(page,limit);

        if (userId!=null && valueId!=null){
            commentDOExample.createCriteria().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andDeletedEqualTo(false);
        }else if (userId==null && valueId!=null){
            commentDOExample.createCriteria().andValueIdEqualTo(valueId).andDeletedEqualTo(false);
        }else if (userId!=null){
            commentDOExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        }else {
            commentDOExample.createCriteria().andDeletedEqualTo(false);
        }

        commentDOExample.setOrderByClause(sort+" "+order);
        List<CommentDO> commentDOList = commentDOMapper.selectByExample(commentDOExample);
        PageInfo<CommentDO> commentDOPageInfo = new PageInfo<>(commentDOList);
        long total = commentDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",commentDOList);
        return map;
    }



    /**
     * 删除单个商品评论
     * @return
     */
    @Override
    public BaseReqVo deleteComment(CommentDO commentDO) {
        commentDO.setDeleted(true);
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria().andIdEqualTo(commentDO.getId());
        commentDOMapper.updateByPrimaryKeySelective(commentDO);
        return new BaseReqVo<>(null,"成功",0);
    }



    /**
     * 上架创建商品
     * @param goodsCreateRequest 封装了request的四个json
     * @return
     */
    @Override
    public int goodsCreate(GoodsCreateRequest goodsCreateRequest) {

        //取出四个json
        GoodsDO goods = goodsCreateRequest.getGoods();
        List<GoodsSpecificationDO> specifications = goodsCreateRequest.getSpecifications();
        List<GoodsProductDO> products = goodsCreateRequest.getProducts();
        List<GoodsAttributeDO> attributes = goodsCreateRequest.getAttributes();

        //insert goods对象
        goods.setAddTime(new Date());  //刚创建时需要添加add_time
        goods.setDeleted(false);       //刚创建时需要添加delete为false
        int insertGoodsNum = goodsDOMapper.insert(goods);


        GoodsDOExample goodsDOExample = new GoodsDOExample();
        goodsDOExample.createCriteria().andGoodsSnEqualTo(goods.getGoodsSn());
        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);//重新获取一个刚刚插入的goods对象，以便得到其goods_id
        Integer goodsId=null;
        for (GoodsDO goodsDO : goodsDOList) {
            goodsId=goodsDO.getId();
        }

        //insert specification对象
        int insertSpecificationNum=0;
        for (GoodsSpecificationDO specification : specifications) {
            specification.setGoodsId(goodsId);  //对应商品对象的表的id (goods_id)
            specification.setAddTime(new Date());
            specification.setDeleted(false);   //刚创建时需要添加delete为false
            int insertSpecificationNum1 = goodsSpecificationDOMapper.insert(specification);
            insertSpecificationNum=insertSpecificationNum+insertSpecificationNum1;
        }


        //insert product对象
        int insertProductNum=0;
        for (GoodsProductDO product : products) {
            product.setGoodsId(goodsId);//对应商品对象的表的id (goods_id)
            product.setAddTime(new Date());
            product.setDeleted(false);    //刚创建时需要添加delete为false
            int insertProductNum1 = goodsProductDOMapper.insert(product);
            insertProductNum=insertProductNum+insertProductNum1;
        }

        //insert attribute对象
        int insertAttributeNum=0;
        for (GoodsAttributeDO attribute : attributes) {
            attribute.setGoodsId(goodsId);//对应商品对象的表的id (goods_id)
            attribute.setAddTime(new Date());
            attribute.setDeleted(false);  //刚创建时需要添加delete为false
            int insertAttributeNum1 = goodsAttributeDOMapper.insert(attribute);
            insertAttributeNum=insertAttributeNum+insertAttributeNum1;
        }
        return insertGoodsNum;
    }



    /**
     * 编辑更新商品
     * @param goodsCreateRequest 封装了request的四个json
     * @return
     */
    @Override
    public int goodsUpdate(GoodsCreateRequest goodsCreateRequest) {
        //取出四个json
        GoodsDO goods = goodsCreateRequest.getGoods();
        List<GoodsSpecificationDO> specifications = goodsCreateRequest.getSpecifications();
        List<GoodsProductDO> products = goodsCreateRequest.getProducts();
        List<GoodsAttributeDO> attributes = goodsCreateRequest.getAttributes();

        //update goods对象
        goods.setUpdateTime(new Date());  //编辑商品时需要更新update_time
        int updateGoodsNum = goodsDOMapper.updateByPrimaryKey(goods);

        //逻辑上delete旧的specification属性(把新的specifications里没有的specification属性在数据表里delete)
        GoodsSpecificationDOExample goodsSpecificationDOExample = new GoodsSpecificationDOExample();
        goodsSpecificationDOExample.createCriteria().andGoodsIdEqualTo(goods.getId());
        List<GoodsSpecificationDO> goodsSpecificationDoOld = goodsSpecificationDOMapper.selectByExample(goodsSpecificationDOExample);//获取原先该商品所有specification属性
        for (GoodsSpecificationDO specificationOld : goodsSpecificationDoOld) {
            //把所有的delete属性改为true,后续如果有update的在下一步会改回false
            specificationOld.setDeleted(true);
            int i = goodsSpecificationDOMapper.updateByPrimaryKey(specificationOld);
        }

        //update specification对象
        for (GoodsSpecificationDO specification : specifications) {
            if (specification.getId()==null) {  //说明这是新加的specification属性，需要insert到数据表里
                specification.setGoodsId(goods.getId());  //对应商品对象的表的id (goods_id)
                specification.setAddTime(new Date());
                specification.setDeleted(false);   //刚创建specification对象时需要添加delete为false
                int insertSpecificationNum1 = goodsSpecificationDOMapper.insert(specification);
            }else {
                //specification.getId()!=null   说明这是旧的的specification属性，需要update到数据表里
                specification.setUpdateTime(new Date());  //update编辑商品specification对象时需要更新update_time
                int updateSpecificationNum1 = goodsSpecificationDOMapper.updateByPrimaryKey(specification);
            }
        }

        //update product对象
        int updateProductNum=0;

        for (GoodsProductDO product : products) {

            //updateGoods时，只要有规格增加或者删除变动,并且不设置product库存就提交更新，则会request中有id=0,表示应该将原有的product都delete为true
            //再次查询product必须新建一行数据变动
            //只要id=0,旧的product必须全部delete为true,再新建insert新的product对象与good绑定
            if (product.getId()==0){
                product.setId(null);//request过来id=0,但是我要insert数据表里，使id自增长，所有把id=null,后面用insertSelective时，如果判断id=null,就不会使用到id这个属性去insert
                GoodsProductDOExample goodsProductDOExample = new GoodsProductDOExample();
                goodsProductDOExample.createCriteria().andGoodsIdEqualTo(goods.getId()).andDeletedEqualTo(false);
                List<GoodsProductDO> goodsProductDoOld = goodsProductDOMapper.selectByExample(goodsProductDOExample);//获取所有旧的product对象List
                for (GoodsProductDO goodsProductDO : goodsProductDoOld) {//表示将原product数据表所有对象的delete为true
                    goodsProductDO.setDeleted(true);
                    int i2 = goodsProductDOMapper.updateByPrimaryKey(goodsProductDO);
                }
                //再新建insert新的product对象与good绑定
                product.setGoodsId(goods.getId());//对应商品对象的表的id (goods_id)
                product.setAddTime(new Date());
                product.setDeleted(false);    //刚创建时需要添加delete为false
                int insertProductNum1 = goodsProductDOMapper.insertSelective(product);//用insertSelective时，如果判断id=null,就不会使用到id这个属性去insert
            }else {
                //product.getId()!=0,则只需要update
                product.setUpdateTime(new Date());  //编辑商品product对象时需要更新update_time
                int updateProductNum1 = goodsProductDOMapper.updateByPrimaryKey(product);
                updateProductNum=updateProductNum+updateProductNum1;
            }
        }



        //逻辑上delete旧的attribute属性(把新的attributes里没有的attribute属性在数据表里delete)
        GoodsAttributeDOExample goodsAttributeDOExample = new GoodsAttributeDOExample();
        goodsAttributeDOExample.createCriteria().andGoodsIdEqualTo(goods.getId());
        List<GoodsAttributeDO> goodsAttributeDoOld = goodsAttributeDOMapper.selectByExample(goodsAttributeDOExample);//获取原先该商品所有attribute属性对象List
        for (GoodsAttributeDO goodsAttributeDO : goodsAttributeDoOld) {
            //把所有的delete属性改为true,后续如果有update的在下一步会改回false
            goodsAttributeDO.setDeleted(true);
            int i = goodsAttributeDOMapper.updateByPrimaryKey(goodsAttributeDO);
        }

        //update attribute对象
        int updateAttributeNum=0;
        for (GoodsAttributeDO attribute : attributes) {
            if (attribute.getGoodsId()==null){      //说明这是新加的attribute对象属性，需要insert到数据表里
                attribute.setGoodsId(goods.getId());//对应商品对象的表的id (goods_id)
                attribute.setAddTime(new Date());
                attribute.setDeleted(false);  //刚创建时需要添加delete为false
                int insertAttributeNum1 = goodsAttributeDOMapper.insert(attribute);
            }else {
                //attribute.getGoodsId()!=null说明这是旧的的attribute属性，需要update到数据表里
                attribute.setUpdateTime(new Date());  //编辑商品attribute对象时需要更新update_time
                int updateAttributeNum1 = goodsAttributeDOMapper.updateByPrimaryKey(attribute);
            }
        }
        return updateGoodsNum;
    }
}
