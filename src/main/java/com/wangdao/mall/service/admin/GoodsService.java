package com.wangdao.mall.service.admin;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.GoodsDO;

import java.util.HashMap;
import java.util.List;

public interface GoodsService {


    HashMap<String, Object> queryGoodsList(Integer page, Integer limit, Integer goodsSn,String name,String sort, String order);

    HashMap<String, Object> queryGoodsDetail(Integer id);

    HashMap<String, Object> queryGoodsCatAndBrandList();

    BaseReqVo queryCommentList(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order);

    BaseReqVo deleteComment(CommentDO commentDO);

    int goodsDelete(GoodsDO goodsDO);

}
