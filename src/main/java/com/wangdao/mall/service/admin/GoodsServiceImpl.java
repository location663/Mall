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
import com.wangdao.mall.mapper.BrandDOMapper;
import com.wangdao.mall.mapper.CategoryDOMapper;
import com.wangdao.mall.mapper.GoodsDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Autowired
    BrandDOMapper brandDOMapper;

    @Override
    public HashMap<String, Object> queryGoodsList(Integer page, Integer limit,Integer goodsSn,String name, String sort, String order) {
        GoodsDOExample goodsDOExample = new GoodsDOExample();
        HashMap<String, Object> map = new HashMap<>();

        PageHelper.startPage(page,limit);

        if (goodsSn!=null && name!=null){
            goodsDOExample.createCriteria().andGoodsSnLike("%"+ goodsSn +"%").andNameLike("%"+ name +"%");
        }else if (goodsSn==null && name!=null){
            goodsDOExample.createCriteria().andNameLike("%"+ name +"%");
        }else if (goodsSn!=null && name==null){
            goodsDOExample.createCriteria().andGoodsSnLike("%"+ goodsSn +"%");
        }
            goodsDOExample.createCriteria();


        goodsDOExample.setOrderByClause(sort+" "+order);

        List<GoodsDO> goodsDOList = goodsDOMapper.selectByExample(goodsDOExample);

        PageInfo<GoodsDO> userPageInfo = new PageInfo<>(goodsDOList);
        long total = userPageInfo.getTotal();

        map.put("total",total);
        map.put("items",goodsDOList);

        return map;
    }

    @Override
    public HashMap<String, Object> queryGoodsDetail(Integer id) {
        HashMap<String, Object> map1 = new HashMap<>();





        return null;
    }

    @Override
    public HashMap<String, Object> queryGoodsCatAndBrandList() {
        HashMap<String, Object> map1 = new HashMap<>();



        //封装categoryList
        CategoryDOExample categoryDOExample1 = new CategoryDOExample();
        List<Object> categoryList = new ArrayList<>();

        //查出所有父类list
        categoryDOExample1.createCriteria().andPidEqualTo(0);
        List<CategoryDO> categoryDOList1 = categoryDOMapper.selectByExample(categoryDOExample1);
        for (CategoryDO categoryDO : categoryDOList1) {
            HashMap<String, Object> map2 = new HashMap<>();

            //查询某父类目下所有子类list
            CategoryDOExample categoryDOExample =new CategoryDOExample();
            categoryDOExample.createCriteria().andPidEqualTo(categoryDO.getId());
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
        brandDOExample.createCriteria();
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
}
