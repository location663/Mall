package com.wangdao.mall.service.admin;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.GoodsCreateRequest;
import com.wangdao.mall.bean.GoodsDO;

import java.util.HashMap;
import java.util.List;

public interface GoodsService {


    HashMap<String, Object> queryGoodsList(Integer page, Integer limit, Integer goodsSnInt,String name,String sort, String order);

    HashMap<String, Object> queryGoodsDetail(Integer id);

    HashMap<String, Object> queryGoodsCatAndBrandList();

    HashMap<String, Object> queryCommentList(Integer page, Integer limit, Integer userIdInt, Integer valueIdInt, String sort, String order);

    BaseReqVo deleteComment(CommentDO commentDO);

    int goodsDelete(GoodsDO goodsDO);

    int goodsCreate(GoodsCreateRequest goodsCreateRequest);

    int goodsUpdate(GoodsCreateRequest goodsCreateRequest);
}
