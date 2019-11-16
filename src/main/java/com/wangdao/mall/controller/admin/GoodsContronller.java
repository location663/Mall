/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/15
 * Time  下午 4:21
 */

package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
     * (也是商品上架初始页)
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

    /**
     * 获取所有商品评论
     * @return
     */
    @RequestMapping("comment/list")
    public BaseReqVo commentList(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order){
        BaseReqVo baseReqVo = goodsService.queryCommentList(page,limit,userId,valueId,sort,order);
        return baseReqVo;
    }

    /**
     * 删除单个商品评论
     */
    @RequestMapping("comment/delete")
    public BaseReqVo commentDelete(@RequestBody CommentDO commentDO){
        BaseReqVo baseReqVo = goodsService.deleteComment(commentDO);
        return baseReqVo;
    }

    /**
     * 删除商品
     * @param goodsDO
     * @return
     */
    @RequestMapping("goods/delete")
    public BaseReqVo goodsDelete(@RequestBody GoodsDO goodsDO){

        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        int i=0;
        i=goodsService.goodsDelete(goodsDO);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }

    /**
     * 上架创建商品
     * @param goodsCreateRequest  封装了request的四个json
     * @return
     */
    @RequestMapping("goods/create")
    public BaseReqVo goodsCreate(@RequestBody GoodsCreateRequest goodsCreateRequest){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        Integer i=0;
        i=goodsService.goodsCreate(goodsCreateRequest);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }

    /**
     * 编辑更新商品
     * @param goodsCreateRequest  封装了request的四个json
     * @return
     */
    @RequestMapping("goods/update")
    public BaseReqVo goodsUpdate(@RequestBody GoodsCreateRequest goodsCreateRequest){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();

        int i=0;
        i=goodsService.goodsUpdate(goodsCreateRequest);

        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        return baseReqVo;
    }


}
