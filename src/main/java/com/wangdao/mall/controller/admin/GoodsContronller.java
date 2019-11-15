/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/15
 * Time  下午 4:21
 */

package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.GoodsService;
import com.wangdao.mall.bean.GoodsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 商品管理
 */
@RestController
@RequestMapping("admin")
public class GoodsContronller {

    @Autowired
    GoodsService goodsService;

    /**
     * 商品列表
     * @return
     */
    @RequestMapping("goods/list")
    public BaseReqVo goodsList(Integer page,Integer limit,Integer goodsSn,String name,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        HashMap<String, Object> map = goodsService.queryGoodsList(page,limit,goodsSn,name,sort, order);

        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }

    /**
     * 商品编辑页的商品介绍
     * @return
     */
    @RequestMapping("goods/detail")
    public BaseReqVo goodsDetail(Integer id){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        HashMap<String, Object> map = goodsService.queryGoodsDetail(id);

        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }

    /**
     * 商品介绍页获取全部类目categoryList
     * @return
     */
    @RequestMapping("goods/catAndBrand")
    public BaseReqVo goodsCatAndBrand(){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        HashMap<String, Object> map = goodsService.queryGoodsCatAndBrandList();

        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }
}
