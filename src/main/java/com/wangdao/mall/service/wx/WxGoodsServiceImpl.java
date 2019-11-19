/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/18
 * Time  下午 7:53
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.CategoryDO;
import com.wangdao.mall.bean.CategoryDOExample;
import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.GoodsDOExample;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
     * 获取(搜索某商品)(显示某类目下所有商品)商品列表
     * response里有desc关键字
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsList(String keyword,Integer categoryId, Integer brandId,boolean isNew,boolean isHot,Integer page, Integer size, String sort, String order) {
        GoodsDOExample goodsDOExample1 = new GoodsDOExample();
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        HashMap<String, Object> map = new HashMap<>();
        List<CategoryDO> filterCategoryList = new ArrayList<>();
        PageHelper.startPage(page,size);

        //封装goodsList和count
        if (keyword!=null ) {
            goodsDOExample1.createCriteria().andDeletedEqualTo(false).andNameLike("%" + keyword + "%");
            goodsDOExample1.setOrderByClause(sort+" "+order);
            List<GoodsDO> goodsDOList1 = goodsDOMapper.selectByExample(goodsDOExample1);

            if (categoryId==0){  //说明是搜索只 关键字 （综合）显示商品List
                PageInfo<GoodsDO> userPageInfo1 = new PageInfo<>(goodsDOList1);
                long total1 = userPageInfo1.getTotal();
                map.put("count",total1);
                map.put("goodsList",goodsDOList1);
            }else if (categoryId!=0){   //说明是搜索 关键字和和显示某分类下 （分类）显示商品List
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
        }else if (brandId==null && keyword==null){   //说明keyword=null,是通过categoryId显示某类下所有商品List   (待改进，返回的filterCategoryList存疑)
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
        }else if (brandId!=null){
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
        }

//        if (keyword!=null){
//            goodsDOExample1.setOrderByClause(sort+" "+order);
//            List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample1);
//            PageInfo<GoodsDO> userPageInfo = new PageInfo<>(goodsDOList);
//            long total = userPageInfo.getTotal();
//            map.put("count",total);
//            map.put("goodsList",goodsDOList);
//
//            //封装filterCategoryList
//            List<CategoryDO> categoryDOArrayList = new ArrayList<>();
//            for (GoodsDO goodsDO : goodsDOList) {
//                CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());
//                if (!categoryDO.getDeleted()){
//                    categoryDOArrayList.add(categoryDO);
//                }
//            }
//            //把 categoryDOArrayList 里的 Category 对象去重
//            List<CategoryDO> filterCategoryList = new ArrayList<>();
//            Set set = new HashSet();
//            for (CategoryDO categoryDO : categoryDOArrayList) {
//                if (set.add(categoryDO)) {
//                    filterCategoryList.add(categoryDO);
//                }
//            }
//        }
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
}
