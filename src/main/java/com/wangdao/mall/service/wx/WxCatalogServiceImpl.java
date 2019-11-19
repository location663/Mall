/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/19
 * Time  下午 10:35
 */

package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CategoryDO;
import com.wangdao.mall.bean.CategoryDOExample;
import com.wangdao.mall.mapper.CategoryDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WxCatalogServiceImpl implements WxCatalogService{

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Override
    public HashMap<String, Object> queryCatalogIndex() {
        HashMap<String, Object> map = new HashMap<>();
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andDeletedEqualTo(false).andPidEqualTo(0);
        List<CategoryDO> categoryFatherList = categoryDOMapper.selectByExample(categoryDOExample);//获取所有未删除的父类

        //封装 categoryFatherList  当前页显示的父类目(好像默认是数据表里第一个父类)
        map.put("currentCategory",categoryFatherList.get(0));

        //封装所有父类 categoryList
        map.put("categoryList",categoryFatherList);

        //封装 currentSubCategory 当前父类的所有子类(默认数据表里第一个父类的所有子类)
        CategoryDOExample categoryDOExample1 = new CategoryDOExample();
        categoryDOExample1.createCriteria().andDeletedEqualTo(false).andPidEqualTo(categoryFatherList.get(0).getId());
        List<CategoryDO> categorySonList = categoryDOMapper.selectByExample(categoryDOExample1);
        map.put("currentSubCategory",categorySonList);
        return map;
    }
}
