/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/18
 * Time  下午 7:53
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.GoodsDOExample;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
}
