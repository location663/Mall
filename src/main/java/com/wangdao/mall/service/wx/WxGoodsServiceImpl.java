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
     * 获取(搜索)商品列表
     * response里有desc关键字
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @Override
    public HashMap<String, Object> queryWxGoodsList(String keyword, Integer page, Integer size, String sort, String order) {
        GoodsDOExample goodsDOExample = new GoodsDOExample();
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        HashMap<String, Object> map = new HashMap<>();

        PageHelper.startPage(page,size);

        //封装goodsList和count
        goodsDOExample.createCriteria().andDeletedEqualTo(false).andNameLike("%"+ keyword +"%");
        goodsDOExample.setOrderByClause(sort+" "+order);
        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);
        PageInfo<GoodsDO> userPageInfo = new PageInfo<>(goodsDOList);
        long total = userPageInfo.getTotal();
        map.put("count",total);
        map.put("goodsList",goodsDOList);

        //封装filterCategoryList

        List<CategoryDO> categoryDOArrayList = new ArrayList<>();

        for (GoodsDO goodsDO : goodsDOList) {
            CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(goodsDO.getCategoryId());
            if (!categoryDO.getDeleted()){
                categoryDOArrayList.add(categoryDO);
            }
        }

        List<CategoryDO> filterCategoryList = new ArrayList<>();
        Set set = new HashSet();
        for (CategoryDO categoryDO : categoryDOArrayList) {
            if (set.add(categoryDO)) {
                filterCategoryList.add(categoryDO);
            }
        }

        map.put("filterCategoryList",filterCategoryList);

        return map;
    }
}
