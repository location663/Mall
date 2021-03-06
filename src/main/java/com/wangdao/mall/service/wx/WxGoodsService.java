package com.wangdao.mall.service.wx;

import java.util.HashMap;

public interface WxGoodsService {
    HashMap<String, Object> queryGoodsCount();

    HashMap<String, Object> queryWxGoodsList(String keyword, Integer categoryId,Integer brandId,Boolean isNew,Boolean isHot,Integer page, Integer size, String sort, String order);

    HashMap<String, Object> queryWxGoodsCategory(Integer id);

    HashMap<String, Object> queryWxGoodsDetail(Integer id);

    HashMap<String, Object> queryWxGoodsRelated(Integer id);
}
